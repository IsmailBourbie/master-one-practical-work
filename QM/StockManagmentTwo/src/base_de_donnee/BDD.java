
package base_de_donnee;
import java.sql.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BDD {
    
    // Les declarations

    Connection connection;
    Statement statement;
    String SQL;
    String url;
    String username;
    String password;
    String Host;
// constructure
    public BDD(){
        Parameter prm=new Parameter();
        this.url = prm.getHOST_DB();
        this.username = prm.getUSERNAME_DB();
        this.password = prm.getPASSWORD_DB();
        connexionDatabase();
    }
//
    private Connection connexionDatabase() {
        
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                Logger.getLogger(BDD.class.getName()).log(Level.SEVERE, null, ex);
            }
        return connection;
    }
//
    public Connection closeconnexion(){
        try {
            connection.close();
        } catch (Exception e) {
            System.err.println(e);
        }
        return connection;
    }
//
    public ResultSet exécutionQuery(String sql){

        connexionDatabase();
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            System.out.println(sql);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return resultSet;
    }
//
    public String exécutionUpdate(String sql){

        connexionDatabase();
        String result = "";
        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);
            result = sql;
        } catch (SQLException ex) {
            result = ex.toString();
        }
        return result;
    }
//
    public ResultSet querySelectAll(String nomTable){

        connexionDatabase();
        SQL = "SELECT * FROM "+ nomTable;
        return this.exécutionQuery(SQL);
    }
//
    public ResultSet querySelectAll(String nomTable, String état){

        connexionDatabase();
       
        SQL = "SELECT * FROM "+ nomTable +" WHERE "+ état; System.out.println(SQL);
        return this.exécutionQuery(SQL);
    }
//
    public ResultSet querySelect(String[] nomColonne, String nomTable){

        connexionDatabase();
        int i;
        SQL = "SELECT ";
        for (i = 0;  i <= nomColonne.length - 1 ; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }
        SQL += " FROM " + nomTable;
        return this.exécutionQuery(SQL);
    }
//
    public ResultSet fcSelectCommand(String[] nomColonne, String nomTable, String état){

        connexionDatabase();
        int i;
        SQL = "SELECT ";
        for (i = 0;  i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length-1) {
                SQL += ",";
            }
        }
        SQL += " FROM " + nomTable + " WHERE " + état ;
        System.out.println(SQL);
        return this.exécutionQuery(SQL);
    }
//
    public String queryInsert(String nomTable, String[] contenuTableau){

        connexionDatabase();
        int i;
        SQL = "INSERT INTO " + nomTable + " VALUES(";
        for (i = 0;  i <= contenuTableau.length - 1; i++) {
            SQL += "'" + contenuTableau[i] + "'";
            if (i < contenuTableau.length - 1) {
                SQL += ",";
            }
        }
        SQL += ")" ;
        return this.exécutionUpdate(SQL);
    }
//
    public String queryInsert(String nomTable, String[] nomColonne, String[] contenuTableau){

        connexionDatabase();
        int i;
        SQL = "INSERT INTO " + nomTable + "(";
        for (i = 0;  i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i];
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }
        SQL +=") VALUES(";
        for (i = 0;  i <= contenuTableau.length - 1; i++) {
            SQL += " '" + contenuTableau[i] + "' ";
            if (i < contenuTableau.length - 1) {
                SQL += ",";
            }
        }
        SQL += " ) " ;
    
        return this.exécutionUpdate(SQL);
    }
 //
    public String queryUpdate(String nomTable, String[] nomColonne, String[] contenuTableau, String état){

        connexionDatabase();
        int i;
        SQL = "UPDATE "+ nomTable +" SET ";
        for (i = 0;  i <= nomColonne.length - 1; i++) {
            SQL += nomColonne[i] + "='" + contenuTableau[i] + "'";
            if (i < nomColonne.length - 1) {
                SQL += ",";
            }
        }
        SQL +=" WHERE " + état;

        return this.exécutionUpdate(SQL);
    }
    public String queryUpdate(String nomTable, String nomColonne, String contenu, String état){

        connexionDatabase();
        SQL = "UPDATE "+ nomTable +" SET "+nomColonne+ " = '" + contenu + "' WHERE " + état;
        System.out.println(SQL);
        return this.exécutionUpdate(SQL);
    }
 //
    public String queryDelete(String nomTable){
        connexionDatabase();
        SQL = "DELETE FROM " + nomTable;
        return this.exécutionUpdate(SQL);
    }
 //
    public String queryDelete(String nomTable, String état){
        connexionDatabase();
        SQL = "DELETE FROM " + nomTable + " WHERE " + état;
        return this.exécutionUpdate(SQL);
    }
    
    public ResultSet innerSelect(String sql){
        connexionDatabase();
        return this.exécutionQuery(sql);
    }
    public ResultSet innerSelect(String col,String[] table,String cond){
        connexionDatabase();
        String sql = "Select "+col+" from "+table[0]+" inner join "+table[1]+" on "+cond;
        return this.exécutionQuery(sql);
    }
}//fin
