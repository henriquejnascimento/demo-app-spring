package com.henriquenascimento.demo.repository;

import com.henriquenascimento.demo.dto.ProductFilterDTO;
import com.henriquenascimento.demo.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends BaseRepository<Product, Long> {

    @Query("SELECT p FROM Product p " +
            "WHERE (:#{#filter.name} IS NULL OR p.name = :#{#filter.name}) " +
            "AND (:#{#filter.description} IS NULL OR p.description = :#{#filter.description}) " +
            "AND (:#{#filter.status} IS NULL OR p.status = :#{#filter.status}) " +
            "AND (:#{#filter.status} IS NULL OR p.status = :#{#filter.status}) " +
            "AND (:#{#filter.startDate} IS NULL OR p.createdAt >= :#{#filter.startDate != null ? #filter.startDate.atStartOfDay() : null}) " +
            "AND (:#{#filter.endDate} IS NULL OR p.createdAt <= :#{#filter.endDate != null ? #filter.endDate.plusDays(1).atStartOfDay() : null})")
    Page<Product> findAllWithFilters(@Param("filter") final ProductFilterDTO filter, final Pageable pageable);
}
