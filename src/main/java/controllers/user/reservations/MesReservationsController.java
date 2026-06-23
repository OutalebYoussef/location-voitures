package controllers.user.reservations;

import dao.ReservationDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.FlowPane;
import model.Reservation;
import utils.Session;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MesReservationsController implements Initializable {

    @FXML
    private FlowPane container;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        ReservationDAO dao=new ReservationDAO();

        List<Reservation> reservations=
                dao.getMesReservations(Session.getUserId());

        for(Reservation reservation:reservations){

            try{

                FXMLLoader loader=new FXMLLoader(
                        getClass().getResource(
                                "/pages/user/mes-reservation-card.fxml"
                        )
                );

                Parent card=loader.load();

                ReservationCardController controller=
                        loader.getController();

                controller.setReservation(reservation);
                controller.setStatus(reservation.getStatus());

                container.getChildren().add(card);

            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}