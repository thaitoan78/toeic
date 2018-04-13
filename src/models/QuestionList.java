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
public class QuestionList {

    private String id;
    private String loaicauhoi;
    private String content;
    private String a;
    private String b;
    private String c;
    private String d;
    private String answer;
    private String name;

    public QuestionList(String id,String loaicauhoi, String content, String a, String b, String c, String d, String answer, String name) {
        this.id = id;
        this.loaicauhoi = loaicauhoi;
        this.content = content;
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.answer = answer;
        this.name=name;
    }
    

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the loaicauhoi
     */
    public String getLoaicauhoi() {
        return loaicauhoi;
    }

    /**
     * @param loaicauhoi the loaicauhoi to set
     */
    public void setLoaicauhoi(String loaicauhoi) {
        this.loaicauhoi = loaicauhoi;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the a
     */
    public String getA() {
        return a;
    }

    /**
     * @param a the a to set
     */
    public void setA(String a) {
        this.a = a;
    }

    /**
     * @return the b
     */
    public String getB() {
        return b;
    }

    /**
     * @param b the b to set
     */
    public void setB(String b) {
        this.b = b;
    }

    /**
     * @return the c
     */
    public String getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(String c) {
        this.c = c;
    }

    /**
     * @return the d
     */
    public String getD() {
        return d;
    }

    /**
     * @param d the d to set
     */
    public void setD(String d) {
        this.d = d;
    }

    /**
     * @return the answer
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * @param answer the answer to set
     */
    public void setAnswer(String answer) {
        this.answer = answer;
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
