package com.example.fmmusic.Model;

public class Users {
    private int idUser;
    private String userName;
    private String fullName;
    private String passWord;
    private String rePass;


    public Users(int idUser, String userName, String fullName, String passWord, String rePass) {
        this.idUser = idUser;
        this.userName = userName;
        this.fullName = fullName;
        this.passWord = passWord;
        this.rePass = rePass;
    }

    public Users() {

    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getRePass() {
        return rePass;
    }

    public void setRePass(String rePass) {
        this.rePass = rePass;
    }
}


