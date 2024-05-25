package org.example.lec13;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class HelloApplication extends Application {

    @Override
    public void start(Stage primaryStage) {
        Text text = new Text("Colorful Text");
        text.setFont(Font.font(24));
        text.setTranslateY(100);

        Slider redSlider = createSlider(0, 255, 0);
        Slider greenSlider = createSlider(0, 255, 0);
        Slider blueSlider = createSlider(0, 255, 0);
        Slider opacitySlider = createSlider(0, 100, 100);

        redSlider.setShowTickLabels(false);
        greenSlider.setShowTickLabels(false);
        blueSlider.setShowTickLabels(false);
        opacitySlider.setShowTickLabels(false);

        text.fillProperty().bind(
                javafx.beans.binding.Bindings.createObjectBinding(() ->
                                Color.rgb((int) redSlider.getValue(), (int) greenSlider.getValue(), (int) blueSlider.getValue(),
                                        opacitySlider.getValue() / 100.0),
                        redSlider.valueProperty(), greenSlider.valueProperty(), blueSlider.valueProperty(),
                        opacitySlider.valueProperty()
                )
        );

        VBox sliders = new VBox(5,
                createSliderWithLabel("Blue", blueSlider),
                createSliderWithLabel("Red", redSlider),
                createSliderWithLabel("Green", greenSlider),
                createSliderWithLabel("Opacity", opacitySlider)
        );
        sliders.setAlignment(Pos.CENTER);
        sliders.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setTop(text);
        root.setBottom(sliders);
        BorderPane.setAlignment(text, Pos.CENTER);
        BorderPane.setAlignment(sliders, Pos.CENTER);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("Color Selector");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private HBox createSliderWithLabel(String labelText, Slider slider) {
        Label label = new Label(labelText);
        label.setPrefWidth(60);
        HBox hbox = new HBox(10, label, slider);
        hbox.setAlignment(Pos.CENTER);
        return hbox;
    }

    private Slider createSlider(double min, double max, double initialValue) {
        Slider slider = new Slider(min, max, initialValue);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setMajorTickUnit(50);
        slider.setBlockIncrement(10);
        return slider;
    }

    public static void main(String[] args) {
        launch(args);
    }
}