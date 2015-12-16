package as.course.db;





import as.course.db.tables.*;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcDatabaseResults;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.support.DatabaseConnection;
import com.j256.ormlite.table.DatabaseTableConfig;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sun.text.normalizer.ICUData;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by RTCCD on 21.10.2015.
 */
public class DBHelper{
    private  Dao<Ticket,String> ticketDao;
    private  Dao<Cashbox,String> cashboxDao;
    private  Dao<Employee,String> employeeDao;
    private  Dao<Station,String> stationDao;
    private  Dao<Train,String> trainDao;
    private  Dao<Wagon,String> wagonDao;
    private Savepoint savepoint;

    private static final String URLConnection = "jdbc:sqlite:db.db3";
    static private JdbcConnectionSource source;
    static private DatabaseConnection connection;

    public DBHelper(){
        try {
            source = new JdbcConnectionSource(URLConnection);
            connection = source.getReadWriteConnection();
            connection.setAutoCommit(false);

        }catch (SQLException e){
            System.out.println("Data base not found" + e);
        }
    }

    public ObservableList<ObservableList> getTicketData() throws SQLException{
        if(ticketDao == null){
            ticketDao = DaoManager.createDao(source,Ticket.class);
            ticketDao.setAutoCommit(connection,false);
        }


        List<Ticket> list = ticketDao.queryForAll();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        for(int i = 0; i < list.size(); i++){
            data.add(FXCollections.observableArrayList());
            data.get(i).add(String.valueOf(list.get(i).getID()));
            data.get(i).add(list.get(i).getDirection());
            data.get(i).add("");
            data.get(i).add(list.get(i).getDispatchDate());
            data.get(i).add(list.get(i).getCost());
            data.get(i).add(String.valueOf(list.get(i).getSeatNumber()));

            data.get(i).add(list.get(i).getDispatchStation());
            data.get(i).add(list.get(i).getArrivalStation());


        }
        return data;
    }
    public ObservableList<ObservableList> getCashboxData() throws SQLException{
        if(cashboxDao == null){
            cashboxDao = DaoManager.createDao(source,Cashbox.class);
            cashboxDao.setAutoCommit(connection,false);
        }
        List<Cashbox> list = cashboxDao.queryForAll();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        for(int i = 0; i < list.size(); i++){
            data.add(FXCollections.observableArrayList());
            data.get(i).add(String.valueOf(list.get(i).getID()));

        }
        return data;
    }
    public ObservableList<ObservableList> getStationData() throws SQLException{
        if(stationDao == null){
            stationDao = DaoManager.createDao(source,Station.class);
            stationDao.setAutoCommit(connection,false);
        }
        List<Station> list = stationDao.queryForAll();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        for(int i = 0; i < list.size(); i++){
            data.add(FXCollections.observableArrayList());
            data.get(i).add(String.valueOf(list.get(i).getID()));
            data.get(i).add(list.get(i).getStationName());
        }
        return data;
    }
    public ObservableList<ObservableList> getTrainData() throws SQLException{
        if(trainDao == null){
            trainDao = DaoManager.createDao(source,Train.class);
            trainDao.setAutoCommit(connection,false);
        }
        List<Train> list = trainDao.queryForAll();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        for(int i = 0; i < list.size(); i++){
            data.add(FXCollections.observableArrayList());
            data.get(i).add(String.valueOf(list.get(i).getID()));
            data.get(i).add(list.get(i).getDirection());
            data.get(i).add(String.valueOf(list.get(i).getWagonNumber()));
            data.get(i).add(list.get(i).getDispatchDate());
            data.get(i).add(list.get(i).getArrivalDate());
        }


        return data;
    }
    public ObservableList<ObservableList> getEmployeeData() throws SQLException{
        if(employeeDao == null){
            employeeDao = DaoManager.createDao(source,Employee.class);
            employeeDao.setAutoCommit(connection,false);
        }

        List<Employee> list = employeeDao.queryForAll();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        for(int i = 0; i < list.size(); i++){
            data.add(FXCollections.observableArrayList());
            data.get(i).add(String.valueOf(list.get(i).getID()));
            data.get(i).add(list.get(i).getFullName());
            data.get(i).add(list.get(i).getPost());
            data.get(i).add(String.valueOf(list.get(i).getSalary()));
        }

        return data;
    }
    public ObservableList<ObservableList> getWagonData() throws SQLException{
        if(wagonDao == null){
            wagonDao = DaoManager.createDao(source,Wagon.class);
            wagonDao.setAutoCommit(connection,false);
        }
        List<Wagon> list = wagonDao.queryForAll();

        Iterator it = wagonDao.iterator();
        ObservableList<String> row = FXCollections.observableArrayList();
        ObservableList<ObservableList> data = FXCollections.observableArrayList();

        for(int i = 0; i < list.size(); i++){
            data.add(FXCollections.observableArrayList());
            data.get(i).add(String.valueOf(list.get(i).getID()));
            data.get(i).add(list.get(i).getWagonType());
            data.get(i).add(String.valueOf(list.get(i).getCost()));
        }

        return data;
    }

    public ObservableList<ObservableList> getData(String tableName) throws SQLException{
        switch(tableName){
            case "ticket"  : return getTicketData();
            case "employee": return getEmployeeData();
            case "cashbox" : return getCashboxData();
            case "train"   : return getTrainData();
            case "wagon"   : return getWagonData();
            case "station" : return getStationData();
        }
        return null;
    }

    public void add(String tableName, Table table){
        String nm = table.getClass().getSimpleName();
        switch(tableName){
            //case "ticket"  : data = FXCollections.observableArrayList(dbHelper.getTicketData()); break;
            case "employee":
                try {
                    employeeDao.create((Employee)table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
                //case "cashbox" : data = FXCollections.observableArrayList(dbHelper.getCashboxData()); break;
            case "train"   :
            //case "wagon"   : data = FXCollections.observableArrayList(dbHelper.getWagonData()); break;
        }
    }

    public void delete(String tableName, String id){
        switch(tableName){
            //case "ticket"  : data = FXCollections.observableArrayList(dbHelper.getTicketData()); break;
            case "employee":
                try {
                    employeeDao.deleteById(id);
                    //employeeDao.delete((Employee)table);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            //case "cashbox" : data = FXCollections.observableArrayList(dbHelper.getCashboxData()); break;
            case "train"   :
                //case "wagon"   : data = FXCollections.observableArrayList(dbHelper.getWagonData()); break;
        }
    }


    public boolean commit(){
        try {
            employeeDao.commit(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }


}
