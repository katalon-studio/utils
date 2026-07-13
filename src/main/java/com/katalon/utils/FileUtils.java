package com.katalon.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import io.github.compress4j.archivers.tar.TarGzArchiveExtractor;
import io.github.compress4j.archivers.zip.ZipArchiveExtractor;

public class FileUtils {

    public static void downloadAndExtract(Logger logger, String fileUrl, File targetDir)
            throws IOException {

        LogUtils.info(logger, "Downloading Katalon Studio from " + fileUrl + ". It may take a few minutes.");

        URL url = new URL(fileUrl);

        try (InputStream inputStream = url.openStream()) {
            Path temporaryFile = Files.createTempFile("Katalon-", "");
            Files.copy(inputStream, temporaryFile, StandardCopyOption.REPLACE_EXISTING);
            LogUtils.info(logger, "Extract " + temporaryFile + " to " + targetDir);

            if (fileUrl.endsWith(".zip")) {
                try (ZipArchiveExtractor extractor = ZipArchiveExtractor.builder(temporaryFile).build()) {
                    extractor.extract(targetDir.toPath());
                } catch (Exception e) {
                    LogUtils.info(logger, "Failed to extract " + temporaryFile + " to " + targetDir);
                    throw e;
                }
            } else if (fileUrl.endsWith(".tar.gz")) {
                try (TarGzArchiveExtractor extractor = TarGzArchiveExtractor.builder(temporaryFile).build()) {
                    extractor.extract(targetDir.toPath());
                } catch (Exception e) {
                    LogUtils.info(logger, "Failed to extract " + temporaryFile + " to " + targetDir);
                    throw e;
                }
            } else {
                throw new IllegalStateException("Unsupported file type: " + fileUrl + ".");
            }

            temporaryFile.toFile().delete();
        }
    }
}
