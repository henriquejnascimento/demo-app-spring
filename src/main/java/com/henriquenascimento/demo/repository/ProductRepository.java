package com.henriquenascimento.demo.repository;

import com.henriquenascimento.demo.model.Product;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {

}
