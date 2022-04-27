package ysaak.imeji.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Path;

@Configuration
public class ApplicationConfig {
    @Value("${imeji.import.directory}")
    private Path importDirectory;

    @Value("${imeji.import.error-directory}")
    private Path importErrorDirectory;

    @Value("${imeji.storage.wallpaper}")
    private Path wallpaperStoragePath;

    @Value("${imeji.storage.thumbnail}")
    private Path thumbnailStoragePath;

    public Path getImportDirectory() {
        return importDirectory;
    }

    public void setImportDirectory(Path importDirectory) {
        this.importDirectory = importDirectory;
    }

    public Path getImportErrorDirectory() {
        return importErrorDirectory;
    }

    public void setImportErrorDirectory(Path importErrorDirectory) {
        this.importErrorDirectory = importErrorDirectory;
    }

    public Path getWallpaperStoragePath() {
        return wallpaperStoragePath;
    }

    public void setWallpaperStoragePath(Path wallpaperStoragePath) {
        this.wallpaperStoragePath = wallpaperStoragePath;
    }

    public Path getThumbnailStoragePath() {
        return thumbnailStoragePath;
    }

    public void setThumbnailStoragePath(Path thumbnailStoragePath) {
        this.thumbnailStoragePath = thumbnailStoragePath;
    }

    public int getThumbnailWidth() {
        return 300;
    }

    public int getThumbnailHeight() {
        return 200;
    }
}
