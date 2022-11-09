package uni.ase.assignment;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;
import uni.ase.assignment.controllers.CanvasController;
import uni.ase.assignment.controllers.CodeParser;
import uni.ase.assignment.controllers.ConsoleController;
import uni.ase.assignment.controllers.LogController;

/**
 * the Main Controller class which is used to process actions from the main window such as clicks or movements
 */
public class MainController {

    double x,y;
    boolean isMaximized = false;
    Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    private Stage stage;
    private FontIcon fullscreenico;
    @FXML private FontIcon run;
    @FXML private ImageView pen;
    @FXML TextArea code;
    @FXML TextArea out;
    @FXML private Canvas canvas;
    @FXML private TextField commandLine;
    private LogController lc;
    private CanvasController cac;
    private CodeParser cp;
    private ConsoleController coc;

    /**
     * an event handler to be used for custom fullscreen functionality
     */
    private EventHandler<MouseEvent> unMaximize = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            if (fullscreenico == null) {
                HBox window = (HBox) stage.getScene().getRoot().getChildrenUnmodifiable().get(1);
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

    /**
     * sets up and initialises some variables when the window is loaded, this is called from the [MainApplication] class
     *
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
        canvas.getGraphicsContext2D().setFill(Color.web("#333333"));
        canvas.getGraphicsContext2D().fillRect(0, 0, 10000, 10000);
        out.setEditable(false);

        lc = new LogController(out);
        cac = new CanvasController(canvas.getGraphicsContext2D(), lc);
        coc = new ConsoleController(commandLine, cac, lc);
        cp = new CodeParser(code, cac, lc, coc);

        pen.setX(0);
        pen.setY(0);

    }

    /**
     * when the commandLine is selected and a key is pressed this method is called to process the action taken by the
     * user. this could be running a command or navigating command history
     *
     * @param k the keyevent containing information about which key was pressed
     */
    @FXML
    protected void cmdPressed(KeyEvent k) {
        if(k.getCode() == KeyCode.ENTER) {
            coc.run("");
        } else if (k.getCode() == KeyCode.UP) {
            coc.cmdLast();
        } else if (k.getCode() == KeyCode.DOWN) {
            coc.cmdNext();
        }
    }

    /**
     * runs the code in the code textbox when the green play button above it is pressed
     */
    @FXML protected void runCode() {
        cp.run();
    }

    /**
     * used for recognising the position of the window when the user tries to drag the window
     *
     * @param e the MouseEvent containing information such as the position of the mouse
     */
    @FXML
    protected void mousePressed(MouseEvent e) {
        x = e.getSceneX();
        y = e.getSceneY();
    }

    /**
     * when the mouse is released this method checks the position of the mouse to decide if the window should snap to a
     * certain position and size
     *
     * @param e for the position of the mouse
     */
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

    /**
     * changes the fullscreen button icon when the window is not fullscreen
     *
     * @param e for the position of the mouse
     */
    @FXML
    protected void mouseDragged(MouseEvent e) {
        if (fullscreenico == null) {
            HBox window = (HBox) stage.getScene().getRoot().getChildrenUnmodifiable().get(1);
            AnchorPane pane = (AnchorPane) window.getChildren().get(1);
            fullscreenico = (FontIcon) pane.getChildren().get(0);
        }
        stage.setX(e.getScreenX() - x);
        stage.setY(e.getScreenY() - y);
    }

    /**
     * handles minimizing the window
     */
    @FXML
    protected void minusMouseClicked() {
        stage.setIconified(true);
    }

    /**
     * changes colour of the minimize icon on mouse enter
     *
     * @param e the mouse event
     */
    @FXML
    protected void minusMouseEntered(MouseEvent e) {
        FontIcon but = (FontIcon) e.getSource();
        but.setIconColor(Color.web("#00ff00"));
    }

    /**
     * changes colour of the minimize icon on mouse exit
     *
     * @param e the mouse event
     */
    @FXML
    protected void minusMouseExited(MouseEvent e) {
        FontIcon but = (FontIcon) e.getSource();
        but.setIconColor(Color.WHITE);
    }

    /**
     * handles making the window fullscreen
     *
     * @param e the mouse event
     */
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

    /**
     * changes colour of the fullscreen icon on mouse enter
     *
     * @param e the mouse event
     */
    @FXML
    protected void fullscreenMouseEntered(MouseEvent e) {
        AnchorPane but = (AnchorPane) e.getSource();
        FontIcon ico = (FontIcon) but.getChildren().get(0);
        ico.setIconColor(Color.YELLOW);
    }

    /**
     * changes colour of the fullscreen icon on mouse exit
     *
     * @param e the mouse event
     */
    @FXML
    protected void fullscreenMouseExited(MouseEvent e) {
        AnchorPane but = (AnchorPane) e.getSource();
        FontIcon ico = (FontIcon) but.getChildren().get(0);
        ico.setIconColor(Color.WHITE);
    }

    /**
     * handles closing the application
     */
    @FXML
    protected void exitMouseClicked() {
        stage.close();
    }

    /**
     * changes colour of the exit icon on mouse enter
     *
     * @param e the mouse event
     */
    @FXML
    protected void exitMouseEntered(MouseEvent e) {
        FontIcon but = (FontIcon) e.getSource();
        but.setIconColor(Color.RED);
    }

    /**
     * changes colour of the fullscreen icon on mouse exit
     *
     * @param e the mouse event
     */
    @FXML
    protected void exitMouseExited(MouseEvent e) {
        FontIcon but = (FontIcon) e.getSource();
        but.setIconColor(Color.WHITE);
    }
}