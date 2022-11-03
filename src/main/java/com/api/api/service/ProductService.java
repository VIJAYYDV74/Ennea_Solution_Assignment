package com.api.api.service;

import com.api.api.ServiceInterface.ProductServiceInterface;
import com.api.api.entity.Product;
import com.api.api.repositories.ProductRepo;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceInterface {
    @Autowired
    private ProductRepo productRepo;

    @Override
    public void save(MultipartFile file) throws Exception {
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {


            // create csv bean reader
            CsvToBean<Product> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Product.class)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            // convert `CsvToBean` object to list of users
            List<Product> products = csvToBean.parse();

            this.productRepo.saveAll(products);


        } catch (Exception ex) {
           ex.printStackTrace();
        }

    }

    @Override
    public List<Product> getAllProducts(Integer pageNumber,Integer pageSize){
        try {
            Pageable p = PageRequest.of(pageNumber, pageSize);
            Page<Product> pageProduct = this.productRepo.findAll(p);
            List<Product> allProduct = pageProduct.getContent();
            return allProduct;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> update(Integer product_id, String product_name) {
        try {
            Optional<Product> product = this.productRepo.findById(product_id);
            if (product.isEmpty()) {
                return new ResponseEntity<>("product not found by this id", HttpStatus.BAD_REQUEST);
            }
            List<Product> list = productRepo.findAll();
            for (Product p : list) {
                if (p.getProductId().equals(product_id)) {
                    p.setDisplayName(product_name);
                    list.add(p);
                    break;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("product name updated",HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> delete(Integer product_id) throws Exception {
        try {
            List<Product> list = productRepo.findAll();
            for (Product p : list) {
                if (p.getProductId().equals(product_id)) {
                    productRepo.deleteById(product_id);
                    return new ResponseEntity<>("product deleted",HttpStatus.OK);
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("product not found",HttpStatus.BAD_REQUEST);
    }

    /*public List<Product> getAllProductWithSupplierName(String supplier_name, String product_name) {
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
    }*/

    /*public List<Product> getAllProductsNotExpired() {
        List<Product> list=new ArrayList<>();
        list=productRepo.findAll();
        LocalDate d=LocalDate.now();
        for (Product p:list){
            if(p.getExpiry().isBefore(d)){
                list.remove(p);
            }
        }
        return list;
    }*/
}
