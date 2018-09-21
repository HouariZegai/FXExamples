package com.houarizegai.pgcd;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable {
    @FXML
    private VBox root;
    @FXML
    private JFXTextField fieldFirst, fieldSecond;
    @FXML
    private Label lblResult;

    private JFXSnackbar toastMsg;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        toastMsg = new JFXSnackbar(root);
    }

    @FXML
    private void onCalc() {
        if(fieldFirst.getText() == null || fieldFirst.getText().trim().isEmpty()) {
            toastMsg.show("First Number is Empty !", 2000);
            return;
        }
        if(!fieldFirst.getText().trim().matches("[0-9]+")) {
            toastMsg.show("First Number is not a number !", 2000);
            return;
        }

        if(fieldFirst.getText().trim().length() > 18) {
            toastMsg.show("First Number is Bigger !", 2000);
            return;
        }

        if(fieldSecond.getText() == null || fieldSecond.getText().trim().isEmpty()) {
            toastMsg.show("Second Number is Empty !", 2000);
            return;
        }
        if(!fieldSecond.getText().trim().matches("[0-9]+")) {
            toastMsg.show("Second Number is not a number !", 2000);
            return;
        }

        if(fieldSecond.getText().trim().length() > 18) {
            toastMsg.show("Second Number is Bigger !", 2000);
            return;
        }

        long num1 = Long.parseLong(fieldFirst.getText().trim());
        long num2 = Long.parseLong(fieldSecond.getText().trim());

        lblResult.setText(String.valueOf(getPgcd(num1, num2)));
    }

    @FXML
    private void onClear() {
        fieldFirst.setText(null);
        fieldSecond.setText(null);
        lblResult.setText(null);
    }

    private long getPgcd(long a, long b) {

        long r,q=0;

        do {
            r = a % b;
            q = (a-r) / b;
            if (r==0)
                break;
            a = b;
            b = r;
        } while(r != 0);

        return b;
    }

}
