package model;
import java.io.*;

/**
 * Created by lizmiller on 5/12/16.
 */
public class ContestDatabaseManager extends DatabaseManager<Contest>{

    // ContestDatabaseManager is type 1
    private final static int DATABASE_TYPE = 1;

    public ContestDatabaseManager(String theFileName) throws Exception {
        super(theFileName);
    }

    public void readCsvFile() throws Exception {
        super.readCsvFile(DATABASE_TYPE, null);
    }

    public void writeCsvFile() throws Exception {
        super.writeCsvFile(DATABASE_TYPE);
    }

    /**
     * Creates and adds a Contest to the database, unless there was an existing contest by the same name.
     * @author Liz, Tabi
     * @param contestName
     * @param contestDescription
     * @param startDate
     * @param endDate
     * @return true on success; false otherwise.
     */
    public boolean addContest(String contestName, String contestDescription, String startDate,
                              String endDate) throws Exception {
        if (contestNameIsUnique(contestName)) {
            int contestCounter = super.getItemCount();
            Contest contest = new Contest(++contestCounter, contestName, contestDescription, startDate, endDate);
            super.getMap().put(contest.getContestNumber(), contest);
            super.getAllItems().add(contest);
            writeCsvFile();
            return true;
        } else {
            return false;
        }
    }

    /**
     * @author Tabi
     * @param contestName
     * @return true if there is no existing contest with the given name; false otherwise.
     */
    private boolean contestNameIsUnique(String contestName) {
    	for (Contest c : super.getMap().values()) {
    		if (c.getName().equals(contestName)) {
    			return false;
    		}
    	}
    	return true;
    }
}
