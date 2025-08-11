//package com.instar.common.util;
//import org.springframework.web.multipart.MultipartFile;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.*;
//
//public class FileUploadUtil {
//    public static String saveFile(String uploadDir, MultipartFile multipartFile) throws IOException {
//        String filename = System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
//        Path uploadPath = Paths.get(uploadDir);
//
//        if (!Files.exists(uploadPath)) {
//            Files.createDirectories(uploadPath);
//        }
//
//        try {
//            Files.copy(multipartFile.getInputStream(), uploadPath.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
//        } catch (IOException e) {
//            throw new IOException("Không thể lưu file: " + filename, e);
//        }
//        return filename;
//    }
//}
