package test;

import javax.crypto.SecretKey;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "login")
@XmlAccessorType (XmlAccessType.FIELD)
public class Login {
    private String username;
    private String password;
    private String chave;

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
    

    public void setUsername(String username) {
        this.username = Encryptor.encrypt(this.chave, "weJiSEvR5yAC5ftB", username);
    }

    public void setPassword(String password) {
        this.password = Encryptor.encrypt(this.chave, "weJiSEvR5yAC5ftB", password);
    }

   

    public String getUsername() {
        return Encryptor.decrypt(this.getChave(), "weJiSEvR5yAC5ftB", username);
    }

    public String getPassword() {
        return Encryptor.decrypt(this.getChave(), "weJiSEvR5yAC5ftB", password);
    }

    



}
