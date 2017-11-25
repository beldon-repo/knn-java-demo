package me.beldon.knn.util;

/**
 * @author Beldon
 * Copyright (c) 2017/11/25, All Rights Reserved.
 * https://beldon.me/
 */
public class CompareData {
    private final int num;
    private final double distance;

    public CompareData(int num, double distance) {
        this.num = num;
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }

    public int getNum() {
        return num;
    }
}
