package com.nazunamoe.microdustapplication;

import android.graphics.Color;

public class LEDColor {
    public int red;
    public int green;
    public int blue;

    public LEDColor(Color a){
        this.red = (int)a.red();
        this.green = (int)a.green();
        this.blue = (int)a.blue();
    }

    public LEDColor(int r, int g, int b){
        this.red = r;
        this.green = g;
        this.blue = b;
    }

    public Color makecolor(int red, int green, int blue){
        return Color.valueOf(red,green,blue);
    }
}
