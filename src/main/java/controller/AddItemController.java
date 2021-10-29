package controller;

import animations.Shaker;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AddItemController {


    public static int userID;

    @FXML
    private ResourceBundle resources;

    @FXML
    private AnchorPane rootAnchorPane;

    @FXML
    private URL location;

    @FXML
    private Label noTaskLabel;
    @FXML
    private ImageView addButton;

    @FXML
    void initialize() {

        addButton.addEventFilter(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            Shaker buttonShaker = new Shaker(addButton);
            buttonShaker.shake();

            FadeTransition fadeTransition =new FadeTransition(Duration.seconds(2),addButton);
            FadeTransition labelTransition=new FadeTransition(Duration.seconds(2),noTaskLabel);

            System.out.println("Added Clicked! ");
            addButton.relocate(0,20);
            noTaskLabel.relocate(0,60);

            addButton.setOpacity(0);
            noTaskLabel.setOpacity(0);

            fadeTransition.setFromValue(1f);
            fadeTransition.setToValue(0f);
            fadeTransition.setCycleCount(1);
            fadeTransition.setAutoReverse(false);
            fadeTransition.play();

            labelTransition.setFromValue(1f);
            labelTransition.setToValue(0f);
            labelTransition.setCycleCount(1);
            labelTransition.setAutoReverse(false);
            labelTransition.play();


            try {

                AnchorPane formPane =
                        FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/todolist/addItemForm.fxml")));
                AddItemController.userID = getUserId();
               // AddItemFormController addItemFormController = new AddItemFormController();
               // addItemFormController.setUserID(getUserId());

                FadeTransition rootTransition = new FadeTransition(Duration.seconds(1),formPane);
                rootTransition.setFromValue(0.5f);
                rootTransition.setToValue(1f);
                rootTransition.setCycleCount(1);
                rootTransition.setAutoReverse(false);
                rootTransition.play();



                rootAnchorPane.getChildren().setAll(formPane);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setUserID(int userID) {
        this.userID=userID;
        System.out.println("User ID ise " + this.userID);
    }

    public int getUserId(){
        return this.userID;
    }
}
