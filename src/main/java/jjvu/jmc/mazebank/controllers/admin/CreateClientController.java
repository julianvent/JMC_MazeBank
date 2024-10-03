package jjvu.jmc.mazebank.controllers.admin;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import jjvu.jmc.mazebank.models.Model;

import java.net.URL;
import java.time.LocalDate;
import java.util.Random;
import java.util.ResourceBundle;

public class CreateClientController implements Initializable {
    public TextField firstNameField;
    public TextField lastNameField;
    public TextField passwordField;
    public CheckBox payeeAddressBox;
    public Label payeeAddressLabel;
    public CheckBox addCheckingAccountBox;
    public TextField checkingAmountField;
    public CheckBox addSavingsAccountBox;
    public TextField savingsAmountField;
    public Button createClientButton;
    public Label errorLabel;

    private String payeeAddress;
    private boolean createCheckingAccountFlag = false;
    private boolean createSavingsAccountFlag = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        createClientButton.setOnAction(actionEvent -> createClient());

        payeeAddressBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) {
                payeeAddress = createPayeeAddress();
                onCreatePayeeAddress();
            }
        });

        addCheckingAccountBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) {
                createCheckingAccountFlag = true;
            }
        });

        addSavingsAccountBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> {
            if (newValue) {
                createSavingsAccountFlag = true;
            }
        });
    }

    private void createClient() {
        // Create Checking Account
        if (createCheckingAccountFlag) {
            createAccount("Checking");
        }

        // Create Savings Account
        if (createSavingsAccountFlag) {
            createAccount("Savings");
        }

        // Create Client
        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String password = passwordField.getText();
        Model.getInstance().getDatabaseDriver().createClient(firstName, lastName, payeeAddress, password, LocalDate.now());
        errorLabel.setStyle("-fx-text-fill: blue; -fx-font-size: 1.5em; -fx-font-weight: bold");
        errorLabel.setText("Client Created Successfully");
        emptyFields();
    }

    private void createAccount(String accountType) {
        double balance = Double.parseDouble(checkingAmountField.getText());
        // Generate Account Number
        String firstSection = "3201";
        String lastSection = Integer.toString(new Random().nextInt(9999) + 1000);
        String accountNumber = firstSection + " " + lastSection;

        // Create the checking or savings account
        if (accountType.equals("Checking")) {
            Model.getInstance().getDatabaseDriver().createCheckingAccount(payeeAddress, accountNumber, 10, balance);
        } else {
            Model.getInstance().getDatabaseDriver().createSavingsAccount(payeeAddress, accountNumber, 2000, balance);
        }
    }

    private void onCreatePayeeAddress() {
        if (firstNameField.getText() != null && lastNameField != null) {
            payeeAddressLabel.setText(payeeAddress);
        }
    }

    private String createPayeeAddress() {
        // payeeAddress format = @+firstNameChar+LastName+SequenceID
        int id = Model.getInstance().getDatabaseDriver().getLastClientID() + 1;
        char firstNameChar = Character.toLowerCase(firstNameField.getText().charAt(0));
        return "@" + firstNameChar + lastNameField.getText() + id;
    }

    private void emptyFields() {
        firstNameField.setText("");
        lastNameField.setText("");
        passwordField.setText("");
        payeeAddressBox.setSelected(false);
        payeeAddressLabel.setText("");
        addCheckingAccountBox.setSelected(false);
        checkingAmountField.setText("");
        addSavingsAccountBox.setSelected(false);
        savingsAmountField.setText("");
    }
}
