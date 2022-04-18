package com.byfan.subsidizesystem.service;

import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName: IFileService
 * @Description: File service层
 * @Author: byfan
 * @Date: 2022/4/18 22:49
 */
public interface IFileService {
    /**
     * @Description 上传文件
     * @Author byfan
     * @Date 2022/4/18 22:50
     * @param multipartFile
     * @return java.lang.String
     * @throws SubsidizeSystemException
     */
    String upload(MultipartFile multipartFile) throws SubsidizeSystemException;

    /**
     * @Description 删除文件
     * @Author byfan
     * @Date 2022/4/18 22:51
     * @param path
     * @return void
     * @throws SubsidizeSystemException
     */
    void delete(String path) throws SubsidizeSystemException;
}
