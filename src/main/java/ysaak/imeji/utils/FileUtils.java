package ysaak.imeji.utils;

import ysaak.imeji.exception.TechnicalException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {
    private FileUtils() { /**/ }

    public static void createDirectoriesIfNotExists(Path directory) {
        if (!Files.exists(directory)) {
            try {
                Files.createDirectories(directory);
            }
            catch (IOException e) {
                throw new TechnicalException("Error while deleting directory", e);
            }
        }
    }
}
