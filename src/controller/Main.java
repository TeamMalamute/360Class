package controller;
import java.awt.EventQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.*;

/**
 * Created by lizmiller on 4/29/16.
 */




public class Main {
	
	/*		File Names  	*/
	private static final String USER_FILE = "User.csv";
	private static final String CONTEST_FILE = "Contests.csv";
	private static final String ENTRY_FILE = "Entries.csv";
	
    public static void main(String args[]) throws Exception {

    	//modelTests(); 
    	modelContestListTests();
    	System.out.println("\nStarting controller....");
    	startController();

    }
    
    /**
     * Lists the contests a User has made submissions to, and has not, to
     * test how complex it is and whether there should be helpers in the model.
     * @author Tabi
     * @throws Exception 
     */
    private static void modelContestListTests() throws Exception {
    	/* ======get user for test======= */  
    	
        ContestDatabaseManager contestDatabaseManager = new ContestDatabaseManager(CONTEST_FILE);
        contestDatabaseManager.readCsvFile();

        EntryDatabaseManager entryDatabaseManager = new EntryDatabaseManager(ENTRY_FILE);
        entryDatabaseManager.readCsvFile();

        UserDatabaseManager userDatabaseManager = new UserDatabaseManager(USER_FILE, entryDatabaseManager);
        userDatabaseManager.readCsvFile();
        
    	User testUser = userDatabaseManager.checkCredentials(5,"pin123");
    	
    	/* =======determine lists========= */    	
    	
    	// store ref to all contests
    	Map<Integer,Contest> allContests = contestDatabaseManager.getMap();
    	
    	// Put all contests into contestsNotSubmtitedTo
    	List<Contest> contestsNotSubmittedTo = new ArrayList<Contest>();
    	contestsNotSubmittedTo.addAll(contestDatabaseManager.getMap().values());

		// get all of User's entries
    	List<Entry> testUsersEntries = testUser.getEntries();
    	
    	// add all contests whose key matches that in the User's entries to a new list, removing
    	// from not submitted to list.
    	List<Contest> contestsSubmittedTo = new ArrayList<Contest>();
    	for (Entry e : testUsersEntries) {
    		Contest submittedTo = allContests.get(e.getContest());
    		if (submittedTo != null) {
    			contestsSubmittedTo.add(submittedTo);
    			contestsNotSubmittedTo.remove(submittedTo);
    		}
    	}
    	
    	/* ========Print to check=========*/
    	System.out.println("Not submitted to:");
    	for (Contest c : contestsNotSubmittedTo) {
    		System.out.println("\t" + c);
    	}
    	System.out.println("Submitted to:");
    	for (Contest c : contestsSubmittedTo) {
    		System.out.println("\t" + c);
    	}
	}

	/**
     * Start up the Controller which starts the GUI.
     * @author Tabi
	 * @throws Exception 
     */
    private static void startController() throws Exception {

        final ContestDatabaseManager contestDatabaseManager = new ContestDatabaseManager(CONTEST_FILE);
        contestDatabaseManager.readCsvFile();

        final EntryDatabaseManager entryDatabaseManager = new EntryDatabaseManager(ENTRY_FILE);
        entryDatabaseManager.readCsvFile();

        final UserDatabaseManager userDatabaseManager = new UserDatabaseManager(USER_FILE, entryDatabaseManager);
        userDatabaseManager.readCsvFile(); // should this happen automatically in userDatabaseManager constructor?
        
    	EventQueue.invokeLater(new Runnable() {

			@Override
			public void run() {
				new MainController(userDatabaseManager, contestDatabaseManager, entryDatabaseManager);
			}

    	});

    }
    
    /** all of Liz's tests, moved to a separate function
     * @author Liz
     * @throws Exception 
     */
    private static void modelTests() throws Exception {
        /*
        * every time a new entry is added update the map and increment the counter for the id of the entry
        * when we need to display the entries to the screen us the updated map
        * ONLY WRITE TO THE FILE ONCE THE PROGRAM IS ENDED?????
        *
        * */



        //Creating the User
        String entryFile = "Entries.csv";
        EntryDatabaseManager entryModel = new EntryDatabaseManager(entryFile);
        entryModel.readCsvFile();
        System.out.println("Entries");

        String userFile = "User.csv";
        UserDatabaseManager userDatabaseManager = new UserDatabaseManager(userFile,entryModel);
        userDatabaseManager.readCsvFile();
        System.out.println("Users");
        Map<Integer, User> userMap = userDatabaseManager.getMap();
        System.out.println("Contestant or Admin " + userMap);
        System.out.println("Get the user that belongs to cardnumber 3 " + userMap.get(3));

        System.out.println("\n\n");

        String contestFile = "Contests.csv";
        ContestDatabaseManager contestDatabaseManager = new ContestDatabaseManager(contestFile);
        contestDatabaseManager.readCsvFile();
        System.out.println("Contests");
        System.out.println(contestDatabaseManager.getMap());

        System.out.println("\n\n");







        //checking if user exists
        //card number: 5
        //pin: pin123
        User findUser = userDatabaseManager.checkCredentials(5,"pin123");


        //ADDING A NEW ENTRY!!!!!!!!!!!!!!!!!!!!
        //how to add a new entry
        //USE ADD ENTRY FOR THE USER ONLY
        int totalEntries = entryModel.getItemCount();
        Entry entry = new Entry(totalEntries + 1, findUser.getCardNumber(),"Path",2,"Name");
        System.out.println(findUser.getName());
        System.out.println(findUser.getEntries());

        findUser.addEntry(entry,entryModel);
        System.out.println(findUser.getName());
        System.out.println(findUser.getEntries());


//TODO  CASEY:
//        //EXAMPLE ON HOW TO ADD NEW Entry
////        entryModel.addEntry("PATH",1,"EntryName!!");
//        System.out.println(entryModel.getEntryMap());

    }
    
}
