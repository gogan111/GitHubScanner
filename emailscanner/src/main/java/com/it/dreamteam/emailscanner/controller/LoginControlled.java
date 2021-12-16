package com.it.dreamteam.emailscanner.controller;

import com.it.dreamteam.emailscanner.Starter;
import com.it.dreamteam.emailscanner.service.GitHubService;
import com.it.dreamteam.emailscanner.service.LoginService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginControlled implements ControlledScreen {

    private ScreensController screensController;

    private final LoginService service = new LoginService();

    @FXML
    public Button generateTokenButton;
    @FXML
    public Button loginButton;
    @FXML
    public Button anonymousButton;
    @FXML
    public TextField tokenField;

    @FXML
    protected void onLoginButtonClick() {
        //валидация
        if (!tokenField.getText().isEmpty()) {

            try {
                GitHubService.github = service.login(tokenField.getText());
                screensController.setScreen(Starter.mainScreen);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("wrong data");
            }
        } else {
            System.out.println("wrong data");
        }
    }

    @FXML
    protected void onAnonymousButtonClick() {
        GitHubService.github = service.login();
        screensController.setScreen(Starter.mainScreen);
    }

    @FXML
    protected void onGenerateButtonClick() {
        screensController.getHostServices().showDocument("https://github.com/settings/tokens/new");
    }

    @Override
    public void setScreenParent(ScreensController screenParent) {
        this.screensController = screenParent;
    }
}
