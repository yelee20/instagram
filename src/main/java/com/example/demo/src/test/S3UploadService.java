package com.example.demo.src.test;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.example.demo.common.exceptions.BaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import static com.example.demo.common.response.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3UploadService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${spring.servlet.multipart.max-file-size}")
    private String maxSizeString;

    private boolean isImage(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            List<String> allowedImageExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");
            return allowedImageExtensions.contains(extension);
        }
        return false;
    }

    private boolean isVideo(MultipartFile file) {
        String filename = file.getOriginalFilename();
        if (filename != null) {
            String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            List<String> allowedVideoExtensions = Arrays.asList("mov", "mp4", "avi");
            return allowedVideoExtensions.contains(extension);
        }
        return false;
    }

    public String upload(MultipartFile file) {
        if(file.isEmpty() || Objects.isNull(file.getOriginalFilename())){
            throw new BaseException(EMPTY_FILE_EXCEPTION);
        }

        if (isImage(file)) {
            return this.uploadImage(file);
        } else if (isVideo(file)) {
            return this.uploadVideo(file);
        } else {
            throw new BaseException(INVALID_FILE_EXTENTION);
        }
    }

    private String uploadImage(MultipartFile image) {
        validateFileExtension(image.getOriginalFilename(), Arrays.asList("jpg", "jpeg", "png", "gif"));
        try {
            return uploadFileToS3(image, "image");
        } catch (IOException e) {
            throw new BaseException(IO_EXCEPTION_ON_IMAGE_UPLOAD);
        }
    }

    private String uploadVideo(MultipartFile video) {
        validateFileExtension(video.getOriginalFilename(), Arrays.asList("mov", "mp4", "avi"));
        try {
            return uploadFileToS3(video, "video");
        } catch (IOException e) {
            throw new BaseException(IO_EXCEPTION_ON_VIDEO_UPLOAD);
        }
    }

    private void validateFileExtension(String filename, List<String> allowedExtensions) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new BaseException(EMPTY_FILE_EXCEPTION);
        }

        String extension = filename.substring(lastDotIndex + 1).toLowerCase();

        if (!allowedExtensions.contains(extension)) {
            throw new BaseException(INVALID_FILE_EXTENTION);
        }
    }
    private String uploadFileToS3(MultipartFile file, String contentType) throws IOException {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename;

        InputStream is = file.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is);

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(contentType + "/" + extension);
        metadata.setContentLength(bytes.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        try {
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);
            amazonS3.putObject(putObjectRequest);
        } catch (Exception e) {
            throw new BaseException(PUT_OBJECT_EXCEPTION);
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    public void deleteImageFromS3(String imageAddress){
        String key = getKeyFromImageAddress(imageAddress);
        try{
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        }catch (Exception e){
            throw new BaseException(IO_EXCEPTION_ON_IMAGE_DELETE);
        }
    }

    private String getKeyFromImageAddress(String imageAddress){
        try{
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        }catch (MalformedURLException | UnsupportedEncodingException e){
            throw new BaseException(IO_EXCEPTION_ON_IMAGE_DELETE);
        }
    }
}