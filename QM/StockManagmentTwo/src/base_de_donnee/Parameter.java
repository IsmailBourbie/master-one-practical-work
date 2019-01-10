
package base_de_donnee;


public class Parameter {

    private final String IPHOST ,HOST_DB ,USERNAME_DB ,PASSWORD_DB ,USER;
    
    public Parameter() {
    this.IPHOST = "127.0.0.1";
    this.HOST_DB = "jdbc:mysql://"+IPHOST+ "/gestion" ;
    this.USERNAME_DB = "root";
    this.PASSWORD_DB = "";
    this.USER="";
    }

    public String getHOST_DB() {
        return HOST_DB;
    }

    public String getUSERNAME_DB() {
        return USERNAME_DB;
    }

    public String getPASSWORD_DB() {
        return PASSWORD_DB;
    }

    public String getUSER() {
        return USER;
    }
    
}
