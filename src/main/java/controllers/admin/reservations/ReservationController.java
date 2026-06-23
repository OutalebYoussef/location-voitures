package controllers.admin.reservations;

import dao.ReservationDAO;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Reservation;

import java.util.Optional;

public class ReservationController {

    @FXML
    private TableView<Reservation> tableReservations;

    @FXML
    private TableColumn<Reservation, Integer> colId;

    @FXML
    private TableColumn<Reservation, String> colClient;

    @FXML
    private TableColumn<Reservation, String> colClientNum;

    @FXML
    private TableColumn<Reservation, String> colClientEmail;

    @FXML
    private TableColumn<Reservation, String> colVoiture;

    @FXML
    private TableColumn<Reservation, String> colDate;

    @FXML
    private TableColumn<Reservation, Integer> colJours;

    @FXML
    private TableColumn<Reservation, Double> colMontant;

    @FXML
    private TableColumn<Reservation, String> colStatus;

    @FXML
    private TableColumn<Reservation, Void> colAction;

    @FXML
    private DatePicker dateFilter;

    @FXML
    private ComboBox<String> statusFilter;

    @FXML
    private Label lblTotal;

    @FXML
    private Label lblAccepte;

    @FXML
    private Label lblAttente;

    @FXML
    private Label lblRefuse;

    private final ReservationDAO dao = new ReservationDAO();

    @FXML
    public void initialize() {

        statusFilter.getItems().addAll(
                "Tous",
                "En attente",
                "Acceptée",
                "Refusée"
        );

        statusFilter.setValue("Tous");

        colId.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleIntegerProperty(
                        cell.getValue().getId()
                ).asObject());

        colClient.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getClientName()
                ));

        colClientNum.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getNumPhone()
                ));

        colClientEmail.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getEmail()
                ));

        colVoiture.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getMarque() + " " + cell.getValue().getModele()
                ));

        colDate.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        String.valueOf(cell.getValue().getDateDebut())
                ));

        colJours.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleIntegerProperty(
                        cell.getValue().getNbJr()
                ).asObject());

        colMontant.setCellValueFactory(cell ->
                new javafx.beans.property.SimpleDoubleProperty(
                        cell.getValue().getMontantTotal()
                ).asObject());

        colStatus.setCellValueFactory(cell ->
                new SimpleStringProperty(
                        cell.getValue().getStatus()
                ));


        loadReservations();
        loadStats();

        addActionButtons();

        loadReservations();
    }

    private void loadStats() {

        lblTotal.setText(
                String.valueOf(
                        dao.countReservations()
                )
        );

        lblAccepte.setText(
                String.valueOf(
                        dao.countByStatus("Acceptée")
                )
        );

        lblAttente.setText(
                String.valueOf(
                        dao.countByStatus("En attente")
                )
        );

        lblRefuse.setText(
                String.valueOf(
                        dao.countByStatus("Refusée")
                )
        );
    }

    private void loadReservations() {

        tableReservations.setItems(
                FXCollections.observableArrayList(
                        dao.getAllReservations()
                )
        );
    }

    @FXML
    private void filtrer() {

        tableReservations.setItems(
                FXCollections.observableArrayList(
                        dao.filterReservations(
                                dateFilter.getValue(),
                                statusFilter.getValue()
                        )
                )
        );
    }

    @FXML
    private void resetFilter() {

        dateFilter.setValue(null);

        statusFilter.setValue("Tous");

        loadReservations();
    }

    private void addActionButtons() {

        colAction.setCellFactory(param -> new TableCell<>() {

            private final Button btnAccept = new Button("Accepter");
            private final Button btnReject = new Button("Refuser");

            {
                btnAccept.setStyle("-fx-background-color:#2ecc71;-fx-text-fill:white;");
                btnReject.setStyle("-fx-background-color:#e74c3c;-fx-text-fill:white;");

                btnAccept.setOnAction(event -> {

                    Reservation reservation =
                            getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Accepter la réservation");
                    alert.setContentText("Voulez-vous vraiment accepter cette réservation ?");

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {

                        dao.updateStatus(
                                reservation.getId(),
                                "Acceptée"
                        );

                        loadReservations();
                        loadStats();
                    }
                });

                btnReject.setOnAction(event -> {

                    Reservation reservation =
                            getTableView().getItems().get(getIndex());

                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);

                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Refuser la réservation");
                    alert.setContentText("Voulez-vous vraiment refuser cette réservation ?");

                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {

                        dao.updateStatus(
                                reservation.getId(),
                                "Refusée"
                        );

                        loadReservations();
                        loadStats();
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {

                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    return;
                }

                Reservation reservation =
                        getTableView().getItems().get(getIndex());

                if (!reservation.getStatus().equalsIgnoreCase("En attente")) {

                    setGraphic(new Label(reservation.getStatus()));
                    return;
                }

                ToolBar bar = new ToolBar(
                        btnAccept,
                        btnReject
                );

                setGraphic(bar);
            }
        });
    }
}