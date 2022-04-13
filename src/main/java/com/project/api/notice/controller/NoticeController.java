package com.project.api.notice.controller;

import com.project.api.common.exception.ApiRuntimeException;
import com.project.api.common.exception.Message;
import com.project.api.common.exception.StatusEnum;

import com.project.api.notice.model.Notice;
import com.project.api.notice.model.NoticeFileDto;
import com.project.api.notice.repository.NoticeRepository;
import com.project.api.notice.service.NoticeService;
import com.project.api.util.FileUpload.model.UploadFileVO;
import com.project.api.util.FileUpload.service.FileUploadUtil;
import com.querydsl.core.Tuple;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Slf4j
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;
    private final NoticeService noticeService;


    @Value("${image.path}")
    private String pathNm;

    @GetMapping("/notice")
    public List<Notice> getAll(@RequestParam(name="title", required = false) String title){
        if(title.isEmpty()){
            return noticeRepository.findAll();
        }
       else{
           return noticeService.findByTitle(title);
            //return noticeRepository.findByTitle(title);
        }
    }

    @GetMapping("/notice/{id}")
    public @ResponseBody
    List<NoticeFileDto> getNoticeDlt(@PathVariable Long id) {
        List<NoticeFileDto> notice = noticeService.findById(id);
        return notice;
        /*return noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID 값 확인 : " + id));*/
    }

    @GetMapping("/noticeTuple/{id}")
    public Map<String, Object>  getNoticeTuple(@PathVariable Long id){
        Map<String, Object>  notice = noticeService.findTupleById(id);
        return notice;
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
                    FileUploadUtil.uploadFile(multipartFile, pathNm, storeFileNm);

                    UploadFileVO uploadFileVO = UploadFileVO.builder()
                            .fileSeq(fileSeq)
                            .atcFilePathNm(pathNm)
                            .storeFileNm(storeFileNm)
                            .atcFileSize(multipartFile.getSize())
                            .fileDtlSeq(dtl)
                            .cretrId(ntc.getCretrId())
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

    @PostMapping("/notice/excelDownload")
    public void noticeListExcelorkbook(@RequestBody List<Notice> ntcList, HttpServletResponse response) throws Exception {

        if(ntcList.size()>10000) throw new ApiRuntimeException(StatusEnum.OVER_MAX_RECORD.getCode(), StatusEnum.OVER_MAX_RECORD.getMsg());
        ServletOutputStream outputStream = null;

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("sheet1");

        Font headerFont = workbook.createFont();
        headerFont.setBoldweight((short)1);
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setColor(IndexedColors.BLUE_GREY.getIndex());

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        Row headerRow = sheet.createRow(0);
        String [] cellArray = {"번호","제목","작성자"};

        for(int i=0; i < cellArray.length; i++){
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(cellArray[i]);
            cell.setCellStyle(headerCellStyle);
        }
        int rowNum = 1;
        for(Notice vo : ntcList){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(rowNum-1);
            row.createCell(1).setCellValue(vo.getTitle());
            row.createCell(2).setCellValue(vo.getCretrId());
            //   row.createCell(3).setCellValue(String.valueOf(vo.getCretDt()));

        }
        for(int i=0; i < cellArray.length; i++){
            sheet.autoSizeColumn(i);
        }

        String excelFileNm ="noticeList";
        response.setHeader("Content-Transfer-Encoding", "binary;");
        response.setHeader("Pragma", "no-cache;");
        response.setHeader("Expires", "-1;");
        response.setHeader("Content-Disposition", "attachment;filename=\"" +excelFileNm+ "\";");
        response.setContentType("application/octet-stream;");
        outputStream = ((ServletResponse)response).getOutputStream();
        workbook.write(outputStream);
    }

    public ResponseEntity<Message> throwErrorResponse(BindingResult bindingResult) {

        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();

        Message message = new Message();
        message.setErrorCode(StatusEnum.VALID_CHECK.getCode());
        message.setErrorMsg(objectError.getDefaultMessage());

        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

}
