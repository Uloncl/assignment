package uni.ase.assignment;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

public class MainController {
    double x,y;
    boolean isMaximized = false;
    Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    @FXML private AnchorPane window;
//    private Stage stage = (Stage) window.getScene().getWindow();
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    protected void mousePressed(MouseEvent e) {
        x = e.getSceneX();
        y = e.getSceneY();
    }
    @FXML
    protected void mouseReleased(MouseEvent e) {
        if (stage.getX() < 5){
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth() / 2);
            stage.setHeight(bounds.getHeight());
            isMaximized = !isMaximized;
        } else if (stage.getX() > bounds.getWidth() - 5) {
            stage.setX(bounds.getWidth() / 2);
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth() / 2);
            stage.setHeight(bounds.getHeight());
            isMaximized = !isMaximized;
        }
    }
    @FXML
    protected void mouseDragged(MouseEvent e) {
        stage.setX(e.getScreenX() - x);
        stage.setY(e.getScreenY() - y);
        if (isMaximized && stage.getY() > 0) {
            HBox box = (HBox) e.getSource();
            AnchorPane pane = (AnchorPane) box.getChildren().get(1);
            FontIcon ico = (FontIcon) pane.getChildren().get(0);
            ico.setIconLiteral("bi-fullscreen");
            double winheight = (bounds.getHeight() / 3) * 2;
            double winwidth  = (winheight / 9) * 16;
            stage.setX(e.getScreenX() - (winwidth/2));
            stage.setY(e.getScreenY() - (winheight/2));
            stage.setWidth(winwidth);
            stage.setHeight(winheight);
            isMaximized = !isMaximized;
        }
        if (e.getScreenY() == 0) {
            stage.addEventHandler(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    HBox box = (HBox) e.getSource();
                    AnchorPane pane = (AnchorPane) box.getChildren().get(1);
                    FontIcon ico = (FontIcon) pane.getChildren().get(0);
                    ico.setIconLiteral("bi-fullscreen-exit");
                    stage.setX(bounds.getMinX());
                    stage.setY(bounds.getMinY());
                    stage.setWidth(bounds.getWidth());
                    stage.setHeight(bounds.getHeight());
                    isMaximized = !isMaximized;
                    stage.removeEventHandler(MouseEvent.MOUSE_RELEASED, this);
                }
            });
        }
    }

    @FXML
    protected void minusMouseClicked(MouseEvent e) {
        stage.setIconified(true);
    }
    @FXML
    protected void minusMouseEntered(MouseEvent e) {
        FontIcon but = (FontIcon) e.getSource();
        but.setIconColor(Color.GREEN);
    }
    @FXML
    protected void minusMouseExited(MouseEvent e) {
        FontIcon but = (FontIcon) e.getSource();
        but.setIconColor(Color.WHITE);
    }

    @FXML
    protected void fullscreenMouseClicked(MouseEvent e) {

        if (isMaximized) {
            AnchorPane but = (AnchorPane) e.getSource();
            FontIcon ico = (FontIcon) but.getChildren().get(0);
            ico.setIconLiteral("bi-fullscreen");
            double winheight = (bounds.getHeight() / 3) * 2;
            double winwidth  = (winheight / 9) * 16;
            stage.setX((bounds.getWidth()/2) - (winwidth/2));
            stage.setY((bounds.getHeight()/2) - (winheight/2));
            stage.setWidth(winwidth);
            stage.setHeight(winheight);
            isMaximized = !isMaximized;
        } else {
            AnchorPane but = (AnchorPane) e.getSource();
            FontIcon ico = (FontIcon) but.getChildren().get(0);
            ico.setIconLiteral("bi-fullscreen-exit");
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            isMaximized = !isMaximized;
        }
    }
    @FXML
    protected void fullscreenMouseEntered(MouseEvent e) {
        AnchorPane but = (AnchorPane) e.getSource();
        FontIcon ico = (FontIcon) but.getChildren().get(0);
        ico.setIconColor(Color.YELLOW);
    }
    @FXML
    protected void fullscreenMouseExited(MouseEvent e) {
        AnchorPane but = (AnchorPane) e.getSource();
        FontIcon ico = (FontIcon) but.getChildren().get(0);
        ico.setIconColor(Color.WHITE);
    }

    @FXML
    protected void exitMouseClicked(MouseEvent e) {
        stage.close();
    }
    @FXML
    protected void exitMouseEntered(MouseEvent e) {
        FontIcon but = (FontIcon) e.getSource();
        but.setIconColor(Color.RED);
    }
    @FXML
    protected void exitMouseExited(MouseEvent e) {
        FontIcon but = (FontIcon) e.getSource();
        but.setIconColor(Color.WHITE);
    }
}