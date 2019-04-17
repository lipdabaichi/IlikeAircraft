package com.jt.service;

import com.jt.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    FileVo upload(MultipartFile uploadFile);
}
