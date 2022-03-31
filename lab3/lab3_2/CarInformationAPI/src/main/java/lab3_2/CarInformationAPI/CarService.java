package lab3_2.CarInformationAPI;

import java.util.List;

public interface CarService {
    public Car getCarById(Long id);

    public List<Car> getAllCars();

    public boolean exists(Long id);

    public Car save(Car car);

}
