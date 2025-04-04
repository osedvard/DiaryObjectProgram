package model;
import java.util.ArrayList;
import java.util.List;

public class EntryList {
    private List<Entry> entries;
    public EntryList() {
        this.entries = new ArrayList<Entry>();
    }
    
    public void addEntry(Entry entry) {
        entries.add(entry);
    }

    public void removeEntry(int id) {
        // Find the index of the object with the given id
        entries.removeIf(entry -> entry.getId() == id);
    }

    public void removeAllEntries() {
        entries.clear();
    }

    public int getLength() {
        return this.entries.size();
    }

    public List<Entry> getEntries() {
        return this.entries;
    }

    public int generateID() {
        if (getLength() == 0) {
            return 1;
        }
        return this.entries.get(getLength()-1).id + 1;
    }

}
