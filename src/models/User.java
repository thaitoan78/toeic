/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author toanten
 */
public class User {

    private String userid;
    private String mauser;
    private String username;
    private String pass;
    private String firstname;
    private String lastname;
    private String email;

    private String name;

    public User(String userid, String mauser, String username, String pass, String firstname, String lastname, String email, String name) {
        this.userid = userid;
        this.mauser = mauser;
        this.username = username;
        this.pass = pass;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

        this.name = name;
    }

    public User(String mauser, String username, String pass, String firstname, String lastname, String email, String name) {
        this.mauser = mauser;
        this.username = username;
        this.pass = pass;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;

        this.name = name;
    }

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * @return the mauser
     */
    public String getMauser() {
        return mauser;
    }

    /**
     * @param mauser the mauser to set
     */
    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the pass
     */
    public String getPass() {
        return pass;
    }

    /**
     * @param pass the pass to set
     */
    public void setPass(String pass) {
        this.pass = pass;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * @param lastname the lastname to set
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the quyen
     */
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
