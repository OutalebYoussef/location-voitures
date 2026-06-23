package dao;

import database.DBConnection;
import model.Reservation;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
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

    public List<Reservation> getAllReservations() {

        List<Reservation> list = new ArrayList<>();

        String sql = """
        SELECT r.*,
               u.username AS client,
               u.email,
               u.num_phone,
               v.marque,
               v.modele
        FROM reservations r
        JOIN users u ON u.id=r.user_id
        JOIN voitures v ON v.id=r.voiture_id
        ORDER BY r.date_reservation DESC
        """;

        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()){

            while(rs.next()){

                Reservation r = new Reservation();

                r.setId(rs.getInt("id"));
                r.setClientName(rs.getString("client"));
                r.setMarque(rs.getString("marque"));
                r.setModele(rs.getString("modele"));
                r.setDateDebut(rs.getDate("date_debut").toLocalDate());
                r.setNbJr(rs.getInt("nb_jr"));
                r.setMontantTotal(rs.getDouble("montant_total"));
                r.setStatus(rs.getString("status"));
                r.setEmail(rs.getString("email"));
                r.setNumPhone(rs.getString("num_phone"));

                list.add(r);
            }

        }catch(Exception e){
            e.printStackTrace();
        }

        return list;
    }

    public List<Reservation> filterReservations(LocalDate date, String status) {

        List<Reservation> list = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT r.*,
               u.username AS client,
               v.marque,
               v.modele
        FROM reservations r
        JOIN users u ON u.id=r.user_id
        JOIN voitures v ON v.id=r.voiture_id
        WHERE 1=1
        """);

        List<Object> params = new ArrayList<>();

        if (date != null) {
            sql.append(" AND r.date_debut=?");
            params.add(Date.valueOf(date));
        }

        if (status != null && !status.equals("Tous")) {
            sql.append(" AND r.status=?");
            params.add(status);
        }

        sql.append(" ORDER BY r.date_reservation DESC");

        try (Connection cn = DBConnection.getConnection();
             PreparedStatement ps = cn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Reservation r = new Reservation();

                r.setId(rs.getInt("id"));
                r.setClientName(rs.getString("client"));
                r.setMarque(rs.getString("marque"));
                r.setModele(rs.getString("modele"));
                r.setDateDebut(rs.getDate("date_debut").toLocalDate());
                r.setNbJr(rs.getInt("nb_jr"));
                r.setMontantTotal(rs.getDouble("montant_total"));
                r.setStatus(rs.getString("status"));

                list.add(r);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public int countReservations() {

        String sql = "SELECT COUNT(*) FROM reservations";

        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public int countByStatus(String status) {

        String sql = """
        SELECT COUNT(*)
        FROM reservations
        WHERE status = ?
    """;

        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, status);

            ResultSet rs = ps.executeQuery();

            if(rs.next()) {
                return rs.getInt(1);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public void updateStatus(int id, String status){

        String sql = """
        UPDATE reservations
        SET status=?
        WHERE id=?
        """;

        try(Connection cn=DBConnection.getConnection();
            PreparedStatement ps=cn.prepareStatement(sql)){

            ps.setString(1,status);
            ps.setInt(2,id);

            ps.executeUpdate();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


//  ===========  for admin dashboard====================
    public int countReservationsAdmin() {
        String sql = "SELECT COUNT(*) AS total FROM reservations";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public double totalRevenue() {
        String sql = """
        SELECT COALESCE(SUM(montant_total),0) AS total
        FROM reservations
        WHERE status='Acceptée'
    """;

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Reservation> getRecentReservations() {

        List<Reservation> list = new ArrayList<>();

        String sql = """
        SELECT r.*,
               u.username AS client,
               v.marque,
               v.modele
        FROM reservations r
        JOIN users u ON u.id = r.user_id
        JOIN voitures v ON v.id = r.voiture_id
        ORDER BY r.date_reservation DESC
        LIMIT 5
    """;

        try(Connection cn = DBConnection.getConnection();
            PreparedStatement ps = cn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery()) {

            while(rs.next()) {

                Reservation r = new Reservation();

                r.setId(rs.getInt("id"));
                r.setClientName(rs.getString("client"));
                r.setMarque(rs.getString("marque"));
                r.setModele(rs.getString("modele"));
                r.setDateDebut(rs.getDate("date_debut").toLocalDate());
                r.setMontantTotal(rs.getDouble("montant_total"));
                r.setStatus(rs.getString("status"));

                list.add(r);
            }

        } catch(Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}