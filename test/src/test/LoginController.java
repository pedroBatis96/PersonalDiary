/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


/**
 * FXML Controller class
 *
 * @author Batista
 */
public class LoginController implements Initializable {

    
    private static Login login = new Login();
    @FXML
    private TextField txtUsername;
    @FXML
    private Label lblUsername;
    @FXML
    private Label lblPass;
    @FXML
    private PasswordField txtPassword;
    @FXML
    private Button btnLogin;
    @FXML
    private Label lblQuotes;
    @FXML 
    private Button btnRegistar;

    public static void setLogin(Login login) {
        LoginController.login = login;
    }

    public static Login getLogin() {
        return login;
    }
    

  
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        txtUsername.setOnMousePressed(event -> ShowLabelUser());
        txtPassword.setOnMousePressed(event -> ShowLabelPassword());
        btnRegistar.setOnAction(event -> registo());
        btnLogin.setOnAction(event -> validarLogin(txtUsername.getText(),txtPassword.getText()));        
        Random rand = new Random();
        int ChosenQuote = rand.nextInt(Models.quotes.length);
        lblQuotes.setText(Models.quotes[ChosenQuote]);
       
    }    

    @FXML
    private void ShowLabelUser() {
    lblUsername.setVisible(true);
        if(txtPassword.getText().matches("")){
            lblPass.setVisible(false);
        }
    }

    private void ShowLabelPassword() {
    lblPass.setVisible(true);
    if(txtUsername.getText().matches("")){
        lblUsername.setVisible(false);
    }
       
    }

     // <editor-fold defaultstate="collapsed" desc="Ler Logins">    
   private void LerLogins() throws JAXBException
    {
        // Cria um JAXBContext e Unmarshaller que serão usados para ler os logins para a  lista 
        JAXBContext jaxbContext = JAXBContext.newInstance(Login.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        try{
            login = (Login) jaxbUnmarshaller.unmarshal( new File("login.xml") );
        }
        catch(Exception ex){
            
        }
    }
        
   private void registarLogin(){  
      
        try {  
            
            KeyGenerator keyGen;
            keyGen = KeyGenerator.getInstance("AES");
            keyGen.init(128);
            SecretKey secretKey = keyGen.generateKey();
            login.setChave(Base64.getEncoder().encodeToString(secretKey.getEncoded()));
            
            
            login.setPassword(txtPassword.getText());
            login.setUsername(txtUsername.getText());
            JAXBContext jaxbContext;
            Pattern pattern = Pattern.compile("[~#@*+%{}<>\\[\\]|\"\\_^?]");
            Matcher matcher = pattern.matcher(txtUsername.getText());
            
            
            if(matcher.find()){
                JOptionPane.showMessageDialog(null," Caracter  inválido!","Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(txtUsername.getText().length()>12  ){
                JOptionPane.showMessageDialog(null," O username só pode ter 12 caracteres!","Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            if(txtUsername.getText().isEmpty()  ){
                JOptionPane.showMessageDialog(null,"Username vazio!","Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if(txtPassword.getText().isEmpty() ){
                JOptionPane.showMessageDialog(null,"Password vazia!","Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            
            try {
                jaxbContext = JAXBContext.newInstance(Login.class);
                Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
                jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
                Files.createDirectory(Paths.get(login.getUsername()));
                jaxbMarshaller.marshal(login, new File(login.getUsername()+"/login.xml"));
                JOptionPane.showMessageDialog(null,"Registado com sucesso!","Registo", JOptionPane.INFORMATION_MESSAGE);
                voltar();
            } catch (JAXBException ex) {
                System.out.println("ola");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"Utilizador já existente!","Erro", JOptionPane.ERROR_MESSAGE);;
            }
            
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
   }
   
   
   private void validarLogin(String nome, String password){
       
            Login loginaux = new Login();
            String user=nome;
            String pass=password;
            
            JAXBContext jaxbContext = null;
            try {
                jaxbContext = JAXBContext.newInstance(Login.class);
            } catch (JAXBException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
                Unmarshaller jaxbUnmarshaller = null;
            try {
                jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            } catch (JAXBException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                loginaux  = (Login)  jaxbUnmarshaller.unmarshal( new File(nome+"/login.xml") );
            } catch (JAXBException ex) {
                JOptionPane.showMessageDialog(null,"Utilizador Não Encontrado!","Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }
               
                if(loginaux.getUsername()!=null || loginaux.getPassword()!=null){
                    if(loginaux.getUsername().equals(nome))
                        if(loginaux.getPassword().equals(pass)){
                            try {
                                FXMLLoader fxmllo =new FXMLLoader(getClass().getResource("Diario.fxml"));
                                Parent NewRoot = (Parent)fxmllo.load();
                                DiarioController diario = fxmllo.<DiarioController>getController();
                                String chave = loginaux.getChave();
                                diario.setUser(user,chave);
                                btnLogin.getScene().getWindow().hide();

                                Stage stage = new Stage();
                                stage.setMinHeight(700);
                                stage.setMinWidth(1132.6328801);
                                Scene scene = new Scene(NewRoot);
                                stage.setTitle("O Diário de "+ Character.toUpperCase(user.charAt(0)) + user.substring(1)) ;
                                stage.setScene(scene);
                                stage.show();
                            } catch (IOException ex) {
                                System.out.println("erro");;
                            }
                        }    
                    else
                            JOptionPane.showMessageDialog(null,"Password Incorreta!","Erro", JOptionPane.ERROR_MESSAGE);
                }
                
                    else
                            JOptionPane.showMessageDialog(null,"Utilizador Não Encontrado!","Erro", JOptionPane.ERROR_MESSAGE);
                
            
    }
   
   

       
   private void registo(){

       btnLogin.setText("Registar");
       btnRegistar.setText("Voltar");
       btnLogin.setOnAction(event -> registarLogin());
       btnRegistar.setOnAction(event -> voltar());


}
   
   private void voltar (){
       btnLogin.setText("Login");
       btnRegistar.setText("Registar");
       btnLogin.setOnAction(event -> validarLogin(txtUsername.getText(),txtPassword.getText()));
       btnRegistar.setOnAction(event -> registo());
   }

    @FXML
    private void Login(ActionEvent event) {
    }
   
}





                   