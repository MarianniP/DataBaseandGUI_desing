/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;

/**
 *
 * @author Μαριάννα
 */
class Tomeas {
    private String title,text,belongs_to;
    public Tomeas(String title,String text,String belongs_to){
    this.title=title;
    this.text=text;
    this.belongs_to=belongs_to;
    }
    public String gettitle(){
        return title;
        
    }
    public String gettext(){
        return text;
        
    }
    public String getbelongs_to(){
        return belongs_to;
        
    }
}
