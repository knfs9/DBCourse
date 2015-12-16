package as.course.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RTCCD on 24.09.2015.
 */
@DatabaseTable(tableName = "station")
public class Station implements Table {
    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField(columnName = "StationName")
    private String StationName;

    public int getID() {
        return ID;
    }

    public String getStationName() {
        return StationName;
    }

    @Override
    public String toString() {
        return "Station{" +
                "ID=" + ID +
                ", StationName='" + StationName + '\'' +
                '}';
    }
}
