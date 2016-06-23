package com.soellner.photoImpactServer.json;

/**
 * Created by Alex on 23.06.2016.
 */
public class User {
    private String _login;
    private String _password;

    public User() {
    }


    public String getLogin() {
        return _login;
    }

    public void setLogin(String login) {
        _login = login;
    }

    public String getPassword() {
        return _password;
    }

    public void setPassword(String password) {
        _password = password;
    }
}
