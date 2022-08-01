package cinema;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
public class CinemaHandler {

    cinemaInformation cinema = new cinemaInformation(9, 9);


    @RequestMapping("/seats")
    public String seats() {
        return "{\"total_rows\":" + cinema.getRows() +",\"total_columns\":" + cinema.getCols() +
                ",\"available_seats\":" + cinema.getSeats().toString() + "}";

    }

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseTicket(@RequestBody Seat seatRequest) {

        int row = seatRequest.getRow();
        int col = seatRequest.getColumn();


        //With the object ResponseEntity you can define the body of the response as well
        //as the HTTP code.
        if (Math.max(row, col) > 9 || Math.min(row, col) < 1) {

            return new ResponseEntity<>("{\"error\": \"The number of a row or a column is out of bounds!\"}", HttpStatus.BAD_REQUEST);
        }

        //If the seat is free, we remove him from the Array and respond with the string-representation
        //and with a 200-code.
        for (Seat seat : cinema.getSeats()) {
            if (seat.getRow() == row && seat.getColumn() == col) {
                cinema.getSeats().remove(seat);
                cinema.getReservedSeats().add(seat);
                return new ResponseEntity<>(seat.toStringWithToken(), HttpStatus.OK);
            }
        }
        //IF we reach this point, no seat matches the requested row and col...
        return new ResponseEntity<>("{\"error\": \"The ticket has been already purchased!\"}", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/return")
    public ResponseEntity<String> returnSeat(@RequestBody Token token) {
        for (Seat seat : cinema.getReservedSeats()) {
            if (seat.getToken().equals(token)) {
                cinema.getReservedSeats().remove(seat);
                cinema.getSeats().add(seat);
                return new ResponseEntity<>("{\"returned_ticket\": " + seat.toString() + "}", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("{\"error\": \"Wrong token!\"}", HttpStatus.BAD_REQUEST);

    }

    @PostMapping("/stats")
    public ResponseEntity<String> showStats(@RequestParam(required = false) String password) {

        int currentIncome = 0;
        int availableSeats;
        int purchasedTickets;
        if (password == null) {
            return new ResponseEntity<>("{\"error\": \"The password is wrong!\"}",
                    HttpStatus.UNAUTHORIZED);
        }
        if ("super_secret".equals(password)) {
            availableSeats = cinema.getSeats().size();
            purchasedTickets = cinema.getReservedSeats().size();
            for (Seat seat : cinema.getReservedSeats()) {
                currentIncome += seat.getPrice();
            }
            return new ResponseEntity<>("{\"current_income\": " + currentIncome +
                    ", \"number_of_available_seats\": " + availableSeats +
                    ", \"number_of_purchased_tickets\": " + purchasedTickets + "}",
                    HttpStatus.OK);
        } else {
            return new ResponseEntity<>("{\"error\": \"The password is wrong!\"}",
                    HttpStatus.UNAUTHORIZED);
        }
    }

}
