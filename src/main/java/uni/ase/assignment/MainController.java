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
    private Stage stage;
    private FontIcon fullscreenico;
    private EventHandler<MouseEvent> unMaximize = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (fullscreenico == null) {
                HBox window = (HBox) stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
                AnchorPane pane = (AnchorPane) window.getChildren().get(1);
                fullscreenico = (FontIcon) pane.getChildren().get(0);
            }
            fullscreenico.setIconLiteral("bi-fullscreen");
            System.out.println("x: " + e.getScreenX() + " y: " + e.getScreenY());
            double winheight = (bounds.getHeight() / 3) * 2;
            double winwidth  = (winheight / 9) * 16;
            stage.setX(e.getScreenX() - (winwidth/2));
            stage.setY(e.getScreenY());
            stage.setWidth(winwidth);
            stage.setHeight(winheight);
            isMaximized = false;
            stage.removeEventHandler(MouseEvent.MOUSE_DRAGGED, this);
        }
    };
//    @FXML CodeArea MainCodeArea;
//    @FXML
//    ScrollPane MainCodeAreaScrollPane;
//    @FXML CodeArea OutputCodeArea;

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
        if (e.getScreenX() < 5 && e.getScreenY() > 5){
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth() / 2);
            stage.setHeight(bounds.getHeight());
            isMaximized = true;
            fullscreenico.setIconLiteral("bi-fullscreen-exit");
            stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, unMaximize);
        }
        if (e.getScreenX() > bounds.getWidth() - 5 && e.getScreenY() > 5) {
            stage.setX(bounds.getWidth() / 2);
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth() / 2);
            stage.setHeight(bounds.getHeight());
            isMaximized = true;
            fullscreenico.setIconLiteral("bi-fullscreen-exit");
            stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, unMaximize);
        }
        if (e.getScreenY() < 5) {
            if (e.getScreenX() > 5 && e.getScreenX() < bounds.getWidth() - 5 ) {
                HBox box = (HBox) e.getSource();
                AnchorPane pane = (AnchorPane) box.getChildren().get(1);
                FontIcon ico = (FontIcon) pane.getChildren().get(0);
                ico.setIconLiteral("bi-fullscreen-exit");
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth());
                stage.setHeight(bounds.getHeight());
                isMaximized = true;
                fullscreenico.setIconLiteral("bi-fullscreen-exit");
                stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, unMaximize);
            } else if (e.getScreenX() < 5) {
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth() / 2);
                stage.setHeight(bounds.getHeight() / 2);
                isMaximized = true;
                fullscreenico.setIconLiteral("bi-fullscreen-exit");
                stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, unMaximize);
            } else if (e.getScreenX() > bounds.getWidth() - 5) {
                stage.setX(bounds.getWidth() / 2);
                stage.setY(bounds.getMinY());
                stage.setWidth(bounds.getWidth() / 2);
                stage.setHeight(bounds.getHeight() / 2);
                isMaximized = true;
                fullscreenico.setIconLiteral("bi-fullscreen-exit");
                stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, unMaximize);
            }
        }
        if (e.getScreenY() > bounds.getHeight() - 5) {
            if (e.getScreenX() < 5) {
                stage.setX(bounds.getMinX());
                stage.setY(bounds.getHeight() / 2);
                stage.setWidth(bounds.getWidth() / 2);
                stage.setHeight(bounds.getHeight() / 2);
                isMaximized = true;
                fullscreenico.setIconLiteral("bi-fullscreen-exit");
                stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, unMaximize);
            } else if (e.getScreenX() > bounds.getWidth() - 5) {
                stage.setX(bounds.getWidth() / 2);
                stage.setY(bounds.getHeight() / 2);
                stage.setWidth(bounds.getWidth() / 2);
                stage.setHeight(bounds.getHeight() / 2);
                isMaximized = true;
                fullscreenico.setIconLiteral("bi-fullscreen-exit");
                stage.addEventHandler(MouseEvent.MOUSE_DRAGGED, unMaximize);
            }
        }
    }
    @FXML
    protected void mouseDragged(MouseEvent e) {
        if (fullscreenico == null) {
            HBox window = (HBox) stage.getScene().getRoot().getChildrenUnmodifiable().get(0);
            AnchorPane pane = (AnchorPane) window.getChildren().get(1);
            fullscreenico = (FontIcon) pane.getChildren().get(0);
        }
        stage.setX(e.getScreenX() - x);
        stage.setY(e.getScreenY() - y);
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
            isMaximized = false;
        } else {
            AnchorPane but = (AnchorPane) e.getSource();
            FontIcon ico = (FontIcon) but.getChildren().get(0);
            ico.setIconLiteral("bi-fullscreen-exit");
            stage.setX(bounds.getMinX());
            stage.setY(bounds.getMinY());
            stage.setWidth(bounds.getWidth());
            stage.setHeight(bounds.getHeight());
            isMaximized = true;
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