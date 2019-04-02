package com.houarizegai.logicaleditor;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class LogicalEditorController implements Initializable {
    @FXML
    private TextArea areaEditor;

    @FXML
    private JFXSlider sliderFontSize;

    @FXML
    private JFXToggleButton tglFontBold, tglFontItalic;

    @FXML
    private JFXColorPicker pickerFontForeground, pickerFontBackground;

    /*
        Symbols:
        not (¬): U+00AC
        and: U+2227
        or: U+2228
        implies (→): U+2192
        propositional logic (↔): U+2194
        ⇒: U+21D2
        Contradiction: U+22A5
        universal (∀): U+2200
        existential (∃): U+2203
        uniqueness (∃!): U+2203 U+0021
        Turnstile(deduction) (⊢): U+22A2
        double turnstile(totologie) (⊨): U+22A8
        exclusive disjunction (⊕): U+2295
        material equivalence (⇔): U+21D4
        (≡): U+2261
    */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initFontAction();
    }

    @FXML
    private void onSymbol(ActionEvent event) {
        areaEditor.appendText(((Button) event.getSource()).getText());
    }

    private void initFontAction() {
        System.out.println(pickerFontBackground.getValue().toString().substring(2, 8));
        sliderFontSize.valueProperty().addListener(e ->
                areaEditor.setStyle("-fx-font-size: " + (int) sliderFontSize.getValue() + "px"));

        tglFontBold.selectedProperty().addListener(e ->
                areaEditor.setStyle("-fx-font-weight: " + (tglFontBold.isSelected() ? "bold" : "100")));

        tglFontItalic.selectedProperty().addListener(e -> // Note: doesn't work
                areaEditor.setStyle("-fx-font-style: " + (tglFontItalic.isSelected() ? "italic" : "normal")));

        pickerFontForeground.valueProperty().addListener(e -> { // Note: doesn't work
            areaEditor.setBackground(new Background(new BackgroundFill(
                    Paint.valueOf(pickerFontBackground.getValue().toString().substring(2, 8)),
                    new CornerRadii(0d),
                    new Insets(0d))));

            //areaEditor.setStyle("-fx-background-color: #" + pickerFontBackground.getValue().toString().substring(2, 8));
        });
    }
}
