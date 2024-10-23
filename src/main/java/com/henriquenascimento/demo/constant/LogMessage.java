package com.henriquenascimento.demo.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogMessage {

    public static final String CREATE = "Creating %s with request: %s";
    public static final String FIND_BY_ID_OR_ELSE_THROW = "Searching for %s with id: %d";
    public static final String FIND_RESPONSEDTO_BY_ID = "Searching for %sResponseDTO with id: %d";
    public static final String FIND_ALL = "Retrieving all records with pagination %s of entity: %s";
    public static final String UPDATE_BY_ID = "Updating %s with id: %d";
    public static final String DELETE_BY_ID = "Deleting %s with id: %d";
    public static final String DELETE_ALL = "Deleting all records of entity: %s";

    public static String buildCreateLogMessage(final String entityName,
                                               final Object requestDTO) {
        return String.format(CREATE, entityName, requestDTO);
    }

    public static String buildFindByIdOrElseThrowLogMessage(final String entityName,
                                                            final Long id) {
        return String.format(FIND_BY_ID_OR_ELSE_THROW, entityName, id);
    }

    public static String buildFindResponseDTOByIdLogMessage(final String entityName,
                                                            final Long id) {
        return String.format(FIND_RESPONSEDTO_BY_ID, entityName, id);
    }

    public static String buildFindAllLogMessage(final Object pageable,
                                                final String entityName) {
        return String.format(FIND_ALL, pageable, entityName);
    }

    public static String buildDeleteById(final String entityName,
                                         final Long id) {
        return String.format(DELETE_BY_ID, entityName, id);
    }

    public static String buildUpdateByIdLogMessage(final String entityName,
                                                   final Long id) {
        return String.format(UPDATE_BY_ID, entityName, id);
    }

    public static String buildDeleteAllLogMessage(final String entityName) {
        return String.format(DELETE_ALL, entityName);
    }

}
