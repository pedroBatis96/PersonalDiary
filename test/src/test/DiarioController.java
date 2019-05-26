
package test;





import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.print.PrinterJob;
import javafx.scene.paint.Color;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.MediaTray;
import javax.print.attribute.standard.Sides;
import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.printing.PDFPageable;


 
public class DiarioController implements Initializable {
    
    @FXML
    private FlowPane painSave;
    @FXML
    private Label lblPage1;
    @FXML
    private Label lblPage2;
    @FXML
    private FlowPane painSearch;
    @FXML
    private FlowPane painPdf;
    @FXML
    private FlowPane painPrint;
    @FXML
    private FlowPane painFont;
    @FXML
    private FlowPane painSettings;
    @FXML
    private Button btnSearchPesquisa;
    @FXML
    private Button btnSearchLimpar;
    @FXML
    private DatePicker dtpSearchInicio;
    @FXML
    private DatePicker dtpSearchFim;
    @FXML
    private TextField txtSearchPalavra;
    @FXML
    private ComboBox<String> cbFontFamily;
    @FXML
    private ComboBox<String> cbFontSize;
    @FXML
    private ComboBox<String> cbSettingsTheme;
    @FXML
    private Label lblFontFamily;
    @FXML
    private Label lblFontSize;
    @FXML
    private TextArea txtPag1;
    @FXML
    private TextArea txtPag2;
    @FXML
    private ImageView imLock1;
    @FXML
    private ImageView imLock2;
    @FXML
    private Button btnLock1;
    @FXML
    private Button btnLock2;
    @FXML
    private ImageView imMenuSeacrch;
    @FXML
    private ImageView imMenuSave;
    @FXML
    private ImageView imMenuPdf;
    @FXML
    private ImageView imMenuPrint;
    @FXML
    private ImageView imMenuFont;
    @FXML
    private ImageView imMenuSettings;
    @FXML
    private Label lblSettingsTheme;
    @FXML
    private AnchorPane ancMenuTop;
    @FXML
    private AnchorPane ancPage1;
    @FXML
    private AnchorPane ancPage2;
    @FXML
    private BorderPane borderApp;
    @FXML
    private ColorPicker clpFontCor;
    @FXML
    private Label lblFontCor;
    @FXML
    private Button btnLeftArrow;
    @FXML
    private Button btnRightArrow;
 
