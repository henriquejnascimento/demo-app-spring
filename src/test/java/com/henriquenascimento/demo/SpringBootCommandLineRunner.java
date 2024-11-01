package com.henriquenascimento.demo;

import com.henriquenascimento.demo.enumerator.ProductStatus;
import com.henriquenascimento.demo.repository.ProductRepository;
import com.henriquenascimento.demo.utils.MockUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Log4j2
public class SpringBootCommandLineRunner implements CommandLineRunner {

    private final ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCommandLineRunner.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Running a simple demo runner test...");
        productRepository.save(MockUtils.createProduct("dummy name", "dummy desc", ProductStatus.AVAILABLE));
    }

}