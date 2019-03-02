package com.tcxx.serve.core.qiniu;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.tcxx.serve.core.exception.QiNiuRuntimeException;
import com.tcxx.serve.core.result.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;

@Slf4j
@Service
public class QiNiuServiceImpl implements QiNiuService {

    @Autowired
    private UploadManager uploadManager;

    @Autowired
    private BucketManager bucketManager;

    @Autowired
    private Auth auth;

    @Autowired
    private QiNiuProperties qiNiuProperties;

    @Override
    public String uploadFile(File file, String fileName) {
        Response response;
        try {
            response = this.uploadManager.put(file, fileName, getUploadToken());
            int retry = 0;
            while (response.needRetry() && retry < 3) {
                response = this.uploadManager.put(file, fileName, getUploadToken());
                retry++;
            }
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException e) {
            log.error("QiNiuServiceImpl#uploadFile error", e);
            throw new QiNiuRuntimeException(ResultCodeEnum.ERROR5001);
        }
    }

    @Override
    public String uploadFile(InputStream inputStream, String fileName) {
        Response response;
        try {
            response = this.uploadManager.put(inputStream, fileName, getUploadToken(), null, null);
            int retry = 0;
            while (response.needRetry() && retry < 3) {
                response = this.uploadManager.put(inputStream, fileName, getUploadToken(), null, null);
                retry++;
            }
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            return putRet.key;
        } catch (QiniuException e) {
            log.error("QiNiuServiceImpl#uploadFile error", e);
            throw new QiNiuRuntimeException(ResultCodeEnum.ERROR5001);
        }
    }

    @Override
    public boolean delete(String key) {
        Response response;
        try {
            response = bucketManager.delete(qiNiuProperties.getBucket(), key);
            int retry = 0;
            while (response.needRetry() && retry++ < 3) {
                response = bucketManager.delete(qiNiuProperties.getBucket(), key);
            }
            return true;
        } catch (QiniuException e) {
            log.error("QiNiuServiceImpl#delete error", e);
            throw new QiNiuRuntimeException(ResultCodeEnum.ERROR5001);
        }
    }

    /**
     * 获取上传凭证
     *
     * @return
     */
    private String getUploadToken() {
        return this.auth.uploadToken(qiNiuProperties.getBucket());
    }

}
