package me.beldon.knn.util;

import org.apache.commons.math3.linear.ArrayRealVector;
import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.linear.RealVector;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Beldon
 * Copyright (c) 2017/11/24, All Rights Reserved.
 * https://beldon.me/
 */
public class KnnUtil {

    public static RealVector image2Vec(File file) throws Exception {
        ArrayRealVector data = new ArrayRealVector(32 * 32);
        BufferedImage bi = ImageIO.read(file);
        int width = bi.getWidth();
        int height = bi.getHeight();
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = bi.getRGB(j, i);
                pixel = pixel == 0 ? 0 : 1;
                data.setEntry(32 * i + j, pixel);
            }
        }
        return data;
    }

    /**
     * 数据转向量
     *
     * @param file
     * @return
     */
    public static RealVector data2Vec(File file) {
        ArrayRealVector data = new ArrayRealVector(32 * 32);
        try {
            FileReader reader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(reader);
            for (int index = 0; index < 32; index++) {
                String str = buffer.readLine();
                int length = str.length();
                for (int i = 0; i < length; i++) {
                    String c = str.substring(i, i + 1);
                    data.setEntry(32 * index + i, Double.valueOf(c));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }


    public static List<CompareData> classify(RealVector testVec, RealMatrix trainMatrix, List<Integer> labels) {
        List<CompareData> compareData = new ArrayList<>();
        int row = trainMatrix.getRowDimension();
        //初始化一个与训练矩阵同大的矩阵，每一行的向量都是 testVec
        RealMatrix temp = new BlockRealMatrix(row, testVec.getDimension());
        for (int i = 0; i < row; i++) {
            temp.setRowVector(i, testVec);
        }

        //矩阵美个元素相减
        RealMatrix sub = temp.subtract(trainMatrix);
        List<Double> values = new ArrayList<>();

        for (int i = 0; i < row; i++) {
            values.add(sub.getRowVector(i).getNorm());   //getNorm() 向量每个元素平方和开根号
        }

        for (int i = 0; i < values.size(); i++) {
            double v = values.get(i);
            compareData.add(new CompareData(labels.get(i), v));
        }

        return compareData;
    }

    public static void main(String[] args) throws Exception{
        image2Vec(new File("2.png"));
    }


//    public static void main(String[] args) {
//        List<Integer> labels = new ArrayList<>();
//        String[] fileNames = Paths.get("samples/trainingDigits/").toFile().list();
//        int length = fileNames.length;
//
//        //收集训练数据
//        RealMatrix trainingMat = new BlockRealMatrix(length, 32 * 32);
//        for (int i = 0; i < length; i++) {
//            String fileName = fileNames[i];
//            String fileNameStr = fileName.split("\\.")[0];
//            String classNumStr = fileNameStr.split("_")[0];
//            labels.add(Integer.parseInt(classNumStr));
//            trainingMat.setRowVector(i, data2Vec(new File("samples/trainingDigits/" + fileName)));
//        }
//
//        String[] testFileNames = Paths.get("samples/testDigits/").toFile().list();
//        int errorCount = 0;
//        int testSize = testFileNames.length;
//        for (String testFileName : testFileNames) {
//            String fileNameStr = testFileName.split("\\.")[0];
//            String classNumStr = fileNameStr.split("_")[0];
//            RealVector testVec = data2Vec(new File("samples/testDigits/" + testFileName));
//            int[] result = classify(testVec, trainingMat, labels, 3);
//            int r = result[0];
//            System.out.println("预测数是：" + r + "; 正确数是：" + classNumStr);
//            if (r != Integer.valueOf(classNumStr)) {
//                errorCount++;
//            }
//        }
//
//        System.out.println("正确率是：" + (double) (testSize - errorCount) / testSize);
//        RealVector testVec = data2Vec(new File("2.txt"));
//        long start = System.currentTimeMillis();
//        System.out.println(classify(testVec, trainingMat, labels, 3)[0]);
//        long end = System.currentTimeMillis();
//        System.out.println((double)(end-start)/1000);
//    }
}
