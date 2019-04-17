package com.jt.controller;

import com.jt.service.FileService;
import com.jt.vo.FileVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Controller
public class FileController {

    @Autowired
    private FileService fileService;

    //要求:当实现了文件上传之后
    //要求重定向到文件上传页面
    @RequestMapping("file")
    public String fileImage(MultipartFile fileImage) throws IOException {
        //步骤: 1.获取文件的名称
        String filename = fileImage.getOriginalFilename();
        System.out.println("获取文件名称:" + filename);
        //      2.指定文件的目录 判断文件是否存在
        String filePath = "E:/jt-upload";
        File dirFile = new File(filePath);
        //如果文件夹不存在
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        //      3.实现文件上传
        // E:/jt-upload/abc.jpg
        String realName = "E:/jt-upload/" + filename;
        System.out.println("真实地址:"+realName);
        fileImage.transferTo(new File(realName));
        return "redirect:/file.jsp";
                //foward:/file.jsp
    }

    //实现商品文件上传
    @RequestMapping("/pic/upload")
    @ResponseBody
    public FileVo uploadFile(MultipartFile uploadFile) {
        return fileService.upload(uploadFile);
    }

}