    Login login = new Login();
    private boolean lock = true,lock2 = true,pag1Text=false,pag2Text=false;
    private boolean searchMenu = false,fontMenu = false,settingsMenu = false;
    static Entradas Entradas = new Entradas();
    private LocalDate lbl1;
    private LocalDate lbl2;
    static Settings setting = new Settings();
    private List<EntradaDiario> entradaPesquisa = null;
    private int leftIndex = 0;
    @FXML
    private Label lblSearchDi;
    @FXML
    private Label lblSearchDf;
    @FXML
    private Label lblSearchPalavra;
    @FXML
    private ListView<String> lvPesquisa;
    @FXML
    private Button btnSettingsLogout;
    
    
    public void setUser(String user,String chave){
         login.setChave(chave);
         login.setUsername(user);
         
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Platform.runLater(()->{
        Entradas.setEntrada((new ArrayList<EntradaDiario>()));
        
      
        // Le todas as Entradas de Diario guardadas no Ficheiro diario.xml
        //Le todos os Settings
        try {
            LerEntradas();
        }catch (JAXBException ex) {
            Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            imLock1.setImage(new Image(new File("src/test/icons/unlocked.png").toURI().toURL().toString()));
            imLock2.setImage(new Image(new File("src/test/icons/unlocked.png").toURI().toURL().toString()));
        } catch (MalformedURLException ex) {
            Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //Dá set às páginas inciais e preenche as combo boxes
        setEntradas(LocalDate.now().minusDays(1),LocalDate.now());
        setIconColor();
        setAppColor();
        setCombos();
        

        
        // Interação com o Menu
        painSearch.setOnMouseClicked(event -> ShowSearch());
        painFont.setOnMouseClicked(event -> ShowFont());
        painSettings.setOnMouseClicked(event -> ShowSettings());
        
        painSave.setOnMousePressed(event -> MenuClicked(painSave));
        painSave.setOnMouseReleased(event -> MenuReleased(painSave));
        painSave.setOnMouseClicked(event -> GuardarClick ());
        
        painPrint.setOnMousePressed(event -> MenuClicked(painPrint));
        painPrint.setOnMouseReleased(event -> MenuReleased(painPrint));
        painPrint.setOnMouseClicked(event ->print());
        
        painPdf.setOnMousePressed(event -> MenuClicked(painPdf));
        painPdf.setOnMouseReleased(event -> MenuReleased(painPdf));
        painPdf.setOnMouseClicked(event -> XMLTOPDF());
        
        btnSearchPesquisa.setOnAction(event -> PesquisaAction());
        
        btnLeftArrow.setOnAction(event -> Arrow(true));
        btnRightArrow.setOnAction(event -> Arrow(false));
        btnSettingsLogout.setOnAction(event -> Logout() );
        
        cbFontFamily.setOnAction(event -> ChangeFont());
        cbFontSize.setOnAction(event -> ChangeFont());
        clpFontCor.setOnAction(event -> ChangeFont());
        
        txtPag1.setOnKeyTyped(event -> typed(true));
        txtPag2.setOnKeyTyped(event -> typed(false));
        
        cbSettingsTheme.setOnAction(event -> ChangeTheme());
        
        btnLock1.setOnMousePressed(event -> {
            try {
                ImageLock(true);
            } catch (MalformedURLException ex) {
                Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        btnLock2.setOnMousePressed(event -> {
            try {
                ImageLock(false);
            } catch (MalformedURLException ex) {
                Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        lvPesquisa.setOnMouseClicked(event -> lvOnClick());
        
        
        
        ShowSearch();
        });
    }    
   
public void typed(boolean page1){
    if(page1){
        pag1Text = true;
    }
    else{
        pag2Text = true;
    }
    borderApp.getScene().getWindow().setOnCloseRequest(event -> onExit());
}
// <editor-fold defaultstate="collapsed" desc="Botão Lock">    
private void ImageLock (boolean page1) throws MalformedURLException{
    if (page1){
        //Verifica se já se encontra trancado ou não e muda a cor e o estado
        if(lock){
            txtPag1.setEditable(false);
            imLock1.setImage(new Image(new File("src/test/icons/locked.png").toURI().toURL().toString()));
            btnLock1.setStyle("-fx-border-radius: 100;-fx-background-radius:100");
            txtPag1.setStyle("text-area-background: #CCCCCC ;-fx-text-fill:" + setting.getFontPageColor());
            lock = false;
        }
        else{
            txtPag1.setEditable(true);
            imLock1.setImage(new Image(new File("src/test/icons/unlocked.png").toURI().toURL().toString()));
            btnLock1.setStyle("-fx-border-radius: 100;-fx-background-radius:100");
            txtPag1.setStyle("-fx-text-fill:" + setting.getFontPageColor());
            lock = true;
        }
    }
    else{
        if(lock2){
            txtPag2.setEditable(false);
            imLock2.setImage(new Image(new File("src/test/icons/locked.png").toURI().toURL().toString()));
            btnLock2.setStyle("-fx-border-radius: 100;-fx-background-radius:100");
            txtPag2.setStyle("text-area-background: #CCCCCC ;-fx-text-fill:" + setting.getFontPageColor());
            lock2 = false;
        }
        else{
            txtPag2.setEditable(true);
            imLock2.setImage(new Image(new File("src/test/icons/unlocked.png").toURI().toURL().toString()));
            btnLock2.setStyle("-fx-border-radius: 100;-fx-background-radius:100");
            txtPag2.setStyle("-fx-text-fill:" + setting.getFontPageColor());
            lock2 = true;
        }
    }
}
// </editor-fold>   

// <editor-fold defaultstate="collapsed" desc="Iniciar Combos">
 // Preenche cada Combo com o conteudo adequado
 public void setCombos (){
        //Vai buscar todos os tipos de letra possivel (estas guardadas na classe Models)
        cbFontFamily.getItems().addAll(Models.family);
        
        //Mete como selecionado o tipo de letra guardado nos settings
        cbFontFamily.getSelectionModel().select(setting.getFamily());
        
        //Preenche a combobox dos tamanhos com os tamanhos respetivos
        for (int i = 8;i<28;i++){
            if(i<=12){
                cbFontSize.getItems().add(String.valueOf(i));
            }
            else if (i>12){
                i++;
                cbFontSize.getItems().add(String.valueOf(i));
            }
        }
        cbFontSize.getItems().addAll("36","48","64","72");
        
        //Mete como selecionado o tamanho de letra guardado nos settings
        cbFontSize.getSelectionModel().select(setting.getSize());
        
        //Preenche a ComboBox dos Temas
        cbSettingsTheme.getItems().addAll(Models.temas);
        cbSettingsTheme.getSelectionModel().select(setting.getTheme());
        
        clpFontCor.setValue(Color.valueOf(setting.getFontPageColor()));
 }
  // </editor-fold>
 
// <editor-fold defaultstate="collapsed" desc="Muda a cor dos Icons">
 public void setIconColor(){
    try {
        //Muda a cor dos icons dependendo do tema, atinge-se isto mudando o ficheiro usado nas imageviews
        //ex search_b para black search_w para white
        String color = setting.getIconColor();
        imMenuSeacrch.setImage(new Image(new File("src/test/icons/search_"+color+".png").toURI().toURL().toString()));
        imMenuSave.setImage(new Image(new File("src/test/icons/save-as_"+color+".png").toURI().toURL().toString()));;
        imMenuPdf.setImage(new Image(new File("src/test/icons/pdf_"+color+".png").toURI().toURL().toString()));;
        imMenuPrint.setImage(new Image(new File("src/test/icons/print_"+color+".png").toURI().toURL().toString()));;
        imMenuFont.setImage(new Image(new File("src/test/icons/typography_"+color+".png").toURI().toURL().toString()));;
        imMenuSettings.setImage(new Image(new File("src/test/icons/settings_"+color+".png").toURI().toURL().toString()));;
    }
    catch (MalformedURLException ex) {
        Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
   }
 }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Muda A cor da aplicação">
 public void setAppColor(){
    //Cor da letra das labels
    lblFontFamily.setTextFill(Paint.valueOf(setting.getFontColor()));
    lblFontSize.setTextFill(Paint.valueOf(setting.getFontColor()));
    lblFontCor.setTextFill(Paint.valueOf(setting.getFontColor()));
    lblPage1.setTextFill(Paint.valueOf(setting.getFontColor()));
    lblPage2.setTextFill(Paint.valueOf(setting.getFontColor()));
    lblSettingsTheme.setTextFill(Paint.valueOf(setting.getFontColor()));
    lblSearchDi.setTextFill(Paint.valueOf(setting.getFontColor()));
    lblSearchDf.setTextFill(Paint.valueOf(setting.getFontColor()));
    lblSearchPalavra.setTextFill(Paint.valueOf(setting.getFontColor()));
     
    //Cor do backgrounds
    ancPage1.setStyle("-fx-background-color :"+setting.getAppColor());
    ancPage2.setStyle("-fx-background-color :"+setting.getAppColor());
    ancMenuTop.setStyle("-fx-background-color :"+setting.getAppColor());
    borderApp.setStyle("-fx-background-color :"+setting.getAppColor());
     
    // Cor do Menu
    painSearch.setStyle("-fx-background-color :"+setting.getMenuColorUnselected());
    painFont.setStyle("-fx-background-color :"+setting.getMenuColorUnselected());
    painSettings.setStyle("-fx-background-color :"+setting.getMenuColorUnselected());
    painSave.setStyle("-fx-background-color :"+setting.getMenuColorUnselected());
    painPdf.setStyle("-fx-background-color :"+setting.getMenuColorUnselected());
    painPrint.setStyle("-fx-background-color :"+setting.getMenuColorUnselected());
     
    //Mete as páginas iniciais de acordo com os settings ("Arial" e "20.0" default)
    txtPag1.setFont(Font.font(setting.getFamily(),Double.valueOf(setting.getSize())));
    txtPag2.setFont(Font.font(setting.getFamily(),Double.valueOf(setting.getSize())));
     
    //Cor da letra das páginas
    if(!lock){
        txtPag1.setStyle("text-area-background: #CCCCCC ;-fx-text-fill:" + setting.getFontPageColor());
    }
    else{
        txtPag1.setStyle("-fx-text-fill:" + setting.getFontPageColor());
    }
    if(!lock2){
        txtPag2.setStyle("text-area-background: #CCCCCC ;-fx-text-fill:" + setting.getFontPageColor());
    }
    else{
        txtPag2.setStyle("-fx-text-fill:" + setting.getFontPageColor());
    }
     
    //Mete a selecionada com a cor certa
    if (searchMenu){
        painSearch.setStyle("-fx-background-color :"+ setting.getMenuColorSelected());
    }
    else if(fontMenu){
        painFont.setStyle("-fx-background-color :"+ setting.getMenuColorSelected());
    }
    else if(settingsMenu){
        painSettings.setStyle("-fx-background-color :"+ setting.getMenuColorSelected());
    }
 }
 // </editor-fold>
 
// <editor-fold defaultstate="collapsed" desc=" Set das Paginas Iniciais">     
 public void setEntradas(LocalDate date1,LocalDate date2){
        
        // Vai buscar a data actual e do dia anterior e guarda respectivamente na lbl2 e lbl1 como Local Date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
	lbl1 = date1;
        lbl2 = date2;
        
        if (LocalDate.now().isBefore(lbl2.plusDays(1))){
            btnRightArrow.setVisible(false);
        }
        else{
            btnRightArrow.setVisible(true);
        }
                
        // Faz set do texto do topo de cada página para a respetiva data
	lblPage1.setText(dtf.format(lbl1));
        lblPage2.setText(dtf.format(lbl2));
        
        txtPag1.setText("");
        txtPag2.setText("");
        // Percorre todas as entradas de diario lidas e guardadas no ficheiro e verifica se existe alguma com a mesma data
        // se sim mete o texto correspondente na página correspondente
        for(EntradaDiario emp : Entradas.getEntradas())
        {
            if(emp.getLocalDate().equals(lblPage1.getText())){
                txtPag1.setText(emp.getTexto(login.getChave()));
            }
            if(emp.getLocalDate().equals(lblPage2.getText())){
                txtPag2.setText(emp.getTexto(login.getChave()));
            }
                
        }
 }
 // </editor-fold>
 
// <editor-fold defaultstate="collapsed" desc=" Font"> 
 public void ChangeFont(){
     
     try{
        //Vai buscar os valores selecionados de cada ComboBox
        String family = cbFontFamily.getSelectionModel().getSelectedItem();
        double size = Double.valueOf(cbFontSize.getSelectionModel().getSelectedItem());

        //Faz set da familia e tamanho a cada página do diário
        txtPag1.setFont(Font.font(family,size));
        txtPag2.setFont(Font.font(family,size ));

        //Muda a familia e tamanho nos settings
        setting.setFamily(family);
        setting.setSize(String.valueOf(size));

        setting.setFontPageColor("#" + Integer.toHexString(clpFontCor.getValue().hashCode()));

        txtPag1.setStyle("-fx-text-fill:" + setting.getFontPageColor());
        txtPag2.setStyle("-fx-text-fill:" + setting.getFontPageColor());
     }
     catch(Exception ex){
          JOptionPane.showMessageDialog(null,"Introduza um tamanho válido (valor numérico).","Erro", JOptionPane.ERROR_MESSAGE);
     }
     //Guarda as mudanças no xml dos settings
     JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Settings.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(setting, new File(login.getUsername()+"/settings.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
     
 }
 // </editor-fold>
 
// <editor-fold defaultstate="collapsed" desc=" Pesquisa">  
  public void PesquisaAction(){
      try{
          if (dtpSearchInicio.getValue() != null ){
            LocalDate inicio = dtpSearchInicio.getValue();
            if(dtpSearchFim.getValue() != null){
                LocalDate fim = dtpSearchFim.getValue();
                if(!txtSearchPalavra.getText().equals("")){
                    String palavra = txtSearchPalavra.getText();
                    Pesquisar(true,true,true,inicio,fim,palavra);
                }
                else{
                    Pesquisar(true,true,false,inicio,fim,"");
                }
            }
            else{
                if(!txtSearchPalavra.getText().equals("")){
                    String palavra = txtSearchPalavra.getText();
                    Pesquisar(true,false,true,inicio,null,palavra);
                }
                else{
                    Pesquisar(true,false,false,inicio,null,"");
                }
            }
          }
          else{
              if(!txtSearchPalavra.getText().equals("")){
                  String palavra = txtSearchPalavra.getText();
                  Pesquisar(false,false,false,null,null,palavra);
              } 
              else{
                  JOptionPane.showMessageDialog(null,"Por favor introduza uma data de inicio ou uma palavra para pesquisar.","Erro", JOptionPane.ERROR_MESSAGE);
              }
          }
      }
      catch(Exception ex){
          JOptionPane.showMessageDialog(null,"Ocorreu um erro durante a pesquisa.","Erro", JOptionPane.ERROR_MESSAGE);
      }
  }
  
  public void Pesquisar(boolean DataI,boolean DataF,boolean Palavra,LocalDate inicio,LocalDate fim, String palavra){
      RestaurarPesquisa();
      entradaPesquisa = new ArrayList<EntradaDiario>();
      leftIndex = 0;
      if (DataI){
          if(DataF){
              if(Palavra){
                  for(EntradaDiario emp : Entradas.getEntradas())
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
                        LocalDate aux = LocalDate.parse(emp.getLocalDate(),formatter);
                        if(aux.isAfter(inicio) && aux.isBefore(fim) && emp.getTexto(login.getChave()).contains(palavra)){
                            entradaPesquisa.add(emp);
                        }
                        else if(aux.isEqual(inicio) || aux.isEqual(fim) && emp.getTexto(login.getChave()).contains(palavra)){
                            entradaPesquisa.add(emp);
                        }
                    }
                  entradaPesquisa = ordenarPesquisa(entradaPesquisa,palavra);
              }
              else{
                    for(EntradaDiario emp : Entradas.getEntradas())
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
                        LocalDate aux = LocalDate.parse(emp.getLocalDate(),formatter);
                        if(aux.isAfter(inicio) && aux.isBefore(fim)){
                            entradaPesquisa.add(emp);
                        }
                        else if(aux.isEqual(inicio) || aux.isEqual(fim)){
                            entradaPesquisa.add(emp);
                        }
                    }   
             }
          }
          else{
              if(Palavra){
                  for(EntradaDiario emp : Entradas.getEntradas())
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
                        LocalDate aux = LocalDate.parse(emp.getLocalDate(),formatter);
                        if(aux.isAfter(inicio) && emp.getTexto(login.getChave()).contains(palavra)){
                            entradaPesquisa.add(emp);
                        }
                        else if(aux.isEqual(inicio) && emp.getTexto(login.getChave()).contains(palavra) ){
                            entradaPesquisa.add(emp);
                        }
                    }
                  entradaPesquisa = ordenarPesquisa(entradaPesquisa,palavra);
              }
              else{
                  for(EntradaDiario emp : Entradas.getEntradas())
                    {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
                        LocalDate aux = LocalDate.parse(emp.getLocalDate(),formatter);
                        if(aux.isAfter(inicio)){
                            entradaPesquisa.add(emp);
                        }
                        else if(aux.isEqual(inicio)){
                            entradaPesquisa.add(emp);
                        }
                    }   
              }
          }
      }
      else{
        for(EntradaDiario emp : Entradas.getEntradas())
        {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
            LocalDate aux = LocalDate.parse(emp.getLocalDate(),formatter);
            if(emp.getTexto(login.getChave()).contains(palavra)){
                entradaPesquisa.add(emp);
            }
        }
        entradaPesquisa = ordenarPesquisa(entradaPesquisa,palavra);
        entradaPesquisa = ordenarPesquisaData(entradaPesquisa);
      }
      if(entradaPesquisa.size() == 0){
          JOptionPane.showMessageDialog(null,"Não foram encontrados resultados.","Erro", JOptionPane.ERROR_MESSAGE);
      }
      else{
        if (entradaPesquisa.size() == 1){
            setEntradasSearchForeverAlone(entradaPesquisa.get(0));
        }
        else{
            entradaPesquisa = ordenarPesquisaData(entradaPesquisa);
            lvPesquisa.setVisible(true);
            for(EntradaDiario entrada : entradaPesquisa){
                lvPesquisa.getItems().add(String.format("%-10s", entrada.getLocalDate().split(" ")[1]));
            }
            setEntradasSearch(entradaPesquisa.get(0),entradaPesquisa.get(1));
        }
        btnLeftArrow.setOnAction(event -> ArrowSearch(true));
        btnRightArrow.setOnAction(event -> ArrowSearch(false));
      }
  }
  
  public List<EntradaDiario> ordenarPesquisa(List<EntradaDiario> entradaPesquisa,String palavra){
    List<EntradaDiario> EntradaFinal = new ArrayList<EntradaDiario>(); 
    while(entradaPesquisa.size() > 0){
        int i = 0,rem = 0,min = 0,j;
        EntradaDiario aux = new EntradaDiario();
        for(j = 0;j<entradaPesquisa.size();j++){
            i = 0;
            Pattern p = Pattern.compile(palavra);
            Matcher m = p.matcher( entradaPesquisa.get(j).getTexto(login.getChave()));
            while (m.find()) {
                i++;
            }
            if(min < i){
                min = i;
                aux.setLocalDate(entradaPesquisa.get(j).getLocalDate());
                aux.setTexto(entradaPesquisa.get(j).getTexto(login.getChave()),login.getChave());
                rem = j;
            }
        }
    EntradaFinal.add(aux);
    entradaPesquisa.remove(rem);
    }
    return EntradaFinal;
  }
  
  public List<EntradaDiario> ordenarPesquisaData(List<EntradaDiario> entradaPesquisaaux){
    List<EntradaDiario> EntradaFinal = new ArrayList<EntradaDiario>(); 
    while(entradaPesquisaaux.size() > 0){
        int i = 0,rem = 0,j;
        LocalDate min = LocalDate.now().plusDays(1);
        EntradaDiario aux = new EntradaDiario();
        for(j = 0;j<entradaPesquisaaux.size();j++){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
            LocalDate auxDate = LocalDate.parse(entradaPesquisaaux.get(j).getLocalDate(),dtf);
            if(auxDate.isBefore(min)){
                min = auxDate;
                aux.setLocalDate(entradaPesquisaaux.get(j).getLocalDate());
                aux.setTexto(entradaPesquisaaux.get(j).getTexto(login.getChave()),login.getChave());
                rem = j;
            }
        }
    EntradaFinal.add(aux);
    entradaPesquisaaux.remove(rem);
    }
    return EntradaFinal;
  }
  
  public void setEntradasSearch(EntradaDiario emp1,EntradaDiario emp2){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
      
        lbl1 = LocalDate.parse(emp1.getLocalDate(),dtf);
        lbl2 = LocalDate.parse(emp2.getLocalDate(),dtf);
      
        if (entradaPesquisa.indexOf(emp2) == (entradaPesquisa.size() - 1)){
            btnRightArrow.setVisible(false);
        }
        else{
            btnRightArrow.setVisible(true);
        }
        
        if (entradaPesquisa.indexOf(emp1) == 0){
            btnLeftArrow.setVisible(false);
        }
        else{
            btnLeftArrow.setVisible(true);
        }
        
        
        // Faz set do texto do topo de cada página para a respetiva data
	lblPage1.setText(emp1.getLocalDate());
        lblPage2.setText(emp2.getLocalDate());
        
        txtPag1.setText("");
        txtPag2.setText("");
        
        txtPag1.setText(emp1.getTexto(login.getChave()));
        txtPag2.setText(emp2.getTexto(login.getChave()));
  }
 
  public void setEntradasSearchForeverAlone(EntradaDiario emp1){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
        
        lbl1 = LocalDate.parse(emp1.getLocalDate(),dtf);
        lbl2 = null;
      
        btnRightArrow.setVisible(false);
        btnLeftArrow.setVisible(false);
        
        // Faz set do texto do topo de cada página para a respetiva data
	lblPage1.setText(emp1.getLocalDate());
        txtPag1.setText("");
        txtPag2.setText("");
        
        
        lblPage2.setText("");
        txtPag2.setEditable(false);
        btnLock2.setVisible(false);
        txtPag2.setStyle("text-area-background: #CCCCCC ;-fx-text-fill:" + setting.getFontPageColor());
        
        
        txtPag1.setText(emp1.getTexto(login.getChave()));
  }
  
      public void lvOnClick(){
        try{
            int dateAux = lvPesquisa.getSelectionModel().getSelectedIndex();
            if(dateAux < entradaPesquisa.size() - 1)
                setEntradasSearch(entradaPesquisa.get(dateAux), entradaPesquisa.get(dateAux + 1));
            else if(dateAux == entradaPesquisa.size() - 1){
                setEntradasSearch(entradaPesquisa.get(dateAux - 1), entradaPesquisa.get(dateAux));
            }
        }
        catch (Exception ex){
            
        }
    }
  
  public void ArrowSearch(boolean left){
    if(left){
        if(pag1Text || pag2Text){
            String[] options = {"Sim","Não"};
            int reply = JOptionPane.showOptionDialog(null, "Foram detetadas mudanças na página, deseja guardar? ", "Atenção", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if (reply == JOptionPane.YES_OPTION) {
              GuardarClick();
            }
        }
        leftIndex = leftIndex - 1;
        setEntradasSearch(entradaPesquisa.get(leftIndex),entradaPesquisa.get(leftIndex + 1));
    }
    else{
        if(pag1Text || pag2Text){
            String[] options = {"Sim","Não"};
            int reply = JOptionPane.showOptionDialog(null, "Foram detetadas mudanças na página, deseja guardar?", "Atenção", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if (reply == JOptionPane.YES_OPTION) {
              GuardarClick();
            }
        }
        leftIndex = leftIndex + 1;
        setEntradasSearch(entradaPesquisa.get(leftIndex),entradaPesquisa.get(leftIndex + 1));
    }
}
  // </editor-fold>
  
// <editor-fold defaultstate="collapsed" desc=" Menu ">
  
    // Mostrar e Esconder os Elementos respectivos de cada menu, bem como mudanças de cor
    public void MenuClicked(FlowPane pain){
        pain.setStyle("-fx-background-color :"+ setting.getMenuColorSelected());
    }
    
    public void MenuReleased(FlowPane pain){
         pain.setStyle("-fx-background-color :"+setting.getMenuColorUnselected());
    }
    
    public void ShowSearch(){
        HideFont();
        HideSettings();
        searchMenu = true;
        painSearch.setStyle("-fx-background-color :"+ setting.getMenuColorSelected());
        btnSearchPesquisa.setVisible(true);
        btnSearchLimpar.setText("Restaurar");
        btnSearchLimpar.setVisible(true);
        btnSearchLimpar.setOnAction(event -> RestaurarPesquisa() );
        dtpSearchInicio.setVisible(true);
        dtpSearchFim.setVisible(true);
        txtSearchPalavra.setVisible(true);
        lblSearchDi.setVisible(true);
        lblSearchDf.setVisible(true);
        lblSearchPalavra.setVisible(true);
              
    }
    
    public void HideSearch(){
        searchMenu = false;
        painSearch.setStyle("-fx-background-color :"+setting.getMenuColorUnselected());
        lvPesquisa.getItems().clear();
        lvPesquisa.setVisible(false);
        btnSearchPesquisa.setVisible(false);
        btnSearchLimpar.setVisible(false);
        dtpSearchInicio.setVisible(false);
        dtpSearchFim.setVisible(false);
        txtSearchPalavra.setVisible(false);
        lblSearchDi.setVisible(false);
        lblSearchDf.setVisible(false);
        lblSearchPalavra.setVisible(false);
    }
    
    public void ShowFont(){
        HideSearch();
        HideSettings();
        fontMenu = true;
        painFont.setStyle("-fx-background-color :"+ setting.getMenuColorSelected());
        
        btnSearchLimpar.setVisible(true);
        btnSearchLimpar.setText("Restaurar");
        btnSearchLimpar.setOnAction(event -> RestaurarFont () );
        clpFontCor.setVisible(true);
        lblFontCor.setVisible(true);
        cbFontFamily.setVisible(true);
        cbFontSize.setVisible(true);
        lblFontFamily.setVisible(true);
        lblFontSize.setVisible(true);
        }
    
    public void HideFont(){
        fontMenu = false;
        painFont.setStyle("-fx-background-color :"+setting.getMenuColorUnselected());
        clpFontCor.setVisible(false);
        lblFontCor.setVisible(false);
        cbFontFamily.setVisible(false);
        cbFontSize.setVisible(false);
        lblFontFamily.setVisible(false);
        lblFontSize.setVisible(false);
    }
    
    public void ShowSettings(){
        HideFont();
        HideSearch();
        btnSettingsLogout.setVisible(true);
        settingsMenu = true;
        btnSearchLimpar.setVisible(true);
        painSettings.setStyle("-fx-background-color :"+ setting.getMenuColorSelected());
        cbSettingsTheme.setVisible(true);
        lblSettingsTheme.setVisible(true);
    }
    
    public void HideSettings(){
        settingsMenu = false;
        btnSettingsLogout.setVisible(false);
        painSettings.setStyle("-fx-background-color :"+setting.getMenuColorUnselected());
        cbSettingsTheme.setVisible(false);
        lblSettingsTheme.setVisible(false);
    }
    

    
    // </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Guardar">
    public void GuardarClick (){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E dd/MM/yyyy");
        
        //Procura uma Entrada de Diario no Entradas que corresponda à Data , se existir substitui caso contrário cria uma nova
        //Pag1
        if (Entradas.getEntradas().stream().filter(var -> var.getLocalDate().equals(dtf.format(lbl1))).toArray().length > 0){
            Entradas.getEntradas().stream().filter(var -> var.getLocalDate().equals(dtf.format(lbl1))).findFirst().get().setTexto(txtPag1.getText(),login.getChave());
        }
        else{
            Entradas.getEntradas().add(new EntradaDiario(lbl1,Encryptor.encrypt(login.getChave(), Models.iv,txtPag1.getText() )));
        }
        
        //Pag2
        if (lbl2 != null){
            if (Entradas.getEntradas().stream().filter(var -> var.getLocalDate().equals(dtf.format(lbl2))).toArray().length > 0){
                Entradas.getEntradas().stream().filter(var -> var.getLocalDate().equals(dtf.format(lbl2))).findFirst().get().setTexto(txtPag2.getText(),login.getChave());
            }
            else{
                Entradas.getEntradas().add(new EntradaDiario(lbl2,Encryptor.encrypt(login.getChave(), Models.iv,txtPag2.getText() )));
            }
        }
        
        //Cria um JAXBContext e Marshaller que irão guardar todas as entradas no ficheiro diario.xml, apresenta seguidamente uma mensagem de sucesso
        JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Entradas.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(Entradas, new File(login.getUsername()+"/diario.xml"));
            JOptionPane.showMessageDialog(null,"Diário guardado com sucesso.","Guardar", JOptionPane.INFORMATION_MESSAGE);
            pag1Text = false;
            pag2Text = false;
        } 
        // Caso falhe apresenta uma mensagem de erro
        catch (JAXBException ex) {
            Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,"Erro ao guardar, tente novamente mais tarde.","Erro", JOptionPane.ERROR_MESSAGE);
        }
        
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Muda o Tema da aplicação">    
private void ChangeTheme(){
    String theme = cbSettingsTheme.getSelectionModel().getSelectedItem();
    switch (theme){
        case "Default":
            setting.setIconColor("b");
            setting.setMenuColorSelected("#1995ad");
            setting.setFontColor("black");
            setting.setTheme("Default");
            setting.setMenuColorUnselected("#bcbabe");
            setting.setPageColor("");
            setting.setAppColor("#f1f1f2");
            setIconColor();
            setAppColor();
            break;
        case "Dark":
            setting.setIconColor("w");
            setting.setMenuColorSelected("#4BC9FF");
            setting.setMenuColorUnselected("#242424");
            setting.setFontColor("white");
            setting.setAppColor("#242424");
            setting.setPageColor("");
            setting.setTheme("Dark");
            setIconColor();
            setAppColor();
            break;
        case "Coffee":
            setting.setIconColor("b");
            setting.setMenuColorSelected("#ddbc95");
            setting.setMenuColorUnselected("#b38867");
            setting.setPageColor("");
            setting.setFontColor("black");
            setting.setAppColor("#cdcdc0");
            setting.setTheme("Coffee");
            setIconColor();
            setAppColor();
            break;
        case "Orange":
            setting.setIconColor("w");
            setting.setMenuColorSelected("#fb8122");
            setting.setMenuColorUnselected("#1d2228");
            setting.setPageColor("");
            setting.setFontColor("black");
            setting.setAppColor("#e1e2e2");
            setting.setTheme("Orange");
            setIconColor();
            setAppColor();
            break;
        case "Pink":
            setting.setIconColor("w");
            setting.setMenuColorSelected("#D693BD");
            setting.setMenuColorUnselected("#CA278C");
            setting.setPageColor("");
            setting.setFontColor("black");
            setting.setAppColor("#EFD3E7");
            setting.setTheme("Pink");
            setIconColor();
            setAppColor();
            break;
        case "Ocean":
            setting.setIconColor("w");
            setting.setMenuColorSelected("#66a5ad");
            setting.setMenuColorUnselected("#07575b");
            setting.setPageColor("");
            setting.setFontColor("black");
            setting.setAppColor("#c4dfe6");
            setting.setTheme("Ocean");
            setIconColor();
            setAppColor();
            break;
        case "Blueberry":
            setting.setIconColor("w");
            setting.setMenuColorSelected("#4d648d");
            setting.setMenuColorUnselected("#283655");
            setting.setPageColor("");
            setting.setFontColor("black");
            setting.setAppColor("#d0e1f9");
            setting.setTheme("Blueberry");
            setIconColor();
            setAppColor();
            break;
    }   
    JAXBContext jaxbContext;
        try {
            jaxbContext = JAXBContext.newInstance(Settings.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(setting, new File(login.getUsername()+"/settings.xml"));
        } catch (JAXBException ex) {
            Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
}
// </editor-fold>
    
// <editor-fold defaultstate="collapsed" desc="Ler Entradas e Settings">    
   private void LerEntradas() throws JAXBException
    {
        // Cria um JAXBContext e Unmarshaller que serão usados para ler todas as Entradas do Diário para a lista Entradas.getEntradas()
        JAXBContext jaxbContext = JAXBContext.newInstance(Entradas.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        try{
            Entradas = (Entradas) jaxbUnmarshaller.unmarshal( new File(login.getUsername()+"/diario.xml") );
        }
        catch(Exception ex){
            
        }
        
        // Cria um JAXBContext e Unmarshaller que serão usados para ler os Settings para a variável setting
        jaxbContext = JAXBContext.newInstance(Settings.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();

        try{
            setting = (Settings) jaxbUnmarshaller.unmarshal( new File(login.getUsername()+"/settings.xml") );
        }
        
        
        //Caso falhe (o ficheiro não exista) põe os valores default
        catch(Exception ex){
            setting.setFamily("Arial");
            setting.setFontPageColor("#030303");
            setting.setSize("20.0");
            setting.setIconColor("b");
            setting.setMenuColorSelected("#1995ad");
            setting.setFontColor("black");
            setting.setTheme("Default");
            setting.setMenuColorUnselected("#bcbabe");
            setting.setPageColor("");
            setting.setAppColor("#f1f1f2");
        }
        
        
        jaxbContext = JAXBContext.newInstance(Login.class);
        jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        
        try{
            login = (Login) jaxbUnmarshaller.unmarshal( new File(login.getUsername()+"/login.xml") );
        }
        catch(Exception ex){
            
        }
    }
// </editor-fold>

// <editor-fold defaultstate="collapsed" desc="Restaurares">    
public void RestaurarFont(){
    setting.setFamily("Arial");
    setting.setFontPageColor("#030303");
    setting.setSize("20.0");
    
    cbFontFamily.getSelectionModel().select(setting.getFamily());
    cbFontSize.getSelectionModel().select(setting.getSize());
    clpFontCor.setValue(Color.valueOf("#030303"));
    
    //Mete as páginas iniciais de acordo com os settings ("Arial" e "20.0" default)
    txtPag1.setFont(Font.font(setting.getFamily(),Double.valueOf(setting.getSize())));
    txtPag2.setFont(Font.font(setting.getFamily(),Double.valueOf(setting.getSize())));
     
    //Cor da letra das páginas
    txtPag1.setStyle("-fx-text-fill:#030303");
    txtPag2.setStyle("-fx-text-fill:#030303");
    
    //Guarda as mudanças no xml dos settings
    JAXBContext jaxbContext;
    try {
            jaxbContext = JAXBContext.newInstance(Settings.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            jaxbMarshaller.marshal(setting, new File(login.getUsername()+"/settings.xml"));
    } catch (JAXBException ex) {
            Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
    }
}

public void RestaurarPesquisa(){
    dtpSearchInicio.setValue(null);
    dtpSearchFim.setValue(null);
    btnLock2.setVisible(true);
    btnLeftArrow.setVisible(true);
    btnRightArrow.setVisible(true);
    txtSearchPalavra.setText("");
    setEntradas(LocalDate.now().minusDays(1),LocalDate.now());
    btnLeftArrow.setOnAction(event -> Arrow(true));
    btnRightArrow.setOnAction(event -> Arrow(false));
    lvPesquisa.getItems().clear();
    lvPesquisa.setVisible(false);
    try {
        if (lock2){
            lock2 = false;
            ImageLock(false);
        }
        else{
            lock2 = true;
            ImageLock(false);
        }
    } catch (MalformedURLException ex) {
        Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
    }
    
}
// </editor-fold>  

public void onExit(){
    if(pag1Text || pag2Text){
        String[] options = {"Sim","Não"};
        int reply = JOptionPane.showOptionDialog(null, "Foram detetadas mudanças na página, deseja guardar antes de sair? ", "Atenção", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if (reply == JOptionPane.YES_OPTION) {
            GuardarClick();
        }
    }
}

public void Arrow(boolean left){
    if(left){
        if(pag1Text || pag2Text){
            String[] options = {"Sim","Não"};
            int reply = JOptionPane.showOptionDialog(null, "Foram detetadas mudanças na página, deseja guardar? ", "Atenção", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if (reply == JOptionPane.YES_OPTION) {
              GuardarClick();
            }
        }
        setEntradas(lbl1.minusDays(1),lbl2.minusDays(1));
    }
    else{
        if(pag1Text || pag2Text){
            String[] options = {"Sim","Não"};
            int reply = JOptionPane.showOptionDialog(null, "Foram detetadas mudanças na página, deseja guardar?", "Atenção", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
            if (reply == JOptionPane.YES_OPTION) {
              GuardarClick();
            }
        }
        setEntradas(lbl1.plusDays(1),lbl2.plusDays(1));
    }
}

private void Logout(){
    String[] options = {"Sim","Não"};
        int reply = JOptionPane.showOptionDialog(null, "Deseja fazer logout? ", "Atenção", JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        if (reply == JOptionPane.YES_OPTION) {
            try {
                FXMLLoader fxmllo =new FXMLLoader(getClass().getResource("Login.fxml"));
                Parent NewRoot = (Parent)fxmllo.load();
                Stage stage = new Stage();
                
                btnLeftArrow.getScene().getWindow().hide();
                
                String image = getClass().getResource("book.jpg").toExternalForm();
                NewRoot.setStyle(
                    "-fx-background-image: url('" + image +"');" +
                    "-fx-background-position: center center;" +
                    "-fx-background-repeat: stretch;");
                Scene scene = new Scene(NewRoot);
                stage.setTitle("O Diário da minha paixão");
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(DiarioController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}

 
   
private void XMLTOPDF () {
        try {
            Document document = new Document();
            FileOutputStream outStream = new FileOutputStream(login.getUsername()+"/Diario.pdf");
            PdfWriter.getInstance(document, outStream);
            
            document.open();
            com.itextpdf.text.Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            
            String aux = "";
            if (entradaPesquisa != null ) {
                entradaPesquisa = ordenarPesquisaData(entradaPesquisa);
                for(EntradaDiario diario : entradaPesquisa){
                    aux ="";
                    aux = aux.concat(diario.getLocalDate() + "\n"+diario.getTexto(login.getChave()) + "\n");
                    Paragraph preface = new Paragraph();
                    preface.add(new Paragraph(aux));
                    document.add(preface);
                    document.newPage();
                }
            }
            else{
                Entradas.setEntrada(ordenarPesquisaData(Entradas.getEntradas()));
                for(EntradaDiario diario : Entradas.getEntradas()){
                    aux ="";
                    aux = aux.concat(diario.getLocalDate() + "\n"+diario.getTexto(login.getChave()) + "\n");
                    Paragraph preface = new Paragraph();
                    preface.add(new Paragraph(aux));
                    document.add(preface);
                    document.newPage();
                }
            }
            
            document.close();
            outStream.close();
            JOptionPane.showMessageDialog(null,"Diário exportado com sucesso.","Guardar em pdf", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (FileNotFoundException ex) {
             JOptionPane.showMessageDialog(null,"Erro ao guardar, tente novamente mais tarde.","Erro", JOptionPane.ERROR_MESSAGE);
        } catch (DocumentException ex) {
             JOptionPane.showMessageDialog(null,"Erro ao guardar, tente novamente mais tarde.","Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) {
             JOptionPane.showMessageDialog(null,"Erro ao guardar, tente novamente mais tarde.","Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

   private void print(){
    String ficheiro = login.getUsername()+"/Diario.pdf";
  
    String printerNameDesired = "HP Deskjet F300 Series";

    PrintService[] services = PrinterJob.lookupPrintServices();
    DocPrintJob docPrintJob = null;
    for (int i = 0; i < services.length; i++) {
      System.out.println(services[i]);
    }   


        try {
          PDDocument pdf = PDDocument.load(new File(ficheiro));
          
          PrinterJob job = PrinterJob.getPrinterJob();
          for (int i = 0; i < services.length; i++) {
           if (services[i].getName().equalsIgnoreCase(printerNameDesired)) {
             docPrintJob = services[i].createPrintJob();
           }
          }

          job.setPrintService(docPrintJob.getPrintService());
          job.setPageable(new PDFPageable(pdf));

          PrintRequestAttributeSet pset = new HashPrintRequestAttributeSet();

      
          pset.add(MediaTray.BOTTOM); 
          pset.add(Sides.DUPLEX);
          job.print(pset);

        } catch (Exception e) {
           JOptionPane.showMessageDialog(null,"Erro ao imprimir, tem o pdf criado ?\n Tente novamente mais tarde.","Erro", JOptionPane.ERROR_MESSAGE);
        }      
      
      }      
  

  }
        
       
        
        

   
