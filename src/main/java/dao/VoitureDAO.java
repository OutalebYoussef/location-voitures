package dao;

import database.DBConnection;
import model.Voiture;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VoitureDAO {

    public boolean ajouter(Voiture voiture) {

        String sql = """
    INSERT INTO voitures
    (marque, modele, matricule, prix_jour, statut, image_path, add_by)
    VALUES (?, ?, ?, ?, ?, ?, ?)
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
            ps.setString(6, voiture.getImagePath());
            ps.setInt(7, voiture.getAddBy());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Voiture> getVoitures() {

        List<Voiture> voitures = new ArrayList<>();

        String sql = "SELECT v.* ,u.* FROM voitures v join users u on v.add_by=u.id";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Voiture voiture = new Voiture();

                voiture.setId(rs.getInt("id"));
                voiture.setMarque(rs.getString("marque"));
                voiture.setModele(rs.getString("modele"));
                voiture.setMatricule(rs.getString("matricule"));
                voiture.setPrixJour(rs.getDouble("prix_jour"));
                voiture.setStatut(rs.getString("statut"));
                voiture.setAddBy(rs.getInt("add_by"));
                voiture.setUsername(rs.getString("username"));
                voiture.setImagePath(rs.getString("image_path"));

                voitures.add(voiture);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return voitures;
    }

    public boolean update(Voiture v) {

        String sql = """
    UPDATE voitures
    SET marque=?, modele=?, matricule=?, prix_jour=?, statut=?, image_path=?
    WHERE id=?
""";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, v.getMarque());
            ps.setString(2, v.getModele());
            ps.setString(3, v.getMatricule());
            ps.setDouble(4, v.getPrixJour());
            ps.setString(5, v.getStatut());
            ps.setString(6, v.getImagePath());
            ps.setInt(7, v.getId());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean delete(int id) {

        String sql = "DELETE FROM voitures WHERE id = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Voiture> getVoituresDisponibles() {

        List<Voiture> voitures = new ArrayList<>();

        String sql = """
        SELECT *
        FROM voitures
        WHERE statut='Disponible'
    """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Voiture v = new Voiture();

                v.setId(rs.getInt("id"));
                v.setMarque(rs.getString("marque"));
                v.setModele(rs.getString("modele"));
                v.setMatricule(rs.getString("matricule"));
                v.setPrixJour(rs.getDouble("prix_jour"));
                v.setStatut(rs.getString("statut"));
                v.setImagePath(rs.getString("image_path"));

                voitures.add(v);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return voitures;
    }
}