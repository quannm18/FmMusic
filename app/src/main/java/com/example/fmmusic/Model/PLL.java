package com.example.fmmusic.Model;

public class PLL {
    private String  idPLL;
    private String namePll;
    private int idUser;

    public PLL() {
    }

    public PLL(String idPLL, String namePll, int idUser) {
        this.idPLL = idPLL;
        this.namePll = namePll;
        this.idUser = idUser;
    }

    public String getIdPLL() {
        return idPLL;
    }

    public void setIdPLL(String idPLL) {
        this.idPLL = idPLL;
    }

    public String getNamePll() {
        return namePll;
    }

    public void setNamePll(String namePll) {
        this.namePll = namePll;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
