package org.example.toeicwords;

import java.awt.Desktop;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class HelloApplication extends Application {
    private static final String FILE_PATH = "words.xlsx";
    private List<Word> words;
    private static final Logger LOGGER = Logger.getLogger(HelloApplication.class.getName());


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        words = readWordsFromExcel();

        primaryStage.setTitle("Word Study Program");

        Button openExcelButton = new Button("Open Excel");
        openExcelButton.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 18px; -fx-pref-width: 250px;");
        openExcelButton.setOnAction(e -> openExcelFile());

        Button quizButton = new Button("Start Quiz");
        quizButton.setStyle("-fx-background-color: #555555; -fx-text-fill: white; -fx-font-size: 18px; -fx-pref-width: 250px;");
        quizButton.setOnAction(e -> startQuiz());

        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(openExcelButton, quizButton);

        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().add(buttonBox);

        VBox vbox = new VBox(hbox);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    private void startQuiz() {
        words = readWordsFromExcel();

        if (words.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No words in excel file.", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        Random random = new Random();
        Word wordToGuess = words.get(random.nextInt(words.size()));

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Quiz");
        dialog.setHeaderText("What is the meaning of '" + wordToGuess.getWord() + "'?");
        Optional<String> result = dialog.showAndWait();

        if (result.isPresent()) {
            String answer = result.get();
            Alert alert;
            if (answer.equalsIgnoreCase(wordToGuess.getMeaning())) {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Correct!", ButtonType.NEXT, ButtonType.CLOSE);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle("-fx-background-color: #81C784;");
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION, "Wrong! The correct meaning is: " + wordToGuess.getMeaning(), ButtonType.NEXT, ButtonType.CLOSE);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setStyle("-fx-background-color: #EF9A9A;");
            }
            Optional<ButtonType> choice = alert.showAndWait();
            if (choice.isPresent() && choice.get() == ButtonType.NEXT) {
                startQuiz();
            }
        }
    }

    private List<Word> readWordsFromExcel() {
        List<Word> words = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return words;
        }
        try (FileInputStream fis = new FileInputStream(file);
        Workbook workbook = WorkbookFactory.create(fis)) {
            Sheet sheet = workbook.getSheetAt(0);
            for (Row row : sheet) {
                Cell wordCell = row.getCell(0);
                Cell meaningCell = row.getCell(1);

                if (wordCell != null && meaningCell != null) {
                    String word = wordCell.getStringCellValue();
                    String meaning = meaningCell.getStringCellValue();
                    words.add(new Word(word, meaning));
                }
            }
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading Excel file", e);
        }
        return words;
    }

    private static void openExcelFile() {
        File file = new File(FILE_PATH);

        Desktop desktop = Desktop.getDesktop();
        try {
            desktop.open(file);
        } catch (IOException e) {
            System.out.println("Error opening file: " + e.getMessage());
        }
    }


    public static class Word {
        private final String word;
        private final String meaning;

        public Word(String word, String meaning) {
            this.word = word;
            this.meaning = meaning;
        }

        public String getWord() {
            return word;
        }

        public String getMeaning() {
            return meaning;
        }

        @Override
        public String toString() {
            return "Word: " + word + ", Meaning: " + meaning;
        }
    }

}
