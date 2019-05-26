/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Batista
 */

@XmlRootElement(name = "settings")
@XmlAccessorType (XmlAccessType.FIELD)
public class Settings {

    private String Family;
    private String Size;
    private String iconColor;
    private String menuColorSelected;
    private String menuColorUnselected;
    private String appColor;
    private String FontColor;
    private String FontPageColor;
    private String PageColor;

    public String getFontPageColor() {
        return FontPageColor;
    }

    public void setFontPageColor(String FontPageColor) {
        this.FontPageColor = FontPageColor;
    }

    
    public String getPageColor() {
        return PageColor;
    }

    public void setPageColor(String PageColor) {
        this.PageColor = PageColor;
    }
    public String getMenuColorUnselected() {
        return menuColorUnselected;
    }

    public void setMenuColorUnselected(String menuColorUnselected) {
        this.menuColorUnselected = menuColorUnselected;
    }
    private String theme;

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }
    

    public String getFontColor() {
        return FontColor;
    }

    public void setFontColor(String FontColor) {
        this.FontColor = FontColor;
    }

    public String getMenuColorSelected() {
        return menuColorSelected;
    }

    public void setMenuColorSelected(String menuColorSelected) {
        this.menuColorSelected = menuColorSelected;
    }

    public String getAppColor() {
        return appColor;
    }

    public void setAppColor(String appColor) {
        this.appColor = appColor;
    }

    public String getIconColor() {
        return iconColor;
    }

    public void setIconColor(String iconColor) {
        this.iconColor = iconColor;
    }

    public String getFamily() {
        return Family;
    }

    public void setFamily(String Family) {
        this.Family = Family;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String Size) {
        this.Size = Size;
    }
    
}
