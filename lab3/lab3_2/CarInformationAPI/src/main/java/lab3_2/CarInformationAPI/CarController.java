package lab3_2.CarInformationAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class CarController {

    @Autowired
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @PostMapping("/car" )
    public ResponseEntity<Car> createCar(@RequestBody @Valid Car car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carService.createCar( car );
        return new ResponseEntity<>(saved, status);
    }


    @GetMapping(path="/getallcars" )
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getCarByID( @PathVariable(value = "id") Long id ) {
        Optional<Car> c = carService.getCarById( 1L );
        return c.map( car -> new ResponseEntity<>( car, HttpStatus.OK ) )
                .orElseGet( () -> new ResponseEntity<>( HttpStatus.NOT_FOUND ) );
        
    }

}

