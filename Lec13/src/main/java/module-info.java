module org.example.lec13 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.lec13 to javafx.fxml;
    exports org.example.lec13;
}