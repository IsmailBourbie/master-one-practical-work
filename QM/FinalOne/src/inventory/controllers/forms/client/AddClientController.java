package inventory.controllers.forms.client;

import com.jfoenix.controls.JFXSnackbar;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import inventory.controllers.ClientController;
import inventory.database.DBConnection;
import inventory.database.dao.ClientDao;
import inventory.database.dao.MainDao;
import inventory.database.models.Client;
import inventory.database.models.designpatterns.builder.ClientBuilder;
import inventory.utils.regex.ClientRegex;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.net.URL;
import java.util.ResourceBundle;

public class AddClientController implements Initializable {

    @FXML // Parent of all client (root node)
    private VBox root;

    @FXML // Error icons
    private FontAwesomeIconView iconSociete, iconNom, iconPrenom, iconTelephone, iconMobile, iconFax,
            iconEmail, iconType, iconAdresse, iconCodePostal, iconVille, iconPays;

    /* ClientRegex infos */
    @FXML
    private TextField fieldNumClient, fieldSociete, fieldNom, fieldPrenom, fieldTelephone, fieldMobile, fieldFax,
            fieldEmail, fieldType, fieldAdresse, fieldCodePostal, fieldVille, fieldPays;
    @FXML
    private ComboBox<String> comboCivilite;


    @FXML
    private CheckBox tglBtnLivreMemeAdresse, tglBtnFactureMemeAdresse, tglBtnExemptTva;

    @FXML
    private TextArea areaObservations;

    // For show error msg (like: Toast in android)
    private JFXSnackbar toastMsg;

