package com.jt.service;

import com.jt.vo.FileVo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


@Service   //默认对象是单例的,所以不要就修改成员变量
@PropertySource("classpath:/properties/image.properties")
public class FileServiceImpl implements FileService {
    //定义本地磁盘路径
    @Value("${image.dirPath}")
    private String localPath ;
    @Value("${image.urlPath}")
    private String urlPath ;

    @Override
    public FileVo upload(MultipartFile uploadFile) {
        FileVo fileVo = new FileVo();
        //1.获取文件名称
        String fileName = uploadFile.getOriginalFilename();
        //2.将文件名称统统小写.方便以后判断.
        fileName = fileName.toLowerCase();
        //3.利用正则表达式判断.
        //^ 开始 , $结束   "."除了回车换行之外的任意单个字符.
        //+ 至少一个 , * 表示0个或者多个
        if (!fileName.matches("^.+\\.(png|jpg|gif)$")) {
            //表示文件类型不匹配
            fileVo.setError(1);
            return fileVo;
        }
        //4.判断是否是一个恶意程序
        try {
            BufferedImage image =
                    ImageIO.read(uploadFile.getInputStream());
            //4.1获取宽度和高度
            int width = image.getWidth();
            int height = image.getHeight();
            //4.2 判断属性是否为0
            if (width == 0 || height == 0) {
                fileVo.setError(1);
                return fileVo;
            }
            //5.根据时间生成文件夹
            String dateDir = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            //  E:/jt-upload/2019-04-08
            String localDir = localPath + dateDir;
            File dirFile = new File(localDir);
            if (!dirFile.exists()) {
                dirFile.mkdirs();
            }
            //表示文件夹已经生成
            //6. 防止文件名称重复.
            //6.1 生成UUID
            String uuidName = UUID.randomUUID().toString().replaceAll("-", "");
            //6.2 获取文件类型   进行拼接
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            String realName = uuidName + fileType;
            //6.3 实现文件上传
            File realFile = new File(localDir + "/" + realName);
            uploadFile.transferTo(realFile);
            fileVo.setHeight(height);
            fileVo.setWidth(width);
            //设置图片的虚拟的网路路径
            String realUrlPath = urlPath + dateDir + "/" + realName;
            fileVo.setUrl(realUrlPath);

        } catch (IOException e) {
            e.printStackTrace();
            //表示为恶意程序
            fileVo.setError(1);
            return fileVo;
        }
        fileVo.setError(0);
        return fileVo;
    }
}
