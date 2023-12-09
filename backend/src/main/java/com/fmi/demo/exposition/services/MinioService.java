package com.fmi.demo.exposition.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;

@Service
public class MinioService {
    @Autowired
    private S3Client minioClient;

    @Autowired
    private S3Presigner presigner;

    @Value("${minio.bucket-name}")
    private String bucketName;

    public String storeImage(MultipartFile file) throws IOException {
        // Generate a unique name for the file
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Upload the file to the MinIO server
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        minioClient.putObject(putObjectRequest, RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
// minioClient.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName));
        // Return the URL of the stored image
        return fileName;
    }

    public void deleteImage(String filename){
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(bucketName)
                .key(filename)
                .build();
        minioClient.deleteObject(deleteObjectRequest);
    }

    public String getImage(String filename) {
        GetObjectRequest getObjectRequest =
                GetObjectRequest.builder()
                        .bucket(bucketName)
                        .key(filename)
                        .build();

        GetObjectPresignRequest getObjectPresignRequest =
                GetObjectPresignRequest.builder()
                        .signatureDuration(Duration.ofHours(1))  // adjust this according to your needs
                        .getObjectRequest(getObjectRequest)
                        .build();

        PresignedGetObjectRequest presignedGetObjectRequest = presigner.presignGetObject(getObjectPresignRequest);

        // Return the URL of the image
        return presignedGetObjectRequest.url().toString();
    }
}