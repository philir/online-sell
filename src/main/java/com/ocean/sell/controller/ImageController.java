package com.ocean.sell.controller;

import com.ocean.sell.VO.ResultVO;
import com.ocean.sell.config.ImageConfig;
import com.ocean.sell.enums.ResultEnum;
import com.ocean.sell.exception.SellException;
import com.ocean.sell.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/buyer")
public class ImageController {

    @Autowired
    private ImageConfig imageConfig;

    @PostMapping("/upload")
    public ResultVO upload(@RequestParam(name = "file", required = false) MultipartFile file) {
        if (file == null) {
            log.error("[上传图片]: 图片为空 file={}",file);
            throw new SellException(ResultEnum.FILE_IS_NULL);
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            log.error("[上传图片]: 图片大小不能超过10M");
            throw new SellException(ResultEnum.FILE_SIZE_ERROR);
        }
        //获取文件后缀
        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1, file.getOriginalFilename().length());
        if (!"jpg,jpeg,gif,png".toUpperCase().contains(suffix.toUpperCase())) {
            log.error("[上传图片]: 图片格式不对");
            throw new SellException(ResultEnum.FILE_FORMAT_ERROR);
        }
        String savePath = imageConfig.getUploadFolder();
        File savePathFile = new File(savePath);
        if (!savePathFile.exists()) {
            //若不存在该目录，则创建目录
            savePathFile.mkdir();
        }
        //通过UUID生成唯一文件名
        String filename = UUID.randomUUID().toString().replaceAll("-","") + "." + suffix;
        try {
            //将文件保存指定目录
            file.transferTo(new File(savePath + filename));
        } catch (Exception e) {
            e.printStackTrace();
            throw new SellException(ResultEnum.FILE_UPLOAD_FAIL);
        }
        //返回文件名称
        return ResultVOUtil.success(filename);
    }
}
