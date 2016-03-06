package fr.esilv.probleme_co_assist.Models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by Charlotte on 29/02/2016.
 */
public class UserData implements Serializable {

    @JsonProperty("first_name")
    private String firstname ;
    @JsonProperty("userID")
    private String UserId;
    @JsonProperty("address")
    private String address;
    @JsonProperty("registerDate")
    private String registerDate;
    @JsonProperty("imgUrl")
    private String image;
    @JsonProperty("password")
    private String pwd;
    @JsonProperty("email")
    private String email_adress;
    @JsonProperty("last_name")
    private String lastname ;


    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }


    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getEmail_adress() {
        return email_adress;
    }

    public void setEmail_adress(String email_adress) {
        this.email_adress = email_adress;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
