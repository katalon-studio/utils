package com.katalon.utils;

import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;
import org.rauschig.jarchivelib.CompressionType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

class FileUtils {

    static void downloadAndExtract(
            Logger logger, String fileUrl, File targetDir)
            throws IOException {

        LogUtils.info(logger, "Downloading Katalon Studio from " + fileUrl + ". It may take a few minutes.");

        URL url = new URL(fileUrl);

        try (InputStream inputStream = url.openStream()) {
            Path temporaryFile = Files.createTempFile("Katalon-", "");
            Files.copy(
                    inputStream,
                    temporaryFile,
                    StandardCopyOption.REPLACE_EXISTING);

            Archiver archiver;
            if (fileUrl.contains(".zip")) {
                archiver = ArchiverFactory.createArchiver(ArchiveFormat.ZIP);
            } else if (fileUrl.contains(".tar.gz")) {
                archiver = ArchiverFactory.createArchiver(ArchiveFormat.TAR, CompressionType.GZIP);
            } else {
                throw new IllegalStateException();
            }
            LogUtils.info(logger, "Extract " + temporaryFile.toString() + " to " + targetDir);
            archiver.extract(temporaryFile.toFile(), targetDir);
        }
    }

}
