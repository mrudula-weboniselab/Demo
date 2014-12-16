package com.boundmap.panner.utils;

import javafx.scene.text.Font;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

public class FontUtils {

    private static final Logger LOG = LoggerFactory.getLogger(FontUtils.class);

    public static Font loadFont(String resourcePath) {
        URL resourceURL = FontUtils.class.getResource(resourcePath);
        Font font = null;

        if (resourceURL != null) {
            font = Font.loadFont(resourceURL.toExternalForm(), 10);
            LOG.info("Loaded font: {}", font.getName());
        } else {
            LOG.error("Could not find font resource: {}", resourcePath);
        }

        return font;
    }
}