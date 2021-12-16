package com.it.dreamteam.emailscanner.service;

import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.IOException;

public class LoginService {

    public GitHub login() {
        try {
            return new GitHubBuilder()
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public GitHub login(String token) throws IOException {
        try {
            return new GitHubBuilder()
                    .withAppInstallationToken(token)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    //todo find out why withPassword is not working
    public GitHub login(String login, String password) {
        try {
            return new GitHubBuilder()
                    .withPassword(login, password)
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
