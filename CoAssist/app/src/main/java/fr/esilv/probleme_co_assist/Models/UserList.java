package fr.esilv.probleme_co_assist.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import fr.esilv.probleme_co_assist.Models.UserData;

/**
 * Created by Charlotte on 05/03/2016.
 */
public class UserList {

    @JsonProperty("Items")
    UserData[] user ;
    @JsonProperty("Count")
    int count;
    @JsonProperty("ScannedCount")
    int scannedCount;

    public UserData[] getUser() {
        return user;
    }

    public void setUser(UserData[] user) {
        this.user = user;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getScannedCount() {
        return scannedCount;
    }

    public void setScannedCount(int scannedCount) {
        this.scannedCount = scannedCount;
    }
}