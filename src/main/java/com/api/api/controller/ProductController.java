package com.api.api.controller;

import com.api.api.entity.Product;
import com.api.api.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/upload-csv-file")
    public ResponseEntity<String> uploadCSVFile(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return new ResponseEntity<>("file is empty",HttpStatus.BAD_REQUEST);
        } else {

            try {

                this.productService.save(file);
                return new ResponseEntity<>("file saved",HttpStatus.OK);
            } catch (Exception e) {
                //throw new RuntimeException(e);
                return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
            }
        }


    }


     @GetMapping("/product")
    public ResponseEntity<List<Product>> getAllProduct(@RequestParam(value = "pageNumber",defaultValue ="10",required = false) Integer pageNumber
                                        ,@RequestParam(value = "pageSize",defaultValue = "2",required = false) Integer pageSize){
         return new ResponseEntity<>(this.productService.getAllProducts(pageNumber,pageSize),HttpStatus.OK);
     }


     @PutMapping(value = "/product/{product_id}",params = "product_name")
    public ResponseEntity<String> update(@PathVariable Integer product_id,@RequestParam String product_name){

        return productService.update(product_id,product_name);
     }

     @DeleteMapping(value = "/product/{product_id}")
    public ResponseEntity<String> delete(@PathVariable Integer product_id){
         try {
             return productService.delete(product_id);
         } catch (Exception e) {
             //throw new RuntimeException(e);
             return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
         }
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
