package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginController {

    @FXML
    private TextField loginTextFileld;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;
    @FXML
    private ImageView close;
    @FXML
    private Button loginButton;

    private String loginPrzyklad = "";
    private String hasloPrzyklad = "";

    @FXML
    public void loginAction() {
        login();
    }

    @FXML
    public void loginActionEnter() {
        login();
    }

    public void login() {
        String login = loginTextFileld.getText();
        String password = passwordField.getText();

        if(login.equals(loginPrzyklad) && password.equals(hasloPrzyklad)){
            try{
                Stage MainStage = new Stage();
                FXMLLoader loader = new FXMLLoader();

                TabPane root = (TabPane) loader.load(getClass().getResource("/fxml/MainScreen.fxml").openStream());

                MainController mainController = (MainController) loader.getController();
                mainController.setMainController(mainController);



                Scene scene = new Scene(root);
                String css = this.getClass().getResource("/css/aplication.css").toExternalForm();
                scene.getStylesheets().add(css);
                MainStage.setTitle("Main screen");
                MainStage.setScene(scene);
                MainStage.setResizable(false);
                MainStage.show();

            }catch (IOException ex){
                ex.printStackTrace();
            }
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.close();
        }else{
            errorLabel.setText("Invalid Login. Pleas try again.");
        }


    }

    @FXML
    public void closeAction(){
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

}
