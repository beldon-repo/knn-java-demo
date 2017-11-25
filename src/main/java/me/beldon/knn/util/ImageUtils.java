package me.beldon.knn.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

/**
 * @author Beldon
 * Copyright (c) 2017/11/25, All Rights Reserved.
 * https://beldon.me/
 */
public class ImageUtils {
    public static void zoomImage(InputStream srcFile, File destFile, int w, int h) throws Exception {
        double wr, hr;
        BufferedImage bufImg = ImageIO.read(srcFile); //读取图片
        Image Itemp;//设置缩放目标图片模板

        wr = w * 1.0 / bufImg.getWidth();     //获取缩放比例
        hr = h * 1.0 / bufImg.getHeight();

        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);
        Itemp = ato.filter(bufImg, null);
        String destName = destFile.getName();
        ImageIO.write((BufferedImage) Itemp,destName.substring(destName.lastIndexOf(".")+1), destFile); //写入缩减后的图片
    }




    public static void main(String[] args) throws Exception {
//        zoomImage(new File("123.png"), new File("2.png"), 32, 32);
    }
}
