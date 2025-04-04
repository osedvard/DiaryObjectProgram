package controller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import model.TextEntry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.*;
import model.Checks;
import model.Entry;
import model.EntryList;
import model.Keywords;
import model.PictureEntry;

public class DiaryController {

    @FXML
    private VBox diaryContents;

    @FXML
    private TextField entryForDeletion;

    @FXML
    private TextArea entryText;

    @FXML
    private TextField entryTitle;

    @FXML
    private Label lblImagePath;

    @FXML
    private AnchorPane mainview;

    private String picturePath = "none";

    private EntryList entryList;

    public void setEntryList(EntryList entryList) {
        this.entryList = entryList; 
    }

    @FXML
    void newDiaryEntry(ActionEvent event) {
        String text = entryText.getText();
        String title = entryTitle.getText();
        LocalDate dateToday = LocalDate.now();
        int id = entryList.generateID();
        if (Checks.checkEntry(title, text, picturePath).equals(Keywords.ERROR)) {
            throw new IllegalArgumentException("Må ha tittel, og enten text eller en bildebane...");
        } if (Checks.checkEntry(title, text, picturePath).equals(Keywords.PICTURE)) {
            System.out.println(picturePath);
            System.out.println("PictureEntry");
            PictureEntry pictureEntry = new PictureEntry(title, picturePath, dateToday, id);
            addAPictureEntry(pictureEntry);
            System.out.println("Picture added");
            showPictureEntry(pictureEntry);
        } if (Checks.checkEntry(title, text, picturePath).equals(Keywords.TEXT)) {
            System.out.println("Text entry!");
            TextEntry textEntry = new TextEntry(title, text, dateToday, id);
            writeToFile(textEntry);
            addATextEntry(textEntry);
            showTextEntry(textEntry);
        }
    }

    @FXML
    void delDiaryEntry() {
        int id;
        boolean idInList;
        try {
            id = Integer.parseInt(entryForDeletion.getText());
            idInList = Checks.checkID(id, entryList);
        } catch (Exception e) {
            //ERROR - Only numbers allowed
            throw new IllegalArgumentException("Only numbers allowed");   
        }
        if (!idInList) {
            // ERROR - Element with this id doesn't exist
            throw new IllegalArgumentException("Element with this id doesn't exist...");
        }
        // Delete the element with the selected ID
        entryList.removeEntry(id);
        updateEntries(entryList);
    }

    @FXML 
    void delDiaryEntries(ActionEvent event) {
        clearEntries();
    }

    @FXML 
    void pickPicturePath(ActionEvent event) {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Velg bilde:");
    File selectedFile = fileChooser.showOpenDialog(new Stage());
    if (selectedFile != null) {
        this.picturePath = selectedFile.getAbsolutePath(); // Store OS path instead of URI
        lblImagePath.setText(this.picturePath);
        lblImagePath.setWrapText(true);
    }
}

    void clearEntries() {
        diaryContents.getChildren().clear();
        entryList.removeAllEntries();
    }

    void updateEntries(EntryList entries) {
        diaryContents.getChildren().clear();
        
        for (Entry entry : entries.getEntries()) {
            if (entry instanceof TextEntry textEntry) {
                showTextEntry(textEntry);
            } if (entry instanceof PictureEntry pictureEntry) {
                showPictureEntry(pictureEntry);
            }
        }
    }

    void addATextEntry(TextEntry textEntry) {
        entryList.addEntry(textEntry);
    }

    void showTextEntry(TextEntry textEntry) {
        String title = textEntry.getTitle(); 
        String text = textEntry.getText();
        LocalDate date = textEntry.getDate(); 
        int id = textEntry.getId();
        // Make the text entry visible in the scenebuilder
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
        Label titleLabel = new Label(title);
        Label dateLabel = new Label(" - " + formattedDate);
        Label idLabel = new Label(String.valueOf(" - id: "+ id));
        Label lineShiftLabel = new Label("\n\n");
        titleLabel.getStyleClass().add("diaryTitle");
        dateLabel.getStyleClass().add("diaryId");
        idLabel.getStyleClass().add("diaryId");
        HBox.setHgrow(titleLabel, Priority.ALWAYS);
        HBox.setHgrow(dateLabel, Priority.ALWAYS);
        HBox.setHgrow(idLabel, Priority.ALWAYS);

        HBox entryHeader = new HBox();
        entryHeader.getChildren().addAll(titleLabel,dateLabel,idLabel,lineShiftLabel);

        Label textLabel = new Label(text);
        textLabel.getStyleClass().add("diaryText");
        textLabel.setWrapText(true);
        textLabel.setMaxWidth(Double.MAX_VALUE);  
        VBox.setVgrow(textLabel, Priority.ALWAYS);

        Separator seperator = new Separator();
        diaryContents.setFillWidth(true);
        diaryContents.getChildren().addAll(entryHeader, textLabel, seperator);
    }

