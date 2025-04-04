package model;
import java.util.Iterator;

public class Checks {
    public static String checkEntry(String title, String text, String picturePath) {
        if (title.isEmpty()) {
            return Keywords.ERROR; // Title cannot be empty
        } 
        if (text.isEmpty() && picturePath.equals("none")) {
            return Keywords.ERROR; // Must have either text or image
        }
        
        // Check if input is an image by looking at file extensions
        if (picturePath.toLowerCase().matches(".*\\.(jpg|jpeg|png|gif|bmp)$")) {
            return Keywords.PICTURE;
        } else {
            return Keywords.TEXT;
        }
    }
    public static boolean checkID(int ID, EntryList entryList) {
        boolean idInList = false;
        Iterator<Entry> iterator = entryList.getEntries().iterator();
        while (iterator.hasNext()) {
        Entry entry = iterator.next();
        if (entry.getId() == ID) {
            idInList = true;
        }
        }
        return idInList;
    }
}
