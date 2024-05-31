package org.example.goodbyefinalexam;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.stage.Stage;


public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage){
        HBox h = new HBox(new Button("ok"), new Button("cancel"));
        h.setAlignment(Pos.CENTER);
    }


    public static void main(String[] args) {
        launch(args);
    }
}

class OKHandler implements EventHandler<ActionEvent>{
    @Override
    public void handle(ActionEvent e){
        System.out.println("ok");
    }
}