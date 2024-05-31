module org.example.goodbyefinalexam {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.goodbyefinalexam to javafx.fxml;
    exports org.example.goodbyefinalexam;
}