package ysaak.imeji.data;

import java.nio.file.Path;
import java.util.Objects;

public class Wallpaper {
    private final String basename;

    private final int width;
    private final int height;

    private final Thumbnail thumbnail;

    public Wallpaper(String basename, int width, int height, Thumbnail thumbnail) {
        this.basename = basename;
        this.width = width;
        this.height = height;
        this.thumbnail = thumbnail;
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

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Wallpaper wallpaper = (Wallpaper) o;
        return Objects.equals(basename, wallpaper.basename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(basename);
    }
}
