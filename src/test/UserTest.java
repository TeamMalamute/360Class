/**
 * 
 */
package test;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import model.Contest;
import model.ContestDatabaseManager;
import model.Entry;
import model.EntryDatabaseManager;
import model.User;
import model.UserDatabaseManager;

/**
 * @author Casey
 *
 */
public class UserTest {
	private static final String USER_FILE = "User.csv";
	private static final String CONTEST_FILE = "Contests.csv";
	private static final String ENTRY_FILE = "Entries.csv";
	private ContestDatabaseManager myContestDB;
	private EntryDatabaseManager myEntryDB;
	private UserDatabaseManager myUserDB;
	private User myUser;
	private Contest myContest;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		myContestDB = new ContestDatabaseManager(CONTEST_FILE);
		myContestDB.readCsvFile();
		myContest = myContestDB.getAllItems().get(1);
		
        myEntryDB = new EntryDatabaseManager(ENTRY_FILE);
        myEntryDB.readCsvFile();
        
        myUserDB = new UserDatabaseManager(USER_FILE, myEntryDB);
        myUserDB.readCsvFile();
        myUser = myUserDB.getMap().get(1);
	}

	/**
	 * Test method for {@link model.User#addEntry(model.Entry, model.EntryDatabaseManager)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testAddEntry() throws FileNotFoundException {
		Entry theEntry = new Entry(0, 
				myUser.getCardNumber(), "Path Test", 
				myContest.getContestNumber(), "Name Test");
		int total = myUser.getEntries().size();
		myUser.addEntry(theEntry, myEntryDB);

		// Check if entries size increased
		assertEquals(total + 1, myUser.getEntries().size());
		
		// Check if myEntry was added to myUser entries list
		assertEquals(theEntry, myUser.getEntries().get(myUser.getEntries().size() - 1));
		
		// Check if myEntry was added to myEntryDB
		assertEquals(theEntry, myEntryDB.getMap().get(myEntryDB.getItemCount()));
	}

	/**
	 * Test method for {@link model.User#updateEntry(int, model.Entry, model.EntryDatabaseManager)}.
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testUpdateEntry() throws FileNotFoundException {
		Entry theOldEntry = new Entry(0, myUser.getCardNumber(), "Path Test 2", 
				myContest.getContestNumber(), "Name Test 2");
		Entry theNewEntry = new Entry(0, 
				myUser.getCardNumber(), "Path Test 3", 
				myContest.getContestNumber(), "Name Test 3");
		myUser.addEntry(theOldEntry, myEntryDB);
		int theOldEntryIdx = myUser.getEntries().indexOf(theOldEntry);

		// Update the Entry
		myUser.updateEntry(theOldEntryIdx, theNewEntry, myEntryDB);
		
		// Check if theOldEntry is still in myEntryDB
		assertNotEquals(theOldEntry, myUser.getEntries().get(theOldEntryIdx));
		
		// Check if theNewEntry was inserted in place of theOldEntry myEntryDB
		assertEquals(theNewEntry, myUser.getEntries().get(theOldEntryIdx));
	}

	/**
	 * Test method for {@link model.User#addReadEntries(model.Entry)}.
	 */
	@Test
	public void testAddReadEntries() {
		Entry theEntry = new Entry(0, 
				myUser.getCardNumber(), "Path Test 4", 
				myContest.getContestNumber(), "Name Test 4");
		int total = myUser.getEntries().size();
		myUser.addReadEntries(theEntry);
		
		// Check if entries size increased
		assertEquals(total + 1, myUser.getEntries().size());
		
		// Check if myEntry was added to myUser entries list
		assertEquals(theEntry, myUser.getEntries().get(myUser.getEntries().size() - 1));		
	}
}
