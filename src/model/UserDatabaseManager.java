package model;
import java.io.FileNotFoundException;
import java.util.*;


/**
 * Created by lizmiller on 5/12/16.
 */
public class UserDatabaseManager extends DatabaseManager<User>{

    // UserDatabaseManager is type 3
    private final static int DATABASE_TYPE = 3;
    private EntryDatabaseManager myEntryDB;


    public UserDatabaseManager(String theFileName, EntryDatabaseManager theEntryDatabase) throws Exception {
        super(theFileName);
        myEntryDB = theEntryDatabase;
    }

    public void readCsvFile() throws Exception {
        super.readCsvFile(DATABASE_TYPE, myEntryDB);
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