package CarInformationAPI;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CarService_Test {
    Car car1, car2, car3;

	@Mock( lenient = true)
    private CarRepository car_rep;

    @InjectMocks
    private CarServiceImpl carService;

    @BeforeEach
    public void setUp() {

        //these expectations provide an alternative to the use of the repository
        car1 = new Car((long) 1100, "BMW", "A5"); 
        car1.setId((long) 1000);

        car2 = new Car((long) 1001, "Ford", "Fiasco"); 
		car3 = new Car((long) 1002, "Toyota", "YAris");

        List<Car> cars = Arrays.asList(car1, car2, car3);

        Mockito.when(car_rep.findById(car1.getId())).thenReturn(Optional.of(car1));
        Mockito.when(car_rep.findById(car2.getId())).thenReturn(Optional.of(car2));
        Mockito.when(car_rep.findById((long) -4)).thenReturn(null);
        Mockito.when(car_rep.findAll()).thenReturn(cars);
        Mockito.when(car_rep.findById((long) 10)).thenReturn(Optional.empty());
    }

    @Test
    public void ValidID_Test() {
        Optional<Car> fromDb = carService.getCarById(car1.getId());

        assertThat(fromDb.get().getId()).isEqualTo(car1.getId());
        Mockito.verify(car_rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void InvalidID_Test() {
        Optional<Car> fromDb = carService.getCarById((long) 15000);

        assertThat(fromDb).isEmpty();
        Mockito.verify(car_rep, VerificationModeFactory.times(1)).findById(Mockito.anyLong());
    }

    @Test
    public void GetAll_Test() { 
        List<Car> allCars = carService.getAllCars();

        Mockito.verify(car_rep, VerificationModeFactory.times(1)).findAll();
        assertThat(allCars).hasSize(3).extracting(Car::getId).contains(car1.getId(), car2.getId(), car3.getId());
    }
}