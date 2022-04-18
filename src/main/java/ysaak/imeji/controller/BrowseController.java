package ysaak.imeji.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ysaak.imeji.config.ApplicationConfig;
import ysaak.imeji.data.Wallpaper;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.Dimension;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class BrowseController {
    private static final Collection<String> IMAGE_EXTENSIONS = Arrays.asList(".png", ".jpg", ".jpeg");

    private final ApplicationConfig applicationConfig;
    private final int basePathLength;

    @Autowired
    public BrowseController(ApplicationConfig applicationConfig) {
        this.applicationConfig = applicationConfig;
        Path wallpaperPath = Paths.get(applicationConfig.getWallpaperLocalPath());
        basePathLength = wallpaperPath.toString().length() + 1; // 1 for the trailing /
    }

    @GetMapping(path = "/")
    public String indexAction(Model model) {
        final List<Wallpaper> wallpapers = getWallpapers();

        model.addAttribute("wallpapers", wallpapers);
        return "index";
    }

    @GetMapping(path = "/random")
    public String randomAction() {
        final List<Wallpaper> wallpapers = getWallpapers();
        int randomIndex = ThreadLocalRandom.current().nextInt(wallpapers.size());

        return "forward:/wallpapers/full/" + wallpapers.get(randomIndex).getBasename();
    }

    private List<Wallpaper> getWallpapers() {
        List<Wallpaper> result;

        Path wallpaperPath = Paths.get(applicationConfig.getWallpaperLocalPath());
        try (Stream<Path> walk = Files.walk(wallpaperPath)) {
            result = walk
                    .filter(p -> !Files.isDirectory(p))   // not a directory
                    .filter(this::isImageFile)       // check end with
                    .map(this::createWallpaperFromPath)
                    .sorted(Comparator.comparing(Wallpaper::getBasename))
                    .collect(Collectors.toList());        // collect all matched to a List
        }
        catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return result;
    }

    private boolean isImageFile(Path path) {
        String filename = path.toString().toLowerCase();
        return IMAGE_EXTENSIONS.stream().anyMatch(filename::endsWith);
    }

    private Wallpaper createWallpaperFromPath(Path path) {
        final Dimension dimension;
        try {
            dimension = getImageDimension(path);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (dimension == null) {
            throw new RuntimeException("Error while reading image dimension");
        }

        // Thumbnail generation
        Dimension thumbnailSize = calculateThumbnailSize(dimension);

        return new Wallpaper(
                getImageBasename(path),
                dimension.width,
                dimension.height,
                thumbnailSize.width,
                thumbnailSize.height
        );
    }

    private Dimension getImageDimension(Path path) throws IOException {
        Dimension dimension = null;

        try(ImageInputStream in = ImageIO.createImageInputStream(path.toFile())) {
            final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                try {
                    reader.setInput(in);
                    dimension = new Dimension(reader.getWidth(0), reader.getHeight(0));
                }
                finally {
                    reader.dispose();
                }
            }
        }

        return dimension;
    }

    private Dimension calculateThumbnailSize(Dimension wallpaperDimension) {
        final int thumbnailWidth;
        final int thumbnailHeight;
        final double aspectRatio = wallpaperDimension.getWidth() / wallpaperDimension.getHeight();

        if (wallpaperDimension.getWidth() > wallpaperDimension.getHeight()) {
            // Wider
            thumbnailWidth = applicationConfig.getThumbnailSize();
            thumbnailHeight = (int) (thumbnailWidth / aspectRatio);
        }
        else {
            // Taller
            thumbnailHeight = applicationConfig.getThumbnailSize();
            thumbnailWidth = (int) (thumbnailHeight * aspectRatio);
        }

        return new Dimension(
                thumbnailWidth,
                thumbnailHeight
        );
    }

    private String getImageBasename(Path path) {
        return path.toString().substring(basePathLength);
    }
}
