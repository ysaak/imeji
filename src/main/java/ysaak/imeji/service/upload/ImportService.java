package ysaak.imeji.service.upload;

import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import ysaak.imeji.config.ApplicationConfig;
import ysaak.imeji.data.Wallpaper;
import ysaak.imeji.data.WallpaperColor;
import ysaak.imeji.exception.ImportException;
import ysaak.imeji.service.wallpaper.WallpaperService;
import ysaak.imeji.utils.ColorConverter;
import ysaak.imeji.utils.ImagePHash;
import ysaak.imeji.utils.colorthief.ColorThief;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class ImportService {

    private final ApplicationConfig applicationConfig;

    private final WallpaperService wallpaperService;

    public ImportService(ApplicationConfig applicationConfig, WallpaperService wallpaperService) {
        this.applicationConfig = applicationConfig;
        this.wallpaperService = wallpaperService;
    }

    public Wallpaper importImage(byte[] fileContent, long fileSize) throws ImportException {
        Wallpaper wallpaper = new Wallpaper();
        wallpaper.setFileSize(fileSize);

        final BufferedImage image;
        try (ByteArrayInputStream is = new ByteArrayInputStream(fileContent)) {
            image = ImageIO.read(is);
        }
        catch (IOException e) {
            throw new ImportException(ImportException.ImportError.READ_IMAGE, "Error while reading image", e);
        }

        wallpaper.setWidth(image.getWidth());
        wallpaper.setHeight(image.getHeight());
        wallpaper.setUploadDate(LocalDateTime.now());

        long imageHash = ImagePHash.hash(image);
        wallpaper.setHash(imageHash);
        // TODO check similarity


        // TODO extract palette
        Set<WallpaperColor> palette = new HashSet<>(5);
        int[][] rgbPalette = ColorThief.getPalette(image, 5, 1, true);
        int order = 1;
        for (int[] rbgColor : rgbPalette) {
            double[] labColor = ColorConverter.rgbToLab(rbgColor[0], rbgColor[1], rbgColor[2]);
            palette.add(new WallpaperColor(
                    wallpaper,
                    order++,
                    rbgColor[0],
                    rbgColor[1],
                    rbgColor[2],
                    labColor[0],
                    labColor[1],
                    labColor[2]
            ));
        }
        wallpaper.setPalette(palette);

        // Store result
        wallpaper = wallpaperService.save(wallpaper);

        System.out.println(wallpaper);

        try {
            writeFile(image, applicationConfig.getWallpaperStoragePath(), wallpaper.getId());
        }
        catch (IOException e) {
            throw new ImportException(ImportException.ImportError.WRITE_IMAGE, "Error while writing image", e);
        }

        BufferedImage thumbnailImage = generateThumbnail(image);
        try {
            writeFile(thumbnailImage, applicationConfig.getThumbnailStoragePath(), wallpaper.getId());
        }
        catch (IOException e) {
            throw new ImportException(ImportException.ImportError.WRITE_THUMBNAIL, "Error while writing thumbnail", e);
        }

        return wallpaper;
    }

    private void writeFile(BufferedImage image, Path storagePath, String id) throws IOException {
        final Path file = storagePath.resolve(id + ".png");
        ImageIO.write(image, "png", file.toFile());
    }

    private BufferedImage generateThumbnail(BufferedImage fullImage) {
        final BufferedImage thumbnailImage;
        if (fullImage.getWidth() > fullImage.getHeight()) {
            thumbnailImage = Scalr.resize(fullImage, Scalr.Mode.FIT_TO_WIDTH, applicationConfig.getThumbnailWidth(), applicationConfig.getThumbnailHeight());
        }
        else {
            // Portrait image, scale without defining height then crop to desired height

            final BufferedImage tmpThumbnailImage = Scalr.resize(fullImage, Scalr.Mode.FIT_TO_WIDTH, applicationConfig.getThumbnailWidth());

            final int x = 0;
            final int y = (int) ((tmpThumbnailImage.getHeight() / 2.0) - (applicationConfig.getThumbnailHeight() / 2.0));

            thumbnailImage = Scalr.crop(tmpThumbnailImage, x, y, applicationConfig.getThumbnailWidth(), applicationConfig.getThumbnailHeight());
        }

        return thumbnailImage;
    }
}
