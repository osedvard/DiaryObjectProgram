package model;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.time.LocalDate;

public class PictureEntry extends Entry {
    private String picturePath;
    private File imageFile;

    public PictureEntry(String title, String picturePath, LocalDate date, int id) {
        super(title, date, id); 
        try {
            // Remove incorrect "file:\\" prefix if present
            if (picturePath.startsWith("file:\\\\")) {
                picturePath = picturePath.replace("file:\\\\", "file:/");
            }

            // Process URI or direct file path
            if (picturePath.startsWith("file:/")) {
                URI uri = new URI(picturePath);
                this.imageFile = Paths.get(uri).toFile();
            } else {
                this.imageFile = new File(picturePath);
            }

            // Verify the file exists
            if (!this.imageFile.exists()) {
                throw new IllegalArgumentException("File not found: " + this.imageFile.getAbsolutePath());
            }

            // Set the picturePath to the absolute path of the image file
            this.picturePath = this.imageFile.getAbsolutePath();

        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Invalid file path: " + picturePath, e);
        }
    }

    public String getPicturePath() {
        return this.picturePath;
    }
}