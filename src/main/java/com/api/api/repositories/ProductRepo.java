package com.api.api.repositories;

import com.api.api.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Integer> {
    List<Product> findAllById(Integer product_id);

}
