package test;

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;
import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import org.junit.Before;
import org.junit.Test;
import model.ContestDatabaseManager;
import model.EntryDatabaseManager;
import model.UserDatabaseManager;

/**
 * 
 * @author Casey
 *
 */
public class UserDatabaseManagerTest {
	private static final String USER_FILE = "User.csv";
	private static final String CONTEST_FILE = "Contests.csv";
	private static final String ENTRY_FILE = "Entries.csv";
	private ContestDatabaseManager myContestDB;
	private EntryDatabaseManager myEntryDB;
	private UserDatabaseManager myUserDB;
	
	/**
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		myContestDB = new ContestDatabaseManager(CONTEST_FILE);
		myContestDB.readCsvFile();

        myEntryDB = new EntryDatabaseManager(ENTRY_FILE);
        myEntryDB.readCsvFile();
        
        myUserDB = new UserDatabaseManager(USER_FILE, myEntryDB);
        myUserDB.readCsvFile();
	}

	/**
	 * Test method for {@link model.UserDatabaseManager#readCsvFile()}.
	 * @throws Exception
	 */
	@Test(expected = FileNotFoundException.class)
	public void testReadCsvFile1() throws Exception {
		// Test No File Found
		UserDatabaseManager theUserDB = new UserDatabaseManager("Not a file name", myEntryDB);
	    theUserDB.readCsvFile();
	    
	}
	
	/**
	 * Test method for {@link model.UserDatabaseManager#readCsvFile()}.
	 * @throws Exception
	 */
	@Test(expected = NumberFormatException.class)
	public void testReadCsvFile2() throws Exception {	    
	    // Test Bad File
		UserDatabaseManager theUserDB = new UserDatabaseManager("superman.jpeg", myEntryDB);
	    theUserDB.readCsvFile();
	}
	
	/**
	 * Test method for {@link model.UserDatabaseManager#readCsvFile()}.
	 * @throws Exception
	 */
	@Test
	public void testReadCsvFile3() throws Exception {	    
	    // Test Good File
		UserDatabaseManager theUserDB = new UserDatabaseManager(USER_FILE, myEntryDB);
	    theUserDB.readCsvFile();
	}
	
	/**
	 * Test method for {@link model.UserDatabaseManager#checkCredentials(int, java.lang.String)}.
	 */
	@Test
	public void testCheckCredientals() {
		// Credentials Fail
		int theCardNumber = 0;
		String thePin = "";
		assertNull(myUserDB.checkCredentials(theCardNumber, thePin));
		
		// Credentials Pass
		theCardNumber = 4;
		thePin = "ieie";
		assertNotNull(myUserDB.checkCredentials(theCardNumber, thePin));
	}

}
