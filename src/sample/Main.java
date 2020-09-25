package sample;

import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {

    private static final double PAUSE = 1;
    public int num = 0;
    double x0 = 20, y0 = 20, altoMarco, anchoLuces, anchoSemaforo=100, altoSemaforo=200;
    private static int threadNumber = 0, invokeThreadNumber =0;
    private static final Object myLock = new Object();

    Color colorsMatrix[][] = {
            {Color.DARKRED,Color.GOLDENROD,Color.DARKGREEN},
            {Color.RED,Color.GOLDENROD,Color.DARKGREEN},
            {Color.DARKRED,Color.YELLOW,Color.DARKGREEN},
            {Color.DARKRED,Color.GOLDENROD,Color.LIMEGREEN}
    };
    String signals[] = {"Semáforo Apagado", "ROJO", "AMARILLO", "VERDE"};
    String lightsBackground[] = {"-fx-background-color: lightgrey;", "-fx-background-color: red;",
            "-fx-background-color: yellow;", "-fx-background-color: limegreen"};
    String messages[] = {"Semáforo", "ROJO", "AMARILLO", "VERDE"};

    GraphicsContext graphicsContext;
    GraphicsContext graphicsContext2;
    GraphicsContext graphicsContext3;
    GraphicsContext graphicsContext4;
    List<GraphicsContext> contextList = new ArrayList<>();

    /*Innit*/
    @Override
    public void start(Stage primaryStage) throws Exception{

        showSemaphore(primaryStage);
    }
    public void showSemaphore(Stage primaryStage)throws Exception{

        Pane root = new Pane(); // pentru a putea da coordonate absolute controalelor
        //Dimensiones del canvas reservado para el primer semaforo.
        Canvas canvasSemaforo1 = new Canvas(400,600);
        canvasSemaforo1.setLayoutX(300);
        canvasSemaforo1.setLayoutY(0);
        graphicsContext = canvasSemaforo1.getGraphicsContext2D();
        contextList.add(graphicsContext);

        Canvas canvasSemaforo2 = new Canvas(400,600);
        canvasSemaforo2.setLayoutX(300);
        canvasSemaforo2.setLayoutY(300);
        graphicsContext2 = canvasSemaforo2.getGraphicsContext2D();
        contextList.add(graphicsContext2);

        Canvas canvasSemaforo3 = new Canvas(400,600);
        canvasSemaforo3.setLayoutX(150);
        canvasSemaforo3.setLayoutY(150);
        graphicsContext3 = canvasSemaforo3.getGraphicsContext2D();
        contextList.add(graphicsContext3);

        Canvas canvasSemaforo4 = new Canvas(400,600);
        canvasSemaforo4.setLayoutX(450);
        canvasSemaforo4.setLayoutY(150);
        graphicsContext4 = canvasSemaforo4.getGraphicsContext2D();
        contextList.add(graphicsContext4);

        creatingSemaphore(primaryStage, root, canvasSemaforo1, graphicsContext);
        creatingSemaphore(primaryStage, root, canvasSemaforo2, graphicsContext2);
        creatingSemaphore(primaryStage, root, canvasSemaforo3, graphicsContext3);
        creatingSemaphore(primaryStage, root, canvasSemaforo4, graphicsContext4);


        Label label = new Label("Semáforo funcional");
        label.setLayoutX(100);
        label.setLayoutY(550);
        label.setPrefHeight(25);
        label.setPrefWidth(200);
        label.setAlignment(Pos.CENTER);
        label.setStyle("-fx-background-color: lightgrey;");
        root.getChildren().add(label);

        //Encendido del semáforo.
        Button encendidoSemaforo = new Button();
        encendidoSemaforo.setText("Encender Semáforo");
        encendidoSemaforo.setLayoutX(100);
        encendidoSemaforo.setLayoutY(600);
        encendidoSemaforo.setPrefHeight(25);
        encendidoSemaforo.setPrefWidth(200);
        PauseTransition pause = new PauseTransition(Duration.seconds(PAUSE));
        control();
//        pause.setOnFinished(actionEvent ->{
//            num=++num%4;
//            //label.setStyle(lightsBackground[num]);
//            // label.setText(signals[num]);
//            //encendidoSemaforo.setText(messages[num]);
//            //porneste becuri semafor
//            for(int idx = 0;idx < 3;idx++) {
//                graphicsContext.setFill(colorsMatrix[num][idx]);
//                graphicsContext.fillOval(x0+altoMarco,y0+altoMarco+idx*(altoMarco+anchoLuces),anchoLuces,anchoLuces);
//            }
//            pause.play();
//        } );
//        pause.play();
        root.getChildren().add(encendidoSemaforo);
        primaryStage.show();
    }
    public void creatingSemaphore(Stage primaryStage, Pane root, Canvas canvas, GraphicsContext graphicsContext){
        altoMarco = altoSemaforo/28;
        anchoLuces = 8 * altoMarco;
        anchoSemaforo = 10 * altoMarco; //Dependiendo de la altura del semaforo.
        //Pintando el cuadro exterior del semáforo.
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(20);
        graphicsContext.strokeRect(x0,y0,anchoSemaforo,altoSemaforo);
        graphicsContext.setFill(Color.GREY);
        graphicsContext.fillRect(x0,y0,anchoSemaforo,altoSemaforo);
        //Dibuja las bombillas del semáforo

        for(int idx = 0 ;idx < 3;idx++) {
            graphicsContext.setStroke(Color.BLACK);
            graphicsContext.setLineWidth(5);
            graphicsContext.strokeOval(x0+altoMarco,y0+altoMarco+idx*(altoMarco+anchoLuces),anchoLuces,anchoLuces);
            graphicsContext.setFill(colorsMatrix[0][idx]);
            graphicsContext.fillOval(x0+altoMarco,y0+altoMarco+idx*(altoMarco+anchoLuces),anchoLuces,anchoLuces);
        }


        root.getChildren().add(canvas);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(root);


        Scene scene = new Scene(borderPane, 800, 650);

        primaryStage.setTitle("JavaFX - Simulador de Semáforo");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        //activatingSequence(graphicsContext, primaryStage);

    }

    public void activatingSequence(GraphicsContext graphicsContext, Stage primaryStage){
        num = 0;
        num=++num%4;

        for(int idx = 0;idx < 3;idx++) {
            graphicsContext.setFill(colorsMatrix[num][idx]);
            graphicsContext.fillOval(x0+altoMarco,y0+altoMarco+idx*(altoMarco+anchoLuces),anchoLuces,anchoLuces);
            primaryStage.show();
        }
    }
    private void control() {
        //invoke 3 synchronized control threads

        new Thread( new ColorControl()).start();
        new Thread( new ColorControl()).start();
        new Thread( new ColorControl()).start();

    }

    private void update() throws InterruptedException {
        num=++num%4;
        for (GraphicsContext gc : contextList){
            for(int idx = 0;idx < 3;idx++) {
                if (idx == 1){
                    Thread.sleep(1000);
                }
                gc.setFill(colorsMatrix[num][idx]);
                gc.fillOval(x0+altoMarco,y0+altoMarco+idx*(altoMarco+anchoLuces),anchoLuces,anchoLuces);
            }
        }
    }

    class ColorControl implements Runnable{
        private int threadID;
        private static final long PAUSE = 1500;
        private int MAX_THREADS = 3;
        private boolean isStopped = false;
        ColorControl() {
            threadID = threadNumber ++;
        }

        void reset() {
            threadNumber = 0; invokeThreadNumber =0;
        }
        @Override
        public void run() {
            synchronized (myLock) {

                while (! isStopped  ) {
                    while (threadID != invokeThreadNumber) {
                        try {
                            myLock.wait();
                        } catch (InterruptedException e) {}
                    }
                    //do work here
                    try {
                        update();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    try {
                        Thread.sleep(PAUSE);
                    } catch (InterruptedException ex) { ex.printStackTrace();}
                    invokeThreadNumber++;
                    myLock.notifyAll();
                    if( invokeThreadNumber >= MAX_THREADS ) {
                        reset();
                    }
                }
            }
        }

    }




    public static void main(String[] args) {
        launch(args);
    }
}
