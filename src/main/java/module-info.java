module com.example.todolist {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.todolist to javafx.fxml;
    exports com.example.todolist;
    exports controller;
    opens controller to javafx.fxml;
}