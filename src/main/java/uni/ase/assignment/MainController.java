package uni.ase.assignment;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.kordamp.ikonli.javafx.FontIcon;

import static java.lang.Double.parseDouble;

public class MainController {
    double x,y;
    boolean isMaximized = false;
    Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    private Stage stage;
    private FontIcon fullscreenico;
    private CanvasController cc;
    @FXML
    private Canvas canvas;
    @FXML
    private TextField commandLine;
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
    @FXML CodeArea MainCodeArea;
//    @FXML
//    ScrollPane MainCodeAreaScrollPane;
    @FXML CodeArea OutputCodeArea;

    public void setStage(Stage stage) {
        this.stage = stage;
        canvas.getGraphicsContext2D().setFill(Color.web("#333333"));
        canvas.getGraphicsContext2D().fillRect(0, 0, 10000, 10000);
        cc = new CanvasController(canvas.getGraphicsContext2D());
        MainCodeArea.setParagraphGraphicFactory(LineNumberFactory.get(MainCodeArea));
        OutputCodeArea.setEditable(false);
    }

    @FXML
    protected void cmdPressed(KeyEvent k) {
        if(k.getCode() == KeyCode.ENTER) {
            String commandStr = commandLine.getCharacters().toString();
            String[] command = commandStr.split(" ");
            switch (command[0]) {
                case "drawOval":
                    if (command.length == 5) {
                        try {
                            cc.DrawOval(parseDouble(command[1]), parseDouble(command[2]), parseDouble(command[3]), parseDouble(command[4]));
                            cc.cmdHistAppend(commandStr);
                        } catch (Exception e) {
                            OutputCodeArea.append(e + "\n", "");
                        }
                    } else {
                        OutputCodeArea.append("error: not enough command params\n", "");
                    }
                    break;
                case "drawRect":
                    if (command.length == 5) {
                        try {
                            cc.DrawRect(parseDouble(command[1]), parseDouble(command[2]), parseDouble(command[3]), parseDouble(command[4]));
                            cc.cmdHistAppend(commandStr);
                        } catch (Exception e) {
                            OutputCodeArea.append(e + "\n", "");
                        }
                    } else {
                        OutputCodeArea.append("error: not enough command params\n", "");
                    }
                    break;
                case "drawLine":
                    if (command.length == 5) {
                        try {
                            cc.DrawLine(parseDouble(command[1]), parseDouble(command[2]), parseDouble(command[3]), parseDouble(command[4]));
                            cc.cmdHistAppend(commandStr);
                        } catch (Exception e) {
                            OutputCodeArea.append(e + "\n", "");
                        }
                    } else {
                        OutputCodeArea.append("error: not enough command params\n", "");
                    }
                    break;
                case "drawArc":
                    if (command.length == 8) {
                        try {
                            cc.DrawArc(parseDouble(command[1]), parseDouble(command[2]), parseDouble(command[3]), parseDouble(command[4]), parseDouble(command[5]), parseDouble(command[6]), command[7]);
                            cc.cmdHistAppend(commandStr);
                        } catch (Exception e) {
                            OutputCodeArea.append(e + "\n", "");
                        }
                    } else {
                        OutputCodeArea.append("error: not enough command params\n", "");
                    }
                    break;
                case "drawPoly":
                    if (command.length == 5) {
                        try {
                            cc.DrawPoly(parseDouble(command[1]), parseDouble(command[2]), parseDouble(command[3]), parseDouble(command[4]));
                            cc.cmdHistAppend(commandStr);
                        } catch (Exception e) {
                            OutputCodeArea.append(e + "\n", "");
                        }
                    } else {
                        OutputCodeArea.append("error: not enough command params\n", "");
                    }
                    break;
                default:
                    OutputCodeArea.append("error: Command Invalid\n", "");
            }
        }
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
            HBox window = (HBox) stage.getScene().getRoot().getChildrenUnmodifiable().get(1);
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
        but.setIconColor(Color.web("#00ff00"));
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