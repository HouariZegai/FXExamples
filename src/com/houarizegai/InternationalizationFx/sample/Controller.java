package com.houarizegai.InternationalizationFx.sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Label fieldUsername, fieldPassword;

    ResourceBundle bundle;
    Locale locale;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void setLangAR() {
        loadLang("ar");
    }

    @FXML
    private void setLangEN() {
        loadLang("en");
    }

    @FXML
    private void setLangFR() {
        loadLang("fr");
    }

    private void loadLang(String lang) {
        locale = new Locale(lang);
        bundle = ResourceBundle.getBundle("com.houarizegai.InternationalizationFx.sample.lang", locale);

        fieldUsername.setText(toUTF(bundle.getString("username")));
        fieldPassword.setText(toUTF(bundle.getString("password")));
    }

    private String toUTF(String val) {
        try {
            return new String(val.getBytes("ISO-8859-1"), "UTF-8");
        } catch(Exception ex) {

        }
        return null;
    }

}
