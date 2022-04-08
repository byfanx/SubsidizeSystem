package com.byfan.subsidizesystem.service.impl;

import com.byfan.subsidizesystem.bean.VerifyCode;
import com.byfan.subsidizesystem.common.CommonResponse;
import com.byfan.subsidizesystem.exception.SubsidizeSystemException;
import com.byfan.subsidizesystem.service.IVerifyCodeService;
import com.byfan.subsidizesystem.utils.RandomUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

/**
 * @ClassName: VerifyCodeServiceImpl
 * @Description:
 * @Author: byfan
 * @Date: 2022/4/6 21:13
 */
@Slf4j
@Service
public class VerifyCodeServiceImpl implements IVerifyCodeService {

    private static final String[] FONT_TYPES = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };

    private static final int VALICATE_CODE_LENGTH = 4;

    @Value("${staticpath}")
    private String staticpath;

    @Value("${verifycodepath}")
    private String verifyCodePath;

    /**
     * 生成验证码并返回code，将图片写的os中
     *
     * @param width
     * @param height
     * @param os
     * @return
     * @throws SubsidizeSystemException
     */
    @Override
    public String generate(int width, int height, OutputStream os) throws SubsidizeSystemException {
        String randomStr = RandomUtils.randomString(VALICATE_CODE_LENGTH);
        try {
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = image.getGraphics();
            fillBackground(graphics, width, height);
            createCharacter(graphics, randomStr);
            graphics.dispose();
            //设置JPEG格式
            ImageIO.write(image, "JPEG", os);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("生成验证码序列异常！");
            throw new SubsidizeSystemException(CommonResponse.UNKNOWN_ERROR,"生成验证码序列异常！");
        }
        return randomStr;
    }

    /**
     * 生成验证码对象
     *
     * @param width
     * @param height
     * @return
     * @throws SubsidizeSystemException
     */
    @Override
    public VerifyCode generate(int width, int height) throws SubsidizeSystemException {
        VerifyCode verifyCode = null;
        try (
            //将流的初始化放到这里就不需要手动关闭流
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ) {
            String code = generate(width, height, baos);
            String fileName = code+".jpeg";

            File folder = new File(staticpath + verifyCodePath);
            if (!folder.exists() || !folder.isDirectory()){
                folder.mkdirs();
            }

            FileOutputStream fos = new FileOutputStream(staticpath + verifyCodePath + fileName);
            fos.write(baos.toByteArray());

            verifyCode = new VerifyCode();
            verifyCode.setCode(code);
            verifyCode.setImgBytes(baos.toByteArray());
            verifyCode.setUrl(verifyCodePath + fileName);
        } catch (IOException e) {
            e.printStackTrace();
            verifyCode = null;
            log.error("生成验证码异常！");
            throw new SubsidizeSystemException(CommonResponse.UNKNOWN_ERROR,"生成验证码异常！");
        }
        return verifyCode;
    }

    /**
     * 设置字符颜色大小
     *
     * @param g
     * @param randomStr
     */
    private void createCharacter(Graphics g, String randomStr) {
        char[] charArray = randomStr.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            //设置RGB颜色算法参数
            g.setColor(new Color(50 + RandomUtils.nextInt(100),
                    50 + RandomUtils.nextInt(100), 50 + RandomUtils.nextInt(100)));
            //设置字体大小，类型
            g.setFont(new Font(FONT_TYPES[RandomUtils.nextInt(FONT_TYPES.length)], Font.BOLD, 26));
            //设置x y 坐标
            g.drawString(String.valueOf(charArray[i]), 15 * i + 5, 19 + RandomUtils.nextInt(8));
        }
    }


    /**
     * 设置背景颜色及大小，干扰线
     *
     * @param graphics
     * @param width
     * @param height
     */
    private static void fillBackground(Graphics graphics, int width, int height) {
        // 填充背景
        graphics.setColor(Color.WHITE);
        //设置矩形坐标x y 为0
        graphics.fillRect(0, 0, width, height);

        // 加入干扰线条
        for (int i = 0; i < 8; i++) {
            //设置随机颜色算法参数
            graphics.setColor(RandomUtils.randomColor(40, 150));
            Random random = new Random();
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            graphics.drawLine(x, y, x1, y1);
        }
    }
}
