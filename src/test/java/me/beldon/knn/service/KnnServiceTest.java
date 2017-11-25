package me.beldon.knn.service;

import me.beldon.knn.util.CompareData;
import me.beldon.knn.util.KnnUtil;
import org.apache.commons.math3.linear.RealVector;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

/**
 * @author Beldon
 * Copyright (c) 2017/11/25, All Rights Reserved.
 * https://beldon.me/
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KnnServiceTest {
    @Autowired
    private KnnService knnService;

    @Test
    public void train() {
    }

    @Test
    public void alike() {
        RealVector testVec = KnnUtil.data2Vec(new File("2.txt"));
        Integer res = knnService.alike(testVec);
        System.out.println(res);

        for (CompareData compareData : knnService.alike(testVec, 5)) {
            System.out.println(compareData.getNum() + ":" + compareData.getDistance());
        }
    }
}