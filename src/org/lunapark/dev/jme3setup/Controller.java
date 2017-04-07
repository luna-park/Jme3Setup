package org.lunapark.dev.jme3setup;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Circle;
import org.lunapark.dev.jme3setup.layout.EventListener;

public class Controller implements EventListener {
    public TextField tfName;
    public TextField tfPackage;
    public Button btnGenerate;
    public Button btnQuit;
    public Circle circle;
    public Label lblStatus;

    public void onClick(ActionEvent actionEvent) {
        btnGenerate.setDisable(true);
        String sProjectName = tfName.getText();
        String sName = sProjectName.toLowerCase();
        String sPackage = tfPackage.getText();
//        System.out.println("Generate: " + sPackage + "." + sName);
        new Creator(sProjectName, sPackage, this);
    }

    public void closeApp(ActionEvent actionEvent) {
        System.exit(0);
    }

    @Override
    public void complete() {
        circle.setVisible(true);
    }

    @Override
    public void onEvent(String message) {
        lblStatus.setText(message);
        System.out.println(message);

    }
}
