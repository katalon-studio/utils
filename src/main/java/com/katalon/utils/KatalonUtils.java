package com.katalon.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
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
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

public class KatalonUtils {

    private static boolean executeKatalon(
            Logger logger,
            String katalonExecutableFile,
            String projectPath,
            String executeArgs,
            String x11Display,
            String xvfbConfiguration)
            throws IOException, InterruptedException {
        File file = new File(katalonExecutableFile);
        if (!file.exists()) {
            file = new File(katalonExecutableFile + ".exe");
        }
        if (file.exists()) {
            file.setExecutable(true);
        }
        if (katalonExecutableFile.contains(" ")) {
            katalonExecutableFile = "\"" + katalonExecutableFile + "\"";
        }
        String command = katalonExecutableFile +
                " -noSplash " +
                " -runMode=console ";
        if (!executeArgs.contains("-projectPath")) {
            command += " -projectPath=\"" + projectPath + "\" ";
        }
        command += " " + executeArgs + " ";

        return OsUtils.runCommand(logger, command, x11Display, xvfbConfiguration);
    }

    /**
     *
     * @param logger Logger to log activities.
     * @param version Version of Katalon Studio to be installed. Ignored if {@code location} is provided.
     * @param location Local location where Katalon Studio has been pre-installed. If this argument is null or empty the package will be downloaded and installed automatically.
     * @param projectPath Path to the Katalon Studio project to be executed. Ignored if provided by (@code executeArgs}.
     * @param executeArgs Arguments for Katalon Studio CLI, without {@code -runMode}. If {@code -projectPath} is missing, the argument {@code projectPath} will be used.
     * @param x11Display Linux only. This value will be used as the {@code DISPLAY} environment variable.
     * @param xvfbConfiguration Linux only. This value will be used as the arguments for {@code xvfb-run}.
     * @return true if the exit code is 0, false otherwise.
     * @throws IOException
     * @throws InterruptedException
     */
    public static boolean executeKatalon(
            Logger logger,
            String version,
            String location,
            String projectPath,
            String executeArgs,
            String x11Display,
            String xvfbConfiguration)
            throws IOException, InterruptedException {

        String katalonDirPath;

        if (StringUtils.isBlank(location)) {
            File katalonDir = KatalonDownloadUtils.getKatalonPackage(logger, version);
            katalonDirPath = katalonDir.getAbsolutePath();
        } else {
            katalonDirPath = location;
        }

        logger.info("Using Katalon Studio at " + katalonDirPath);
        String katalonExecutableFile;
        String os = OsUtils.getOSVersion(logger);
        if (os.contains("macos")) {
            katalonExecutableFile = Paths.get(katalonDirPath, "Contents", "MacOS", "katalon")
                    .toAbsolutePath()
                    .toString();
        } else {
            katalonExecutableFile = Paths.get(katalonDirPath, "katalon")
                    .toAbsolutePath()
                    .toString();
        }
        return executeKatalon(
                logger,
                katalonExecutableFile,
                projectPath,
                executeArgs,
                x11Display,
                xvfbConfiguration);
    }
}
