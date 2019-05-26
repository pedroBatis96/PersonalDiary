/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Batista
 */

@XmlRootElement(name = "entrada")
@XmlAccessorType (XmlAccessType.FIELD)
public class EntradaDiario {
    private String Data;
    private String Texto;
    
    public EntradaDiario(){
        
    }
   public EntradaDiario(LocalDate Data,String Texto){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
	LocalDate localDate = Data;
        this.Data = dtf.format(localDate);
        this.Texto = Texto;
    }

    public String getLocalDate() {
        return Data;
    }

    public void setLocalDate(String localDate) {
        this.Data = localDate;
    }

    public String getTexto(String chave) {
        return Encryptor.decrypt(chave, Models.iv, Texto);
    }

    public void setTexto(String Texto,String chave) {
        this.Texto = Encryptor.encrypt(chave, Models.iv, Texto);
    }

}
