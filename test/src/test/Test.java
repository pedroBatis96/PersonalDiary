/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Batista
 */
public class Test extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        String image = getClass().getResource("book.jpg").toExternalForm();
        root.setStyle(
            "-fx-background-image: url('" + image +"');" +
            "-fx-background-position: center center;" +
            "-fx-background-repeat: stretch;");
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("O Diário da Minha Paixão");
        stage.show();
        stage.setResizable(false);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
