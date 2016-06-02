package test;

import model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by cppeters on 6/2/2016.
 */
public class JudgeTest {
    private static final String TEST_JUDGE_FILE = "TestJudge.csv";
    private static final String CONTEST_FILE = "Contests.csv";
    private static final String ENTRY_FILE = "Entries.csv";
    private static final String USER_FILE = "User.csv";
    private ContestDatabaseManager myContestDB;
    private EntryDatabaseManager myEntryDB;
    private JudgeDatabaseManager myJudgeDB;
    private UserDatabaseManager myUserDB;
    private Judge myJudge;
    private Contest myContest;

    @Before
    public void setUp() throws Exception {
        myContestDB = new ContestDatabaseManager(CONTEST_FILE);
        myContestDB.readCsvFile();
        myContest = myContestDB.getAllItems().get(1);

        myEntryDB = new EntryDatabaseManager(ENTRY_FILE);
        myEntryDB.readCsvFile();

        myUserDB = new UserDatabaseManager(USER_FILE, myEntryDB);
        myUserDB.readCsvFile();

        myJudgeDB = new JudgeDatabaseManager(TEST_JUDGE_FILE, myEntryDB);
        myJudgeDB.readCsvFile();
        String newJudgeName = "Steve Testman";
        int theCardNumber = myUserDB.getItemCount() + 1;
        for (int i : myUserDB.getMap().keySet()) {
            User aUser = myUserDB.getMap().get(i);
            if (newJudgeName.equals(aUser.getName())) {
                theCardNumber = aUser.getCardNumber();
            }
        }
        myJudge = new Judge(theCardNumber, newJudgeName, 28, "steve", "JUDGE",
                8, 7, 8, 9);
    }

    @Test
    public void addContestJudged() throws Exception {
        // Total amount of contests by this Judge
        int total = myJudge.getContestsJudged().size();

        // Add a new Contest Judged by using setters like it would in GUI then call addContestJudged()
        myJudge.setMyContestNumber(10);
        myJudge.setMyFirst(100);
        myJudge.setMySecond(101);
        myJudge.setMyThird(102);
        myJudge.addContestJudged(myJudgeDB);

        // Check if Judged contests size increased
        assertEquals(total + 1, myJudge.getContestsJudged().size());

        // Check if the judged contest was added to MyJudge list
        //assertEquals(myJudge.getEntryNumbers().toArray().toString(), myJudge.getContestsJudged().get(myJudge.getContestNumber()));

        // Check if the judged contest was added to myJudgeDB Map
        //assertEquals(myJudge.getContestNumber(), myJudgeDB.getMap().get(myJudge.getContestNumber()));
    }

    @Test
    public void updateContestJudged() throws Exception {

    }

}