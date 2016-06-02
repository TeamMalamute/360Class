package model;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by lizmiller on 5/12/16.
 */
public class EntryDatabaseManager extends DatabaseManager<Entry> {

    // EntryDatabaseManager is type 2
    private final static int DATABASE_TYPE = 2;

    public EntryDatabaseManager(String theFileName) throws Exception {
        super(theFileName);

    }

    @SuppressWarnings("unchecked")
    public void readCsvFile() throws Exception {
        super.readCsvFile(DATABASE_TYPE, null);
    }

    public void writeCsvFile() throws Exception {
        super.writeCsvFile(DATABASE_TYPE);
    }

    public void addEntry(Entry entry) throws Exception {
        super.getMap().put(entry.getEntryNumber(), entry);
        super.getAllItems().add(entry);
        writeCsvFile();
    }

    public void updateEntry(Entry theOldEntry, Entry theNewEntry) throws Exception {
        // Update the Map
       /* for (int i : super.getMap().keySet()) {
            if (super.getMap().get(i).equals(theOldEntry)) {
                super.getMap().replace(i, theNewEntry);
            }
        }*/
        super.getMap().replace(theOldEntry.getEntryNumber(), theNewEntry);

        // Update the List
        super.getAllItems().set(super.getAllItems().indexOf(theOldEntry), theNewEntry);

        // Save changes
        writeCsvFile();
    }
}