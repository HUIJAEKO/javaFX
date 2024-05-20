package org.example.lec12;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        //Text
        Pane pane = new Pane();
        Text text1 = new Text(330, 85, "X");
        Text text2 = new Text(210, 40, "Y");
        Text text3 = new Text(140, 115, "-π");
        Text text4 = new Text(240, 115, "π");
        Text text5 = new Text(90, 115, "-2π");
        Text text6 = new Text(290, 115, "2π");

        //Line
        Line line1 = new Line(40, 105, 340, 105);
        Line line2 = new Line(340, 105, 320, 90);
        Line line3 = new Line(340, 105, 320, 120);
        Line line4 = new Line(190, 30, 190, 190);
        Line line5 = new Line(190, 30, 205, 50);
        Line line6 = new Line(190, 30, 175, 50);

        //Cosine Function
        Polyline polyline = new Polyline();
        for (double x = -3*Math.PI; x <= 3*Math.PI; x += 0.01) {
            double y = Math.cos(x);
            polyline.getPoints().addAll(
                    x * 16 + 380 / 2,
                    -y * 40 + 210 / 2
            );
        }
        polyline.setStroke(Color.RED);
        polyline.setStrokeWidth(2);

        //Circle
        Circle circle = new Circle(10);
        circle.setFill(Color.GREEN);

        //circle move
        PathTransition pt = new PathTransition();
        pt.setDuration(Duration.millis(3000));
        pt.setPath(polyline);
        pt.setNode(circle);
        pt.setCycleCount(Timeline.INDEFINITE);
        pt.setAutoReverse(true);
        pt.play();

        pane.setOnMousePressed(e -> pt.pause());
        pane.setOnMouseReleased(e -> pt.play());

        //addAll
        pane.getChildren().addAll(text1, text2, text3, text4, text5, text6,
                line1, line2, line3, line4, line5, line6, polyline, circle);

        //Scene
        Scene scene = new Scene(pane, 380, 230);
        primaryStage.setTitle("CosineCurveBall");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}