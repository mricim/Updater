package main.java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.java.os.OsCheck;
import main.java.propieties.GetPropertyValues;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.io.File;

import static main.java.CallOthers.generateFiles;


public class Main extends Application {
    public static final boolean DEVfalsePROUDCTIONtrue = false;//"DEV"=false or "PRODUCTION"=true

    public static final String PATH = System.getProperty("user.dir").replace("Updater", "");
    public static final String OS = OsCheck.operativeSystem();
    public static String propFileName = PATH + "/conf/config.properties";
    protected static String pathTemp;

    public static String name;
    public static String versionOld;
    public static String host;
    public static String project;
    public static String fileList;

    public static String hostDowloads;
    public static String[] versionOldSplit;
    public static String[] versionNewSplit;

    public static void main(String[] args) {
        launch(args);
    }

    private static String bold = "-fx-font-weight: bold";
    //Que hace?
    private static final Label isDoing = new Label("Read configs");
    //desarrolladores
    private static final TextArea textArea = new TextArea();
    //print versiones
    private static final HBox tagsHbox = new HBox();
    private static final HBox versionOldHbox = new HBox();
    private static final Label flecha = new Label();
    private static final HBox versionNewHbox = new HBox();
    private static final Region region = new Region();
    private static final Region region2 = new Region();


