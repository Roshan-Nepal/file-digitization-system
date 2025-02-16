package com.roshan.filedigitizationsystem.repo;

import com.roshan.filedigitizationsystem.entity.FileMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileMetaDataRepo extends JpaRepository<FileMetaData, Long> {
}
