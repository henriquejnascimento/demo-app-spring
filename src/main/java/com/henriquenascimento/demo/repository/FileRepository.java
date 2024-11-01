package com.henriquenascimento.demo.repository;

import com.henriquenascimento.demo.model.File;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends BaseRepository<File, Long> {

}
