package com.project.api.util.FileUpload.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

public class FileUploadUtil {
    private static final long MAX_FILE_SIZE = 52428800; // 50 메가바이트

    public static void uploadFile(MultipartFile uploadFile, String uploadFilePath, String storeFileNm) throws IOException {

        if (uploadFile.getSize() == 0) {
            // 파일 비었음
        }


        long fileSize = uploadFile.getSize();
        if (fileSize > MAX_FILE_SIZE) {
            // 파일 용량 큼
        }

        String extNm = null; // 확장자명
        String uuid = UUID.randomUUID().toString();
        // String storeFileNm = uuid;
        String originalFileNm = uploadFile.getOriginalFilename();

        if (originalFileNm != null && originalFileNm.lastIndexOf(".") >= 0) {
            extNm = originalFileNm.substring(originalFileNm.lastIndexOf(".") + 1);
        }

        // 확장자 검사
        if (extNm == null) {
            // 확장자 없음
        }

        // 파일 생성
        File file = new File(uploadFilePath + "/" + storeFileNm + "." + extNm.toLowerCase(Locale.ROOT));

        if (file.exists()) {
            // 파일 중복
        }

        BufferedOutputStream bufferedOutputStream = null;

        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(file));
            bufferedOutputStream.write(uploadFile.getBytes());
        } catch (IOException e) {
            throw e;
        } finally {
            if (bufferedOutputStream != null) {
                bufferedOutputStream.close();
            }
        }

      /*  UploadFileVO uploadFileVO = UploadFileVO.builder()
                .atcFileNm(storeFileNm)
              *//*  .atcFilePathNm(uploadFilePath)
                .fileExtNm(extNm)
                .atcFileSize(fileSize)
                .storeFileNm(storeFileNm)
                .originFileNm(originalFileNm)*//*
                .cretrId("system")
                .amtId("system")
                .build();

                */
    }
}
