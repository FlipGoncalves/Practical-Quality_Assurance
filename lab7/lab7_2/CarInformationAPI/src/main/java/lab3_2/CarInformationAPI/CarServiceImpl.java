package lab3_2.CarInformationAPI;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository car_rep;

    @Override
    public Optional<Car> getCarById(Long id) {
        return car_rep.findById(id);
    }

    @Override
    public Car createCar(Car car) {
        return car_rep.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        return car_rep.findAll();
    }
}