package me.beldon.knn.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author Beldon
 * Copyright (c) 2017/11/25, All Rights Reserved.
 * https://beldon.me/
 */
public class JavaWalkBufferedImageTest1 extends Component {

    public static void main(String[] foo) {
        new JavaWalkBufferedImageTest1();
    }

    public void printPixelARGB(int pixel) {
        int alpha = (pixel >> 24) & 0xff;
        int red = (pixel >> 16) & 0xff;
        int green = (pixel >> 8) & 0xff;
        int blue = (pixel) & 0xff;
        System.out.println("argb: " + alpha + ", " + red + ", " + green + ", " + blue);
        if (alpha != 0 || red != 0 || green != 0 || blue != 0) {
            System.out.println("dd");
        }
    }

    private void marchThroughImage(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        System.out.println("width, height: " + w + ", " + h);

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                System.out.println("x,y: " + j + ", " + i);
                int pixel = image.getRGB(j, i);
                printPixelARGB(pixel);
                System.out.println("");
            }
        }
    }

    public JavaWalkBufferedImageTest1() {
        try {
            // get the BufferedImage, using the ImageIO class
            BufferedImage image = ImageIO.read(new File("2.png"));
            marchThroughImage(image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}