package Hotel.booking.services;

import Hotel.booking.entities.Bookings;
import Hotel.booking.repositories.BookingRepo;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Optional;

@Service
public class BookingServicePdf {


    @Autowired
    BookingRepo bookingRepo;

    // Generate PDF for an existing booking (GET method)

    public byte[] generateBookingPdf(Long bookingId) {
        Optional<Bookings> bookingsOptional = bookingRepo.findById(bookingId);


        if (bookingsOptional.isEmpty())
        {
            throw new IllegalArgumentException("Booking not found");
        }

        Bookings booking=bookingsOptional.get();
        ByteArrayOutputStream out= new ByteArrayOutputStream();

        try(PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf))

        {
            document.add(new Paragraph(("Hotel Booking conformation")));
            document.add(new Paragraph("Customer First Name:" +booking.getFirstName()));
            document.add(new Paragraph("Customer Last Name:" +booking.getLastName()));
            document.add(new Paragraph("Email:" +booking.getEmail()));
            document.add(new Paragraph("Mobile Number:" +booking.getMobileNumber()));
            document.add(new Paragraph("Room Number:" +booking.getNumberOfRooms()));
            document.add(new Paragraph("Check in Date:" +booking.getCheckInDate()));
            document.add(new Paragraph("Check out Date:" +booking.getCheckOutDate()));
            document.add(new Paragraph("Total Price:" +booking.getTotalPrice()));


        }
        catch (Exception e)
        {

            throw new RuntimeException("Error getting PDF", e);
        }

        return out.toByteArray();
    }

    // Create a new booking and generate a PDF (POST method)

    public byte[] createBookingAndGeneratePdf(Bookings bookings)
    {
      Bookings savedBooking=  bookingRepo.save(bookings);

      // Generate a PDF for the newly created booking
        return generateBookingPdf(savedBooking.getBookingId());
    }
}
