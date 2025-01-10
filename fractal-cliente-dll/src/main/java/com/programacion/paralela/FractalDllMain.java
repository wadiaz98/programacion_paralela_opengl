package com.programacion.paralela;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class FractalDllMain {

    static int WIDTH = 1600;
    static int HEIGHT = 900;

    public static void main(String[] args) throws Exception {
        int[] pixels = new int[WIDTH * HEIGHT];
        int num_iterations = 100;

        FractalDll.INSTACE.mandelbrotCpu(pixels, num_iterations);

        //--------------------------------------------------------------------------------
        BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
        image.setRGB(0, 0, WIDTH, HEIGHT, pixels, 0, WIDTH);

        //--------------------------------------------------------------------------------
        File archivo = new File("mandelbrot-cpp.png");
        ImageIO.write(image, "png", archivo);
    }
}
