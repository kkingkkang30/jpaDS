package com.project.api.notice.service;

import com.project.api.util.FileUpload.model.UploadFileVO;
import com.project.api.util.FileUpload.repository.FileRepository;
import lombok.AllArgsConstructor;
import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

@Service
@AllArgsConstructor
public class NoticeService {

    private final FileRepository fileRepository;


    public Long findNewSeq(){
        long seq = fileRepository.findNewSeq();
        System.out.println("seq = " + (seq+1));
        return seq+1;
    }
    public Long registerFile(UploadFileVO uploadFileVO){
        UploadFileVO newFileVO = fileRepository.save(uploadFileVO);
        return newFileVO.getFileSeq();
    }

}

