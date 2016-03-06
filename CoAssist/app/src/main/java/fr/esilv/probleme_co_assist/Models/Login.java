package fr.esilv.probleme_co_assist.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Charlotte on 02/03/2016.
 */
public class Login {

    @JsonProperty("Item")
    private UserData user;
    private String token;

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
