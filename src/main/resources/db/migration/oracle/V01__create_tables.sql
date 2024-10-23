CREATE TABLE PRODUCT (
    ID_PRODUCT NUMBER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    NAME VARCHAR2(20) NOT NULL,
    DESCRIPTION VARCHAR2(60) NOT NULL,
    STATUS VARCHAR2(50) NOT NULL,
    CREATED_AT TIMESTAMP WITH TIME ZONE NOT NULL,
    UPDATED_AT TIMESTAMP WITH TIME ZONE
);