# Katalon Utilities

Utilities to work with Katalon Studio.

## Install

```xml
<dependency>
    <groupId>com.katalon</groupId>
    <artifactId>utils</artifactId>
    <version>1.0.15</version>
</dependency>
```

## API

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

## Companion products

### Katalon TestOps

[Katalon TestOps](https://analytics.katalon.com) is a web-based application that provides dynamic perspectives and an insightful look at your automation testing data. You can leverage your automation testing data by transforming and visualizing your data; analyzing test results; seamlessly integrating with such tools as Katalon Studio and Jira; maximizing the testing capacity with remote execution.

* Read our [documentation](https://docs.katalon.com/katalon-analytics/docs/overview.html).
* Ask a question on [Forum](https://forum.katalon.com/categories/katalon-analytics).
* Request a new feature on [GitHub](CONTRIBUTING.md).
* Vote for [Popular Feature Requests](https://github.com/katalon-analytics/katalon-analytics/issues?q=is%3Aopen+is%3Aissue+label%3Afeature-request+sort%3Areactions-%2B1-desc).
* File a bug in [GitHub Issues](https://github.com/katalon-analytics/katalon-analytics/issues).

### Katalon Studio
[Katalon Studio](https://www.katalon.com) is a free and complete automation testing solution for Web, Mobile, and API testing with modern methodologies (Data-Driven Testing, TDD/BDD, Page Object Model, etc.) as well as advanced integration (JIRA, qTest, Slack, CI, Katalon TestOps, etc.). Learn more about [Katalon Studio features](https://www.katalon.com/features/).
