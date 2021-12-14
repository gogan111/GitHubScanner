package com.it.dreamteam.emailscanner.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ProfileInfo {

    public String picture;

    public String login;

    public String name;

    public String email;

    public String workEmail;

    public Date lastCommit;

}
