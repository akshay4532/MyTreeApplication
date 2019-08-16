package com.example.mytreeapplication.model;

public class Data {
    private String Uname;
    private String Umob;
    private String Umail;
    private String Upas;

    public Data() {


    }

    public Data(String uname, String umob, String umail, String upas) {
        Uname = uname;
        Umob = umob;
        Umail = umail;
        Upas = upas;
    }

    @Override
    public String toString() {
        return "Data{" +
                "Uname='" + Uname + '\'' +
                ", Umob='" + Umob + '\'' +
                ", Umail='" + Umail + '\'' +
                ", Upas='" + Upas + '\'' +
                '}';
    }

    public String getUname() {
        return Uname;
    }

    public void setUname(String uname) {
        Uname = uname;
    }

    public String getUmob() {
        return Umob;
    }

    public void setUmob(String umob) {
        Umob = umob;
    }

    public String getUmail() {
        return Umail;
    }

    public void setUmail(String umail) {
        Umail = umail;
    }

    public String getUpas() {
        return Upas;
    }

    public void setUpas(String upas) {
        Upas = upas;
    }
}
