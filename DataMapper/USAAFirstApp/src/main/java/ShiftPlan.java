package main.java;

import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;

public class ShiftPlan {

    GridPane gp3 = new GridPane();
    Label siteLbl = new Label ("Select Site");
    Label deptLbl = new Label ("Select Dept");
    Label totalEmpLbl = new Label ("Total Emps: ");
    TextArea totalEmpTxt = new TextArea ("0");
    ComboBox<String> siteBox;
    ComboBox<String> deptBox;

    public GridPane displayForm(){

        String sitePhx3 = "PHX3";
        String sitePhx5 = "PHX5";
        String sitePhx6 = "PHX6";
        String sitePhx7 = "PHX7";


        ComboBox<String> siteBox = new ComboBox<String>();
        siteBox.getItems().addAll(sitePhx3, sitePhx5, sitePhx6, sitePhx7);


        String deptIb = "Inbound";
        String deptAdmin = "Admin";
        String deptICQA = "ICQA";
        String deptOb = "Outbound";
        String deptSupport = "Support";

        ComboBox<String> deptBox = new ComboBox<String>();

        deptBox.getItems().addAll(deptAdmin, deptIb, deptICQA, deptOb, deptSupport);

        Button startReport = new Button("Start");


        gp3.add(siteLbl, 1, 1);
        gp3.add(siteBox, 1, 2);

        gp3.add(deptLbl, 2, 1);
        gp3.add(deptBox, 2, 2);
        gp3.add(startReport, 3, 3);

        return gp3;

    }
    public ShiftPlan(TableView<ObservableList<StringProperty>> table, String csvFileToRead, Boolean hasHeader) {
        DataImporter myData = new DataImporter();
        myData.populateTable(table, csvFileToRead, hasHeader);


    }


}