import java.util.Date;
import java.util.Map;

/**
 * Created by lizmiller on 4/29/16.
 */




public class Main {
    public static void main(String args[]) {


        /*
        * every time a new entry is added update the map and increment the counter for the id of the entry
        * when we need to display the entries to the screen us the updated map
        * ONLY WRITE TO THE FILE ONCE THE PROGRAM IS ENDED?????
        *
        * */



        //Creating the User
        String userFile = "User.csv";
        UserModel userModel = new UserModel(userFile);
        userModel.readCsvFile();
        System.out.println("Users");
        Map<Integer, User> userMap = userModel.getUserMap();
        System.out.println(userMap);

        System.out.println("Get the user that belongs to cardnumber 3 " + userMap.get(3));


        String contestFile = "Contests.csv";
        ContestModel contestModel = new ContestModel(contestFile);
        contestModel.readCsvFile();
        System.out.println("Contests");
        System.out.println(contestModel.getContestMap());


        
        String entryFile = "Entries.csv";
        EntryModel entryModel = new EntryModel(entryFile);
        entryModel.readCsvFile();
        System.out.println("Entries");
        System.out.println(entryModel.getEntryMap());
        entryModel.writeCsvFile();


    }
}
