package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.service.IFileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.UUID;

/**
 * @ClassName: FileServiceImpl
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/18 22:52
 */
@Slf4j
@Service
public class FileServiceImpl implements IFileService {
    @Value("${staticpath}")
    private String staticPath;

    @Value("${imagepath}")
    private String imagePath;

    @Value("${noPicturesYetPath}")
    private String noPicturesYetPath;

    @Value("#{'${allowedfiletypes:}'.split(',')}")
    private List<String> allowedfiletypes;



    /**
     * @Description 上传文件
     * @Author byfan
     * @Date 2022/4/18 22:50
     * @param multipartFile
     * @return java.lang.String
     * @throws SubsidizeSystemException
     */
    @Override
    public String upload(MultipartFile multipartFile) throws SubsidizeSystemException {
        String originalFilename = multipartFile.getOriginalFilename();
        String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
        log.info("upload file, fileName={}, fileType={}", originalFilename, fileType);
        if (!allowedfiletypes.contains(fileType)){
            log.error("upload file format error, fileName={}", originalFilename);
            throw new SubsidizeSystemException(CommonResponse.FILE_FORMAT_ERROR,"文件格式不支持");
        }
        String uuid = getUuid();
        String uuidFileName = uuid + fileType;
        try{
            File filePath = new File(staticPath + imagePath);
            if (!filePath.isDirectory()){
                filePath.mkdir();
            }

            File saveFile = new File(staticPath + imagePath + uuidFileName);
            OutputStream out = new FileOutputStream(saveFile);
            byte[] bytes = multipartFile.getBytes();
            out.write(bytes);
            out.close();

            return imagePath + uuidFileName;

        }catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @Description 删除文件
     * @Author byfan
     * @Date 2022/4/18 22:51
     * @param path
     * @return void
     * @throws SubsidizeSystemException
     */
    @Override
    public void delete(String path) throws SubsidizeSystemException {
        log.info("delete file, path={}", path);
        if (StringUtils.isBlank(path)){
            log.error("delete path is null");
            throw new SubsidizeSystemException(CommonResponse.PARAM_ERROR,"path is null");
        }
        File deleteFile = new File(staticPath + path);
        if (!deleteFile.exists()){
            log.error("delete file is not exist");
            throw new SubsidizeSystemException(CommonResponse.RESOURCE_NOT_EXIST,"文件不存在");
        }
        if (!path.equals(noPicturesYetPath)){
            deleteFile.delete();
        }
    }


    /**
     * @Description 获取uuid
     * @Author byfan
     * @Date 2022/4/18 23:51
     * @param
     * @return java.lang.String
     * @throws
     */
    public String getUuid(){
        String uuid = UUID.randomUUID().toString();
        String[] split = uuid.split("-");
        String result = "";
        for (String s : split){
            result += s;
        }
        return result;
    }
}
