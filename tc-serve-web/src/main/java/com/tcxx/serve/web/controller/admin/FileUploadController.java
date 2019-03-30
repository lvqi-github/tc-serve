package com.tcxx.serve.web.controller.admin;

import com.tcxx.serve.core.annotation.AdminLoginTokenValidation;
import com.tcxx.serve.core.annotation.PassRequestSignValidation;
import com.tcxx.serve.core.exception.CustomRuntimeException;
import com.tcxx.serve.core.qiniu.QiNiuProperties;
import com.tcxx.serve.core.qiniu.QiNiuService;
import com.tcxx.serve.core.result.Result;
import com.tcxx.serve.core.result.ResultBuild;
import com.tcxx.serve.core.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/admin/upload")
public class FileUploadController {

    @Autowired
    private QiNiuService qiNiuService;

    @Autowired
    private QiNiuProperties qiNiuProperties;

    @PassRequestSignValidation
    @AdminLoginTokenValidation
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public Result<Map<String, String>> fileUpload(@RequestParam("file") MultipartFile multiple, String fileType) {
        if (multiple.isEmpty()){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "文件不能为空");
        }
        if (StringUtils.isBlank(fileType)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "fileType不能为空");
        }
        String fileName = multiple.getOriginalFilename();
        String qiNiuFileName = fileType + "/" + UUID.randomUUID().toString().replaceAll("-", "") + fileName.substring(fileName.lastIndexOf("."));

        try {
            FileInputStream inputStream = (FileInputStream) multiple.getInputStream();
            String filePath = qiNiuService.uploadFile(inputStream, qiNiuFileName);

            Map<String, String> map = new HashMap<>();
            map.put("name", filePath);
            map.put("url", String.format("//%s/%s", qiNiuProperties.getCdnPrefixOfBucket(), filePath));

            Result<Map<String, String>> result = ResultBuild.wrapSuccess();
            result.setValue(map);
            return result;
        } catch (IOException e) {
            log.error("FileUploadController#qiNiuUpload error", e);
            throw new CustomRuntimeException(ResultCodeEnum.ERROR5002);
        }
    }

    @AdminLoginTokenValidation
    @RequestMapping(value = "/deleteFile", method = RequestMethod.POST)
    public Result<Boolean> deleteFile(String fileName) {
        if (StringUtils.isBlank(fileName)){
            return ResultBuild.wrapResult(ResultCodeEnum.ERROR4001, "文件名不能为空");
        }
        boolean res = qiNiuService.delete(fileName);
        Result<Boolean> result = ResultBuild.wrapSuccess();
        result.setValue(res);
        return result;
    }
}
