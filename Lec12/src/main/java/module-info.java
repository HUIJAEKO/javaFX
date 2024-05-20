module org.example.lec12 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.lec12 to javafx.fxml;
    exports org.example.lec12;
}