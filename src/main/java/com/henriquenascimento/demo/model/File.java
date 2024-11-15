package com.henriquenascimento.demo.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "FILE_STORAGE")
@EqualsAndHashCode(callSuper = false)
public class File extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_FILE_STORAGE", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_FILE_PROCESS")
    private FileProcess fileProcess;

    @Column(name = "FILE_PATH")
    private String path;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_SIZE")
    private Long size;

    @Column(name = "FILE_HASH")
    private String hash;

    @Column(name = "MIME_TYPE")
    private String mimeType;

    @Column(name = "DESCRIPTION")
    private String description;

}
