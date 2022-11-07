package com.api.api.MyExelHelper;

import com.api.api.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;

public interface MyExcleHelperInterface {

    public  List<Product> convertExcelToListOfProduct(InputStream is)throws Exception;

    public  boolean checkExcelFormat(MultipartFile multipartFile)throws Exception;

    public  LocalDate convertToLocalDate(String date)throws Exception;
}
