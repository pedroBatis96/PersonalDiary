/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.List;
 
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "entradas")
@XmlAccessorType (XmlAccessType.FIELD)
public class Entradas 
{

    @XmlElement(name = "entrada")
    private List<EntradaDiario> entrada = null;
 
    public List<EntradaDiario> getEntradas() {
        return entrada;
    }

    public void setEntrada(List<EntradaDiario> entrada) {
        this.entrada = entrada;
    }
 
}
