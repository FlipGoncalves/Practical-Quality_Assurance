package lab3_2.CarInformationAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/car" )
    public ResponseEntity<Car> createEmployee(@RequestBody Car car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carService.save( car );
        return new ResponseEntity<>(saved, status);
    }


    @GetMapping(path="/getallcars" )
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

}

