package com.henriquenascimento.demo.model;

import com.henriquenascimento.demo.enumerator.FileProcessStatus;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "FILE_PROCESS")
@EqualsAndHashCode(callSuper = false)
public class FileProcess extends BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_FILE_PROCESS", unique = true, nullable = false)
    private Long id;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private FileProcessStatus status;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "FILES_FOUND")
    private Long filesFound;

    @Column(name = "FILES_SENT")
    private Long filesSent;

    @Column(name = "COMPLETION_DATE")
    private Instant completionDate;

    @OneToMany(mappedBy = "fileProcess", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<File> files = new ArrayList<>();

}
