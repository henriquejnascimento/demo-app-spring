package com.henriquenascimento.demo.controller.advice;

import com.henriquenascimento.demo.constant.ErrorConstant;
import lombok.Getter;

@Getter
public class CustomEntityNotFoundException extends RuntimeException {

    private final String entityName;

    public CustomEntityNotFoundException(final String entityName,
                                         final Long id) {
        super(ErrorConstant.buildResourceNotFoundErrorMessage(entityName, id));
        this.entityName = entityName;
    }

}
