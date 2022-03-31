package lab3_2.CarInformationAPI;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarServiceImpl implements CarService {

    @Autowired
    private CarRepository car_rep;

    @Override
    public Car getCarById(Long id) {
        return car_rep.findById(id).orElse(null);
    }

    @Override
    public boolean exists(Long id) {
        if (car_rep.findById(id) != null) {
            return true;
        }
        return false;
    }

    @Override
    public Car save(Car car) {
        return car_rep.save(car);
    }

    @Override
    public List<Car> getAllCars() {
        return car_rep.findAll();
    }
}