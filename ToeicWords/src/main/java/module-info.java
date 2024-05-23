module org.example.toeicwords {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires java.desktop;
    requires java.logging;


    opens org.example.toeicwords to javafx.fxml;
    exports org.example.toeicwords;
}