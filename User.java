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
class User {
    private String usrname,password,name,surname;
    
    public User(String usrname,String password,String name,String surname){
        this.usrname=usrname;
        this.password=password;
        this.name=name;
        this.surname=surname;
    }
    public String getusrname(){
        return usrname;
        
    }
    public String getpassword(){
        return password;
        
    }
    public String getname(){
        return name;
        
    }
    public String getsurname(){
        return surname;
        
    }
}
  
