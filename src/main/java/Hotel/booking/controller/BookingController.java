package Hotel.booking.controller;

import Hotel.booking.entities.Bookings;
import Hotel.booking.services.BookingServicePdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/hotel/booking")
public class BookingController {

    @Autowired
    BookingServicePdf bookingServicePdf;

    // GET endpoint to retrieve a booking PDF by booking ID

    //  url=  http://localhost:8080/hotel/booking/pdf?bookingId=id
    @GetMapping("/pdf")
    public ResponseEntity<byte[]> getBookingPdf(@RequestParam Long bookingId)
    {

      byte[] pdfBytes=  bookingServicePdf.generateBookingPdf(bookingId);

        HttpHeaders headers= new HttpHeaders();
        headers.add("Content-Type", "application/pdf");  // Ensure this header is set
        headers.add(" Content-Disposition", "attachment; filename=booking-" + bookingId + ".pdf");

        return new ResponseEntity<>(pdfBytes,headers,HttpStatus.OK);

    }

    // POST endpoint to create a new booking and generate the PDF

    //url =  http://localhost:8080/hotel/booking/pdf

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> createBookingAndGeneratePdf(@RequestBody Bookings bookings)
    {
       byte[] pdfByte= bookingServicePdf.createBookingAndGeneratePdf(bookings);

       HttpHeaders headers=new HttpHeaders();
       headers.add("Content-Type", "application/pdf");  // Ensure this header is set
       headers.add(" Content-Disposition", "attachment; filename=booking.pdf");

       return new ResponseEntity<>(pdfByte,headers,HttpStatus.CREATED);
    }

}
