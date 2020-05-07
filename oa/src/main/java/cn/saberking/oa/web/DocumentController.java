package cn.saberking.oa.web;

import cn.saberking.oa.domain.Document;
import cn.saberking.oa.domain.User;
import cn.saberking.oa.service.DocumentService;
import cn.saberking.oa.util.common.HrmConstants;
import cn.saberking.oa.vo.DocQuery;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/9
 * @Description:cn.saberking.oa.web 处理文档请求控制器
 * @version:1.0
 */
@Controller
@RequestMapping("hrm")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    /**
     * 第一次访问页面，默认显示所有数据
     *
     * @param pageable 分页对象
     * @param document 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/documents")
    public String documents(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                    Pageable pageable, DocQuery document, Model model) {
        //查询文档信息
        model.addAttribute("page", documentService.findAll(document, pageable));
        return "documents";
    }

    /**
     * 处理查询请求
     *
     * @param pageable 分页对象
     * @param document 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/documents/search")
    public String selectDocument(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                         Pageable pageable, DocQuery document, Model model) {
        System.out.println("document = " + document);
        //查询文档信息
        model.addAttribute("page", documentService.findAll(document, pageable));
        return "documents :: documentList";

    }

    /**
     * 处理删除文档请求
     *
     * @param pageable 分页对象
     * @param docQuery 模糊查询参数
     * @param model    向页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping(value = "/documents/delete")
    public String batchDelDoc(@PageableDefault(size = HrmConstants.PAGE_DEFAULT_SIZE, sort = {"createDate"}, direction = Sort.Direction.DESC)
                                          Pageable pageable,
                              DocQuery docQuery,
                              HttpSession session,
                              Model model) {
        if (StringUtils.isNotBlank(docQuery.getDocIds())){
            List<Document> documentList = documentService.findAllByIdIn(docQuery.getDocIds());
            // 上传文件路径
            String path = session.getServletContext().getRealPath(
                    "/upload/");
            for (Document document : documentList) {
                File file = new File(path + File.separator + document.getFileName());
                if (file.exists()) {
                    boolean delete = file.delete();
                    if (!delete) {
                        model.addAttribute("error", "出问题了请稍后再尝试一下！");
                        return "documents :: documentList";
                    }
                }
            }
            documentService.batchDelById(docQuery.getDocIds());
        }
        //查询文档信息
        model.addAttribute("page", documentService.findAll(docQuery, pageable));
        return "documents :: documentList";
    }

    /**
     * 路由新增请求的页面
     *
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/documents/input")
    public String input(Model model) {
        initialDocument(new Document(), model);
        return "documents-input";
    }

    private void initialDocument(Document document, Model model) {
        model.addAttribute("document", document);
    }

    /**
     * 处理修改文档请求
     *
     * @param id    修改的文档id
     * @param model 向页面封装数据的对象
     * @return 返回页面
     */
    @GetMapping("/documents/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        Document document = documentService.findById(id);
        initialDocument(document, model);
        return "documents-input";
    }

    /**
     * 处理添加或修改完之后的提交请求
     *
     * @param document   新增或修改后的文档信息
     * @param attributes 向重定向后的页面封装数据的对象
     * @return 返回页面
     */
    @PostMapping("/documents")
    public String post(
            Document document,
            @RequestParam String oldFileName,
            BindingResult result,
            RedirectAttributes attributes,
            HttpSession session) {
        Document e;
        // 上传文件路径
        String path = session.getServletContext().getRealPath(
                "/upload/");
        // 创建上传文件夹
        try {
            FileUtils.forceMkdir(new File(path));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        // 上传文件名
        String fileName = document.getFile().getOriginalFilename();
        // 获取后缀名
        if (StringUtils.isNotBlank(fileName)){
            String suffixName = fileName.substring(fileName.lastIndexOf("."));
            boolean fileAllowed = isFileAllowed(suffixName);
            if (!fileAllowed) {
                result.rejectValue("fileNameType", "fileNameTypeError", "上传的文件格式不正确！");
                return "documents-input";
            }
            // 将上传文件保存到一个目标文件当中
            try {
                document.getFile().transferTo(new File(path + File.separator + fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // 插入数据库
            // 设置fileName
            document.setFileName(fileName);
            // 设置关联的User对象
            User user = (User) session.getAttribute(HrmConstants.USER_SESSION);
            document.setUser(user);
            if (document.getId() == null) {
                if (verifyRepeat(document, null, result)) return "documents-input";
                e = documentService.save(document);
            } else {
                if (verifyRepeat(document, oldFileName, result)) return "documents-input";
                e = documentService.update(document.getId(), document);
            }
            if (e == null) {
                attributes.addFlashAttribute("message", "操作失败");
            } else {
                attributes.addFlashAttribute("message", "操作成功");
            }
        }
        return "redirect:/hrm/documents";
    }

    public static String[] IMAGE_FILE_EXT = new String[] { ".zip", ".rar", ".7z", ".txt", ".doc", ".xls", ".ppt", ".docx", ".xlsx", ".pptx", ".pdf" };
    //判断文件格式是否符合要求
    public static boolean isFileAllowed(String extName) {
        for (String ext : IMAGE_FILE_EXT) {
            if (ext.equals(extName)) {
                return true;
            }
        }
        return false;
    }

    private boolean verifyRepeat(Document document, String oldFileName, BindingResult result) {
        boolean ret = false;
        Document dbDocument = documentService.findByFileName(document.getFileName());
        if (oldFileName == null) {
            if (dbDocument != null) {
                ret = true;
            }
        } else {
            if (dbDocument != null && !oldFileName.equals(document.getFileName())) {
                ret = true;
            }
        }
        if (ret) {
            result.rejectValue("fileName", "fileNameError", "该名称的文件已存在，不能重复添加！");
        }
        return ret;
    }

    @RequestMapping(value="/documents/downLoad")
    public String downLoad(
            Long id,
            HttpSession session,
            RedirectAttributes attributes,
            HttpServletResponse response) {
        // 根据id查询文档
        Document target = documentService.findById(id);
        String fileName = target.getFileName();
        // 上传文件路径
        String path = session.getServletContext().getRealPath(
                "/upload/");
        // 获得要下载文件的File对象
        File file = new File(path + File.separator + fileName);
        if(file.exists()){ //判断文件父目录是否存在
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // response.setContentType("application/force-download");
            response.setHeader("Content-Disposition", "attachment;fileName=" +   java.net.URLEncoder.encode(fileName, StandardCharsets.UTF_8));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;

            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while(i != -1){
                    os.write(buffer);
                    i = bis.read(buffer);
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (fis != null) {
                        fis.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("----------file download---" + fileName);
            return null;
        }else {
            attributes.addFlashAttribute("error", "您要下载的文件不存在了，换一个吧？");
            return "redirect:/hrm/documents";
        }
    }

}
