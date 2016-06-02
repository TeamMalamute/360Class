package test;

import model.Entry;
import model.EntryDatabaseManager;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;

import static org.junit.Assert.*;

/**
 * Created by cppeters on 6/1/2016.
 * @author Casey
 */
public class EntryDatabaseManagerTest {

    // Instance fields
    private static final String TEST_ENTRY_FILE = "TestEntries.csv";
    private EntryDatabaseManager myEntryDB;

    /**
     * Setup method for testing.
     * @throws Exception
     */
    @Before
    public void setUp() throws Exception {
        myEntryDB = new EntryDatabaseManager(TEST_ENTRY_FILE);
        myEntryDB.readCsvFile();
    }


    /**
     * Test method for {@link model.EntryDatabaseManager#addEntry(Entry)} }.
     * @throws Exception
     */
    @Test
    public void addEntry() throws Exception {
        Entry theEntry = new Entry(myEntryDB.getItemCount() + 1, 1, "Path Test", 2, "Add Test");

        // Initial count
        int total = myEntryDB.getItemCount();

        // Add one entry
        myEntryDB.addEntry(theEntry);

        // Check if items incremented
        assertEquals(total + 1, myEntryDB.getItemCount());

        // Check if theEntry was added to myEntryDB Map
        assertEquals(theEntry, myEntryDB.getMap().get(theEntry.getEntryNumber()));

        // Check if theEntry was added to TEST_ENTRY_FILE
        myEntryDB.readCsvFile();
        assertEquals(theEntry, myEntryDB.getMap().get(theEntry.getEntryNumber()));
    }

    /**
     * Test method for {@link model.EntryDatabaseManager#updateEntry(Entry, Entry)}.
     * @throws Exception
     */
    @Test
    public void updateEntry() throws Exception {
        EntryDatabaseManager theEntryDB = new EntryDatabaseManager(TEST_ENTRY_FILE);
        theEntryDB.readCsvFile();
        int theOldEntryNum = theEntryDB.getItemCount() - 1;
        Entry theOldEntry = new Entry(theOldEntryNum, 1, "Path Test", 2, "Old Entry");

        theEntryDB.addEntry(theOldEntry);

        Entry theNewEntry = new Entry(theOldEntryNum, 1, "Path Test", 2, "New Entry");

        // Update the Entry
        theEntryDB.updateEntry(theOldEntry, theNewEntry);

        // Read in the new TEST_ENTRY_FILE
        theEntryDB.readCsvFile();

        // Check if theOldEntry is still in User Map
        assertNotEquals(theOldEntry, theEntryDB.getMap().get(theOldEntry.getEntryNumber()));

        // Check if theNewEntry was inserted in place of theOldEntry in Map
        assertEquals(theNewEntry, theEntryDB.getMap().get(theNewEntry.getEntryNumber()));
    }



    /**** Test Suite for readCsv() ******/


    /**
     * Test method for {@link model.EntryDatabaseManager#readCsvFile()}.
     * @throws Exception
     */
    @Test(expected = FileNotFoundException.class)
    public void testReadCsvFile1() throws Exception {
        // Test No File Found
        EntryDatabaseManager theEntryDB = new EntryDatabaseManager("Not a file name");
        theEntryDB.readCsvFile();
    }

    /**
     * Test method for {@link model.EntryDatabaseManager#readCsvFile()}.
     * @throws Exception
     */
    @Test(expected = NumberFormatException.class)
    public void testReadCsvFile2() throws Exception {
        // Test Bad File
        EntryDatabaseManager theEntryDB = new EntryDatabaseManager("superman.jpeg");
        theEntryDB.readCsvFile();
    }

    /**
     * Test method for {@link model.EntryDatabaseManager#readCsvFile()}.
     * @throws FileNotFoundException
     */
    @Test
    public void testReadCsvFile3() throws Exception {
        // Test Good File
        EntryDatabaseManager theEntryDB = new EntryDatabaseManager(TEST_ENTRY_FILE);
        theEntryDB.readCsvFile();
    }



    /**** Test Suite for writeCsv() ******/


    /**
     * Test method for {@link model.EntryDatabaseManager#writeCsvFile()}.
     * @throws FileNotFoundException
     */
    @Test(expected = FileNotFoundException.class)
    public void testWriteCsvFile1() throws Exception {
        // Test No File Found
        EntryDatabaseManager theEntryDB = new EntryDatabaseManager("Not a file name");
        theEntryDB.writeCsvFile();
    }

    /**
     * Test method for {@link model.EntryDatabaseManager#writeCsvFile()}.
     * @throws NumberFormatException
     */
    @Test(expected = NumberFormatException.class)
    public void testWriteCsvFile2() throws Exception {
        // Test Bad File
        EntryDatabaseManager theEntryDB = new EntryDatabaseManager("superman.jpeg");
        theEntryDB.writeCsvFile();
    }

    /**
     * Test method for {@link model.EntryDatabaseManager#writeCsvFile()}.
     * @throws FileNotFoundException
     */
    @Test
    public void testWriteCsvFile3() throws Exception {
        // Test Good File
        EntryDatabaseManager theEntryDB = new EntryDatabaseManager(TEST_ENTRY_FILE);
        theEntryDB.readCsvFile();
        theEntryDB.writeCsvFile();
    }

}