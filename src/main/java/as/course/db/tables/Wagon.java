package as.course.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RTCCD on 24.09.2015.
 */
@DatabaseTable(tableName = "wagon")
public class Wagon implements Table {
    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField
    private String WagonType;
    @DatabaseField
    private float Cost;



    public int getID() {
        return ID;
    }

    public String getWagonType() {
        return WagonType;
    }

    public float getCost() {
        return Cost;
    }

    @Override
    public String toString() {
        return "Wagon{" +
                "ID=" + ID +
                ", WagonType='" + WagonType + '\'' +
                ", Cost=" + Cost +
                '}';
    }
}
