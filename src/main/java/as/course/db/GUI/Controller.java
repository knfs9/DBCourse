package as.course.db.GUI;

import as.course.db.DBHelper;
import as.course.db.DBase;
import as.course.db.TableDaos.UniversalDao;
import as.course.db.tables.*;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;


import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by RTCCD on 12.10.2015.
 */

public class Controller implements Initializable{
    @FXML
    AnchorPane mainAnchor;
    DBase base;
    @FXML
    private ListView<String> listView;
    @FXML
    private TableView tableView;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private Button commitButton;
    private UniversalDao dao;

    DBHelper dbHelper;
    public static boolean isCommitted;
    private boolean dataChanged = false;

    public boolean isDataChanged(){
        return dataChanged;
    }

    public void initialize(URL location, ResourceBundle resources) {
        dbHelper = new DBHelper();
        base = new DBase();
        tableView.getColumns().clear();

        ObservableList<String> items = FXCollections.observableArrayList(base.getAllTables());
        listView.setItems(items);

        listView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                String tableName = listView.getSelectionModel().getSelectedItem();
                try {
                    if(tableView.getColumns().size() != 0){
                        tableView.getColumns().clear();
                    }
                    List<String> columns = base.getColumnsName(tableName);
                    for(int i = 0; i < columns.size(); i++){
                        final int j = i;
                        TableColumn tableColumn = new TableColumn(columns.get(i));


                        tableColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                            public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                                ObservableList values = param.getValue();
                                if(j >= values.size()){
                                    return new SimpleStringProperty("");
                                }else {
                                    Object obj = param.getValue().get(j).toString();
                                    System.out.println(param.getValue().size());
                                    return new SimpleStringProperty(param.getValue().get(j).toString());
                                }

                            }
                        });

                        tableView.getColumns().addAll(tableColumn);
                    }

                    ObservableList<ObservableList> data = null;

                    data = dbHelper.getData(tableName);



                    tableView.setItems(data);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });

        commitButton.setOnAction(event -> {
            dbHelper.commit();
            isCommitted = true;
        });



        addAction();
        deleteAction();
    }

    public void deleteAction(){
        deleteButton.setOnAction(event ->{
            String tableName = listView.getSelectionModel().getSelectedItem();
            if(tableName == null){
                return;
            }
            Table table = null;

                switch (tableName){
                    case "employee" : {
                        int row = tableView.getSelectionModel().getSelectedIndex();
                        ObservableList temp = tableView.getItems();
                        ObservableList<ObservableList> data = null;
                        try {
                            data =  dbHelper.getData(tableName);
                            Object obj =(String) data.get(row).get(0);
                            dbHelper.delete(tableName, obj.toString());
                            tableView.getItems().remove(row);
                            isCommitted = false;
                            dataChanged = true;
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }


                        break;
                    }
                    case "train" :  break;
                    case "cashbox" :  break;
                    case "ticket" : break;
                    case "station" : break;
                    case "wagon" : break;


                }

        });
    }

    public void addAction(){
        addButton.setOnAction(event -> {
            String tableName = listView.getSelectionModel().getSelectedItem();
            //Если не выбрана для изменения таблица, то кнопка не срабатывает
            if(tableName == null){
                return;
            }
            Dialog<?> dialog = null;

            switch (tableName){
                case "employee" : dialog = new Dialog<Employee>(); break;
                case "train" : dialog = new Dialog<Train>(); break;
                case "cashbox" : dialog = new Dialog<Cashbox>(); break;
                case "ticket" : dialog = new Dialog<Ticket>(); break;
                case "station" : dialog = new Dialog<Station>(); break;
                case "wagon" : dialog = new Dialog<Wagon>(); break;
            }
            Group group = new Group();


            dialog.setTitle(tableName);
            dialog.setHeaderText("Enter required data");
            ArrayList<Label> labels = new ArrayList<Label>();
            ArrayList<TextField> textFields = new ArrayList<TextField>();
            List<String> columns = null;
            try {
                columns = base.getColumnsName(tableName);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            for(int i = 0;  i < columns.size(); i++){
                labels.add(new Label(columns.get(i)));
                textFields.add(new TextField(""));
            }
            GridPane grid = new GridPane();

            for(int i = 0; i < labels.size(); i++){
                grid.addRow(i,labels.get(i));
            }

            for(int i = 0; i < labels.size(); i++){
                grid.addRow(i,textFields.get(i));
            }

            dialog.getDialogPane().setContent(grid);
            ButtonType buttunTypeOk = new ButtonType("Okay", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttunTypeOk);
            dialog.show();
            Table table = null;
            int s = columns.size();
            dialog.setResultConverter(buttontype -> {
                final int size = s;
                if(buttontype == buttunTypeOk){
                    switch (tableName){
                        case "employee" :

                            ObservableList tempData = FXCollections.observableArrayList();
                            ObservableList row = FXCollections.observableArrayList();
                            try {
                                //Считываем данные из базы
                                tempData = dbHelper.getData(tableName);
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                            String[] str = new String[size];
                            for(int i = 0; i < textFields.size(); i++){
                                if(validateField(textFields.get(i))){
                                    str[i] = textFields.get(i).getText();
                                    //Создаем новую строку для таблици
                                    row.add(textFields.get(i).getText());
                                }else{
                                    Alert fail = new Alert(Alert.AlertType.INFORMATION);
                                    fail.setHeaderText(tableName);
                                    fail.setContentText("the field is not correct " + labels.get(i).getText());
                                    fail.show();
                                    break;
                                }
                            }
                            Employee temp = new Employee(Integer.valueOf(str[0]),str[1],str[2],Float.valueOf(str[3]));

                            //Добавляем новый элеммент в базу
                            dbHelper.add("employee",temp);
                            //Новую строку таблицы добавляем в общий контейнер
                            tempData.add(row);
                            tableView.setItems(tempData);
                            isCommitted = false;
                            dataChanged = true;
                            break;
                        case "train" :break;
                        case "cashbox" :break;
                        case "ticket" :break;
                        case "station" :break;
                        case "wagon" : break;
                        case "ticketype" :break;
                    }

                }
                else{
                    //Событе закончено, но закрываем только текущее окно
                    event.consume();
                    return null;
                }
                return null;
            });
        });
    }

    private boolean validateField(TextField textField){
        if(textField.getText().trim().isEmpty()){
            return false;
        }else {
            return true;
        }

    }

    private void addData(String tableName, Table table){

    }

    private void warningAlert(String tableName){

    }



//    public void deleteAction(){
//        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent event) {
//
//            }
//        });
//    }
}
