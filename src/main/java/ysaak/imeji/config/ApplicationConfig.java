package ysaak.imeji.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {
    @Value("${imeji.wallpaper.path}")
    private String wallpaperLocalPath;

    @Value("${imeji.thumbnail.path}")
    private String thumbnailLocalPath;

    @Value("${imeji.thumbnail.size}")
    private int thumbnailSize;

    public String getWallpaperLocalPath() {
        return wallpaperLocalPath;
    }

    public void setWallpaperLocalPath(String wallpaperLocalPath) {
        this.wallpaperLocalPath = wallpaperLocalPath;
    }

    public String getThumbnailLocalPath() {
        return thumbnailLocalPath;
    }

    public void setThumbnailLocalPath(String thumbnailLocalPath) {
        this.thumbnailLocalPath = thumbnailLocalPath;
    }

    public int getThumbnailSize() {
        return thumbnailSize;
    }

    public void setThumbnailSize(int thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }
}
