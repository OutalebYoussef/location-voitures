package dao;

import database.DBConnection;
import model.Voiture;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class VoitureDAO {

    public boolean ajouter(Voiture voiture) {

        String sql = """
                INSERT INTO voitures
                (marque, modele, matricule, prix_jour, statut)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, voiture.getMarque());
            ps.setString(2, voiture.getModele());
            ps.setString(3, voiture.getMatricule());
            ps.setDouble(4, voiture.getPrixJour());
            ps.setString(5, voiture.getStatut());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}