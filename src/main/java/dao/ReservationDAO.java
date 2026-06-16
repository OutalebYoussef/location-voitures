package dao;

import database.DBConnection;
import model.Reservation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {

    public boolean ajouter(Reservation reservation) {

        String sql = """
    INSERT INTO reservations
    (user_id, voiture_id, nb_jr, montant_total, date_debut, status)
    VALUES (?, ?, ?, ?, ?, ?)
""";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setInt(1, reservation.getUserId());
            ps.setInt(2, reservation.getVoitureId());
            ps.setInt(3, reservation.getNbJr());
            ps.setDouble(4, reservation.getMontantTotal());
            ps.setDate(5, java.sql.Date.valueOf(reservation.getDateDebut()));
            ps.setString(6, reservation.getStatus());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<Reservation> getMesReservations(int userId) {

        List<Reservation> list = new ArrayList<>();

        String sql = """
        SELECT r.*,
               v.marque,
               v.modele,
               v.prix_jour,
               v.image_path
        FROM reservations r
        JOIN voitures v
            ON r.voiture_id=v.id
        WHERE r.user_id=?
        ORDER BY r.date_reservation DESC
        """;

        try(Connection cn=DBConnection.getConnection();
            PreparedStatement ps=cn.prepareStatement(sql)) {

            ps.setInt(1,userId);

            ResultSet rs=ps.executeQuery();

            while(rs.next()){

                Reservation r=new Reservation();

                r.setId(rs.getInt("id"));
                r.setVoitureId(rs.getInt("voiture_id"));
                r.setNbJr(rs.getInt("nb_jr"));
                r.setMontantTotal(rs.getDouble("montant_total"));
                r.setStatus(rs.getString("status"));
                r.setImagePath(rs.getString("image_path"));
                r.setMarque(rs.getString("marque"));
                r.setModele(rs.getString("modele"));

                list.add(r);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }
}