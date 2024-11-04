package com.henriquenascimento.demo.repository;

import com.henriquenascimento.demo.model.File;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface FileRepository extends BaseRepository<File, Long> {

    List<File> findAllByCreatedAtLessThan(final Timestamp createdAt);

}
