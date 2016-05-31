package model;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created by lizmiller on 5/12/16.
 */
public class EntryDatabaseManager extends DatabaseManager<Entry> {

    // EntryDatabaseManager is type 2
    private final static int DATABASE_TYPE = 2;

    public EntryDatabaseManager(String theFileName) {
        super(theFileName);

    }

    @SuppressWarnings("unchecked")
    public void readCsvFile() {
        super.readCsvFile(DATABASE_TYPE, null);
    }

    public void writeCsvFile() throws FileNotFoundException {
        super.writeCsvFile(DATABASE_TYPE);
    }

    public void addEntry(Entry entry) throws FileNotFoundException {
        super.updateCount();
        super.getMap().put(super.getItemCount(), entry);
        writeCsvFile();
    }

    public void updateEntry(Entry theOldEntry, Entry theNewEntry) throws FileNotFoundException {
        for (int i : super.getMap().keySet()) {
            if (super.getMap().get(i).equals(theOldEntry)) {
                super.getMap().replace(i, theNewEntry);
            }
        }
        writeCsvFile();
    }

    /**
     * @author Lan
     * @param theContestNumber
     */
    public List<Entry> getEntries(int theContestNumber) {
        List<Entry>subListEntries = new ArrayList<>();
        for(int index = 0; index < super.getAllItems().size(); index++){
            if(super.getAllItems().get(index).getContest() == theContestNumber){
                subListEntries.add(super.getAllItems().get(index));
            }
        }
        return subListEntries;
    }
}