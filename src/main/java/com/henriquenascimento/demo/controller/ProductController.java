package com.henriquenascimento.demo.controller;

import com.henriquenascimento.demo.dto.ProductFilterDTO;
import com.henriquenascimento.demo.dto.ProductRequestDTO;
import com.henriquenascimento.demo.dto.ProductResponseDTO;
import com.henriquenascimento.demo.enumerator.ProductStatus;
import com.henriquenascimento.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(ProductController.BASE_URI)
@Tag(name = "Product", description = "Manage products")
public class ProductController {

    static final String BASE_URI = "/product";
    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Create a new product", description = "Add a new product to the inventory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request")
    })
    public ResponseEntity<ProductResponseDTO> create(
            @RequestBody @Valid ProductRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @GetMapping
    @Operation(
            summary = "Get all products",
            description = "Get a list of all products in the inventory",
            parameters = {
                    @Parameter(
                            name = "page",
                            description = "Page number (starting from 0). Default is 0.",
                            example = "0"
                    ),
                    @Parameter(
                            name = "size",
                            description = "Number of products per page. Default is 10.",
                            example = "10"
                    ),
                    @Parameter(
                            name = "sort",
                            description = "Sorting criteria in the format: property(,asc|desc). Default is 'id,asc'.",
                            example = "id,asc",
                            schema = @Schema(type = "string", defaultValue = "id,asc")
                    )
            }
    )
    public ResponseEntity<Page<ProductResponseDTO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") final int page,
            @RequestParam(value = "size", defaultValue = "10") final int size,
            @RequestParam(value = "sort", defaultValue = "id,asc") final String[] sort,
            final ProductFilterDTO productFilterDTO) {
        return ResponseEntity.ok(productService.findAll(page, size, sort, productFilterDTO));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a product by ID", description = "Get the details of a specific product by its unique ID")
    public ResponseEntity<ProductResponseDTO> findById(
            @PathVariable Long id) {
        return ResponseEntity.ok(productService.findResponseDTOById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an existing product", description = "Update the details of an existing product")
    public ResponseEntity<ProductResponseDTO> updateById(
            @PathVariable Long id,
            @RequestBody ProductRequestDTO productRequestDTO) {
        return ResponseEntity.ok(productService.updateById(id, productRequestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a product by ID", description = "Remove a product from the inventory by its unique ID")
    public ResponseEntity<Void> deleteById(
            @PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @Operation(summary = "Delete all products", description = "Remove all products from the inventory")
    public ResponseEntity<Void> deleteAll() {
        productService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    @Operation(
            summary = "Update product status",
            description = "Update the status of an existing product by its unique ID",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Product status updated successfully"),
                    @ApiResponse(responseCode = "404", description = "Product not found")
            }
    )
    public ResponseEntity<ProductResponseDTO> updateProductStatus(
            @PathVariable Long id,
            @RequestBody ProductStatus newStatus) {
        return ResponseEntity.ok(productService.updateProductStatus(id, newStatus));
    }

    @PostMapping("/reset-database")
    @Operation(summary = "Reset database", description = "Delete all records and insert initial records")
    public ResponseEntity<Void> resetDatabase() {
        productService.resetDatabase();
        return ResponseEntity.ok().build();
    }

}
