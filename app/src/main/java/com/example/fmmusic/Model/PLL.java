package com.example.fmmusic.Model;

public class PLL {
    private int  idPLL;
    private String namePll;
    private String idUser;

    public PLL() {
    }

    public PLL(int idPLL, String namePll, String idUser) {
        this.idPLL = idPLL;
        this.namePll = namePll;
        this.idUser = idUser;
    }



    public int getIdPLL() {
        return idPLL;
    }

    public void setIdPLL(int idPLL) {
        this.idPLL = idPLL;
    }

    public String getNamePll() {
        return namePll;
    }

    public void setNamePll(String namePll) {
        this.namePll = namePll;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
