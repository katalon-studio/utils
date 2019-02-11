# Katalon Utilities

Utilities to work with Katalon Studio

```java
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
            throws IOException, InterruptedException
```
