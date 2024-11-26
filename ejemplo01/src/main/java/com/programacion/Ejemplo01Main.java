package com.programacion;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Ejemplo01Main {

    private static long window;
    static int textureID;

    static void run() {
        System.out.println("LWJGL " + Version.getVersion());

        init();

        loop();
    }

    static void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Create the window
        window = glfwCreateWindow(Params.WIDTH, Params.HEIGHT, "Ejemplo 01!", NULL, NULL);

        if (window == NULL)
            throw new RuntimeException("Failed to create the GLFW window");

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE)
                glfwSetWindowShouldClose(window, true);
        });

        glfwSetFramebufferSizeCallback(window, (window, width, height) -> {
            glViewport(0, 0, width, height);
        });

        {
            // centrar ventana
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - Params.WIDTH) / 2,
                    (vidmode.height() - Params.HEIGHT) / 2
            );
        }

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);

        GL.createCapabilities();

        String version = glGetString(GL_VERSION);
        String vendor = glGetString(GL_VENDOR);
        String renderer = glGetString(GL_RENDERER);

        System.out.println("OpenGL version: " + version);
        System.out.println("OpenGL vendor: " + vendor);
        System.out.println("OpenGL renderer: " + renderer);

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-1, 1, -1, 1, -1, 1);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        glEnable(GL_TEXTURE_2D);

        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);

        initTexteures();
    }

    static void initTexteures() {
        textureID = glGenTextures();

        glBindTexture(GL_TEXTURE_2D, textureID);

        glTexImage2D(GL_TEXTURE_2D,
                0,
                GL_RGBA8,
                Params.WIDTH, Params.HEIGHT, 0,
                GL_RGBA,
                GL_UNSIGNED_BYTE,
                NULL
        );

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        glBindTexture(GL_TEXTURE_2D, 0);
    }

    static void loop() {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            paint();

            glfwSwapBuffers(window);

            glfwPollEvents();
        }
    }


    //--------------------------------------------------------------------------------
    static int pixel_buffer[] = new int[Params.WIDTH*Params.HEIGHT]; // size= WIDTHxHEIGHT

    static void mandelbrotCpu() {
        int black = 0x000000;
        int white = 0xFFFFFF;

        for (int px = 0; px < Params.WIDTH; px++) {
            for (int py = 0; py < Params.HEIGHT; py++) {

                double x0 = Params.xMin + (Params.xMax - Params.xMin) * px / Params.WIDTH;
                double y0 = Params.yMin + (Params.yMax - Params.yMin) * py / Params.HEIGHT;

                double x = 0.0;
                double y = 0.0;
                int iteration = 0;
                int maxIteration = 2;
                double R = 2.0;


                while (x*x + y*y <= R*R && iteration < maxIteration) {
                    double tempX = x*x - y*y + x0;
                    y = 2*x*y + y0;
                    x = tempX;
                    iteration++;
                }


                pixel_buffer[py * Params.WIDTH + px] = (iteration == maxIteration) ? black : white;
            }
        }

        // Dibuja la textura
        glEnable(GL_TEXTURE_2D);
        glBindTexture(GL_TEXTURE_2D, textureID);
        glTexImage2D(GL_TEXTURE_2D,
                0,
                GL_RGBA,
                Params.WIDTH, Params.HEIGHT, 0,
                GL_RGBA,
                GL_UNSIGNED_BYTE,
                pixel_buffer);

        glBegin(GL_QUADS);
        {
            glTexCoord2f(0, 1);
            glVertex3f(-1, -1, 0);

            glTexCoord2f(0, 0);
            glVertex3f(-1, 1, 0);

            glTexCoord2f(1, 0);
            glVertex3f(1, 1, 0);

            glTexCoord2f(1, 1);
            glVertex3f(1, -1, 0);
        }
        glEnd();
    }

    //--------------------------------------------------------------------------------

    static void paint() {
        mandelbrotCpu();

//        glBegin(GL_TRIANGLES);
//        {
//            glVertex2d(-1, -1);
//            glVertex2d(0, 0);
//            glVertex2d(0, -1);
//        }
//        glEnd();
    }

    public static void main(String[] args) {
        run();
    }
}
