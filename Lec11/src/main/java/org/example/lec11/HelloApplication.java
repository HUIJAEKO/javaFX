package org.example.lec11;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.text.Text;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        //3.6 : 1%
        double appleAngle = 3.6 * 20;
        double htcAngle = 3.6 * 26;
        double samsungAngle = 3.6 * 28;
        double othersAngle = 3.6 * 26;

        Arc  apple = new Arc(150, 100, 80, 80, 0, appleAngle);
        apple.setFill(Color.RED);
        apple.setType(ArcType.ROUND);
        apple.setStroke(Color.RED);

        Arc htc = new Arc(150, 100, 80, 80, appleAngle, htcAngle);
        htc.setFill(Color.BLUE);
        htc.setType(ArcType.ROUND);
        htc.setStroke(Color.BLUE);

        Arc samsung = new Arc(150, 100, 80, 80, htcAngle + appleAngle, samsungAngle);
        samsung.setFill(Color.GREEN);
        samsung.setType(ArcType.ROUND);
        samsung.setStroke(Color.GREEN);

        Arc others = new Arc(150, 100, 80, 80, htcAngle + appleAngle + samsungAngle, othersAngle);
        others.setFill(Color.ORANGE);
        others.setType(ArcType.ROUND);
        others.setStroke(Color.ORANGE);

        Group group = new Group();
        group.getChildren().addAll(new Text(210, 40, "Apple: 20%"),
                apple, new Text(20, 40, "HTC: 26%"), htc,
                new Text(20, 170, "Samsung: 28%"), samsung,
                new Text(210, 170, "Others: 26%"), others);

        Scene scene = new Scene(new BorderPane(group), 600, 400);
        primaryStage.setTitle("ShowMarket");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}