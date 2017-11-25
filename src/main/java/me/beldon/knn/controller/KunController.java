package me.beldon.knn.controller;

import me.beldon.knn.service.KnnService;
import me.beldon.knn.util.CompareData;
import me.beldon.knn.util.ImageUtils;
import me.beldon.knn.util.KnnUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * @author Beldon
 * Copyright (c) 2017/11/25, All Rights Reserved.
 * https://beldon.me/
 */
@RestController
public class KunController {

    @Autowired
    private KnnService knnService;

    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        String fileName = UUID.randomUUID().toString() + ".png";
        File tempFile = new File("temp/" + fileName);
        ImageUtils.zoomImage(file.getInputStream(), tempFile, 32, 32);
        Integer res = knnService.alike(KnnUtil.image2Vec(tempFile));
        return String.valueOf(res);
    }

    @PostMapping("/uploadBase64")
    public List<CompareData> uploadBase64(@RequestParam("image") String base64) throws Exception {

        String[] d = base64.split("base64,");
        String image = d[1];
        byte[] bytes = Base64.getDecoder().decode(image);
        String fileName = UUID.randomUUID().toString() + ".png";
        File base64File = new File("temp/base64/" + fileName);
        FileOutputStream baseOutput = new FileOutputStream(base64File);
        try {
            StreamUtils.copy(bytes, baseOutput);
        }finally {
            baseOutput.close();
        }

        String tempFileName = UUID.randomUUID().toString() + ".png";
        File tempFile = new File("temp/" + tempFileName);
        FileInputStream fileInputStream = new FileInputStream(base64File);
        try {
            ImageUtils.zoomImage(fileInputStream, tempFile, 32, 32);
        }finally {
            fileInputStream.close();
        }
        Integer res = knnService.alike(KnnUtil.image2Vec(tempFile));
        List<CompareData> alike = knnService.alike(KnnUtil.image2Vec(tempFile), 3);
        for (CompareData compareData : alike) {
            System.out.println(compareData.getNum()+":"+compareData.getDistance());
        }

        return alike;
    }
}
