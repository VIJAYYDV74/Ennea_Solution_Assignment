package com.api.api.ServiceInterface;

import com.api.api.entity.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductServiceInterface   {


    public void save(MultipartFile file) throws Exception;

    public List<Product> getAllProducts(Integer pageNumber, Integer pageSize)throws Exception;

    public String update(Integer product_id, String product_name)throws Exception;

    public String delete(Integer product_id)throws Exception;
}
