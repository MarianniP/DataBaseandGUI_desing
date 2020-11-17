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
class Antikeimeno {
    private String title,descr,belongs_to;
    public Antikeimeno(String title,String descr,String belongs_to){
        this.title=title;
        this.descr=descr;
        this.belongs_to=belongs_to;
    }
    public String gettitle(){
        return title;
        
    }
    public String getdescr(){
        return descr;
        
    }
    public String getbelongs_to(){
        return belongs_to;
        
    }
    
}
