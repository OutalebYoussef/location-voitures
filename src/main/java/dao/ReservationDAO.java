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

    public List<Reservation> getMesReservations() {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservations r join voitures v on v.id=r.voiture_id WHERE user_id = ?";

        try (
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()
        ){
            while (rs.next()){
                Reservation reservation = new Reservation();
                reservation.setId(rs.getInt("id"));
                reservation.setUserId(rs.getInt("user_id"));
                reservation.setVoitureId(rs.getInt("voiture_id"));
                reservation.setNbJr(rs.getInt("nb_jr"));
                reservation.setStatus(rs.getString("status"));
                reservation.setDateReservation(rs.getTimestamp("date_reservation"));
                reservations.add(reservation);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return reservations;
    }
}