    void writeToFile(TextEntry textEntry) {
        String title = textEntry.getTitle();
        String text = textEntry.getText();
        LocalDate date = textEntry.getDate();
        int id = textEntry.getId();
        String contentToWrite = title + "|" + text + "|" + date.toString() + "|" + String.valueOf(id) + "\n";
        try {
            FileWriter writer = new FileWriter("src\\diaryentries.txt", true);
            writer.write(contentToWrite);
            writer.close();
        } catch (Exception e) {
            System.out.println("File not found...");
        }
    }
    
    void addAPictureEntry(PictureEntry pictureEntry) {
        entryList.addEntry(pictureEntry);
    }

    void showPictureEntry(PictureEntry pictureEntry) {
        String title = pictureEntry.getTitle();
        String picturePath = pictureEntry.getPicturePath();
        LocalDate date = pictureEntry.getDate();
        int id = pictureEntry.getId();
    
        // Format the date
        String formattedDate = date.format(DateTimeFormatter.ofPattern("MMMM d, yyyy"));
    
        // Create Labels for entry metadata
        Label titleLabel = new Label(title);
        Label dateLabel = new Label(" - " + formattedDate);
        Label idLabel = new Label(" - id: " + id);
        Label lineShiftLabel = new Label("\n\n");
    
        // Apply CSS styles
        titleLabel.getStyleClass().add("diaryTitle");
        dateLabel.getStyleClass().add("diaryId");
        idLabel.getStyleClass().add("diaryId");
        HBox.setHgrow(titleLabel, Priority.ALWAYS);
        HBox.setHgrow(dateLabel, Priority.ALWAYS);
        HBox.setHgrow(idLabel, Priority.ALWAYS);
    
        // Create HBox for the header
        HBox entryHeader = new HBox();
        entryHeader.getChildren().addAll(titleLabel, dateLabel, idLabel, lineShiftLabel);
    
        // Create ImageView to display the picture
        ImageView imageView = new ImageView();
        try {
            File imageFile = new File(picturePath);
            if (!imageFile.exists()) {
                throw new IllegalArgumentException("Image file not found: " + imageFile.getAbsolutePath());
            }
            String imageUri = imageFile.toURI().toString();
            Image image = new Image(imageUri, true); // Background loading
            imageView.setImage(image);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);
            VBox.setVgrow(imageView, Priority.ALWAYS);
        } catch (Exception e) {
            System.out.println("Error loading image: " + e.getMessage());
        }
    
        // Create a separator
        Separator separator = new Separator();
    
        // Add components to the diary contents
        diaryContents.setFillWidth(true);
        diaryContents.getChildren().addAll(entryHeader, imageView, separator);
        lblImagePath.setText("Ingen fil valgt");
        this.picturePath = "none";
    }

    public void printFile(String fileString){
        File file = new File(fileString);
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String contents = scanner.nextLine();
                String[] splitContents = contents.split("\\|");
                String title = splitContents[0];
                String pathOrText = splitContents[1];
                LocalDate date = LocalDate.parse(splitContents[2]);
                int id = Integer.parseInt(splitContents[3]);
                if (Checks.checkEntry(title, pathOrText, pathOrText).equals(Keywords.TEXT)) {
                    TextEntry textEntry = new TextEntry(title, pathOrText, date, id);
                    addATextEntry(textEntry);
                    showTextEntry(textEntry);
                } else {
                    System.out.println("Not suffiecient text info");
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found...");
        }
    }
}
