package com.houarizegai.dashboard;

import com.jfoenix.controls.JFXDrawer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    @FXML // menu
    private JFXDrawer drawerMenu;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initMenu();
    }

    private void initMenu() {
        VBox menuPane = null;
        try {
            menuPane = FXMLLoader.load(getClass().getResource("/com/houarizegai/dashboard/menu/Menu.fxml"));
        } catch(IOException ioe) {
            ioe.printStackTrace();
        }

        drawerMenu.setSidePane(menuPane);

        for (Node node : menuPane.getChildren()) {
            if (node.getAccessibleText() != null) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    switch (node.getAccessibleText()) {
                        case "Profile" :
                            System.out.println("Profile Clicked !");
                            break;
                        case "Settings" :
                            System.out.println("Settings Clicked !");
                            break;
                        case "Report" :
                            System.out.println("Report Clicked !");
                            break;
                        case "Logout" :
                            System.out.println("Logout Clicked !");
                            break;
                    }
                });
            }
        }
    }

    @FXML
    private void onMenu() {
        if (drawerMenu.isShown())
            drawerMenu.close();
        else
            drawerMenu.open();
    }
}
