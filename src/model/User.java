package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lizmiller on 5/12/16.
 */
public class User {

    private String name;
    private int cardNumber;
    private int age;
    private String loginCredential;
    private String type;
    private List<Entry> entries;

    public User(int cardNumber, String name,int age, String loginCredential, String type) {
        this.age = age;
        this.cardNumber = cardNumber;
        this.name = name;
        this.loginCredential = loginCredential;
        this.type = type;
        entries = new ArrayList<>();

    }



    public String getType() {
        return type;
    }

    public int getCardNumber() {
        return cardNumber;
    }

    public int getAge () {
        return  age;
    }

    public String getName() {
        return name;
    }

    public String getLoginCredential() {
        return loginCredential;
    }

    public String toString() {
        return  "["+name + ", " + cardNumber + ", " + age + ", " + loginCredential + ", " + type+"]" ;
    }

    //When a new entry is added
    public void addEntry(Entry entry, EntryDatabaseManager entryDatabase){

        //adds the entry to the actual db entry list
        entryDatabase.addEntry(entry);
        entries.add(entry);
    }


    //ONLY USE WHEN THE USERS ARE READ IN
    //DO NOT USE TO ADD NEW ENTRIES
    public void addReadEntries(Entry entry){

        //adds the entry to the actual db entry list
        entries.add(entry);
    }

    public List<Entry> getEntries() {
        return entries;
    }

}
