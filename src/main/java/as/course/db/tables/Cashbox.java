package as.course.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RTCCD on 24.09.2015.
 */
@DatabaseTable(tableName = "cashbox")
public class Cashbox implements Table {

    @DatabaseField(generatedId = true)
    private int ID;

    @DatabaseField
    private String StationName;

    public String getStationName() {
        return StationName;
    }

    public int getID() {
        return ID;
    }

    @Override
    public String toString() {
        return "Cashbox{" +
                "ID=" + ID +
                ", StationName='" + StationName + '\'' +
                '}';
    }
}
