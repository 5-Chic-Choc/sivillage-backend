package com.chicchoc.sivillage.global.infra.application;

import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.chicchoc.sivillage.global.common.generator.NanoIdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3 s3Client;
    private final NanoIdGenerator nanoIdGenerator;

    @Value(("${cloud.aws.s3.bucket}"))
    private String bucketName;

    public String uploadFile(MultipartFile file, String category) throws IOException {
        // 파일 이름 추출
        String originalFilename = file.getOriginalFilename();

        // 파일명이나 확장자가 null일 경우 처리
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new IllegalArgumentException("파일명이 유효하지 않습니다.");
        }

        // 파일 확장자 추출
        String fileNameExtension = getFileExtension(file.getOriginalFilename());

        // uuid로 중복 파일 이름 방지
        String fileName = "images/" + category + "/" + nanoIdGenerator.generateNanoId() + fileNameExtension;

        // 파일 메타데이터 설정 (ContentType 및 파일 크기)
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        // S3에 파일 업로드
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead)); // 파일 접근 권한을 public으로 설정

        // 업로드된 파일의 URL 반환
        return s3Client.getUrl(bucketName, fileName).toString();
    }

    // DB에 저장된 URL 그대로 사용할 것 이기 때문에 get은 필요 없음
    //    public String getFile(String category, String fileName) throws IOException {
    //        String objKey = "images/" + category + "/"  + fileName;
    //        return S3Client.getUrl(bucketName, objKey).toString();
    //    }

    private String getFileExtension(String fileName) {
        // 파일 확장자 추출 -> '.'이 포함되지 않은 파일명은 빈 문자열을 반환
        int lastDotIndex = fileName.lastIndexOf('.');
        if (lastDotIndex == -1) {
            return "";  // 확장자가 없는 경우 빈 문자열 반환
        }
        return fileName.substring(lastDotIndex);  // 확장자 반환
    }
}
