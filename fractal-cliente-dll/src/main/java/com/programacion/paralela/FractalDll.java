package com.programacion.paralela;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface FractalDll extends Library{

    String LIBRARY_NAME = "libfractal_dll";
    FractalDll INSTACE = Native.loadLibrary(LIBRARY_NAME, FractalDll.class);

    //--------------------------------------------------------------------------------

    void mandelbrotCpu(int[] pixels, int maxIterations);



}
