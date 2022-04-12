package com.project.api.util.FileUpload.repository;

import com.project.api.util.FileUpload.model.UploadFileVO;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface FileRepository extends JpaRepository<UploadFileVO, Long> {

    //JPQL
    @Query("select COALESCE(max(b.fileSeq),1) from UploadFileVO b")
    public Long findNewSeq();

}

