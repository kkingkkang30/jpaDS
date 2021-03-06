package com.project.api.notice.service;

import com.project.api.notice.model.Notice;
import com.project.api.notice.model.NoticeFileDto;
import com.project.api.notice.repository.NoticeQueryRepository;
import com.project.api.util.FileUpload.model.UploadFileVO;
import com.project.api.util.FileUpload.repository.FileRepository;
import com.querydsl.core.Tuple;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Slf4j
@Service
@AllArgsConstructor
public class NoticeService {

    private final FileRepository fileRepository;
    private final NoticeQueryRepository noticeQueryRepository;

    public Long findNewSeq(){
        long seq = fileRepository.findNewSeq();
        return seq+1;
    }
    public Long registerFile(UploadFileVO uploadFileVO){

        UploadFileVO newFileVO = fileRepository.save(uploadFileVO);
        return newFileVO.getFileSeq();
    }

    public List<Notice> findByTitle(String title){
        return noticeQueryRepository.findByTitle(title);
    }

    public List<NoticeFileDto> findById(Long id){
        List<NoticeFileDto> notice;
        notice = noticeQueryRepository.findById(id);
        return  notice;
    }

    public  Map<String, Object> findTupleById(Long id){
        Map<String, Object> notice = noticeQueryRepository.findTupleById(id);
        return notice;
    }

    public List<Notice> getUsingAtcFileNoticeList(Long fileSeq) {
        return noticeQueryRepository.findNtcByFileSeq(fileSeq);
    }
}

