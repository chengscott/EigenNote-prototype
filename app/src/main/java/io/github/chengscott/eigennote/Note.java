package io.github.chengscott.eigennote;

public class Note {
    private int x, y, width, height, image_fk;
    private String type;

    public Note(int x, int y, int width, int height, String type, int image_fk) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.type = type;
        this.image_fk = image_fk;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getImage_fk() {
        return image_fk;
    }

    public String getType() {
        return type;
    }
}
