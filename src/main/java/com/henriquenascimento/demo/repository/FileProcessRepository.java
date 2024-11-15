package com.henriquenascimento.demo.repository;

import com.henriquenascimento.demo.model.FileProcess;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FileProcessRepository extends BaseRepository<FileProcess, Long> {

    List<FileProcess> findAllByCreatedAtLessThan(final Timestamp createdAt);

}
