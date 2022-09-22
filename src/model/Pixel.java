package model;

public class Pixel {
    int RED, GREEN, BLUE;

    public Pixel(int RED, int GREEN, int BLUE) {
        this.RED = RED;
        this.GREEN = GREEN;
        this.BLUE = BLUE;
    }

    public Pixel() {
    }

    public int getBLUE() {
        return BLUE;
    }

    public int getGREEN() {
        return GREEN;
    }

    public int getRED() {
        return RED;
    }

    public void setBLUE(int BLUE) {
        this.BLUE = BLUE;
    }

    public void setGREEN(int GREEN) {
        this.GREEN = GREEN;
    }

    public void setRED(int RED) {
        this.RED = RED;
    }
}
