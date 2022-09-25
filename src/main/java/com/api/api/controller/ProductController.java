package com.api.api.controller;

import com.api.api.entity.Product;
import com.api.api.helper.MyExelHelper;
import com.api.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

     @PostMapping("/product/upload")
     public ResponseEntity<?> upload(@RequestParam("file")MultipartFile file){
         if(MyExelHelper.checkExcelFormat(file)){
                this.productService.save(file);
                return ResponseEntity.ok(Map.of("message","file is uploaded"));
         }
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please Upload excel file");
     }

     @GetMapping("/product")
    public List<Product> getAllProduct(@RequestParam(value = "pageNumber",defaultValue ="10",required = false) Integer pageNumber
                                        ,@RequestParam(value = "pageSize",defaultValue = "1",required = false) Integer pageSize){
         return this.productService.getAllProducts(pageNumber,pageSize);
     }

     @GetMapping(value = "/product/supplier/{supplier_name}",params = "product_name")
    public List<Product> getAllProductWithSupplierName(@PathVariable String supplier_name,@RequestParam String product_name) throws ParseException {
         return productService.getAllProductWithSupplierName(supplier_name,product_name);
     }

     @GetMapping("product/notExpired")
    public List<Product> getAllProductsNotExpired(){
         return productService.getAllProductsNotExpired();
     }
}
