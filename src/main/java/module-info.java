module uni.ase.assignment {
    requires javafx.controls;
    requires javafx.fxml;
            
                    requires org.kordamp.ikonli.javafx;
                
    opens uni.ase.assignment to javafx.fxml;
    exports uni.ase.assignment;
}