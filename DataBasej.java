package database;

import java.sql.*; 

public class DataBasej {
    
    public static void main (String[] args) throws SQLException {
        
        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRs = null;
        
        String user = "root";
        
        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost/datab_etaireia?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", user,null);
            
            myStmt = myConn.createStatement();
            
            myRs = myStmt.executeQuery("Select * from user");
            
            while (myRs.next()) {
                System.out.println(myRs.getString("username") + "," +myRs.getString("name"));
            }
        }catch (Exception exc) {
            exc.printStackTrace();
        } 
        finally {
            if (myRs != null){
                myRs.close();
            }
            
            if (myStmt != null){
                myStmt.close();
            }
            
            if (myConn != null) {
                myConn.close();
            }
        }
    }
    
}