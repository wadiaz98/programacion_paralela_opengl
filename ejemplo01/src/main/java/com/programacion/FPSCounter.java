package com.programacion;

public class FPSCounter {

    int frames;
    int fps;
    long lastTime;

    public FPSCounter (){
       lastTime = System.currentTimeMillis();
       fps = 0;
       frames = 0;
    }

    public void update(){
        frames++;
         Long currentTime = System.currentTimeMillis();

         if( currentTime - lastTime > 1000){
             fps = frames;
             frames = 0;
             lastTime = currentTime;
             System.out.println("FPS: " + fps);
         }
    }
}
