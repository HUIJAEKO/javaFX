module org.example.lec11 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.lec11 to javafx.fxml;
    exports org.example.lec11;
}