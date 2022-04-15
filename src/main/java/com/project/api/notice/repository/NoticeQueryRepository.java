package com.project.api.notice.repository;

import com.project.api.notice.model.Notice;
import com.project.api.notice.model.NoticeFileDto;
import com.project.api.notice.model.QNotice;
import com.project.api.util.FileUpload.model.QUploadFileVO;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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

    public Map<String,Object> findTupleById(Long id){
        QNotice qNotice = QNotice.notice;
        QUploadFileVO qUploadFileVO = QUploadFileVO.uploadFileVO;

        List<Tuple> notice = query
                .select(qNotice.seq,qNotice.title,qNotice.sbst,qNotice.atcFileSeq,qUploadFileVO.fileDtlSeq)
                .from(qNotice)
                .where(qNotice.seq.eq(id))
                .leftJoin(qUploadFileVO).on(qNotice.atcFileSeq.eq(qUploadFileVO.fileSeq))
                .fetch();

        HashMap<String,Object> returnNtc = new HashMap<>();
        List<Long> fileDtlList = new ArrayList<>();
        for (Tuple tuple : notice) {
            fileDtlList.add(tuple.get(qUploadFileVO.fileDtlSeq));
        }

        returnNtc.put("ntcSeq",notice.get(0).get(qNotice.seq));
        returnNtc.put("ntcTitle",notice.get(0).get(qNotice.title));
        returnNtc.put("fileSeq",notice.get(0).get(qNotice.atcFileSeq));
        returnNtc.put("fileDtlSeq",fileDtlList);

        return returnNtc;
    }

    public List<Notice> findNtcByFileSeq(Long fileSeq) {
        QNotice qNotice = QNotice.notice;
        QUploadFileVO qUploadFileVO = QUploadFileVO.uploadFileVO;
        return query
                .selectFrom(qNotice)
                .where(qNotice.atcFileSeq.in(
                        JPAExpressions
                                .select(qUploadFileVO.fileSeq)
                                .from(qUploadFileVO)
                                .where(qUploadFileVO.fileSeq.eq(fileSeq))))
                .fetch();

    }
}

// 서브 쿼리
// map으로 받기ㅂ