package dev.ogabek.java.model;

public class ScreenSize {
     private int width;
     private int heigh;

    public ScreenSize(int width, int heigh) {
        this.width = width;
        this.heigh = heigh;
    }

    public int getWidth() {
        return width;
    }

    public int getHeigh() {
        return heigh;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeigh(int heigh) {
        this.heigh = heigh;
    }
}
