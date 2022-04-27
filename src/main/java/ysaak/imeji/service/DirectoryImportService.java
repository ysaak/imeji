package ysaak.imeji.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.devtools.filewatch.ChangedFile;
import org.springframework.boot.devtools.filewatch.ChangedFiles;
import org.springframework.boot.devtools.filewatch.FileSystemWatcher;
import org.springframework.stereotype.Service;
import ysaak.imeji.config.ApplicationConfig;
import ysaak.imeji.exception.ImportException;
import ysaak.imeji.exception.TechnicalException;
import ysaak.imeji.utils.FileUtils;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DirectoryImportService  {
    private final Logger log = LoggerFactory.getLogger(DirectoryImportService.class);

    private final ApplicationConfig applicationConfig;

    private final ImportService importService;

    private FileSystemWatcher fileSystemWatcher = null;

    @Autowired
    public DirectoryImportService(ApplicationConfig applicationConfig, ImportService importService) {
        this.applicationConfig = applicationConfig;
        this.importService = importService;
    }

    @PostConstruct
    public void fileSystemWatcher() {
        // Create directories
        FileUtils.createDirectoriesIfNotExists(applicationConfig.getImportDirectory());
        FileUtils.createDirectoriesIfNotExists(applicationConfig.getImportErrorDirectory());

        // Import file already present in folder
        importFileInFolder();

        // Init file watcher
        fileSystemWatcher = new FileSystemWatcher(true, Duration.ofMillis(5000L), Duration.ofMillis(3000L));
        fileSystemWatcher.addSourceDirectory(applicationConfig.getImportDirectory().toFile());
        fileSystemWatcher.addListener(this::handleFileChangeEvent);
        fileSystemWatcher.start();
        log.debug("Import directory watch started");
    }

    @PreDestroy
    public void onDestroy() {
        if (fileSystemWatcher != null) {
            fileSystemWatcher.stop();
        }
    }

    private void importFileInFolder() {
        final List<Path> files;

        try (Stream<Path> paths = Files.walk(applicationConfig.getImportDirectory())) {
            files = paths.filter(Files::isRegularFile).collect(Collectors.toList());
        }
        catch (IOException e) {
            throw new TechnicalException("Error while reading import directory content", e);
        }

        log.info("{} files to import on startup", files.size());

        files.forEach(this::importWallpaper);

        log.info("End of initial importing");
    }

    private void handleFileChangeEvent(Set<ChangedFiles> changeSet) {
        for(ChangedFiles changes : changeSet) {
            changes.getFiles().stream()
                    .filter(file -> file.getType() == ChangedFile.Type.ADD)
                    .map(ChangedFile::getFile)
                    .map(File::toPath)
                    .forEach(this::importWallpaper);
        }
    }

    private void importWallpaper(Path file) {
        log.debug("Import file {}", file);

        final byte[] fileContent;
        boolean fileImported = false;
        try {
            fileContent = Files.readAllBytes(file);

            importService.importImage(fileContent, Files.size(file));
            fileImported = true;

        }
        catch (IOException e) {
            log.error("Error while opening input stream", e);
        }
        catch (ImportException e) {
            log.error("Error while importing image", e);
        }
        finally {
            if (fileImported) {
                try {
                    Files.delete(file);
                }
                catch (IOException e) {
                    log.error("Error while deleting file", e);
                }
            }
            else {
                moveToErrorFolder(file);
            }
        }
    }

    private void moveToErrorFolder(Path originalFile) {
        try {
            Files.move(
                    originalFile,
                    applicationConfig.getImportErrorDirectory().resolve(originalFile.getFileName()),
                    StandardCopyOption.REPLACE_EXISTING
            );
        }
        catch (IOException e) {
                log.error("Error while moving image to error folder", e);
        }
    }
}
