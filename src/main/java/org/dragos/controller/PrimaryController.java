package org.dragos.controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalTime;
import java.util.Random;
import java.util.ResourceBundle;


public class PrimaryController implements Initializable {

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

        Random r = new Random();
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            time.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
            try {
                myWriter.write("asd");    //aici o sa iau date din fisier cu temperatura in care rulez un script
                temperature.setText(String.valueOf(r.nextInt(50 - 40) + 40));  //aici o sa setez text ce vine din fisier
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

    public void stopUi(ActionEvent actionEvent) {
        try {
            myWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clock.stop();
    }
}