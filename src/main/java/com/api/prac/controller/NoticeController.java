package com.api.prac.controller;

import antlr.StringUtils;
import com.api.common.exception.Message;
import com.api.common.exception.StatusEnum;
import com.api.prac.model.Notice;
import com.api.prac.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;

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
    public ResponseEntity registNtc(@Valid @RequestBody Notice ntc, BindingResult bindingResult) {

        Notice newNtc = new Notice();
        //LocalDateTime now = LocalDateTime.now();

        if(bindingResult.hasErrors()) {
            return throwErrorResponse(bindingResult);
        }else {
            //ntc.setCretDt(now);
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

            newNtc.setId(id);
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
