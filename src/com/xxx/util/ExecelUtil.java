package com.xxx.util;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.hash.BeanUtilsHashMapper;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExecelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExecelUtil.class);
    
    public static final int EXECEL_TYPE_XLS = 1;

    public static final int EXECEL_TYPE_XLSX = 2;

    public static <T> List<T> read(InputStream inputStream, int fileType, Map<String, Class> columnMapper, Class clz, int rowOffset) throws Exception {
        Workbook wb = null;
        if (fileType == EXECEL_TYPE_XLS) {
            wb = new HSSFWorkbook(inputStream);
        } else if (fileType == EXECEL_TYPE_XLSX) {
            wb = new XSSFWorkbook(inputStream);
        }
        BeanUtilsHashMapper hashMapper = new BeanUtilsHashMapper(clz);
        List<T> list = new ArrayList();
        for (int i = 0; i < wb.getNumberOfSheets(); i++) {
            Sheet sheet = wb.getSheetAt(i);
            int rows = sheet.getPhysicalNumberOfRows();
            logger.info("sheet {} has {} rows", i, rows);
            for (int r = rowOffset; r < rows; r++) {
                Row row = sheet.getRow(r);
                if (row == null) {
                    continue;
                }
                int cells = row.getPhysicalNumberOfCells();
                logger.info("row {} has {} cells", row, cells);
                Map<String, String> map = new HashMap<>(10);
                int index = 0;
                for (String column : columnMapper.keySet()) {
                    Cell cell = row.getCell(index);
                    String value = "";
                    switch (cell.getCellType()) {
                        case HSSFCell.CELL_TYPE_NUMERIC:
                            if (columnMapper.get(column) == Integer.class) {
                                value = "" + (int) (cell.getNumericCellValue());
                            } else {
                                value = "" + cell.getNumericCellValue();
                            }
                            break;
                        case HSSFCell.CELL_TYPE_STRING:
                            value = cell.getStringCellValue();
                            break;
                        default:
                            break;
                    }
                    map.put(column, value);
                    index++;
                }
                list.add((T) hashMapper.fromHash(map));
            }
        }
        return list;
    }

    public static void write(OutputStream outputStream, int fileType, Map<String, Class> columnMapper, String[] header, List list, Class clz) throws Exception {
        Workbook workbook = null;
        if (fileType == EXECEL_TYPE_XLS) {
            workbook = new HSSFWorkbook();
        } else if (fileType == EXECEL_TYPE_XLSX) {
            workbook = new XSSFWorkbook();
        }
        Sheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(15);
        int rowNumber = 0;
        Row row = sheet.createRow(rowNumber++);
        for (int i = 0; i < header.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellValue(header[i]);
        }
        BeanUtilsHashMapper hashMapper = new BeanUtilsHashMapper(clz);
        for (Object obj : list) {
            Map<String, String> map = hashMapper.toHash(obj);
            row = sheet.createRow(rowNumber++);
            int index = 0;
            for (String column : columnMapper.keySet()) {
                Cell cell = row.createCell(index++);
                cell.setCellValue(map.get(column));
            }
        }
        workbook.write(outputStream);
        ((InputStream) workbook).close();
    }

}