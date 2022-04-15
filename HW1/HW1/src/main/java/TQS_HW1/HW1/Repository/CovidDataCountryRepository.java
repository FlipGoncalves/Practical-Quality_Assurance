package TQS_HW1.HW1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import TQS_HW1.HW1.Models.CovidDataCountry;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CovidDataCountryRepository extends JpaRepository<CovidDataCountry, Long> {
    Optional<CovidDataCountry> findByCountryAndDay(String country, String date);
    List<CovidDataCountry> findAllByDayIsLessThanEqual(Date date);
    List<CovidDataCountry> findAllByDay(String date);
    List<CovidDataCountry> findAllByCountry(String country);
    List<CovidDataCountry> findAllByContinent(String continent);
}
