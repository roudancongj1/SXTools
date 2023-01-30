package com.sics.sxt.utils;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.sics.sxt.common.ObjListener;
import com.sics.sxt.pojo.vo.ER;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Excel工具类 2007+
 * @Auth: Gao
 * */
public class ExcelUtil {

    public static List<List<Object>> readExcel(File localFile) throws Exception {
        if(!localFile.exists()){
            throw new Exception("Excel file is null");
        }
        return readPOIExcel(new FileInputStream(localFile));
    }

    public static List<List<Object>>  readExcel(InputStream localFileInputStream) throws Exception {
        if(null == localFileInputStream){
            throw new Exception("InputStream is null");
        }
        return readPOIExcel(localFileInputStream);
    }

    /**
     * 导出excel
     * @param excel_name 导出的excel路径（需要带.xlsx)
     * @param headList  excel的标题备注名称
     * @param fieldList excel的标题字段（与数据中map中键值对应）
     * @param dataList  excel数据
     */
    public static void createExcel(String excel_name, String[] headList,
                                   String[] fieldList, List<Map<String, Object>> dataList)
            throws Exception {
        // 创建新的Excel 工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 在Excel工作簿中建一工作表，其名为缺省值
        XSSFSheet sheet = workbook.createSheet();
        // 在索引0的位置创建行（最顶端的行）
        XSSFRow row = sheet.createRow(0);
        // 设置excel头（第一行）的头名称
        for (int i = 0; i < headList.length; i++) {

            // 在索引0的位置创建单元格（左上端）
            XSSFCell cell = row.createCell(i);
            // 定义单元格为字符串类型
            cell.setCellType(CellType.STRING);
            // 在单元格中输入一些内容
            cell.setCellValue(headList[i]);
        }
        // ===============================================================
        //添加数据
        for (int n = 0; n < dataList.size(); n++) {
            // 在索引1的位置创建行（最顶端的行）
            XSSFRow row_value = sheet.createRow(n + 1);
            Map<String, Object> dataMap = dataList.get(n);
            // ===============================================================
            for (int i = 0; i < fieldList.length; i++) {

                // 在索引0的位置创建单元格（左上端）
                XSSFCell cell = row_value.createCell(i);
                // 定义单元格为字符串类型
                cell.setCellType(CellType.STRING);
                // 在单元格中输入一些内容
                cell.setCellValue((dataMap.get(fieldList[i])).toString());
            }
            // ===============================================================
        }
        // 新建一输出文件流
        FileOutputStream fos = new FileOutputStream(excel_name);
        // 把相应的Excel 工作簿存盘
        workbook.write(fos);
        fos.flush();
        // 操作结束，关闭文件
        fos.close();
    }

    private static List<List<Object>> readPOIExcel(InputStream inputStream) throws IOException {
        List<List<Object>> list = new LinkedList<>();
        XSSFWorkbook xwb = new XSSFWorkbook(inputStream);
        // 读取第一张表格内容
        XSSFSheet sheet = xwb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;
        for (int i = (sheet.getFirstRowNum() + 1); i <= (sheet.getPhysicalNumberOfRows() - 1); i++) {
            row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            List<Object> linked = new LinkedList<>();
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
                Object value = null;
                cell = row.getCell(j);
                if (cell == null) {
                    continue;
                }
                switch (cell.getCellType()) {
                    case STRING:
                        //String类型返回String数据
                        value = cell.getStringCellValue();
                        break;
                    case NUMERIC:
                        //日期数据返回LONG类型的时间戳
                        if ("yyyy\"年\"m\"月\"d\"日\";@".equals(cell.getCellStyle().getDataFormatString())) {
                            //System.out.println(cell.getNumericCellValue()+":日期格式："+cell.getCellStyle().getDataFormatString());
                            value = ""; // 1000;
                        } else {
                            //数值类型返回double类型的数字
                            //System.out.println(cell.getNumericCellValue()+":格式："+cell.getCellStyle().getDataFormatString());
                            value = cell.getNumericCellValue();
                        }
                        break;
                    case BOOLEAN:
                        //布尔类型
                        value = cell.getBooleanCellValue();
                        break;
                    case BLANK:
                        //空单元格
                        break;
                    default:
                        value = cell.toString();
                }
                if (value != null && !value.equals("")) {
                    //单元格不为空，则加入列表
                    linked.add(value);
                }
            }
            if (linked.size()!= 0) {
                list.add(linked);
            }
        }
        return list;
    }

    public static List<Object> readExcelObj(MultipartFile webFile, Class<?> clazz) throws Exception {
        if (webFile.isEmpty()){
            throw new Exception("Excel file is null");
        }
        return readEasyExcel(webFile.getInputStream(),clazz);
    }

    /**
     * E e = (E)Object
     * */
    private static List<Object> readEasyExcel(InputStream inputStream, Class<?> clazz) throws Exception {
        ObjListener<T> listener = new ObjListener<>();
        if (null == inputStream){
            throw new Exception("InputStream is null");
        }
        EasyExcel.read(inputStream,clazz,listener).sheet().doRead();
        return listener.getList();

    }

    public static void whiteERList(List<ER> erList) throws IOException {
        String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        white(ER.class,erList,("Result-"+now));
    }

    public static void whiteTemplate(Class<?> clazz) throws IOException {
        white(clazz,new ArrayList<>(),(clazz.getSimpleName()+"Template"));
    }

    public static void white(Class<?> clazz,List<?> list,String filename) throws IOException {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        assert attributes.getResponse() != null;
        HttpServletResponse response = attributes.getResponse();
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(filename, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");


        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setColor(IndexedColors.WHITE.getIndex());

        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setBorderBottom(BorderStyle.NONE);
        headWriteCellStyle.setBorderLeft(BorderStyle.NONE);
        headWriteCellStyle.setBorderRight(BorderStyle.NONE);
        headWriteCellStyle.setBorderTop(BorderStyle.NONE);
        headWriteCellStyle.setFillBackgroundColor(IndexedColors.WHITE.getIndex());
        headWriteCellStyle.setWriteFont(headWriteFont);

        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setBorderBottom(BorderStyle.NONE);
        contentWriteCellStyle.setBorderLeft(BorderStyle.NONE);
        contentWriteCellStyle.setBorderRight(BorderStyle.NONE);
        contentWriteCellStyle.setBorderTop(BorderStyle.NONE);

        EasyExcel.write(response.getOutputStream(),clazz).sheet()
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerWriteHandler(new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle))
                .doWrite(list);
    }
}
