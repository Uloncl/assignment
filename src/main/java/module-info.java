module uni.ase.assignment {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires org.fxmisc.richtext;
    requires org.fxmisc.flowless;
    requires reactfx;

    opens uni.ase.assignment to javafx.fxml;
    exports uni.ase.assignment;
}