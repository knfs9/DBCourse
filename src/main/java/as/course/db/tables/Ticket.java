package as.course.db.tables;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by RTCCD on 23.09.2015.
 */
@DatabaseTable(tableName = "ticket")
public class Ticket implements Table{

    private String tableName = "Ticket";

    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField
    private String Direction;
    @DatabaseField
    private int TrainID;
    @DatabaseField(dataType = DataType.DATE)
    private java.util.Date DispatchDate;
    @DatabaseField
    private float Cost;
    @DatabaseField
    private int SeatNumber;
    @DatabaseField
    private String DispatchStation;
    @DatabaseField
    private String ArrivalStation;


    public int getID(){
        return ID;
    }

    public Ticket(){

    }

    public String getDirection() {
        return Direction;
    }

    public Date getDispatchDate() {
        return DispatchDate;
    }

    public float getCost() {
        return Cost;
    }

    public int getSeatNumber() {
        return SeatNumber;
    }

    public String getArrivalStation() {
        return ArrivalStation;
    }

    public String getDispatchStation() {
        return DispatchStation;
    }




    public String toString(){
        return tableName.toUpperCase() + "\nDirection: " + Direction + "\nDispatch date: " + DispatchDate
                + "\nCost: " + Cost + "\nSeat number: " + SeatNumber
                + "\nDispatch station: " + DispatchStation + "\nArrival date: " + ArrivalStation;
    }

}
