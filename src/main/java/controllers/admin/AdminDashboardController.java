package controllers.admin;

import dao.ReservationDAO;
import dao.UserDAO;
import dao.VoitureDAO;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Reservation;

public class AdminDashboardController {

    @FXML
    private Label lblUsers;

    @FXML
    private Label lblVoitures;

    @FXML
    private Label lblReservations;

    @FXML
    private Label lblRevenue;

    @FXML
    private TableView<Reservation> tableRecent;

    @FXML
    private TableColumn<Reservation, String> colClient;

    @FXML
    private TableColumn<Reservation, String> colVoiture;

    @FXML
    private TableColumn<Reservation, String> colDate;

    @FXML
    private TableColumn<Reservation, String> colStatus;

    @FXML
    private TableColumn<Reservation, Double> colMontant;

    private final UserDAO userDAO = new UserDAO();
    private final VoitureDAO voitureDAO = new VoitureDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    private void loadStats() {

        lblUsers.setText(String.valueOf(userDAO.countUsers()));

        lblVoitures.setText(String.valueOf(voitureDAO.countVoitures()));

        lblReservations.setText(String.valueOf(reservationDAO.countReservations()));

        lblRevenue.setText(reservationDAO.totalRevenue() + " DH");
    }

    private void loadRecentReservations() {

        tableRecent.setItems(
                FXCollections.observableArrayList(
                        reservationDAO.getRecentReservations()
                )
        );
    }

    @FXML
    public void initialize() {

        loadStats();

        colClient.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getClientName()
                ));

        colVoiture.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getMarque()
                                + " "
                                + cell.getValue().getModele()
                ));

        colDate.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getDateDebut().toString()
                ));

        colStatus.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getStatus()
                ));

        colMontant.setCellValueFactory(cell ->
                new SimpleDoubleProperty(
                        cell.getValue().getMontantTotal()
                ).asObject());

        loadRecentReservations();
    }
}