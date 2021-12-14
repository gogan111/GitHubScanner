package com.it.dreamteam.emailscanner.controller;

import com.it.dreamteam.emailscanner.model.ProfileInfo;
import com.it.dreamteam.emailscanner.service.GitHubJob;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.Date;

public class GitController {
    @FXML
    public TextField textField;
    @FXML
    public TableColumn<ProfileInfo, String> name;
    @FXML
    public TableColumn<ProfileInfo, String> login;
    @FXML
    public TableColumn<ProfileInfo, String> email;
    @FXML
    public TableColumn<ProfileInfo, String> workEmail;
    @FXML
    public TableColumn<ProfileInfo, Date> date;

    @FXML
    TableView<ProfileInfo> table = new TableView<>();

    private final GitHubJob job = new GitHubJob();

    @FXML
    protected void onScanButtonClick() {
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        workEmail.setCellValueFactory(new PropertyValueFactory<>("workEmail"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));


        if (!textField.getText().isEmpty()) {
            ObservableList<ProfileInfo> people = FXCollections.observableArrayList();
            ProfileInfo user = job.findProfileInfo(textField.getText());

            people.add(user);
            table.setItems(people);

            ProfileInfo profileInfo = table.getItems().get(0);
            System.out.println(profileInfo.getName());
        }

    }
}