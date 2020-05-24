package ru.spbstu;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MinesweeperApp extends Application {
    static Stage stage;
    Menu m = new Menu();

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.getIcons().add(new Image(MinesweeperApp.class.getResourceAsStream("/images/icon.png")));
        stage.setTitle("Hex Minesweeper");
        stage.setResizable(false);
        stage.setScene(m.createMenu());
        stage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}