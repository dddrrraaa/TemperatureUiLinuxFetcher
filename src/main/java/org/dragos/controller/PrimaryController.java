package org.dragos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.time.LocalTime;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Scanner;

public class PrimaryController implements Initializable {

    private static final String INPUT_FILE = "fisier.txt";
    @FXML
    public Label time;
    @FXML
    public Button stopButton;
    @FXML
    public Label temperature;

    private static final String FILE_NAME = "input.txt";
    private static FileWriter myWriter;
    private static Timeline clock;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            myWriter = new FileWriter(FILE_NAME);
        } catch (IOException e) {
            e.printStackTrace();
        }
        initClock();
        initHoverState();
    }

    void initClock() {

        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            time.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
            try (Scanner s = new Scanner(new BufferedReader(new FileReader(INPUT_FILE)))) {
                s.useLocale(Locale.US);
                int n = Integer.parseInt(s.next());
                System.out.println(n); //used for validation
                temperature.setText(String.valueOf(n));
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    void initHoverState() {
        stopButton.setOnMouseEntered(event -> stopButton.setStyle("-fx-background-color:orange;"));
        stopButton.setOnMouseExited(event -> stopButton.setStyle("-fx-background-color:coral;"));
    }

    public void stopUi() {
        try {
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clock.stop();
    }
}