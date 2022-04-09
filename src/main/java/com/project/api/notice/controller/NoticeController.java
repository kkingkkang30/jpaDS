package com.project.api.notice.controller;

import com.project.api.common.exception.Message;
import com.project.api.common.exception.StatusEnum;

import com.project.api.notice.model.Notice;
import com.project.api.notice.repository.NoticeRepository;
import com.project.api.notice.service.NoticeService;
import com.project.api.util.FileUpload.model.UploadFileVO;
import com.project.api.util.FileUpload.service.FileUploadUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;
    final int SHORT_ID_LENGTH = 8;

    @GetMapping("/notice")
    public List<Notice> getAll(){
        return noticeRepository.findAll();
    }

    @GetMapping("/notice/{id}")
    public Notice getNoticeDlt(@PathVariable Long id) {
        return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID 값 확인 : " + id));
    }

    @PostMapping("/notice")
    public ResponseEntity registNtc(@Valid Notice ntc, BindingResult bindingResult, @RequestParam(name="uploadFiles", required = false) List<MultipartFile> multipartFileList) throws IOException {

        Notice newNtc;

        if(bindingResult.hasErrors()) {
            return throwErrorResponse(bindingResult);
        }else {
            if(multipartFileList!=null && multipartFileList.size()!=0){
                int dtl = 1;
                Long fileSeq = noticeService.findNewSeq();
                for(MultipartFile multipartFile : multipartFileList) {
                    String storeFileNm = UUID.randomUUID().toString();
                    FileUploadUtil.uploadFile(multipartFile, "/Users/sujin/Desktop/fileTest", storeFileNm);

                    UploadFileVO uploadFileVO = UploadFileVO.builder()
                            .fileSeq(fileSeq)
                            .atcFilePathNm("")
                            .storeFileNm(storeFileNm)
                            .atcFileSize(multipartFile.getSize())
                            .fileDtlSeq(dtl)
                            .cretrId(ntc.getAmtId())
                            .originFileNm(multipartFile.getOriginalFilename())
                            .fileExtNm(multipartFile.getContentType())
                                    .build();

                    noticeService.registerFile(uploadFileVO);
                    dtl++;
                }

                ntc.setAtcFileSeq(fileSeq);
            }

            newNtc = noticeRepository.save(ntc);
            return ResponseEntity.ok(newNtc);
        }

    }

    @PutMapping("/notice/{id}")
    public ResponseEntity modifyNtc(@PathVariable Long id, @Valid @RequestBody Notice newNtc, BindingResult bindingResult){
        //LocalDateTime now = LocalDateTime.now();
        //newNtc.setAmtDt(now);

        if(bindingResult.hasErrors()) {
            return throwErrorResponse(bindingResult);
        }else {
            noticeRepository.findById(id)
                    .map(ntc -> {
                        ntc.setTitle(newNtc.getTitle());
                        ntc.setSbst(newNtc.getSbst());
                        //ntc.setAmtDt(newNtc.getAmtDt());
                        ntc.setAmtId(newNtc.getAmtId());
                        return noticeRepository.save(ntc);
                    });

            newNtc.setSeq(id);
            return ResponseEntity.ok(newNtc);
        }
    }

    @DeleteMapping("/notice/{id}")
    public void deleteNtc(@PathVariable Long id){
        noticeRepository.deleteById(id);
    }


    public ResponseEntity<Message> throwErrorResponse(BindingResult bindingResult) {

        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();

        Message message = new Message();
        message.setErrorCode(StatusEnum.VALID_CHECK.getCode());
        message.setErrorMsg(objectError.getDefaultMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
