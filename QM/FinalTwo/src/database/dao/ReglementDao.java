package database.dao;

import database.DBConnection;
import database.models.ModeReglement;
import database.models.Reglement;
import utils.UsefulMethods;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class ReglementDao {
    public static List<ModeReglement> getModeReglements() {

        String sql = "SELECT * FROM ModeReglement;";

        List<ModeReglement> modeReglements = new LinkedList<>();
        try {
            Statement st = DBConnection.con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                modeReglements.add(new ModeReglement(rs.getInt("IDModeReglement"), rs.getString("LibModeReglement")));
            }

        } catch (SQLException se) {
            System.out.println("Get Mode ReglementDao Error SQL");
            se.printStackTrace();
            return null;
        }
        return modeReglements;
    }

    public static int addReglement(Reglement reglement) {
        if(DBConnection.con == null)
            return -1;

        String sql = "INSERT INTO `reglement`(`DateReglement`, `IDModeReglement`, `NumFacture`, `SaisiPar`, `SaisiLe`, `Observations`) VALUES (?, ?, ?, ?, ?, ?);";

        try {
            PreparedStatement prest = DBConnection.con.prepareStatement(sql);
            prest.setDate(1, UsefulMethods.getSQLDate(reglement.getDateReglement()));
            prest.setInt(2, reglement.getIdModeReglement());
            prest.setInt(3, reglement.getNumFacture());
            prest.setString(4, reglement.getSaisiPar());
            prest.setDate(5, UsefulMethods.getSQLDate(reglement.getSaisiLe()));
            prest.setString(6, reglement.getObservations());

            return prest.executeUpdate();

        } catch (SQLException se) {
            System.out.println("Add Reglement Error SQL");
            se.printStackTrace();
            return 0;
        }
    }
}
