/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.imdb;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.imdb.model.Grado;
import it.polito.tdp.imdb.model.Model;
import it.polito.tdp.imdb.model.Movie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnGrandoMax"
    private Button btnGrandoMax; // Value injected by FXMLLoader

    @FXML // fx:id="btnCammino"
    private Button btnCammino; // Value injected by FXMLLoader

    @FXML // fx:id="txtRank"
    private TextField txtRank; // Value injected by FXMLLoader

    @FXML // fx:id="cmbMovie"
    private ComboBox<Movie> cmbMovie; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCammino(ActionEvent event) {
    	Movie movie = this.cmbMovie.getValue();
    	if(movie==null) {
    		this.txtResult.setText("Selezionare un  Film (m).");
    		return;
    	}
    	this.txtResult.setText("Cammino creato.\n");
    	List<Movie> cammino = model.calcolaCammino(movie);
    	for(Movie m : cammino) {
    		this.txtResult.appendText(m+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.cmbMovie.getItems().clear();
    	String input = this.txtRank.getText();
    	if(input=="") {
    		this.txtResult.setText("Inserire un valore per Rank (r).");
    		return;
    	}
    	try {
    		double rank = Double.parseDouble(input);
    		if(rank>=0.0 && rank<=10.0) {
    			model.creaGrafo(rank);
    			this.txtResult.setText("Grafo creato!\n");
    			this.txtResult.appendText("# Vertici: "+model.getVerticiSize()+"\n");
    			this.txtResult.appendText("# Archi: "+model.getArchiSize()+"\n");
    			this.btnGrandoMax.setDisable(false);
    			this.cmbMovie.setDisable(false);
    			this.btnCammino.setDisable(false);
    			this.cmbMovie.getItems().addAll(model.getVertici());
    		}
    		else {    		
    			this.txtResult.setText("Il rank deve essere compreso tra 0.0 e 10.0.");
    			return;
    		}
     	} catch (NumberFormatException e) {
     		this.txtResult.setText("Il valore immesso non è valido.");
     		return;
     	}
    }
    
    @FXML
    void doGradoMax(ActionEvent event) {
    	Grado m = model.getPesoMassimo();
    	this.txtResult.setText("FILM GRADO MASSIMO:\n");
    	this.txtResult.appendText(m.getM().toString()+"("+m.getGrado()+")");
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnGrandoMax != null : "fx:id=\"btnGrandoMax\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCammino != null : "fx:id=\"btnCammino\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtRank != null : "fx:id=\"txtRank\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbMovie != null : "fx:id=\"cmbMovie\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.btnGrandoMax.setDisable(true);
    	this.btnCammino.setDisable(true);
    	this.cmbMovie.setDisable(true);
    	this.txtResult.setEditable(false);
    }
}
