package controller;

import Database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Task;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

public class AddItemFormController {



    private int userID;
    private DatabaseHandler databaseHandler;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button todosButton;

    @FXML
    private Label successLabel;

    @FXML
    private TextField descriptionField;

    @FXML
    private Button saveTaskButton;

    @FXML
    private TextField taskField;

    @FXML
    void initialize() {
        databaseHandler= new DatabaseHandler();
        Task task=new Task();
        saveTaskButton.setOnAction(actionEvent -> {


            Calendar calendar = Calendar.getInstance();
            java.sql.Timestamp timestamp =
                    new Timestamp(calendar.getTimeInMillis());

            String taskText = taskField.getText().trim();
            String taskDescription = descriptionField.getText().trim();

            if(!taskText.equals("")|| !taskDescription.equals("")){

                System.out.println("User id: " + AddItemController.userID);

                task.setUserID(AddItemController.userID);
                task.setDatecreated(timestamp);
                task.setDescription(taskDescription);
                task.setTask(taskText);

                databaseHandler.insertTask(task);
                successLabel.setVisible(true);
                todosButton.setVisible(true);
                int taskNumber = 0;
                try {
                    taskNumber = databaseHandler.getAllTasks(AddItemController.userID);
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                todosButton.setText("My 2DO's: "+ "("+ taskNumber+ ")");

                taskField.setText("");
                descriptionField.setText("");

                todosButton.setOnAction(actionEvent1 -> {
                    //send users to the list screen
                    FXMLLoader loader= new FXMLLoader();
                    loader.setLocation(getClass().getResource("/com/example/todolist/taskList.fxml"));

                    try {
                        loader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Parent root = loader.getRoot();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.showAndWait();
                });
                System.out.println("Task Added Succesfully! ");

            }else {
                System.out.println("Nothing Added! ");
            }


        });
    }
    public int getUserID() {
        return this.userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
        System.out.println(this.userID);
    }
}
