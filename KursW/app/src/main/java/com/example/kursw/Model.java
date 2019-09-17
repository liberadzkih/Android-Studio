package com.example.kursw;

public class Model {
    private String currencyID, currencyFullName;
    private String kursSredniValue, kursSprzedazyValue, kursKupnaValue;

    Model(){}

    Model(String currencyID, String currencyFullName, String kursSredniValue, String kursSprzedazyValue, String kursKupnaValue){
        this.kursKupnaValue = kursKupnaValue;
        this.currencyID = currencyID;
        this.currencyFullName = currencyFullName;
        this.kursSredniValue = kursSredniValue;
    }

    public String getCurrencyID() {
        return currencyID;
    }

    public void setCurrencyID(String currencyID) {
        this.currencyID = currencyID;
    }

    public String getCurrencyFullName() {
        return currencyFullName;
    }

    public void setCurrencyFullName(String currencyFullName) {
        this.currencyFullName = currencyFullName;
    }

    public String getKursSredniValue() {
        return kursSredniValue;
    }

    public void setKursSredniValue(String kursSredniValue) {
        this.kursSredniValue = kursSredniValue;
    }

    public String getKursSprzedazyValue() {
        return kursSprzedazyValue;
    }

    public void setKursSprzedazyValue(String kursSprzedazyValue) {
        this.kursSprzedazyValue = kursSprzedazyValue;
    }

    public String getKursKupnaValue() {
        return kursKupnaValue;
    }

    public void setKursKupnaValue(String kursKupnaValue) {
        this.kursKupnaValue = kursKupnaValue;
    }
}
