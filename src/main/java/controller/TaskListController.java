package controller;

import Database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import model.Task;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.ResourceBundle;

public class TaskListController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private ImageView listRefreshButton;

    @FXML
    private URL location;

    @FXML
    private TextField listDescriptionField;

    @FXML
    private Button listSaveTaskButton;

    @FXML
    private ListView<Task> listTask;

    @FXML
    private TextField listTaskField;

    @FXML
    private AnchorPane rootAnchorPane;

    private ObservableList<Task> tasks;
    private ObservableList<Task> refreshedTasks;

    private DatabaseHandler databaseHandler;

//    ObservableList<String> listview = FXCollections.observableArrayList(
//            "asd",
//            "asd3",
//            "dfghgjj"
//    );

    @FXML
    void initialize() throws SQLException {
//        listTask.setItems(listview);
//
//        listTask.setCellFactory( param -> new listCell());
        tasks = FXCollections.observableArrayList();

        databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userID);

        while (resultSet.next()) {
            Task task = new Task();
            task.setTaskID(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            tasks.addAll(task);

        }


        listTask.setItems(tasks);
        listTask.setCellFactory(CellController -> new CellController());

        listRefreshButton.setOnMouseClicked(mouseEvent -> {
            try {
                refreshList();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
        listSaveTaskButton.setOnAction(actionEvent -> {
            addNewTask();
        });

    }

    public void addNewTask(){

            if(!listTaskField.getText().equals("") || !listDescriptionField.getText().equals("")){
                Task myNewTask = new Task();

                Calendar calendar = Calendar.getInstance();
                java.sql.Timestamp timestamp=new java.sql.Timestamp(calendar.getTimeInMillis());

                myNewTask.setUserID(AddItemController.userID);
                myNewTask.setTask(listTaskField.getText().trim());
                myNewTask.setDescription(listDescriptionField.getText().trim());
                myNewTask.setDatecreated(timestamp);

                databaseHandler.insertTask(myNewTask);

                listTaskField.setText("");
                listDescriptionField.setText("");
                try {
                    initialize();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

    }
    public void refreshList () throws SQLException {
        System.out.println("refreshLiist in Listcont called");

        refreshedTasks= FXCollections.observableArrayList();

        DatabaseHandler databaseHandler = new DatabaseHandler();
        ResultSet resultSet = databaseHandler.getTasksByUser(AddItemController.userID);

        while (resultSet.next()){
            Task task = new Task();
            task.setTaskID(resultSet.getInt("taskid"));
            task.setTask(resultSet.getString("task"));
            task.setDatecreated(resultSet.getTimestamp("datecreated"));
            task.setDescription(resultSet.getString("description"));

            refreshedTasks.addAll(task);
        }
        listTask.setItems(refreshedTasks);
        listTask.setCellFactory(CellController-> new CellController());
    }

//    static class listCell extends ListCell<String> {
//        // Hbox = Horizontal Box
//        HBox hBox = new HBox();
//        Button helloButton = new Button("Hello");
//        Label task = new Label();
//
//        Pane pane = new Pane();
//
//        Image icon = new Image("/com/example/todolist/icon_add.png");
//        ImageView iconImg = new ImageView(icon);
//
//
//        public listCell() {
//            super();
//
//            hBox.getChildren().addAll(iconImg, task, helloButton);
//            hBox.setHgrow(pane, Priority.ALWAYS);
//        }
//
//        public void updateItem(String taskName, boolean empty) {
//            super.updateItem(taskName, empty);
//            setText(null);
//            setGraphic(null);
//
//            if (taskName != null && !empty) {
//                task.setText(taskName);
//                setGraphic(hBox);
//            }
//        }
//
//    }
}
