package CarInformationAPI;

import java.util.List;
import java.util.Optional;

public interface CarService {
    public Optional<Car> getCarById(Long id);

    public List<Car> getAllCars();

    public Car createCar(Car car);

}
