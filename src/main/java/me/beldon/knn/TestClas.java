package me.beldon.knn;

import org.apache.commons.math3.linear.BlockRealMatrix;
import org.apache.commons.math3.linear.RealMatrix;

/**
 * @author Beldon
 * Copyright (c) 2017/11/25, All Rights Reserved.
 * https://beldon.me/
 */
public class TestClas {
    public static void main(String[] args) {
        RealMatrix trainingMat = new BlockRealMatrix(2, 2);
        trainingMat.setRow(0, new double[]{1, 2});
        trainingMat.setRow(1, new double[]{3, 4});
        System.out.println(trainingMat.toString());
        System.out.println(trainingMat.getRowVector(0).getNorm());

        System.out.println(Math.sqrt(17D));
    }
}
