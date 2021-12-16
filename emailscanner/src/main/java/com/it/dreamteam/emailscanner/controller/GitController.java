package com.it.dreamteam.emailscanner.controller;

import com.it.dreamteam.emailscanner.Starter;
import com.it.dreamteam.emailscanner.model.ProfileInfo;
import com.it.dreamteam.emailscanner.service.GitHubService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;

import java.time.LocalDateTime;

public class GitController implements ControlledScreen {
    private ScreensController screensController;

    @FXML
    public Button logoutButton;
    @FXML
    public Label errorLabel;
    @FXML
    public Pane pane = new Pane();
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
    public TableColumn<ProfileInfo, LocalDateTime> date;
    @FXML
    TableView<ProfileInfo> table = new TableView<>();

    private final GitHubService service = new GitHubService();
    private final ObservableList<ProfileInfo> people = FXCollections.observableArrayList();

    @FXML
    protected void onScanButtonClick() {
        errorLabel.setVisible(false);
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        login.setCellValueFactory(new PropertyValueFactory<>("login"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        workEmail.setCellValueFactory(new PropertyValueFactory<>("workEmail"));
        date.setCellValueFactory(new PropertyValueFactory<>("lastCommit"));

        if (!textField.getText().isEmpty()) {
            ProfileInfo user = null;
            try {
                user = service.findProfileInfo(textField.getText());
            } catch (Exception e) {
                errorLabel.setText(e.getMessage());
                errorLabel.setVisible(true);
            }
            if (user != null) {
                people.removeAll();
                people.add(user);
            }
            table.setItems(people);

//            ProfileInfo profileInfo = table.getItems().get(0);
//            System.out.println(profileInfo.getName());
        }
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }

    @FXML
    protected void onLogoutButtonClick() {
        screensController.setScreen(Starter.loginScreen);
    }

}
