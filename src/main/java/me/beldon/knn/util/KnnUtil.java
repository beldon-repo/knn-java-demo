package me.beldon.knn.util;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Beldon
 * Copyright (c) 2017/11/24, All Rights Reserved.
 * https://beldon.me/
 */
public class KnnUtil {
    /**
     * 数据转向量
     * @param file
     * @return
     */
    public static RealMatrix data2Vec(File file) {
        RealMatrix data = new BlockRealMatrix(1, 32 * 32);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(reader);
            for (int index = 0; index < 32; index++) {
                String str = buffer.readLine();
                int length = str.length();
                for (int i = 0; i < length; i++) {
                    String c = str.substring(i, i + 1);
                    data.setEntry(0, 32 * index + i, Double.valueOf(c));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static int[] classify(RealMatrix testMatrix, RealMatrix trainMatrix, List<Integer> labels, int k) {
        int result[] = new int[2];
        int row = trainMatrix.getRowDimension();
        RealMatrix temp = new BlockRealMatrix(row, testMatrix.getColumnDimension());
        for (int i = 0; i < row; i++) {
            temp.setRowMatrix(i, testMatrix);
        }

        RealMatrix sub = temp.subtract(trainMatrix);
        List<Double> values = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            values.add(sub.getRowVector(i).getNorm());
        }


        double dis[] = new double[k];
        int num[] = new int[k];

        //初始化值
        for (int index = 0; index < k; index++) {
            dis[index] = 32;
            num[index] = -1;
        }

        for (int i = 0; i < values.size(); i++) {
            double v = values.get(i);
            for (int j = 0; j < k; j++) {
                if (v < dis[j]) {
                    dis[j] = v;
                    num[j] = labels.get(i);
                    break;
                }
            }
        }
        result[0] = num[0];
        result[1] = num[1];
        return result;
    }


    public static void main(String[] args) throws Exception {
        List<Integer> labels = new ArrayList<>();
        String[] fileNames = Paths.get("samples/trainingDigits/").toFile().list();
        int length = fileNames.length;

        RealMatrix trainingMat = new BlockRealMatrix(length, 32 * 32);
        //收集训练数据
        for (int i = 0; i < length; i++) {
            String fileName = fileNames[i];
            String fileNameStr = fileName.split("\\.")[0];
            String classNumStr = fileNameStr.split("_")[0];
            labels.add(Integer.parseInt(classNumStr));
            trainingMat.setRowMatrix(i, data2Vec(new File("samples/trainingDigits/" + fileName)));
        }

        String[] testFileNames = Paths.get("samples/testDigits/").toFile().list();
        int errorCount = 0;
        int testSize = testFileNames.length;
        for (String testFileName : testFileNames) {
            String fileNameStr = testFileName.split("\\.")[0];
            String classNumStr = fileNameStr.split("_")[0];
            RealMatrix testMatri = data2Vec(new File("samples/testDigits/" + testFileName));
            int[] result = classify(testMatri, trainingMat, labels, 3);
            int r = result[0];
            System.out.println("预测数是：" + r + "; 正确数是：" + classNumStr);
            if (r != Integer.valueOf(classNumStr)) {
                errorCount++;
            }
        }

        System.out.println("正确率是：" + (double) (testSize - errorCount) / testSize);
    }
}
