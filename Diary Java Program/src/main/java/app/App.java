package app;  // Ensure this matches your folder structure

import controller.DiaryController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.EntryList;
import java.io.IOException;

public class App extends Application {

    private EntryList entryList = new EntryList(); // Model

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            System.out.println("Initializing application...");

            // Load FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/mainview.fxml"));
            Scene scene = new Scene(loader.load());

            // Load controller and set model
            DiaryController controller = loader.getController();
            controller.setEntryList(entryList); 

            // Set up stage
            primaryStage.setTitle("Diary");
            primaryStage.setScene(scene);
            primaryStage.show();

            // Print diary file
            controller.printFile("src/main/resources/diaryentries.txt");

            System.out.println("Application started successfully!");

        } catch (IOException e) {
            System.err.println("Error loading FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
