package com.boundmap.panner;


import com.boundmap.panner.utils.FontUtils;
import javafx.application.Application;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DemoBoundMapApp extends Application {

    private static final Logger LOG = LoggerFactory.getLogger(DemoBoundMapApp.class);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        super.init();
        loadFonts();
    }

    private void loadFonts() {
        FontUtils.loadFont("/fonts/RBNo2Light.otf");
        FontUtils.loadFont("/fonts/RBNo2Light_a.otf");
        FontUtils.loadFont("/fonts/Quicksand_Bold.otf");
    }

    public void start(Stage stage) throws Exception {
        LOG.info("Starting PrecisionHawk Flight Planner");

        ApplicationContext context = new AnnotationConfigApplicationContext(FlightPlannerAppConfiguration.class);
        ScreensConfiguration screens = context.getBean(ScreensConfiguration.class);
        screens.setPrimaryStage(stage);
        screens.flightPlannerDialog().show();
    }
}
