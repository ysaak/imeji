package ysaak.imeji.dto;

import java.util.List;

public class WallpaperViewDto {
    private final String id;
    private final int width;
    private final int height;
    private final long fileSize;
    private final List<Color> palette;

    public WallpaperViewDto(String id, int width, int height, long fileSize, List<Color> palette) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.fileSize = fileSize;
        this.palette = palette;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getFileSize() {
        return fileSize;
    }

    public List<Color> getPalette() {
        return palette;
    }

    public static class Color {
        private final int order;
        private final String color;

        public Color(int order, String color) {
            this.order = order;
            this.color = color;
        }

        public int getOrder() {
            return order;
        }

        public String getColor() {
            return color;
        }
    }

}
