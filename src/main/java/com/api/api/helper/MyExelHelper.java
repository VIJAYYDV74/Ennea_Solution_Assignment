package com.api.api.helper;

import com.api.api.entity.Product;
import com.api.api.service.ProductService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyExelHelper {


    public static boolean checkExcelFormat(MultipartFile multipartFile){
        String contentType=multipartFile.getContentType();
        if(contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")){
            return true;
        }
        return false;

    }

    public static List<Product> convertExcelToListOfProduct(InputStream is){
        List<Product> list=new ArrayList<>();
        try {

            XSSFWorkbook workbook= new XSSFWorkbook(is);
            XSSFSheet sheet=workbook.getSheet("sample_inventory");
            int rowNumber=0;
            Iterator<Row> iterator = sheet.iterator();
            while(iterator.hasNext()){
                Row row=iterator.next();
                if(rowNumber==0){
                    rowNumber++;
                    continue;
                }
                Product p=new Product();
                Iterator<Cell> cells= row.iterator();
                int cid=0;
                //p.setProductId((Integer) rowNumber);
                while(cells.hasNext()){
                    Cell cell=cells.next();
                    try {

                        switch (cid) {
                            case 0:
                                p.setProductCode(cell.getStringCellValue());
                                break;
                            case 1:
                                p.setProductName(cell.getStringCellValue());
                                break;
                            case 2:
                                p.setBatch(cell.getStringCellValue());
                                break;
                            case 3:
                                p.setStock((int) cell.getNumericCellValue());
                                break;
                            case 4:
                                p.setDeal((int) cell.getNumericCellValue());
                                break;
                            case 5:
                                p.setFree((int) cell.getNumericCellValue());
                                break;
                            case 6:
                                p.setMrp(cell.getNumericCellValue());
                                break;
                            case 7:
                                p.setRate(cell.getNumericCellValue());
                                break;
                            case 8:
                                p.setExpiry(convertToLocalDate(cell.getStringCellValue()));
                                break;
                            case 9:
                                p.setCompany(cell.getStringCellValue());
                                break;
                            case 10:
                                p.setSupplier(cell.getStringCellValue());
                                break;
                            default:
                                break;
                        }
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    cid++;
                }
                list.add(p);
            }


        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return list;
    }

    private static LocalDate convertToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

}
