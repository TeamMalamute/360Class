package model;

import java.util.*;

/**
 * @author Casey
 */
public class Judge extends User {

    // Instance Fields
    private Map<Integer, List<Integer>> myContests;         //Map<ContestNumber, List of Judged Entries>
    List<Integer> myEntryNumbers;
    private int myContestNumber;
    private int myFirst;
    private int mySecond;
    private int myThird;

	
    public Judge(int theCardNumber, String theName, int theAge, String theLoginCredential,
                 String theType, int theContestNumber, int theFirst, int theSecond, int theThird) {
        super(theCardNumber, theName, theAge, theLoginCredential, theType);
    	myContests = new HashMap<>();
        myContestNumber = theContestNumber;
        myFirst = theFirst;
        mySecond = theSecond;
        myThird = theThird;
        myEntryNumbers = new ArrayList<>();
        addToList();
    }

    // Copy Constructor
    public Judge(Judge theOtherJudge) {
        super(theOtherJudge.getCardNumber(), theOtherJudge.getName(), theOtherJudge.getAge(),
                theOtherJudge.getLoginCredential(), theOtherJudge.getType());
        this.myContests = theOtherJudge.getContestsJudged();
        this.myContestNumber = theOtherJudge.getContestNumber();
        this.myFirst = theOtherJudge.getMyFirst();
        this.mySecond = theOtherJudge.getMySecond();
        this.myThird = theOtherJudge.getMyThird();
        this.myEntryNumbers = new ArrayList<>();
        addToList();
    }

    private void addToList() {
        // Clear and Add to List
        myEntryNumbers.clear();
        myEntryNumbers.add(myFirst);
        myEntryNumbers.add(myFirst);
        myEntryNumbers.add(myThird);
    }

    public void addContestJudged(JudgeDatabaseManager theJudgeDB) throws Exception {

        // Add the new entries that have been set to List
        addToList();

        // Add to Map after contest number has been set
        myContests.put(myContestNumber, myEntryNumbers);

        // Add to JudgeDB
        theJudgeDB.addJudge(this, super.getCardNumber());
    }

    public void updateContestJudged(JudgeDatabaseManager theJudgeDB) throws Exception {

        // Add the new entries that have been set to List
        addToList();

        // Update Map
        for (int i : myContests.keySet()) {
            if (i == myContestNumber) {
                myContests.replace(i, myEntryNumbers);
            }
        }

        // Update JudgeDB
        theJudgeDB.updateJudge(this, myContestNumber);
    }

    public Map<Integer, List<Integer>> getContestsJudged() {
        return myContests;
    }

    public int getContestNumber() {
        return myContestNumber;
    }

    public void setMyContestNumber(int theContestNumber) {
        myContestNumber = theContestNumber;
    }

    public void setMyFirst(int theFirst) { myFirst = theFirst; }

    public void setMySecond(int theSecond) { mySecond = theSecond; }

    public void setMyThird (int theThird) { myThird = theThird; }

    public int getMyFirst() { return myFirst; }

    public int getMySecond() { return mySecond; }

    public int getMyThird () { return myThird; }

    public List<Integer> getEntryNumbers() {
        return myEntryNumbers;
    }


    @Override
    public boolean equals(Object theObject) {
        boolean result = false;
        if(theObject == null) result = false;
        final Judge theOther = (Judge) theObject;
        if (this.getClass().equals(theObject.getClass())  &&
                this.getName().equals(theOther.getName()) &&
                this.getCardNumber() == theOther.getCardNumber() &&
                this.getAge() == theOther.getAge() &&
                this.getLoginCredential().equals(theOther.getLoginCredential()) &&
                this.getType().equals(theOther.getType()) &&
                Arrays.equals(this.getEntries().toArray(), theOther.getEntries().toArray()))
            result = true;
        return result;
    }

    @Override
    public int hashCode(){
        int result = 0;
        result = (int) this.getCardNumber() / 11;
        return result;
    }

    @Override
    public String toString() {

        return  super.getCardNumber() + "," + super.getName() + "," + super.getAge() +
                "," + super.getLoginCredential() + "," + super.getType() + "," +
                myContestNumber + "," + myFirst + "," + mySecond + "," + myThird;
    }
}
