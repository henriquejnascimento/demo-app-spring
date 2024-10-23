package com.henriquenascimento.demo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorConstant {

    public static final String RESOURCE_NOT_FOUND_ERROR_CODE = "RESOURCE_NOT_FOUND";
    public static final String RESOURCE_ENTITY_NOT_FOUND_ERROR_MESSAGE_TEMPLATE = "%s with id %d not found"; // Example: "Product with id 1 not found"
    public static final String GLOBAL_EXCEPTION = "GLOBAL_EXCEPTION";

    public static String buildResourceNotFoundErrorMessage(final String entityName,
                                                           final Long id) {
        return String.format(RESOURCE_ENTITY_NOT_FOUND_ERROR_MESSAGE_TEMPLATE, entityName, id);
    }

}
