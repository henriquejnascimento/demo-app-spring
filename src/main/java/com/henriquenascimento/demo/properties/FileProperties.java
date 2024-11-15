package com.henriquenascimento.demo.properties;

import java.util.List;

public interface FileProperties {

    Boolean getEnabled();
    int getMaxFiles();
    Long getMaxSizePerFile();
    List<String> getAllowedMimeType();
    String getBasePath();

}
