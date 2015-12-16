package as.course.db;

import as.course.db.tables.Table;
import as.course.db.tables.Ticket;
import as.course.db.tables.Train;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.j256.ormlite.table.TableInfo;
import com.j256.ormlite.table.TableUtils;
import org.sqlite.core.CoreDatabaseMetaData;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by HP PC on 17.09.2015.
 */
public class DBase {
    static private ConnectionSource source;
    static private List<String> tables;
    public static final String URLConnection = "jdbc:sqlite:db.db3";
    static private Connection connection;

    static private Dao<Ticket, String> dao;
    public static void main(String[] args) throws ClassNotFoundException,SQLException{
        //Class.forName("org.sqlite.JDBC");
        new DBase().instance(args);

    }

    public static ConnectionSource getConnection(){
        return source;
    }
    public static void connect(String dbName){
        try {
            source = new JdbcConnectionSource("jdbc:sqlite:" + dbName);
        }catch (SQLException e){
            System.out.println("Data base not found" + e);
        }
    }

    public static void instance(String[] args){
        connect("db.db3");

    }

    public List<Object> getData(String tableName) throws SQLException{
        Class clazz = null;
        try {
            char ch = tableName.charAt(0);
            clazz = Class.forName("as.course.db.tables." + tableName.replaceFirst(String.valueOf(ch),String.valueOf(ch).toUpperCase()));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        connect("db.db3");
        Dao<Object,String> dao = DaoManager.createDao(source,clazz);
        List<Object> list = dao.queryForAll();
        for(Object obj : list){
            System.out.println(obj.toString());
        }

        return dao.queryForAll();
    }

    public List<String> getAllTables(){
        tables = new ArrayList<String>();
        ResultSet meta = null;
        try {
            connection = DriverManager.getConnection(URLConnection);
            meta = connection.getMetaData().getTables(null,null,"%",null);
            while(meta.next()){
                tables.add(meta.getString(3));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        tables.remove("sqlite_sequence");
        return tables;
    }

    public List<String> getColumnsName(String tableName) throws SQLException {
        List<String> list =  new ArrayList<String>();
        Statement s = connection.createStatement();
        ResultSet resultSet = s.executeQuery(" SELECT * FROM " + tableName);
        ResultSetMetaData result = resultSet.getMetaData();
        int columnCount = result.getColumnCount();
        for(int i = 1; i <columnCount + 1; i++ ){
            list.add(result.getColumnName(i));
        }
        return list;
    }



}
