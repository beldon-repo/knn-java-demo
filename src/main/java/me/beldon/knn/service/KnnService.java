package me.beldon.knn.service;

import me.beldon.knn.util.CompareData;
import me.beldon.knn.util.KnnUtil;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Beldon
 * Copyright (c) 2017/11/25, All Rights Reserved.
 * https://beldon.me/
 */
@Service
public class KnnService {
    private List<Integer> labels = new ArrayList<>();
    private RealMatrix trainingMat = null;


    private List<CompareData> train(RealVector vec) {
        if (trainingMat == null) {
            loadTrainData();
        }
        return KnnUtil.classify(vec, trainingMat, labels);
    }


    public Integer alike(RealVector vec) {
        return alike(vec,1).get(0).getNum();
    }

    /**
     * 相似数
     * @param vec
     * @param k
     * @return
     */
    public List<CompareData> alike(RealVector vec, int k) {
        List<CompareData> compareData = train(vec);
        List<CompareData> respData = new ArrayList<>();
        compareData = compareData.stream().sorted(Comparator.comparing(CompareData::getDistance)).collect(Collectors.toList());
        Set<Integer> cache = new HashSet<>();
        int count = 0;
        for (int i = 0; i < compareData.size(); i++) {
            CompareData data = compareData.get(i);
            if (!cache.contains(data.getNum())) {
                cache.add(data.getNum());
                count++;
                respData.add(data);
                if (count >= k) {
                    break;
                }
            }
        }
        return respData;
    }

    /**
     * 加载训练数据
     */
    private void loadTrainData() {
        String[] fileNames = Paths.get("samples/trainingDigits/").toFile().list();
        int length = fileNames.length;

        //收集训练数据
        trainingMat = new BlockRealMatrix(length, 32 * 32);
        for (int i = 0; i < length; i++) {
            String fileName = fileNames[i];
            String fileNameStr = fileName.split("\\.")[0];
            String classNumStr = fileNameStr.split("_")[0];
            labels.add(Integer.parseInt(classNumStr));
            trainingMat.setRowVector(i, KnnUtil.data2Vec(new File("samples/trainingDigits/" + fileName)));
        }
    }
}
