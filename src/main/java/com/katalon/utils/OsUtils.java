package com.katalon.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;

public class OsUtils {

    public static String getOSVersion(Logger logger) {

        if (SystemUtils.IS_OS_WINDOWS) {

            try {
                Process p = Runtime.getRuntime().exec("wmic os get osarchitecture");
                try (InputStream inputStream = p.getInputStream()) {
                    String output = IOUtils.toString(inputStream, (Charset)null);
                    p.destroy();

                    if (output.contains("64")) {
                        return "windows 64";
                    } else {
                        return "windows 32";
                    }
                }
            } catch (Exception e) {
                LogUtils.info(logger, "Cannot detect the OS architecture. Assume it is x64.");
                LogUtils.info(logger, "Reason: " + e.getMessage() + ".");
                return "windows 64";
            }

        } else if (SystemUtils.IS_OS_MAC) {
            return "macos (app)";
        } else if (SystemUtils.IS_OS_LINUX) {
            return "linux";
        }
        return "";
    }

    public static boolean runCommand(
            Logger logger,
            String command,
            Path workingDirectory,
            String x11Display,
            String xvfbConfiguration,
            Map<String, String> environmentVariablesMap)
            throws IOException, InterruptedException {

        String[] cmdarray;
        if (SystemUtils.IS_OS_WINDOWS) {
            cmdarray = Arrays.asList("cmd", "/c", command).toArray(new String[]{});
        } else {
            if (!StringUtils.isBlank(x11Display)) {
                command = "DISPLAY=" + x11Display + " " + command;
            }
            if (!StringUtils.isBlank(xvfbConfiguration)) {
                command = "xvfb-run " + xvfbConfiguration + " " + command;
            }
            List<String> cmdlist = Arrays.asList("sh", "-c", command);
            cmdarray = cmdlist.toArray(new String[]{});
        }

        LogUtils.info(logger, "Execute " + Arrays.toString(cmdarray) + " in " + workingDirectory);

        ProcessBuilder pb = new ProcessBuilder(cmdarray);
        Map<String, String> env = pb.environment();
        if (environmentVariablesMap != null) {
            env.putAll(environmentVariablesMap);
        }
        pb.directory(workingDirectory.toFile());
        pb.redirectErrorStream(true);
        Process cmdProc = pb.start();
        try (
                BufferedReader stdoutReader = new BufferedReader(
                        new InputStreamReader(
                                cmdProc.getInputStream(), StandardCharsets.UTF_8));
        ) {
            String line;
            while ((line = stdoutReader.readLine()) != null) {
                LogUtils.info(logger, line);
            }
        }

        cmdProc.waitFor();
        LogUtils.info(logger, MessageFormat.format("Finished executing {0}. Exit code: {1}.", String.join(" ", cmdarray), cmdProc.exitValue()));
        return cmdProc.exitValue() == 0;
    }
}