    public static void something(TextField field, FontAwesomeIconView errorIcon, String newValue, String regex) {
        if (newValue != null && !newValue.isEmpty() && !newValue.trim().matches(regex)) {
            field.setStyle("-jfx-un-focus-color: #E00; -jfx-focus-color: #D00;");

        } else {
            field.setStyle("-jfx-un-focus-color: #777; -jfx-focus-color: #0f9d58;");

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialize combo Civilite
        comboCivilite.getItems().addAll("Mr", "Mme", "Melle");

        toastMsg = new JFXSnackbar(root);
        initFieldListener();

        // Initialize Numero client (get autoincrement from db)
        int currentAutoIncrement = MainDao.getCurrentAutoIncrement("Client");
        fieldNumClient.setText(String.valueOf(currentAutoIncrement));
    }

    private void initFieldListener() {
        fieldSociete.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldSociete, iconSociete, newValue, ClientRegex.SOCIETE));
        fieldNom.textProperty().addListener((observable, oldValue, newValue) -> setValidFontRequired(fieldNom, iconNom, newValue, ClientRegex.NOM));
        fieldPrenom.textProperty().addListener((observable, oldValue, newValue) -> setValidFontRequired(fieldPrenom, iconPrenom, newValue, ClientRegex.PRENOM));
        fieldTelephone.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldTelephone, iconTelephone, newValue, ClientRegex.TELEPHONE));
        fieldMobile.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldMobile, iconMobile, newValue, ClientRegex.MOBILE));
        fieldFax.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldFax, iconFax, newValue, ClientRegex.FAX));
        fieldEmail.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldEmail, iconEmail, newValue, ClientRegex.EMAIL));
        fieldType.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldType, iconType, newValue, ClientRegex.TYPE));
        fieldAdresse.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldAdresse, iconAdresse, newValue, ClientRegex.ADRESSE));
        fieldCodePostal.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldCodePostal, iconCodePostal, newValue, ClientRegex.CODE_POSTAL));
        fieldVille.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldVille, iconVille, newValue, ClientRegex.VILLE));
        fieldPays.textProperty().addListener((observable, oldValue, newValue) -> setValidFont(fieldPays, iconPays, newValue, ClientRegex.PAYS));
    }

    private void setValidFont(TextField field, FontAwesomeIconView errorIcon, String newValue, String regex) { // Change the font if not valid or reset color
        something(field, errorIcon, newValue, regex);
    }

    private void setValidFontRequired(TextField field, FontAwesomeIconView errorIcon, String newValue, String regex) {
        if (newValue == null || !newValue.trim().matches(regex)) {
            field.setStyle("-jfx-un-focus-color: #E00; -jfx-focus-color: #D00;");
        } else {
            field.setStyle("-jfx-un-focus-color: #777; -jfx-focus-color: #0f9d58;");
        }
    }

    @FXML
    private void onAdd() { // Add new ClientRegex

        // validate
        if (fieldNom.getText() == null || fieldNom.getText().trim().isEmpty()) {
            fieldNom.setStyle("-jfx-un-focus-color: #E00; -jfx-focus-color: #D00;");

        }
        if (fieldPrenom.getText() == null || fieldPrenom.getText().trim().isEmpty()) {
            fieldPrenom.setStyle("-jfx-un-focus-color: #E00; -jfx-focus-color: #D00;");

        }


        // Using builder design pattern to make client object
        Client client = new ClientBuilder()
                .setSociete(fieldSociete.getText())
                .setCivilite(comboCivilite.getSelectionModel().getSelectedItem())
                .setNomClient(fieldNom.getText().trim())
                .setPrenom(fieldPrenom.getText().trim())
                .setTelephone(fieldTelephone.getText())
                .setMobile(fieldMobile.getText())
                .setFax(fieldFax.getText())
                .setEmail(fieldEmail.getText())
                .setType((fieldType.getText() == null || fieldType.getText().trim().isEmpty()) ? 0 : Integer.parseInt(fieldType.getText()))
                .setAdresse(fieldAdresse.getText())
                .setCodePostal(fieldCodePostal.getText())
                .setVille(fieldVille.getText())
                .setPays(fieldPays.getText())
                .setLivreMemeAdresse(tglBtnLivreMemeAdresse.isSelected())
                .setFactureMemeAdresse(tglBtnFactureMemeAdresse.isSelected())
                .setExemptTva(tglBtnExemptTva.isSelected())
                .setSaisiPar(DBConnection.user)
                .setSaisiLe(new java.util.Date())
                .setAuteurModif(DBConnection.user)
                .setDateModif(new java.util.Date())
                .setObservations(areaObservations.getText())
                .build();

        int status = ClientDao.addclient(client);

        switch (status) {
            case -1:
                toastMsg.show("Erreur de connexion !", 1500);
                break;
            case 0:
                toastMsg.show("Erreur dans l'ajoute de client !", 1500);
                break;
            default: {
                System.out.println("All things done");
                onClear();
                // Initialize Numero Client (get auto increment from db)
                int currentAutoIncrement = MainDao.getCurrentAutoIncrement("Client");
                fieldNumClient.setText(String.valueOf(currentAutoIncrement));
                break;
            }
        }
    }

    @FXML
    private void onClear() { // Clear everything in interface
        fieldSociete.setText(null);
        comboCivilite.getSelectionModel().clearSelection();
        fieldNom.setText(null);
        fieldPrenom.setText(null);
        fieldTelephone.setText(null);
        fieldMobile.setText(null);
        fieldFax.setText(null);
        fieldEmail.setText(null);
        fieldType.setText(null);
        fieldAdresse.setText(null);
        fieldCodePostal.setText(null);
        fieldVille.setText(null);
        fieldPays.setText(null);

        tglBtnLivreMemeAdresse.setSelected(false);
        tglBtnFactureMemeAdresse.setSelected(false);
        tglBtnExemptTva.setSelected(false);

        areaObservations.setText(null);

        fieldNom.setStyle("-jfx-un-focus-color: #777; -jfx-focus-color: #0f9d58;");
        fieldPrenom.setStyle("-jfx-un-focus-color: #777; -jfx-focus-color: #0f9d58;");

    }

    @FXML
    private void onClose() {
        ClientController.dialogClientAdd.close();
    }
}
