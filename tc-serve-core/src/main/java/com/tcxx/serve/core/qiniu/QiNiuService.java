package com.tcxx.serve.core.qiniu;

import java.io.File;
import java.io.InputStream;

public interface QiNiuService {

    /**
     * 上传文件
     *
     * @param file
     * @param fileName
     * @return 文件地址（不带域名）
     */
    String uploadFile(File file, String fileName);

    /**
     * 上传文件
     *
     * @param inputStream
     * @param fileName
     * @return 文件地址（不带域名）
     */
    String uploadFile(InputStream inputStream, String fileName);

    /**
     * 删除
     *
     * @param key
     * @return 成功true 失败false
     */
    boolean delete(String key);

}
