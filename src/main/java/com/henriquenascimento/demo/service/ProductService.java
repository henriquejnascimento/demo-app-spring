package com.henriquenascimento.demo.service;

import com.henriquenascimento.demo.constant.LogMessage;
import com.henriquenascimento.demo.controller.advice.CustomEntityNotFoundException;
import com.henriquenascimento.demo.dto.ProductRequestDTO;
import com.henriquenascimento.demo.dto.ProductResponseDTO;
import com.henriquenascimento.demo.enumerator.ProductStatus;
import com.henriquenascimento.demo.mapper.ProductRequestMapper;
import com.henriquenascimento.demo.mapper.ProductResponseMapper;
import com.henriquenascimento.demo.model.Product;
import com.henriquenascimento.demo.repository.ProductRepository;
import com.henriquenascimento.demo.utils.MockUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductResponseMapper productResponseMapper;
    private final ProductRequestMapper productRequestMapper;

    public ProductResponseDTO create(final ProductRequestDTO productRequestDTO) {
        // TODO validation

        log.info(LogMessage.buildCreateLogMessage(productRepository.getEntityName(), productRequestDTO));
        return productResponseMapper.toDTO(
                productRepository.save(
                        productRequestMapper.toEntity(productRequestDTO))
        );
    }

    @Transactional
    public Page<ProductResponseDTO> saveByImportFile() {
        return null; // TODO implement
    }

    // TODO add ProductFilterDTO param
    public Page<ProductResponseDTO> findAll(final Pageable pageable) {
        log.info(LogMessage.buildFindAllLogMessage(pageable, productRepository.getEntityName()));
        return productRepository.findAll(pageable)
                .map(productResponseMapper::toDTO);
    }

    public Product findByIdOrElseThrow(final Long id) {
        log.info(LogMessage.buildFindByIdOrElseThrowLogMessage(productRepository.getEntityName(), id));
        return productRepository.findById(id)
                .orElseThrow(() -> new CustomEntityNotFoundException(productRepository.getEntityName(), id));
    }

    public ProductResponseDTO findResponseDTOById(final Long id) {
        log.info(LogMessage.buildFindResponseDTOByIdLogMessage(productRepository.getEntityName(), id));
        return productResponseMapper.toDTO(findByIdOrElseThrow(id));
    }

    public ProductResponseDTO updateById(final Long id,
                                         final ProductRequestDTO productRequestDTO) {
        log.info(LogMessage.buildUpdateByIdLogMessage(productRepository.getEntityName(), id));
        Product existingProduct = findByIdOrElseThrow(id);
        BeanUtils.copyProperties(productRequestDTO, existingProduct, "id");
        return productResponseMapper.toDTO(
                productRepository.save(existingProduct));
    }

    public void deleteById(final Long id) {
        log.info(LogMessage.buildDeleteById(productRepository.getEntityName(), id));
        findByIdOrElseThrow(id);
        productRepository.deleteById(id);
    }

    public void deleteAll() {
        log.info(LogMessage.buildDeleteAllLogMessage(productRepository.getEntityName()));
        productRepository.deleteAll();
    }

    public ProductResponseDTO updateProductStatus(final Long id,
                                                  final ProductStatus newStatus) {
        Product product = findByIdOrElseThrow(id);
        log.info("updating status of {} with id {} from {} to {}",
                productRepository.getEntityName(), id, product.getStatus(), newStatus);
        product.setStatus(newStatus);
        return productResponseMapper.toDTO(
                productRepository.save(product));
    }

    @Transactional
    public void resetDatabase() {
        productRepository.deleteAll();
        productRepository.saveAll(MockUtils.createMockProducts());
    }
}
