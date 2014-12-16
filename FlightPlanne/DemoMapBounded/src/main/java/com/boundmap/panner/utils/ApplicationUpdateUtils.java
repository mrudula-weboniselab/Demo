package com.boundmap.panner.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

public class  ApplicationUpdateUtils {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationUpdateUtils.class);

    @Autowired
    private Properties projectProperties;

    public ApplicationUpdateUtils() {

    }

    public boolean isNewerVersionAvailable() {
        String latestVersion = getLatestVersion();
        String runningVersion = getRunningVersion();

        LOG.info("LatestVersion: {}, RunningVersion: {}", latestVersion, runningVersion);

        List<Integer> latestVersionParts;
        List<Integer> runningVersionParts;

        try {
            latestVersionParts = getVersionParts(latestVersion);
            runningVersionParts = getVersionParts(runningVersion);
        } catch (NumberFormatException ex) {
            LOG.error("Unable to determine if newer version is available; Error parsing version string: " + ex.getMessage(), ex);
            return false;
        }

        for (int i = 0; i < Math.min(latestVersionParts.size(), runningVersionParts.size()); i++) {
            int latestVersionPart = latestVersionParts.get(i);
            int runningVersionPart = runningVersionParts.get(i);

            if (latestVersionPart > runningVersionPart) {
                return true;
            } else if (runningVersionPart > latestVersionPart) {
                return false;
            }
        }

        // All compared version parts have been identical.
        // Last check for cases such as runningVersion = 2.2.1, latestVersion = 2.2.1.5
        return latestVersionParts.size() > runningVersionParts.size();
    }

    private List<Integer> getVersionParts(String versionString) throws NumberFormatException {
        versionString = versionString.replace("-SNAPSHOT", "");
        List<Integer> versionParts = new ArrayList<>();

        for (String part : versionString.split("\\.")) {
            versionParts.add(Integer.parseInt(part));
        }

        return versionParts;
    }

    /**
     * This method attempts to determine the latest updated application version.
     */
    public String getLatestVersion() {
        String latestVersion = "1.0";
//        try {
//             String url = ApplicationConstants.APPLICATION_VERSION_URL ;
//             LOG.info("Update Application Version URL: "+url);
//             latestVersion = performServerCall(url);
//
//        } catch (IOException ex) {
//             LOG.error("Error while update application version api call: " + ex.getMessage(), ex);
//        }
        return latestVersion;
    }

    public String getRunningVersion() {
        return projectProperties.getProperty("version");
    }

    private String performServerCall(String url) throws IOException {
        String result = "";
        HttpClient client = new DefaultHttpClient();

        try {
            result = client.execute(new HttpGet(url),  new BasicResponseHandler());
            result = result.replace("\n", "");
            LOG.info("Application version update API call Response: " + result);
        } finally {
            client.getConnectionManager().shutdown();
        }

        return result;
    }

}
