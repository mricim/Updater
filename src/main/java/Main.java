package main.java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.java.jsoup.ListWeb;
import main.java.os.Os;
import main.java.propieties.GetPropertyValues;
import main.java.versions.Version;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;

import static main.java.CallOthers.generateFiles;


public class Main extends Application {
    public static final String STATUS = "DEV";//TODO   "DEV" or "PRODUCTION"

    public static final String PATH = System.getProperty("user.dir").replace("Updater", "");
    public static final String OS = Os.operativeSystem();
    public static String propFileName = PATH + "/conf/config.properties";
    //public static String propFileName = PATH.replace("/bin", "") + "\\conf/config.properties";
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

    private static final TextArea textArea = new TextArea();
    private static final HBox versionOldBOX = new HBox();
    private static final Label label = new Label("check new versions...");

    @Override
    public void start(Stage window) {
        window.setTitle(name);


        StackPane root = new StackPane();
        window.setScene(new Scene(root, 300, 250));
        window.show();
        window.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        //


        //
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(15);
        vBox.prefWidthProperty().bind(window.widthProperty().multiply(0.80));
        root.getChildren().add(vBox);
        ObservableList<Node> vBoxChildren = vBox.getChildren();
        //label
        vBoxChildren.add(label);
        ProgressBar bar = new ProgressBar();
        bar.prefWidthProperty().bind(vBox.widthProperty().multiply(0.80));
        vBoxChildren.add(bar);
        if (STATUS.equals("DEV")) {
            textArea.setEditable(false);
            vBoxChildren.add(textArea);
        }
        HBox hBox = new HBox();
        hBox.prefWidthProperty().bind(vBox.widthProperty().multiply(0.80));

        Label flecha = new Label();
        HBox versionNewText = new HBox();
        hBox.getChildren().addAll(versionOldBOX, flecha, versionNewText);
        vBoxChildren.add(hBox);

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
                    generatorLabel(versionOldBOX, versionOldSplit, false, false, false);
                    consolaPRINT("generator Labels");
                } catch (Exception e) {
                    consolaPRINT("Generate Files error");
                    e.printStackTrace();
                }
                updateProgress(3, 100);

                consolaPRINT("Check Versions");
                //
                try {
                    Document dowloadPrincipal = ListWeb.parseFile(hostDowloads, fileList);
                    Version toUpload = Version.chekUpdateMajor(dowloadPrincipal);
                    updateProgress(10, 100);
                    //TODO XMLtoUploader toXML = null;
                    if (toUpload == null) {
                        consolaPRINT("SUB VERSION?");
                        toUpload = Version.chekUpdateMinor(dowloadPrincipal);
                        updateProgress(20, 100);
                    }
                    consolaPRINT("...");
                    if (toUpload != null) {
                        comparatorVersion(versionOldBOX, versionNewText, flecha, versionOldSplit, versionNewSplit);
                        labelSetText("Donwload new upload");
                        consolaPRINT(toUpload.toString());
                        try {
                            //GererateXMLtoUpdater.generar(file, toXML);
                            updateProgress(80, 100);
                            File file = Updater.dowloadFiles(toUpload);
                            labelSetText("Install...");
                            updateProgress(100, 100);
                            new File(propFileName).delete();//ELIMINAR EL CONFIGS
                            if (file != null) {// INSTALAR
                                CallOthers.installer(file);
                                System.exit(0);
                            } else {
                                consolaPRINT("UPDATE NORMAL");
                                CallOthers.inciarApp();
                                System.exit(0);
                            }
                        } catch (Exception e) {
                            consolaPRINT(e.getMessage());
                            e.printStackTrace();
                            //TODO ERROR DE DESCARGA
                        }
                    } else {
                        updateProgress(100, 100);
                        consolaPRINT("NO UPDATES");
                        labelSetText("Start!");
                        CallOthers.inciarApp();
                        System.exit(0);
                    }
                } catch (Exception e) {
                    consolaPRINT(e.getMessage());
                    e.printStackTrace();
                    //TODO ERROR NO se encuentra la web
                }
                return null;
            }

        };
        bar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    private void labelSetText(String string) {
        Platform.runLater(new Runnable() {//in case you call from other thread
            @Override
            public void run() {
                label.setText(string);
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
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void generatorLabel(HBox versionOldBOX, String[] versionSplit, boolean zeroIf, boolean unoIf, boolean dosIf) {
        String punto = ".";
        final Text puntoText = new Text(punto);
        final Text puntoTextdos = new Text(punto);
        String bold = "-fx-font-weight: bold";

        final Text zeroText = new Text(versionSplit[0]);
        final Text unoText = new Text(versionSplit[1]);
        final Text dosText = new Text(versionSplit[2]);
        if (zeroIf) {
            zeroText.setStyle(bold);
        }
        if (unoIf) {
            zeroText.setStyle(bold);
        }
        if (dosIf) {
            zeroText.setStyle(bold);
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
        flecha.setText("-->");
        generatorLabel(textFlowNew, versionNewSplit, zero, uno, dos);
    }

    private void closeWindowEvent(WindowEvent event) {
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
