package com.project.api.notice.repository;

import com.project.api.notice.model.Notice;
import com.project.api.notice.model.NoticeFileDto;
import com.project.api.notice.model.QNotice;
import com.project.api.util.FileUpload.model.QUploadFileVO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@AllArgsConstructor
@Repository
public class NoticeQueryRepository {

    @Autowired
    private NoticeRepository noticeRepository;

    private final JPAQueryFactory query;

    public List<Notice> findByTitle(String title){
        QNotice qNotice = QNotice.notice;
        List<Notice> notice = query
                .select(qNotice)
                .from(qNotice)
                .where(qNotice.title.eq(title))
                .fetch();

        return notice;
    }
    public List<NoticeFileDto> findById(Long id){

        QNotice qNotice = QNotice.notice;
        QUploadFileVO qUploadFileVO = QUploadFileVO.uploadFileVO;

        List<NoticeFileDto> notice = query
                .select(Projections.constructor(NoticeFileDto.class, qNotice.seq,qNotice.title,qNotice.sbst,qUploadFileVO.fileDtlSeq))
                .from(qNotice)
                .where(qNotice.seq.eq(id))
                .leftJoin(qUploadFileVO).on(qNotice.atcFileSeq.eq(qUploadFileVO.fileSeq))
                .fetch();

        return notice;
    }
}
