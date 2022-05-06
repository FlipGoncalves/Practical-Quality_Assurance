package CarInformationAPI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Car> createCar(@RequestBody @Valid CarDTO car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carService.createCar( new Car(car.id, car.maker, car.model) );
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/car/{id}")
    public ResponseEntity<Car> getCarByID( @PathVariable(value = "id") Long id ) {
        Optional<Car> c = carService.getCarById( 1L );
        return c.map( car -> new ResponseEntity<>( car, HttpStatus.OK ) )
                .orElseGet( () -> new ResponseEntity<>( HttpStatus.NOT_FOUND ) );
        
    }

}

