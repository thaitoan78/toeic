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
public class LoaiCauHoi {
     private String maloai;
     private String name;

    public LoaiCauHoi(String maloai, String name) {
        this.maloai = maloai;
        this.name = name;
    }

    /**
     * @return the maloai
     */
    public String getMaloai() {
        return maloai;
    }

    /**
     * @param maloai the maloai to set
     */
    public void setMaloai(String maloai) {
        this.maloai = maloai;
    }

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
