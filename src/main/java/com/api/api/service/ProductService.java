package com.api.api.service;

import com.api.api.entity.Product;
import com.api.api.helper.MyExelHelper;
import com.api.api.repositories.ProductRepo;
import org.hibernate.id.uuid.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepo productRepo;

    public void save(MultipartFile file){
        try {
            List<Product> products=MyExelHelper.convertExcelToListOfProduct(file.getInputStream());
            this.productRepo.saveAll(products);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public List<Product> getAllProducts(Integer pageNumber,Integer pageSize){
        Pageable p= PageRequest.of(pageNumber,pageSize);
        Page<Product> pageProduct=this.productRepo.findAll(p);
        List<Product> allProduct= pageProduct.getContent();
        return allProduct;
    }

    public List<Product> getAllProductWithSupplierName(String supplier_name, String product_name) {
        List<Product> list=new ArrayList<>();

        list=productRepo.findAll();
        for(Product p:list){
            if(!p.getSupplier().equals(supplier_name)){
                list.remove(p);
            }
        }
        if(product_name!=null){
            for(Product p:list){
                if(!p.getProductName().equals(product_name)){
                    list.remove(p);
                }
            }
        }
        return list;
    }

    public List<Product> getAllProductsNotExpired() {
        List<Product> list=new ArrayList<>();
        list=productRepo.findAll();
        LocalDate d=LocalDate.now();
        for (Product p:list){
            if(p.getExpiry().isBefore(d)){
                list.remove(p);
            }
        }
        return list;
    }
}
