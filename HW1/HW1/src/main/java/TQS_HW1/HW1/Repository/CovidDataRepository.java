package TQS_HW1.HW1.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import TQS_HW1.HW1.Models.CovidData;

import java.util.Optional;

@Repository
public interface CovidDataRepository extends JpaRepository<CovidData, Long> {
    Optional<CovidData> findByCountry(String country);
}
