package com.andreabrioschi.bikesharing;

import com.andreabrioschi.bikesharing.database.DbFactory;
import com.andreabrioschi.bikesharing.models.Rastrelliera;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    public static AppMode mode;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("login"), 923, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        startingProcedure();
        launch();
    }
    
    private static void startingProcedure(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Seleziona modalità di utilizzo:\n1) TOTEM\n2) MANUTENZIONE");
        while (!sc.hasNextInt()) {
            sc.nextInt();
        }
        int actMode = sc.nextInt();
        switch (actMode) {
            case 1:
                System.out.println("CONFIGURAZIONE MODALITA' TOTEM");
                System.out.println("Inserire id della rastrelliera:");
                while (!sc.hasNextInt()) {
                    sc.nextInt();
                }
                int id = sc.nextInt();

                Rastrelliera r = DbFactory.rastrelliera().getById(id);
                
                AppSession.getInstance().setRastrellieraSelezionata(r);
                
                mode = AppMode.TOTEM;
                break;
            case 2:
                mode = AppMode.MAINTENANCE;
                break;
            default:
                mode = AppMode.DEVELOPMENT;
                break;

        }
    }

}
