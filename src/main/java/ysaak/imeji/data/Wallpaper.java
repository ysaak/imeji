package ysaak.imeji.data;

import java.nio.file.Path;
import java.util.Objects;

public class Wallpaper {
    private final String basename;

    private final int width;
    private final int height;

    private final int thumbnailWidth;
    private final int thumbnailHeight;

    public Wallpaper(String basename, int width, int height, int thumbnailWidth, int thumbnailHeight) {
        this.basename = basename;
        this.width = width;
        this.height = height;
        this.thumbnailWidth = thumbnailWidth;
        this.thumbnailHeight = thumbnailHeight;
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

    public int getThumbnailWidth() {
        return thumbnailWidth;
    }

    public int getThumbnailHeight() {
        return thumbnailHeight;
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
