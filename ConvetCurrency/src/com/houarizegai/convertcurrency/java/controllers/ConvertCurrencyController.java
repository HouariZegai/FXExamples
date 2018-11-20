package com.houarizegai.convertcurrency.java.controllers;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.net.URL;
import java.util.ResourceBundle;

public class ConvertCurrencyController implements Initializable {

    @FXML
    private VBox root;

    @FXML
    private JFXTextField fieldMoney;

    @FXML
    private JFXComboBox<String> comboCurreny;

    @FXML
    private Label lblResult;

    private JFXSnackbar toastMsg;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboCurreny.getItems().addAll("EURO", "DZD");
        toastMsg = new JFXSnackbar(root);
    }

    @FXML
    private void onConvert() {
        if(fieldMoney.getText() == null || !fieldMoney.getText().trim().matches("[0-9]+")) {
            toastMsg.show("Please Type Money in Degit !", 2500);
            return;
        }

        if(comboCurreny.getSelectionModel().getSelectedItem() == null) {
            toastMsg.show("Please Select Currency !", 2500);
            return;
        }

        if(comboCurreny.getSelectionModel().getSelectedItem().equalsIgnoreCase("Euro")) {
            lblResult.setText(Integer.parseInt(fieldMoney.getText().trim()) * getEuroValuesFromRest() + " DA");
        }

    }

    private double getEuroValuesFromRest() {
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get("http://data.fixer.io/api/latest")
                    .queryString("access_key", "8245004aec4e53232b7286456e039daf")
                    .asJson();

            JSONObject jsonRoot = new JSONObject(jsonResponse.getBody().toString());

            JSONObject currencyObject = jsonRoot.getJSONObject("rates");
            double dzdValue = currencyObject.getDouble("DZD");

            return dzdValue;
        } catch(UnirestException ure) {
            ure.printStackTrace();
        }

        return 0;
    }
}
