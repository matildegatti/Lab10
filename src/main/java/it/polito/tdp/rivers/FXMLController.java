/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Informazioni;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void handleFiume(ActionEvent event) {
    	this.txtEndDate.clear();
    	this.txtFMed.clear();
    	this.txtNumMeasurements.clear();
    	this.txtStartDate.clear();
    	
    	River r=this.boxRiver.getValue();
    	if(r==null) {
    		txtResult.appendText("Selezionare un fiume");
    	}
    	
    	Informazioni info=this.model.getInformazioni(r.getId());
    	
    	this.txtStartDate.setText(info.getMindata().toString());
    	this.txtEndDate.setText(info.getMaxdata().toString());
    	this.txtNumMeasurements.setText(""+info.getTot());
    	this.txtFMed.setText(""+info.getMedia());
    }

    @FXML
    void handleSimula(ActionEvent event) {
    	float k;
    	try {
    		k=Float.parseFloat(this.txtK.getText());
    	}catch(NumberFormatException e) {
    		txtResult.setText("Inserire un numero");
    		return;
    	}
    	
    	River r=this.boxRiver.getValue();
    	if(r==null) {
    		txtResult.appendText("Selezionare un fiume");
    	}
    	
    	Informazioni info=this.model.getInformazioni(r.getId());
    	
    	int numOsservazioni=Integer.parseInt(this.txtNumMeasurements.getText());
    	float fmedia=Float.parseFloat(this.txtFMed.getText());
    	LocalDate datainizio=LocalDate.parse(this.txtStartDate.getText());
    	LocalDate datafine=LocalDate.parse(this.txtEndDate.getText());
    	
    	this.model.getSimulation(k, r.getId(), fmedia, datainizio, datafine);
    	
    	this.txtResult.setText("Il numero di giorni in cui non si è potuta garantire l'erogazione minima è: "+model.getNgiornifailure()+", \n l'occupazione media del bacino è stata: "+model.getCsomma()/numOsservazioni);
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	List<River> fiumi=new LinkedList<>(this.model.getFiumi());
    	this.boxRiver.getItems().addAll(fiumi);
    }
}
