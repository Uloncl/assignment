package uni.ase.assignment;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;



public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("main-view.fxml"));
        Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
        double winheight = (bounds.getHeight() / 3) * 2;
        double winwidth  = (winheight / 9) * 16;
        Scene scene = new Scene(fxmlLoader.load(), winwidth, winheight);
        MainController controller = fxmlLoader.getController();
        controller.setStage(stage);
        
        scene.getStylesheets().add(MainApplication.class.getResource("main.css").toString());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setMaxWidth(bounds.getWidth());
        stage.setMaxHeight(bounds.getHeight());
        stage.setTitle("ASE Assignment");
        stage.setScene(scene);
        stage.setResizable(true);
        ResizeHelper.addResizeListener(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}