    @Override
    public void start(Stage window) {
        int tamano;
        if (DEVfalsePROUDCTIONtrue) {
            tamano = 100;
        } else {
            tamano = 250;
        }
        window.setTitle(name);


        StackPane root = new StackPane();
        window.setScene(new Scene(root, 400, tamano));
        window.show();
        window.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        //


        //
        VBox vBox = new VBox();
        vBox.setPadding(new Insets(25, 5, 5, 5));
        vBox.setAlignment(Pos.TOP_CENTER);
        vBox.setSpacing(10);
        vBox.prefWidthProperty().bind(window.widthProperty().multiply(0.80));
        root.getChildren().add(vBox);
        ObservableList<Node> vBoxChildren = vBox.getChildren();
        //labels
        tagsHbox.setAlignment(Pos.CENTER);
        tagsHbox.getChildren().addAll(isDoing);
        tagsHbox.prefWidthProperty().bind(vBox.widthProperty().multiply(0.80));
        vBoxChildren.add(tagsHbox);
        ProgressBar bar = new ProgressBar();
        bar.prefWidthProperty().bind(vBox.widthProperty().multiply(0.80));
        vBoxChildren.add(bar);
        if (!DEVfalsePROUDCTIONtrue) {
            textArea.setEditable(false);
            vBoxChildren.add(textArea);
        }
        HBox hBoxVersions = new HBox();
        hBoxVersions.setAlignment(Pos.CENTER);
        hBoxVersions.prefWidthProperty().bind(vBox.widthProperty().multiply(0.80));


        region.setMinWidth(5);
        HBox.setHgrow(region, Priority.ALWAYS);
        region.setMinWidth(5);
        HBox.setHgrow(region2, Priority.ALWAYS);
        flecha.setStyle(bold);
        hBoxVersions.getChildren().addAll(versionOldHbox, region, flecha, region2, versionNewHbox);
        vBoxChildren.add(hBoxVersions);

        System.out.println(propFileName);

        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                //final int max = 1000000;
                /*
                final int max = 18000;
                for (int i = 1; i <= max; i++) {
                    updateProgress(i, max);
                    consolaPRINT(i);
                }
                 */
                updateProgress(1, 100);
                try {
                    //crear fichero de configs
                    generateFiles();
                    //leer del fichero de configs
                    updateProgress(2, 100);
                    consolaPRINT("Read configs");
                    new GetPropertyValues().getPropValues();
                    consolaPRINT("starter variables");
                    hostDowloads = host + project;
                    versionOldSplit = versionOld.split("\\.");
                    consolaPRINT("generator Labels");
                    generatorLabel(versionOldHbox, versionOldSplit, false, false, false);

                    updateProgress(3, 100);

                    consolaPRINT("Check Versions");
                    labelSetText(isDoing, "check new versions...");
                    //
                    try {
                        NodeList nodeList= XML.getList(XML.getDocument(hostDowloads, fileList));
                        Rutas toUpload = XML.chekUpdateMajor(nodeList,"installer");
                        consolaPRINT("INSTALLER? "+toUpload);
                        updateProgress(10, 100);

                        //TODO XMLtoUploader toXML = null;

                        if (toUpload == null) {
                            toUpload = XML.chekUpdateMinor(nodeList,"updater");
                            consolaPRINT("SUB VERSION? "+toUpload);
                            updateProgress(20, 100);
                        }
                        System.exit(0);
                        consolaPRINT("...");
                        if (toUpload != null) {
                            comparatorVersion(versionOldHbox, versionNewHbox, flecha, versionOldSplit, toUpload.getVersion().split("\\."));

                            labelSetText(isDoing, "Donwload new upgrade");
                            consolaPRINT(toUpload.toString());
                            try {
                                //GererateXMLtoUpdater.generar(file, toXML);
                                updateProgress(80, 100);
                                File file = Updater.dowloadFiles(toUpload);
                                labelSetText(isDoing, "Install...");
                                updateProgress(100, 100);
                                new File(propFileName).delete();//ELIMINAR EL CONFIGS
                                if (file != null) {// INSTALAR
                                    consolaPRINT("UPDATE INSTALLER");
                                    CallOthers.installer(file);
                                    System.exit(0);
                                } else {
                                    consolaPRINT("UPDATE NORMAL");
                                }
                            } catch (Exception e) {
                                consolaPRINT(e.getMessage());
                                e.printStackTrace();
                                //TODO ERROR DE DESCARGA
                            }
                        } else {
                            consolaPRINT("NO UPDATES");
                        }
                        updateProgress(100, 100);
                        //Updater.files()
                        labelSetText(isDoing, "Start!");
                        CallOthers.inciarApp();
                        System.exit(0);
                    } catch (Exception e) {
                        consolaPRINT(e.getMessage());
                        e.printStackTrace();
                        //TODO ERROR NO se encuentra la web
                    }
                } catch (Exception e) {
                    consolaPRINT("Generate Files error");
                    e.printStackTrace();
                }
                return null;
            }

        };
        bar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }



    private void labelSetText(Label label, String text) {
        Platform.runLater(new Runnable() {//in case you call from other thread
            @Override
            public void run() {
                label.setText(text);
            }
        });
    }

    public static void consolaPRINT(String s) {
        Platform.runLater(new Runnable() {//in case you call from other thread
            @Override
            public void run() {
                textArea.setText(textArea.getText() + s + "\n");
                System.out.println(s);//for echo if you want
            }
        });
        //
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void generatorLabel(HBox versionOldBOX, String[] versionSplit, boolean zeroIf, boolean unoIf, boolean dosIf) {
        String punto = ".";
        final Text puntoText = new Text(punto);
        final Text puntoTextdos = new Text(punto);

        final Text zeroText = new Text(versionSplit[0]);
        final Text unoText = new Text(versionSplit[1]);
        final Text dosText = new Text(versionSplit[2]);
        if (zeroIf) {
            zeroText.setStyle(bold);
        }
        if (unoIf) {
            unoText.setStyle(bold);
        }
        if (dosIf) {
            dosText.setStyle(bold);
        }
        Platform.runLater(new Runnable() {//in case you call from other thread
            @Override
            public void run() {
                versionOldBOX.getChildren().clear();
                versionOldBOX.getChildren().addAll(zeroText, puntoText, unoText, puntoTextdos, dosText);
            }
        });
    }

    private void comparatorVersion(HBox textFlowOld, HBox textFlowNew, Label flecha, String[] versionOldSplit, String[] versionNewSplit) {
        boolean zero = false;
        boolean uno = false;
        boolean dos = false;
        if (Integer.parseInt(versionOldSplit[0]) != Integer.parseInt(versionNewSplit[0])) {
            zero = true;
        }
        if (Integer.parseInt(versionOldSplit[1]) != Integer.parseInt(versionNewSplit[1])) {
            uno = true;
        }
        if (Integer.parseInt(versionOldSplit[2]) != Integer.parseInt(versionNewSplit[2])) {
            dos = true;
        }

        generatorLabel(textFlowOld, versionOldSplit, zero, uno, dos);
        Platform.runLater(new Runnable() {//in case you call from other thread
            @Override
            public void run() {
                flecha.setText("-->");
            }
        });
        generatorLabel(textFlowNew, versionNewSplit, zero, uno, dos);
    }

    private void closeWindowEvent(WindowEvent event) {
        //https://stackoverflow.com/questions/26619566/javafx-stage-close-handler
        System.out.println("Window close request ...");
        System.exit(0);
/*
        if(storageModel.dataSetChanged()) {  // if the dataset has changed, alert the user with a popup
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.getButtonTypes().remove(ButtonType.OK);
            alert.getButtonTypes().add(ButtonType.CANCEL);
            alert.getButtonTypes().add(ButtonType.YES);
            alert.setTitle("Quit application");
            alert.setContentText(String.format("Close without saving?"));
            alert.initOwner(primaryStage.getOwner());
            Optional<ButtonType> res = alert.showAndWait();

            if(res.isPresent()) {
                if(res.get().equals(ButtonType.CANCEL))
                    event.consume();
            }
        }

 */
    }
}
