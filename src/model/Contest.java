package model;
import java.util.Date;

/**
 * Created by lizmiller on 5/12/16.
 */
public class Contest {
    private int contestNumber;
    private String name;
    private String description;
    private String startDate;
    private String endDate;

    public Contest(int contestNumber,String name, String description, String startDate, String endDate) {
        this.contestNumber = contestNumber;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getContestNumber() {
        return contestNumber;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }


    public String toString() {
        return  contestNumber + "," + name + "," + description + "," + startDate + "," + endDate;
    }
}
