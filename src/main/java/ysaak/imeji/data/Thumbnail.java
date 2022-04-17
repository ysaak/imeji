package ysaak.imeji.data;

import java.nio.file.Path;

public class Thumbnail {
    private final String basename;
    private final int width;
    private final int height;

    public Thumbnail(String basename, int width, int height) {
        this.basename = basename;
        this.width = width;
        this.height = height;
    }

    public String getBasename() {
        return basename;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
