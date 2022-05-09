package ysaak.imeji.view.dto;

import java.util.List;

public class WallpaperViewDto {
    private final String id;
    private final int width;
    private final int height;
    private final long fileSize;
    private final List<Color> palette;
    private final List<Tag> tags;

    public WallpaperViewDto(String id, int width, int height, long fileSize, List<Color> palette, List<Tag> tags) {
        this.id = id;
        this.width = width;
        this.height = height;
        this.fileSize = fileSize;
        this.palette = palette;
        this.tags = tags;
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

    public List<Tag> getTags() {
        return tags;
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

    public static class Tag {
        private final String name;
        private final String type;

        public Tag(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }
    }

}
