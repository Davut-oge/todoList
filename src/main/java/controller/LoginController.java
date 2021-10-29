package controller;

import Database.DatabaseHandler;
import animations.Shaker;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController {

    private int userID;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField loginPassword;

    @FXML
    private Button loginSignupButton;

    @FXML
    private TextField loginUsername;

    private DatabaseHandler databaseHandler;

    @FXML
    void initialize() {
        databaseHandler = new DatabaseHandler();


        loginButton.setOnAction(actionEvent -> {
            String loginText = loginUsername.getText().trim();
            String loginPass = loginPassword.getText().trim();
            String loginName = "";
            User user = new User();
            user.setUserName(loginText);
            user.setPassword(loginPass);

            ResultSet userRow = databaseHandler.getUser(user);

            int counter = 0;

            try {
                if(user.getUserName().equals("") || user.getPassword().equals("")){
                    System.out.println("null");
                    Shaker userNameShaker = new Shaker(loginUsername);
                    Shaker passwordShaker= new Shaker(loginPassword);
                    userNameShaker.shake();
                    passwordShaker.shake();
                }else{

                    while (userRow.next()){
                        counter=1;
                        String name = userRow.getString("firstname");
                        loginName=name;
                        userID = userRow.getInt("userid");
                           
                    }
                    if (counter==1){
                        System.out.println("Login Successful! ");
                        System.out.println("Welcome!  "+  loginName);
                        showAddItemScreen();
                    } else {
                        Shaker userNameShaker = new Shaker(loginUsername);
                        Shaker passwordShaker= new Shaker(loginPassword);
                        userNameShaker.shake();
                        passwordShaker.shake();
                    }
                }
            } catch (SQLException e){

            }

        });

        //URL fxmlLocation = getClass().getResource("/resources/com/example/todolist/signup.fxml");

        loginSignupButton.setOnAction(actionEvent -> {
           //Take users to signup screen
            loginSignupButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/todolist/signup.fxml"));
            System.out.println(loader.getLocation());
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


    }

    private void showAddItemScreen(){
    //Take users to AddItem screen
        loginSignupButton.getScene().getWindow().hide();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/com/example/todolist/addItem.fxml"));
        System.out.println(loader.getLocation());
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Parent root = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        AddItemController itemController = loader.getController();
        itemController.setUserID(userID);

        stage.showAndWait();
    }

//    private void loginUser(String userName,String password) {
//        //Check in the database if user exist, if true take them ti AddItem Screen
//        if(!userName.equals("") || !password.equals("")){
//
//        }else {
//            //they need to enter their credentials
//        }
//
//    }
}