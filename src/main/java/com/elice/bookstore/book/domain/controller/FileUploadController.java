package com.elice.bookstore.book.domain.controller;

import com.elice.bookstore.book.domain.service.FileUploadService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * FileUpload.
 */
@RestController
public class FileUploadController {


  @Autowired
  private FileUploadService fileUploadService;


  /**
   * upload file.
   *
   * @param file .
   * @return file .
   */
  @PostMapping("/api/v1/upload")
  public String uploadFile(@RequestParam("file") MultipartFile file) {
    try {
      return fileUploadService.uploadFile(file);
    } catch (IOException e) {
      return "파일 업로드에 실패했습니다: " + e.getMessage();
    }
  }
}
