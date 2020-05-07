package cn.saberking.oa.web;

import cn.saberking.oa.domain.Department;
import cn.saberking.oa.domain.Employee;
import cn.saberking.oa.domain.Job;
import cn.saberking.oa.service.DepartmentService;
import cn.saberking.oa.service.EmployeeService;
import cn.saberking.oa.service.JobService;
import cn.saberking.oa.util.common.ExcelUtils;
import cn.saberking.oa.vo.EmpQuery;
import cn.saberking.oa.vo.FileVo;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/12/5
 * @Description:cn.saberking.oa.web
 * @version:1.0
 */
@Controller
@RequestMapping("hrm")
public class ExcelController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private JobService jobService;

    /**
     * 处理查询请求
     *
     * @param pageable 分页对象
     * @param empQuery 模糊查询参数
     * @param response HttpServletResponse对象向页面以流的方式输出文件
     * @return 返回页面
     */
    @PostMapping(value = "/employees/getExcel")
    public void getExcel(@PageableDefault(size = 100000, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                 Pageable pageable,
                         EmpQuery empQuery,
                         HttpServletResponse response) throws IOException {
        List<Employee> employees = employeeService.findAll(empQuery, pageable).getContent();
        String fileName = new SimpleDateFormat("yyyy-MM-dd").format(new Date()) + "_员工信息.xls";
        String[] headers = {"员工编号", "姓名", "部门", "职位", "性别", "年龄", "手机", "邮箱", "学历", "工资", "建档日期", "备注"};
        CreateExcel createExcel = new CreateExcel(response, fileName, headers).invoke();
        HSSFWorkbook workbook = createExcel.getWorkbook();
        HSSFSheet sheet = createExcel.getSheet();
        CellStyle cellStyle = workbook.createCellStyle();
        HSSFFont hssFont = workbook.createFont();
        setFont(cellStyle, hssFont, "宋体", (short) 12, Font.COLOR_NORMAL);

        //设置列宽和字体样式
        for (int i = 0; i < headers.length; i++) {
            sheet.setColumnWidth(i, 256 * 15);
            sheet.setDefaultColumnStyle(i, cellStyle);
        }

        //添加数据
        int rowNum = createExcel.getRowNum();
        for (Employee employee : employees) {
            rowNum++;
            HSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(employee.getEmpId());
            row.createCell(1).setCellValue(employee.getName());
            row.createCell(2).setCellValue(employee.getDepartment().getDepartName());
            row.createCell(3).setCellValue(employee.getJob().getName());
            row.createCell(4).setCellValue(employee.getSex() == 0 ? "女" : "男");
            row.createCell(5).setCellValue(employee.getAge());
            row.createCell(6).setCellValue(employee.getPhone());
            row.createCell(7).setCellValue(employee.getEmail());
            row.createCell(8).setCellValue(employee.getEducation());
            row.createCell(9).setCellValue(employee.getWage().doubleValue());
            row.createCell(10).setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(employee.getCreateDate()));
            row.createCell(11).setCellValue(employee.getRemark());
        }

        workbook.write(response.getOutputStream());

    }

    /**
     * 导入excel
     *
     * @param file：文件路径
     * @return 结果
     */
    @ResponseBody
    @RequestMapping(value = "/employees/importEmp", method = RequestMethod.POST)
    public Object importEmp(@RequestParam("file") MultipartFile file, HttpServletRequest request, Model model) {
        FileVo ret = new FileVo();
        int flag = 0;//flag是进度条的值
        int count = 0;
        int code = 0;
        StringJoiner buffer = new StringJoiner("\n");
        try {
            String filename = file.getOriginalFilename();
            if (filename != null) {
                InputStream inputStream = file.getInputStream();
                Workbook book = null;
                if (isExcel2003(filename)) {
                    book = new HSSFWorkbook(inputStream);
                }
                if (isExcel2007(filename)) {
                    book = new XSSFWorkbook(inputStream);
                }
                if (book != null) {
                    Sheet sheet = book.getSheetAt(0);
                    int allRowNum = sheet.getLastRowNum();
                    System.out.println(allRowNum);
                    if (allRowNum == 0) {
                        buffer.add("导入文件数据为空");
                    }
                    for (int i = 1; i <= allRowNum; i++) {
                        //加载状态值，当前进度
                        Employee employee = new Employee();//我需要插入的数据类型
                        Row row = sheet.getRow(i); //获取第i行
                        if (row != null) {
                            Cell cell1 = row.getCell(0); //获取第1个单元格的数据
                            Cell cell2 = row.getCell(1);
                            Cell cell3 = row.getCell(2);
                            Cell cell4 = row.getCell(3);
                            Cell cell5 = row.getCell(4);
                            Cell cell6 = row.getCell(5);
                            Cell cell7 = row.getCell(6);
                            Cell cell8 = row.getCell(7);
                            Cell cell9 = row.getCell(8);
                            Cell cell10 = row.getCell(9);
                            Cell cell11 = row.getCell(10);
                            if (cell1 != null) {//员工编号列验证
                                Employee dbEmp = employeeService.findByEmpId(cell1.getStringCellValue());
                                if (dbEmp==null){
                                    employee.setEmpId((String) ExcelUtils.getCellValue(cell1));
                                }else {
                                    buffer.add("第" + i + "行的第1列的员工编号已存在");
                                }
                            } else {
                                buffer.add("第" + i + "行的第1列的数据不能为空");
                            }
                            if (cell2 != null) {//姓名列验证
                                employee.setName((String) ExcelUtils.getCellValue(cell2));
                            } else {
                                buffer.add("第" + i + "行的第2列的数据不能为空");
                            }
                            if (cell3 != null) {//部门列验证
                                Department department = departmentService.findByName((String) ExcelUtils.getCellValue(cell3));
                                if (department != null) {
                                    employee.setDepartment(department);
                                }else {
                                    buffer.add("第" + i + "行的第3列的部门不存在");
                                }
                            } else {
                                buffer.add("第" + i + "行的第3列的数据不能为空");
                            }
                            if (cell4 != null) {//职位列验证
                                Job job  = jobService.findByName((String) ExcelUtils.getCellValue(cell4));
                                if (job != null) {
                                    employee.setJob(job);
                                }else {
                                    buffer.add("第" + i + "行的第4列的职位不存在");
                                }
                            } else {
                                buffer.add("第" + i + "行的第4列的数据不能为空");
                            }
                            if (cell5 != null) {//性别列验证
                                if (((String) ExcelUtils.getCellValue(cell5)).equals("女")){
                                    employee.setSex(0);
                                } else if (((String) ExcelUtils.getCellValue(cell5)).equals("男")) {
                                    employee.setSex(1);
                                } else {
                                    buffer.add("第" + i + "行的第5列的性别格式错误");
                                }
                            }
                            if (cell6!=null ){
                                Double cellValue = (double) ExcelUtils.getCellValue(cell6);
                                employee.setAge(cellValue.intValue());
                            }
                            if (cell7 != null) {//手机号码列验证
                                String verify = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17[013678])|(18[0,5-9]))\\d{8}$";
                                String phone = String.format("%.0f",ExcelUtils.getCellValue(cell7));
                                if (phone.length() != 11) {
                                    buffer.add("第" + i + "行的第7列的手机号码不复合要求11位");
                                } else {
                                    Pattern p = Pattern.compile(verify);
                                    Matcher m = p.matcher(phone);
                                    boolean isMatch = m.matches();
                                    if (isMatch) {
                                        employee.setPhone(phone);
                                    } else {
                                        buffer.add("第" + i + "行的第7列的手机号码格式错误");
                                    }
                                }

                            } else {
                                buffer.add("第" + i + "行的第7列的数据不能为空");
                            }
                            //邮箱列验证
                            if (cell8!=null) {
                                String email = (String) ExcelUtils.getCellValue(cell8);
                                String pattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
                                Pattern p = Pattern.compile(pattern);
                                Matcher m = p.matcher(email);
                                boolean isMatch = m.matches();
                                if (isMatch) {
                                    employee.setEmail(email);
                                } else {
                                    buffer.add("第" + i + "行的第8列的邮箱格式错误");
                                }
                            }
                            if (cell9 != null) {//学历列验证
                                employee.setEducation((String) ExcelUtils.getCellValue(cell9));
                            } else {
                                buffer.add("第" + i + "行的第9列的数据不能为空");
                            }
                            if (cell10 != null) {
                                employee.setWage(BigDecimal.valueOf((Double) ExcelUtils.getCellValue(cell10)));
                            } else {
                                buffer.add("第" + i + "行的第10列的数据不能为空");
                            }
                            if (cell11!=null){//备注列导入
                                employee.setRemark((String) ExcelUtils.getCellValue(cell11));
                            }
                            if(employee.getEmpId()!=null&&employee.getName()!=null&&employee.getDepartment()!=null&&employee.getJob()!=null&&employee.getAge()!=null&&employee.getPhone()!=null&&employee.getEducation()!=null&&employee.getWage()!=null) {
                                count++;
                                employeeService.save(employee);//保存到数据库
                            }
                        }
                    }
                    flag = (int)(((double)count / (double)allRowNum)*100);
                    ret.setFlag(flag);
                    ret.setCount("共计"+allRowNum+"条数据，导入成功"+count+"条数据，导入失败"+(allRowNum-count)+"条");
//                    model.addAttribute("count", "共计"+allRowNum+"条数据，导入成功"+count+"条数据，导入失败"+(allRowNum-count)+"条");
                    code = 1;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        ret.setCode(code);
        ret.setResult(buffer.toString());
//        model.addAttribute("code",code);
//        model.addAttribute("result",buffer.toString());
        return ret;
    }

    /***
     * 判断文件类型是不是2003版本
     * @return 结果
     */
    public static boolean isExcel2003(String filePath) {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    /**
     * 判断文件类型是不是2007版本
     *
     * @return 结果
     */
    public static boolean isExcel2007(String filePath) {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    @GetMapping("/employees/getImpDatXls")
    public void getImpDatXls(HttpServletResponse response) throws IOException {
        String fileName = "model.xls";
        String[] headers = {"员工编号", "姓名", "部门", "职位", "性别", "年龄", "手机", "邮箱", "学历", "工资", "备注"};
        String[] texts = {"000000", "小明", "神仙部", "天仙", "男", "1000(注：插入时使用数字类型)", "1234567890", "xiaoming@ShenXian.com", "本科", "100000(注：插入时使用数字类型)", "案例，使用时删除本行！！！"};
        CreateExcel createExcel = new CreateExcel(response, fileName, headers).invoke();
        HSSFWorkbook workbook = createExcel.getWorkbook();
        HSSFSheet sheet = createExcel.getSheet();
        CellStyle cellStyle = workbook.createCellStyle();
        HSSFFont hssFont = workbook.createFont();
        setFont(cellStyle, hssFont, "宋体", (short) 12, Font.COLOR_RED);
        int rowNum = createExcel.getRowNum();

        //添加数据
        HSSFRow row = sheet.createRow(++rowNum);

        for (int i = 0; i < texts.length; i++) {
            HSSFCell cell = row.createCell(i);
            HSSFRichTextString text = new HSSFRichTextString(texts[i]);
            cell.setCellValue(text);
            sheet.setColumnWidth(i, 256 * 15);
            cell.setCellStyle(cellStyle);
        }
        row.setRowStyle(cellStyle);

        workbook.write(response.getOutputStream());

    }

    private void setFont(CellStyle cellStyle, HSSFFont hssFont, String font, short fontSize, short color) {
        hssFont.setFontName(font);
        hssFont.setFontHeightInPoints(fontSize);
        hssFont.setColor(color);
        //设置样式
        cellStyle.setFont(hssFont);
    }

    private static class CreateExcel {
        private HttpServletResponse response;
        private String fileName;
        private String[] headers;
        private HSSFWorkbook workbook;
        private HSSFSheet sheet;

        private int rowNum;

        public CreateExcel(HttpServletResponse response, String fileName, String... headers) {
            this.response = response;
            this.fileName = fileName;
            this.headers = headers;
        }

        public HSSFWorkbook getWorkbook() {
            return workbook;
        }

        public HSSFSheet getSheet() {
            return sheet;
        }

        public int getRowNum() {
            return rowNum;
        }

        public CreateExcel invoke() {
            response.setContentType("application/excel");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ";filename*=utf-8''" + URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            workbook = new HSSFWorkbook();
            sheet = workbook.createSheet();
            //设置默认列宽
            rowNum = 0;

            //添加标题
            HSSFRow row = sheet.createRow(rowNum);

            for (int i = 0; i < headers.length; i++) {
                HSSFCell cell = row.createCell(i);
                HSSFRichTextString text = new HSSFRichTextString(headers[i]);
                cell.setCellValue(text);
                sheet.autoSizeColumn(i);
            }
            return this;
        }

    }
}
