package ru.spbstu;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Menu {
    Field field = new Field(20, 50, this);
    static Scene scene2;

    TextField fieldSizeTF = new TextField("20");
    TextField bombsAmountTF = new TextField("50");

    int W;
    int H;

    Scene createMenu() {
        Image img = new Image(MinesweeperApp.class.getResourceAsStream("/images/menuBackground.png"));
        BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        Label label = new Label("WELCOME TO THE HEX MINESWEEPER GAME");
        label.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 40));
        Button b = new Button("CLICK TO START");
        b.setMaxSize(250, 100);
        b.setDefaultButton(true);

        Text fSText = new Text("Enter N to set a field size (N x N)");
        fSText.setFont(Font.font("arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 20));
        Button button1 = new Button("Confirm");

        Text bAText = new Text("Enter bombs amount");
        bAText.setFont(Font.font("arial", FontWeight.SEMI_BOLD, FontPosture.ITALIC, 20));
        Button button2 = new Button("Confirm");

        b.setOnAction(e -> {
            scene2 = new Scene(field.createScene());
            MinesweeperApp.stage.setScene(scene2);
        });
        button1.setOnAction(e -> getFieldSize());
        button2.setOnAction(e -> getBombsAmount());

        VBox vBox = new VBox(25);
        vBox.setPrefSize(1200, 800);
        HBox hBox1 = new HBox(25);
        HBox hBox2 = new HBox(25);

        hBox1.getChildren().addAll(fSText, fieldSizeTF, button1);
        hBox2.getChildren().addAll(bAText, bombsAmountTF, button2);
        hBox1.setAlignment(Pos.CENTER);
        hBox2.setAlignment(Pos.CENTER);

        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(label, hBox1, hBox2, b);

        Pane pane1 = new Pane();
        pane1.getChildren().add(vBox);
        pane1.setBackground(new Background(backgroundImage));
        return new Scene(pane1, 1200, 800);
    }


    //setting a new value to fieldSize according to user input
    public void getFieldSize() {
        int res = Integer.parseInt(fieldSizeTF.getText());
        if (res != 0) {
            field.fieldSize = res;
        }
        W = Hex.tileSize * field.fieldSize;
        H = Hex.tileSize * (field.fieldSize + 1);
    }

    //setting a new value to bombsAmount according to user input
    public void getBombsAmount() {
        int res = Integer.parseInt(bombsAmountTF.getText());
        if (res != 0) {
            field.bombsAmount = res;
        }
    }

    //creating a final scene if a user won the game
    public Pane win(int W, int H) {
        Image img = new Image(MinesweeperApp.class.getResourceAsStream("/images/confetti.png"));
        BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        Pane pane = new Pane();

        VBox vBox = new VBox(20);
        vBox.setPrefSize(W, H);
        vBox.setMinSize(800, 600);
        Label label = new Label("CONGRATULATIONS");
        label.setFont(new Font(35));
        Label label2 = new Label("You WON");
        label2.setFont(new Font(25));
        Button b = new Button("Start again");
        b.setOnAction(e -> Menu.scene2.setRoot(field.createScene()));
        vBox.getChildren().addAll(label, label2, b);
        vBox.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(vBox);
        pane.setBackground(new Background(backgroundImage));
        return pane;
    }

    //creating a final scene if a user lost the game
    public Pane lost(int W, int H) {
        Image img = new Image(MinesweeperApp.class.getResourceAsStream("/images/lost.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(img, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(1.0, 1.0, true, true, false, false));

        Pane pane = new Pane();

        VBox vBox = new VBox(20);
        vBox.setPrefSize(W, H);
        vBox.setMinSize(800, 600);
        Text text = new Text("GAME OVER");
        text.setFont(Font.font("arial", FontWeight.BOLD, FontPosture.REGULAR, 30));
        text.setFill(Color.WHITE);
        Button b = new Button("Start again");
        b.setOnAction(e -> Menu.scene2.setRoot(field.createScene()));
        vBox.getChildren().addAll(text, b);
        vBox.setAlignment(Pos.CENTER);
        pane.getChildren().addAll(vBox);
        pane.setBackground(new Background(backgroundImage));

        return pane;
    }
}
