module org.example.toeicwordstudy {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.poi.poi;
    requires org.apache.poi.ooxml;


    opens org.example.toeicwordstudy to javafx.fxml;
    exports org.example.toeicwordstudy;
}