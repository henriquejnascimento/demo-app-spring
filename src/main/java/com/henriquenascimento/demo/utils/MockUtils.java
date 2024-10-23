package com.henriquenascimento.demo.utils;

import com.henriquenascimento.demo.enumerator.ProductStatus;
import com.henriquenascimento.demo.model.Product;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MockUtils {

    public static List<Product> createMockProducts() {
        List<Product> products = new ArrayList<>();

        products.add(createProduct("Laptop", "A high-performance laptop.", ProductStatus.AVAILABLE));
        products.add(createProduct("Smartphone", "A flagship smartphone.", ProductStatus.OUT_OF_STOCK));
        products.add(createProduct("Headphones", "Noise-cancelling headphones.", ProductStatus.DISCONTINUED));
        products.add(createProduct("Smartwatch", "A smartwatch with health tracking features.", ProductStatus.PENDING));
        products.add(createProduct("Tablet", "A tablet for media consumption.", ProductStatus.PRE_ORDER));
        products.add(createProduct("Camera", "A DSLR camera with high resolution.", ProductStatus.IN_TRANSIT));
        products.add(createProduct("Monitor", "A 4K Ultra HD monitor.", ProductStatus.BACKORDERED));
        products.add(createProduct("Printer", "A wireless color printer.", ProductStatus.AVAILABLE));
        products.add(createProduct("Router", "A high-speed wireless router.", ProductStatus.OUT_OF_STOCK));
        products.add(createProduct("Keyboard", "A mechanical keyboard with RGB lighting.", ProductStatus.AVAILABLE));
        return products;
    }

    public static Product createProduct(String name, String description, ProductStatus status) {
        return Product.builder()
                .name(name)
                .description(description)
                .status(status)
                .build();
    }

}
