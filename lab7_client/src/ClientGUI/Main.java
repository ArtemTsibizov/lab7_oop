package ClientGUI;

import javafx.scene.paint.Color;
import matrix.Matrix;
import client.SocketClient;
import exception.MatrixSizeOutOfBoundException;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InterfaceAddress;

public class Main extends Application{
    private Matrix matrix;
    private SocketClient socketClient;
    private GridPane gridPane;
    public static void main(String[] args)  {
        Application.launch();

        Main main = new Main();

    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Client App");

        /*stage.setX(150);
        stage.setY(70);*/
        stage.centerOnScreen();

        stage.setMinWidth(1200);
        stage.setMinHeight(700);


        // Верхняя часть
        BorderPane borderPane = new BorderPane();

        Label labelTop = new Label("Клиентская часть приложения");
        labelTop.setFont(Font.font ("TimesRoman", 18));
        labelTop.setTextFill(Color.rgb(255,255,255));
        HBox hboxTop = new HBox();
        hboxTop.setAlignment(Pos.CENTER);
        hboxTop.getChildren().add(labelTop);
        hboxTop.setBackground(new Background(new  BackgroundFill(Color.valueOf("#18007a"), null, null)));
        hboxTop.setPrefHeight(35);
        borderPane.setTop(hboxTop);




        // Центральная часть
        this.gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(20);
        gridPane.setVgap(20);
        borderPane.setBackground(new Background(new  BackgroundFill(Color.valueOf("#737591"), null, null)));

        borderPane.setCenter(gridPane);



        // Нижняя часть
        Label labelBottom = new Label("Результат (сумма нечётных(по значению) элементов матрицы): ");
        labelBottom.setFont(Font.font ("Verdana", 15));
        labelBottom.setTextFill(Color.rgb(255,255,255));


        HBox hboxBottom  = new HBox();
        hboxBottom.setAlignment(Pos.CENTER);
        hboxBottom.getChildren().add(labelBottom);
        hboxBottom.setBackground(new Background(new  BackgroundFill(Color.valueOf("#18007a"), null, null)));
        hboxBottom.setPrefHeight(35);
        borderPane.setBottom(hboxBottom);



        // Правая часть
        Label labelRight = new Label("Установить соединение");
        labelRight.setFont(Font.font ("Verdana", 15));
        labelRight.setTextFill(Color.rgb(255,255,255));

        //host
        HBox hBoxSocketIpAddress = new HBox();
        Label labelSocketIpAddress = new Label("Ip: ");
        labelSocketIpAddress.setPrefWidth(30);
        TextField textFieldsocketIpAddress = new TextField("127.0.0.1");
        hBoxSocketIpAddress.getChildren()
                .addAll(labelSocketIpAddress, textFieldsocketIpAddress);

        //port
        HBox hBoxsocketPort = new HBox();
        Label labelSocketPort = new Label("Port:");
        labelSocketPort.setPrefWidth(30);
        TextField textFieldSocketPort = new TextField("4000");
        hBoxsocketPort.getChildren()
                .addAll(labelSocketPort, textFieldSocketPort);
        //btn
        Button establishConnection = new Button("Ввод");


        VBox vboxRight = new VBox();
        vboxRight.setAlignment(Pos.CENTER);
        vboxRight.setSpacing(10);
        vboxRight.setPadding(new Insets(10,10,10,10));
        vboxRight.getChildren().addAll(labelRight,
                hBoxSocketIpAddress, hBoxsocketPort,
                establishConnection);
        vboxRight.setBackground(new Background(new  BackgroundFill(Color.valueOf("#2f3475"), null, null)));


        borderPane.setRight(vboxRight);

        establishConnection.setOnMouseClicked(event -> {

            String socketIpAddress = "";
            int socketPort = 0;

            try {
                socketIpAddress = textFieldsocketIpAddress.getText().trim();
                socketPort =  Integer.parseInt(textFieldSocketPort.getText());

            }catch (Exception e) {
                displayError("Порт или адрес введен неправильно!");
            }


            if (matrix == null) {
                displayError("Сначала нужно создать матрицу!");
            } else if(!socketIpAddress.matches("(\\b25[0-5]|\\b2[0-4][0-9]|\\b[01]?[0-9][0-9]?)(\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)){3}")){
                displayError("Адрес введен неправильно!");
            } else if (socketPort < 0 || socketPort > 65_535) {
                displayError("Порт введен неправильно!");
            } else{
                try {
                    double sum = SocketClient.getSumOfOddElements(socketIpAddress, socketPort, matrix);
                    labelBottom.setText("Результат (сумма нечётных(по значению) элементов матрицы): " + sum);
                } catch (IOException e) {
                    displayError("Не удалось получить ответ от сервера!");
                }
            }
        });


        // Левая часть
        Label labelLeft = new Label("Введите размеры матрицы");
        labelLeft.setFont(Font.font ("Verdana", 15));
        labelLeft.setTextFill(Color.rgb(255,255,255));
        TextField matrixWidthInput = new TextField("1");
        TextField matrixHeightInput = new TextField("1");
        Button submitInput = new Button("Ввод");

        submitInput.setOnMouseClicked(event -> {
            try{
                int rows = Integer.parseInt(matrixWidthInput.getText());
                int columns = Integer.parseInt(matrixHeightInput.getText());

                if(rows > 10 || columns > 10 || rows < 1 || columns < 1){
                    displayError("Длина и ширина должны быть целыми числами от 1 до 10");
                }
                else{
                    this.matrix = new Matrix(rows, columns);
                    displayMatrix(matrix);

                }
            } catch (NumberFormatException e){

                displayError("Длина и ширина должны быть целыми числами от 1 до 10");

            }


        });



        VBox vboxLeft = new VBox();
        vboxLeft.setAlignment(Pos.CENTER);
        vboxLeft.setSpacing(10);
        vboxLeft.setPadding(new Insets(10,10,10,10));
        vboxLeft.getChildren().add(labelLeft);
        vboxLeft.getChildren().add(matrixWidthInput);
        vboxLeft.getChildren().add(matrixHeightInput);
        vboxLeft.getChildren().add(submitInput);
        vboxLeft.setBackground(new Background(new  BackgroundFill(Color.valueOf("#2f3475"), null, null)));
        borderPane.setLeft(vboxLeft);





        /*borderPane.getTop().setStyle("-fx-border-color: red;");
        borderPane.getCenter().setStyle("-fx-border-color: pink;");
        borderPane.getRight().setStyle("-fx-border-color: orange;");
        borderPane.getLeft().setStyle("-fx-border-color: blue;");*/

        Scene scene = new Scene(borderPane);
        stage.setScene(scene);
        stage.show();



    }
    public void displayError(String error){
        Stage numberFormatErrorStage = new Stage();
        Label numberFormatErrorLabel = new Label(error);
        numberFormatErrorLabel.setPadding(new Insets(10));
        numberFormatErrorLabel.setAlignment(Pos.CENTER);
        numberFormatErrorLabel.setFont(Font.font ("Verdana", 20));

        Scene numberFormatErrorScene = new Scene(numberFormatErrorLabel);
        numberFormatErrorStage.initModality(Modality.APPLICATION_MODAL);
        numberFormatErrorStage.setScene(numberFormatErrorScene);
        numberFormatErrorStage.setTitle("Ошибка!");
        numberFormatErrorStage.showAndWait();
    }

    public void displayMatrix(Matrix matrix){

        gridPane.getChildren().clear();
        for(int y = 0; y < matrix.getRowCount(); y++){
            for(int x = 0 ; x < matrix.getColumnCount(); x++) {
                HBox hBox = new HBox();

                Label labelCenter = new Label(  y + " " + x + " = ");

                Label labelElement = new Label(String.format("%.3f",matrix.getElement(y,x)));
                labelElement.setPrefWidth(80);
                labelElement.setFont(Font.font ("Verdana", 15));
                hBox.getChildren().addAll(labelCenter,labelElement);
                hBox.setStyle("-fx-border-color: black;");
                labelElement.setFont(Font.font ("Verdana", 15));
                labelElement.setTextFill(Color.rgb(255,255,255));
                hBox.setPrefWidth(115);
                hBox.setAlignment(Pos.CENTER);
                gridPane.add(hBox, x, y);
            }
        }
    }
}

