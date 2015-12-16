package as.course.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RTCCD on 24.09.2015.
 */
@DatabaseTable(tableName = "train")
public class Train implements Table {
    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField
    private String Direction;
    @DatabaseField
    private int WagonNumber;
    @DatabaseField
    private int SeatNumber;
    @DatabaseField
    private String DispatchDate;
    @DatabaseField
    private String ArrivalDate;

    /*@DatabaseField(canBeNull = false,foreign = true)
    private Employee Employee;*/
    public int getWagonNumber() {
        return WagonNumber;
    }

    public int getID() {
        return ID;
    }

    public String getDirection() {
        return Direction;
    }

    public String getDispatchDate() {
        return DispatchDate;
    }

    public int getSeatNumber() {
        return SeatNumber;
    }

    public String getArrivalDate() {
        return ArrivalDate;
    }

    @Override
    public String toString() {
        return "Train{" +
                "ID=" + ID +
                ", Direction='" + Direction + '\'' +
                ", WagonNumber=" + WagonNumber +
                ", SeatNumber=" + SeatNumber +
                ", DispatchDate='" + DispatchDate + '\'' +
                ", ArrivalDate='" + ArrivalDate + '\'' +
                '}';
    }
}
