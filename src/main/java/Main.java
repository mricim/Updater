package main.java;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.java.jsoup.ListWeb;
import main.java.os.Os;
import main.java.propieties.GetPropertyValues;
import main.java.versions.Version;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;


public class Main extends Application {
    public static final String STATUS = "PRODUCTION";//TODO   "DEV" or "PRODUCTION"

    public static final String PATH = System.getProperty("user.dir").replace("Updater", "");
    public static final String OS = Os.operativeSystem();
    public static String propFileName = PATH.replace("/bin", "") + "/config.properties";
    protected static String pathTemp;

    public static String name;
    public static String versionOld;
    public static String host;
    public static String project;
    public static String fileList;

    public static String hostDowloads;
    public static String[] versionOldSplit;

    public static void main(String[] args) {
        consolaPRINT(propFileName);
        launch(args);
    }

    private static final TextArea textArea = new TextArea();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle(name);


        StackPane root = new StackPane();
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();


        //


        //
        VBox vBox = new VBox();
        root.getChildren().add(vBox);
        ObservableList<Node> vBoxChildren = vBox.getChildren();
        Label label = new Label("check new versions...");
        vBoxChildren.add(label);
        ProgressBar bar = new ProgressBar();
        vBoxChildren.add(bar);
        if (STATUS.equals("DEV")) {
            vBoxChildren.add(textArea);
        }
        Task task = new Task<Void>() {
            @Override
            public Void call() {
                //final int max = 1000000;
                consolaPRINT("cuenta atras");
                try {
                    Thread.sleep(6000);
                } catch (InterruptedException e) {
                    consolaPRINT(e.getMessage());
                    e.printStackTrace();
                }
                consolaPRINT("iniciamos codigo");
                /*
                final int max = 18000;
                for (int i = 1; i <= max; i++) {
                    updateProgress(i, max);
                    consolaPRINT(i);
                }
                 */
                updateProgress(2, 100);
                try {
                    //leer del fichero de configs
                    new GetPropertyValues().getPropValues();
                    consolaPRINT("lectura de configs");
                    updateProgress(5, 100);
                    hostDowloads = host + project;
                    versionOldSplit = versionOld.split("\\.");
                    //
                    try {
                        Document dowloadPrincipal = ListWeb.parseFile(hostDowloads, fileList);
                        Version toUpload = Version.chekUpdateMajor(dowloadPrincipal);
                        updateProgress(10, 100);
                        //TODO XMLtoUploader toXML = null;
                        if (toUpload == null) {
                            consolaPRINT("SUBVERSION?");
                            toUpload = Version.chekUpdateMinor(dowloadPrincipal);
                            updateProgress(20, 100);
                        }
                        consolaPRINT("...");
                        if (toUpload != null) {
                            consolaPRINT(toUpload.toString());
                            try {
                                //GererateXMLtoUpdater.generar(file, toXML);
                                updateProgress(80, 100);
                                File file = Updater.dowloadFiles(toUpload);
                                updateProgress(90, 100);
                                if (file != null) {// INSTALAR
                                    Updater.installer(file);
                                    System.exit(0);
                                } else {
                                    consolaPRINT("UPDATE NORMAL");
                                    primaryStage.close();
                                    Updater.inciarApp();
                                }
                            } catch (IOException e) {
                                consolaPRINT(e.getMessage());
                                e.printStackTrace();
                                //TODO ERROR DE DESCARGA
                            }
                        } else {
                            consolaPRINT("NO UPDATES");
                            Updater.inciarApp();
                        }
                    } catch (Exception e) {
                        consolaPRINT(e.getMessage());
                        e.printStackTrace();
                        //TODO ERROR NO se encuentra la web
                    }
                } catch (IOException e) {//RECIEN INSTALADO
                    consolaPRINT("recien instalado");
                    try {
                        Updater.inciarApp();
                    } catch (IOException ioException) {
                        consolaPRINT(ioException.getMessage());
                        ioException.printStackTrace();
                    }
                }

                return null;
            }
        };
        bar.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    public static void consolaPRINT(String s) {
        Platform.runLater(new Runnable() {//in case you call from other thread
            @Override
            public void run() {
                textArea.setText(textArea.getText() + s + "\n");
                System.out.println(s);//for echo if you want
            }
        });
    }
}
