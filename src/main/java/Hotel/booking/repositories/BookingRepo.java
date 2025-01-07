package Hotel.booking.repositories;

import Hotel.booking.entities.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepo extends JpaRepository<Bookings,Long> {

}
