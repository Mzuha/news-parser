package com.mzuha.newsparser.event.listener;

import com.mzuha.newsparser.event.StageReadyEvent;
import java.io.IOException;
import java.security.InvalidParameterException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Component
public class StageReadyEventListener implements ApplicationListener<StageReadyEvent> {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    private final ApplicationContext applicationContext;
    @Value("classpath:/news.fxml")
    private Resource resource;

    public StageReadyEventListener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public void onApplicationEvent(StageReadyEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
            fxmlLoader.setControllerFactory(applicationContext::getBean);
            Parent parent = fxmlLoader.load();

            Stage stage = event.getStage();

            stage.setScene(new Scene(parent, WIDTH, HEIGHT));
            stage.show();
        } catch (IOException e) {
            throw new InvalidParameterException("Invalid fxml!");
        }
    }
}
