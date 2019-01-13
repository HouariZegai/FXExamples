package com.houarizegai.convertcurrency.java.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.io.File;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ConvertCurrencyController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXTextField fieldMoney;

    @FXML
    private JFXComboBox<String> comboFromCurreny, comboToCurreny;

    @FXML
    private Label lblResult;

    // Like Toast, an error Msg showing in the bottom of view
    private JFXSnackbar toastMsg;

    // JSON contain value of currency
    private JSONObject jsonCurrencyRates;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toastMsg = new JFXSnackbar(root);

        getCurrencyRatesFromRest();
        initComboCurrency();
    }

    private void initComboCurrency() {
        comboFromCurreny.getItems().addAll("Euro");
        comboToCurreny.getItems().addAll("Euro");

        jsonCurrencyRates.keys().forEachRemaining(item -> {
            comboFromCurreny.getItems().add(item);
            comboToCurreny.getItems().add(item);
        });
    }

    @FXML
    private void onConvert() {
        if(fieldMoney.getText() == null || !fieldMoney.getText().trim().matches("[0-9]+[.]?[0-9]*")) {
            toastMsg.show("Please Type Money in Degit !", 2500);
            return;
        }

        if(comboFromCurreny.getSelectionModel().getSelectedItem() == null) {
            toastMsg.show("Please Select From Currency !", 2500);
            return;
        }
        if(comboToCurreny.getSelectionModel().getSelectedItem() == null) {
            toastMsg.show("Please Select To Currency !", 2500);
            return;
        }

        double moneyAfterConvert = convertCurrency(comboFromCurreny.getSelectionModel().getSelectedItem(),
                comboToCurreny.getSelectionModel().getSelectedItem(),
                Double.parseDouble(fieldMoney.getText()));

        lblResult.setText(String.valueOf(moneyAfterConvert));

    }

    private void getCurrencyRatesFromRest() { // Get JSON of currency rates from Rest API
        try {
//            HttpResponse<JsonNode> jsonResponse
//                    = Unirest.get("http://data.fixer.io/api/latest")
//                    .queryString("access_key", "8245004aec4e53232b7286456e039daf")
//                    .asJson();
//
//            JSONObject jsonRoot = new JSONObject(jsonResponse.getBody().toString());

            // Get Path of Project
            Path currentRelativePath = Paths.get("");
            // convert the path to absolute
            String currentAbsolutePath = currentRelativePath.toAbsolutePath().toString();

            String jsonCurrencyRatesPath = currentAbsolutePath + "\\src\\com\\houarizegai\\convertcurrency\\resources\\utils\\currencyRates.json";
            this.jsonCurrencyRates = new JSONObject(readJsonFromFile(jsonCurrencyRatesPath))
                    .getJSONObject("rates");

        } catch(Exception ure) {
            ure.printStackTrace();
        }

    }

    private String readJsonFromFile(String pathname) { // Read json file and convert it to String
        try {
            File file = new File(pathname);

            StringBuilder fileContents = new StringBuilder((int) file.length());

            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    fileContents.append(scanner.nextLine() + System.lineSeparator());
                }
                return fileContents.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private double convertCurrency(String fromCurrency, String toCurrency, double money) {
        double moneyResult = money;

        double moneyInEuro;

        if(fromCurrency.equalsIgnoreCase("EURO")) {
            moneyInEuro = money;
        } else {
             moneyInEuro = money / jsonCurrencyRates.getDouble(fromCurrency);
        }

        if(toCurrency.equalsIgnoreCase("EURO")) {
            moneyResult = moneyInEuro;
        } else {
            moneyResult = moneyInEuro * jsonCurrencyRates.getDouble(toCurrency);
        }

        return moneyResult;
    }
}
