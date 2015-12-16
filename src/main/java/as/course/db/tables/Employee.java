package as.course.db.tables;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by RTCCD on 24.09.2015.
 */
@DatabaseTable(tableName = "employee")
public class Employee implements Table{
    @DatabaseField(generatedId = true)
    private int ID;
    @DatabaseField
    private String FullName;
    @DatabaseField
    private String Post;
    @DatabaseField
    private float Salary;

    public float getSalary() {
        return Salary;
    }

    public int getID() {
        return ID;
    }

    public String getFullName() {
        return FullName;
    }

    public String getPost() {
        return Post;
    }

    public Employee(int ID, String fullName ,String post, float salary ) {
        this.ID = ID;
        Salary = salary;
        Post = post;
        FullName = fullName;
    }

    public Employee(){}
    public Table getType(){
        return this;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "ID=" + ID +
                ", FullName='" + FullName + '\'' +
                ", Post='" + Post + '\'' +
                ", Salary=" + Salary +
                '}';
    }
}
