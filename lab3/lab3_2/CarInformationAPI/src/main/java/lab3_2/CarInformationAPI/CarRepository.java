package lab3_2.CarInformationAPI;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    public List<Car> findAll();
    public Car getCarById(long l);
}
