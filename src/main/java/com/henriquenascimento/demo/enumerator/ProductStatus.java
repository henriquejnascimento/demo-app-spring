package com.henriquenascimento.demo.enumerator;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductStatus {
    AVAILABLE("Available"),
    OUT_OF_STOCK("Out of Stock"),
    DISCONTINUED("Discontinued"),
    PENDING("Pending"),
    IN_TRANSIT("In Transit"),
    BACKORDERED("Backordered"),
    PRE_ORDER("Pre-order"),
    DAMAGED("Damaged");

    private final String name;

}