package org.example.toeicwordstudy;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class HelloApplication extends Application {
    private static final String FILE_PATH = "words.xlsx";
    private List<Word> words;
    private TextArea wordListArea;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        words = readWordsFromExcel();

        primaryStage.setTitle("Word Study Program");

        // Create UI elements
        wordListArea = new TextArea();
        wordListArea.setEditable(false);
        updateWordList();

        TextField wordField = new TextField();
        wordField.setPromptText("Word");

        TextField meaningField = new TextField();
        meaningField.setPromptText("Meaning");

        Button addButton = new Button("Add Word");
        addButton.setOnAction(e -> {
            String word = wordField.getText();
            String meaning = meaningField.getText();
            if (!word.isEmpty() && !meaning.isEmpty()) {
                words.add(new Word(word, meaning));
                writeWordsToExcel(words);
                updateWordList();
                wordField.clear();
                meaningField.clear();
            }
        });

        Button removeButton = new Button("Remove Word");
        removeButton.setOnAction(e -> {
            String word = wordField.getText();
            words.removeIf(w -> w.getWord().equalsIgnoreCase(word));
            writeWordsToExcel(words);
            updateWordList();
            wordField.clear();
        });

        Button quizButton = new Button("Start Quiz");
        quizButton.setStyle("-fx-font-size: 16px; -fx-pref-width: 200px;");
        quizButton.setOnAction(e -> startQuiz());

        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(10));
        inputGrid.setVgap(10);
        inputGrid.setHgap(10);
        inputGrid.add(new Label("Word:"), 0, 0);
        inputGrid.add(wordField, 1, 0);
        inputGrid.add(new Label("Meaning:"), 0, 1);
        inputGrid.add(meaningField, 1, 1);
        inputGrid.add(addButton, 0, 2);
        inputGrid.add(removeButton, 1, 2);

        VBox vbox = new VBox(10, inputGrid, quizButton);
        vbox.setPadding(new Insets(10));
        vbox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(vbox, 400, 500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void updateWordList() {
        wordListArea.clear();
        for (Word word : words) {
            wordListArea.appendText(word + "\n");
        }
    }

    private void startQuiz() {
        if (words.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No words available for quiz.", ButtonType.OK);
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
                alert = new Alert(Alert.AlertType.INFORMATION, "Correct!", ButtonType.OK);
            } else {
                alert = new Alert(Alert.AlertType.INFORMATION, "Wrong! The correct meaning is: " + wordToGuess.getMeaning(), ButtonType.OK);
            }
            alert.showAndWait();
        }
    }

    private List<Word> readWordsFromExcel() {
        List<Word> words = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(FILE_PATH);
             Workbook workbook = new XSSFWorkbook(fis)) {

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
            e.printStackTrace();
        }
        return words;
    }

    private void writeWordsToExcel(List<Word> words) {
        try (Workbook workbook = new XSSFWorkbook();
             FileOutputStream fos = new FileOutputStream(FILE_PATH)) {

            Sheet sheet = workbook.createSheet("Words");
            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                Row row = sheet.createRow(i);
                row.createCell(0).setCellValue(word.getWord());
                row.createCell(1).setCellValue(word.getMeaning());
            }
            workbook.write(fos);
        } catch (IOException e) {
            e.printStackTrace();
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
