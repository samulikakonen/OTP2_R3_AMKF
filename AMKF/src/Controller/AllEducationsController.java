/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.AMKFKone;
import model.LanguageSelection;

/**
 * FXML Controller class
 *
 * @author Samuli Käkönen
 */
public class AllEducationsController extends Template implements Initializable {

    AMKFKone kone;
    
    @FXML
    Button closeButton;
    @FXML
    Button kyselyBtn;
    @FXML
    Button yhteystiedotBtn;
    @FXML
    Button koulutuksetBtn;

    @FXML
    private BorderPane borderPane;
    @FXML
    private ScrollPane scrollPane;

    private LanguageSelection languageSelection;
    private ResourceBundle messages;
    
    String[] educations;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        kone = new AMKFKone();

        listAllEducations();
    }

    /**
     * Creates ListView for all educations in database
     */
    public void listAllEducations() {
        ListView list = new ListView();
        educations = kone.getKoulutukset();

        for (String education : educations) {
            Text txt = new Text(education);
            list.getItems().add(txt);
        }

        list.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                listItemHandler(list, educations);
            }
        });

        //list.getItems().add(educations);
        borderPane.setCenter(list);
        scrollPane.setContent(borderPane);
        
    }
    
    /**
     * Runs when ListView Object is clicked
     * @param list ListViewl
     * @param educations String array of educations in database
     */
    private void listItemHandler(ListView list,String[] educations) {
        Text txt = (Text) list.getSelectionModel().getSelectedItem();
        txt.setWrappingWidth(700);
        String origTxt = educations[list.getSelectionModel().getSelectedIndex()];
        if (!txt.getText().contains("Koulut:")) {
            String schools = "\nKuvaus: " + kone.getKuvaus(educations[list.getSelectionModel().getSelectedIndex()]) +  "\nKoulut:";
            for (String school : kone.getKoulutForKoulutus(origTxt)) {
                schools += "\n>" + school;
            }
            txt.setText(txt.getText() + schools);
        }else {
            txt.setText(origTxt);
        }
    }

    @Override
    public void updateGUI() {
        //System.out.println(messages.getString("shutdown"));
        closeButton.setText(messages.getString("shutdown"));
        kyselyBtn.setText(messages.getString("questions"));
        koulutuksetBtn.setText(messages.getString("educations"));
        yhteystiedotBtn.setText(messages.getString("contactinfo"));
    }

    /**
     * Sulkee ohjelman
     */
    @FXML
    @Override
    public void closeButtonAction() {
        kone.resetPisteet();
        kone.sulje();
        System.out.println("Tietokantayhteys suljettu");
        // get a handle to the stage
        Stage stage = (Stage) closeButton.getScene().getWindow();

        // do what you have to do
        stage.close();

    }

}
