package com.api.api.controller;

import com.api.api.entity.Product;
import com.api.api.helper.MyExelHelper;
import com.api.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/upload-csv-file")
    public String uploadCSVFile(@RequestParam("file") MultipartFile file, Model model) {

        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload.");
            model.addAttribute("status", false);
        } else {

            return this.productService.save(file,model);
        }

        return "file-upload-status";
    }


     @GetMapping("/product")
    public List<Product> getAllProduct(@RequestParam(value = "pageNumber",defaultValue ="10",required = false) Integer pageNumber
                                        ,@RequestParam(value = "pageSize",defaultValue = "2",required = false) Integer pageSize){
         return this.productService.getAllProducts(pageNumber,pageSize);
     }


     @PutMapping(value = "/product/{product_id}",params = "product_name")
    public String update(@PathVariable Integer product_id,@RequestParam String product_name){
         return productService.update(product_id,product_name);
     }

     @DeleteMapping(value = "/product/{product_id}")
    public String delete(@PathVariable Integer product_id){
         return productService.delete(product_id);
     }





  /*   @GetMapping(value = "/product/supplier/{supplier_name}",params = "product_name")
    public List<Product> getAllProductWithSupplierName(@PathVariable String supplier_name,@RequestParam String product_name) throws ParseException {
         return productService.getAllProductWithSupplierName(supplier_name,product_name);
     }*/
/*
     @GetMapping("product/notExpired")
    public List<Product> getAllProductsNotExpired(){
         return productService.getAllProductsNotExpired();
     }*/
}
