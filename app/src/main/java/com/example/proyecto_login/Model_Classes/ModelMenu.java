package com.example.proyecto_login.Model_Classes;

public class ModelMenu {

    private String name,id, type, billtype1, billtype2, imgURL;

    public String getImgURL(){
        return imgURL;
    }

    public void setImgURL(String imgURL){
        this.imgURL = imgURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getsetBillType1() {
        return billtype1;
    }

    public void setBillType1(String billtype1) {
        this.billtype1 = billtype1;
    }

    public String getsetBillType2() {
        return billtype2;
    }

    public void setBillType2(String billtype2) {
        this.billtype2 = billtype2;
    }
}


