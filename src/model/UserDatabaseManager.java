package model;
import java.util.*;


/**
 * Created by lizmiller on 5/12/16.
 */
public class UserDatabaseManager extends DatabaseManager<User>{

    // UserDatabaseManager is type 3
    private final static int DATABASE_TYPE = 3;
    private Map<Integer,Entry> myEntries;


    public UserDatabaseManager(String theFileName, EntryDatabaseManager theEntryDatabase) {
        super(theFileName);
        myEntries = theEntryDatabase.getMap();
    }

    public void readCsvFile() {
        super.readCsvFile(DATABASE_TYPE, myEntries);
    }

    public User checkCredentials(int theCardNumber, String thePin) {
        //check user map first then judge map
        User user = null;
        if(super.getMap().containsKey(theCardNumber)) {
            User foundUser = super.getMap().get(theCardNumber);
            if(foundUser.getLoginCredential().equals(thePin)) {
                user = foundUser;
            }
        }
        return user;
    }
}