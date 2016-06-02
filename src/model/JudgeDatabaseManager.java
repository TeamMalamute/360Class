/**
 * 
 */
package model;


/**
 * @author Casey
 *
 */
public class JudgeDatabaseManager extends DatabaseManager <Judge>{

	// JudgeDatabaseManager is type 4
	private final static int DATABASE_TYPE = 4;
	private EntryDatabaseManager myEntryDB;

	public JudgeDatabaseManager(String theFileName, EntryDatabaseManager theEntryDatabase) throws Exception {
		super(theFileName);
		myEntryDB = theEntryDatabase;
	}

	public void readCsvFile() throws Exception {
		super.readCsvFile(DATABASE_TYPE, myEntryDB);
	}

	public void writeCsvFile() throws Exception {
		super.writeCsvFile(DATABASE_TYPE);
	}

	public void addJudge(Judge theJudge, int theCardNumber) throws Exception {

		// Add to the Map
		super.getMap().put(theCardNumber, theJudge);

		// Add to the List
		super.getAllItems().add(theJudge);

		// Save to file
		writeCsvFile();
	}

	public void updateJudge(Judge theJudge, int theContestNumber) throws Exception {

		/*// Update the Map
		for (int i : super.getMap().keySet()) {
			if (super.getMap().get(i).getCardNumber() == theJudge.getCardNumber() &&
					super.getMap().get(i).getContestNumber() == theContestNumber) {
				System.out.println("Old: " + super.getMap().get(i));
				super.getMap().replace(i, theJudge);
			}
		}*/

		// Update the List
		for (int i = 0; i < super.getAllItems().size(); i++) {
			if (super.getAllItems().get(i).getCardNumber() == theJudge.getCardNumber() &&
					super.getAllItems().get(i).getContestNumber() == theContestNumber) {
				super.getAllItems().set(i, theJudge);
			}
		}

		// Save changes
		writeCsvFile();
	}


}
