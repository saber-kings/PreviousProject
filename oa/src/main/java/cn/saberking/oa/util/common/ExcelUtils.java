package cn.saberking.oa.util.common;

import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Auther:luanzhaofei@outlook.com
 * @Date:2019/11/9
 * @Description:cn.saberking.oa.schedule
 * @version:1.0
 */
public class ExcelUtils {

    public static Object getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        Object obj = null;
        switch (cell.getCellType()) {
            case BOOLEAN:
                obj = cell.getBooleanCellValue();
                break;
            case ERROR:
                obj = cell.getErrorCellValue();
                break;
            case FORMULA:
                try {
                    obj = String.valueOf(cell.getStringCellValue());
                } catch (IllegalStateException e) {
                    String valueOf = String.valueOf(cell.getNumericCellValue());
                    BigDecimal bd = new BigDecimal(Double.valueOf(valueOf));
                    bd = bd.setScale(2, RoundingMode.HALF_UP);
                    obj = bd;
                }
                break;
            case NUMERIC:
                obj = cell.getNumericCellValue();
                break;
            case STRING:
                String value = String.valueOf(cell.getStringCellValue());
                value = value.replace(" ", "");
                value = value.replace("\n", "");
                value = value.replace("\t", "");
                obj = value;
                break;
            default:
                break;
        }
        return obj;
    }
}
