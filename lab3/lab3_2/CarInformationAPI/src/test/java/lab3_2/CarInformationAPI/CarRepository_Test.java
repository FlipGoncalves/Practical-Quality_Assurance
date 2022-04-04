package lab3_2.CarInformationAPI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;


import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * DataJpaTest limits the test scope to the data access context (no web environment loaded, for example)
 * tries to autoconfigure the database, if possible (e.g.: in memory db)
 */
@DataJpaTest
class CarRepository_Test {

    // get a test-friendly Entity Manager
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository car_rep;

    @Test
    public void persistence_Test() {
        Car alex = new Car( 1000L, "Seat", "Ibiza" );
        entityManager.persistAndFlush( alex );
        
        Optional<Car> found = car_rep.findById( alex.getId() );
        assertThat( found.isPresent() ).isTrue();
        assertThat( found.get() ).isEqualTo( alex );
    }

    @Test
    void findByid_Test() {
        Car car1 = new Car((long) 1000, "BMW", "A5"); 
        entityManager.persistAndFlush(car1);

        Optional<Car> fromDb = car_rep.findById(car1.getId());
        assertThat(fromDb).isNotNull();
        assertThat(fromDb.get().getId()).isEqualTo( car1.getId());
    }

    @Test
    void findByid_Fail_Test() {
        Optional<Car> fromDb = car_rep.findById((long) -4);
        assertThat(fromDb).isEmpty();
    }

    @Test
    void findAll_Test() {
        Car car1 = new Car((long) 1000, "BMW", "A5"); 
        Car car2 = new Car((long) 1001, "Ford", "Fiasco"); 
		Car car3 = new Car((long) 1002, "Toyota", "YAris");

        entityManager.persist(car1);
        entityManager.persist(car2);
        entityManager.persist(car3);
        entityManager.flush();

        List<Car> cars = car_rep.findAll();

        assertThat(cars).hasSize(3).extracting(Car::getId).contains(car1.getId(), car2.getId(), car3.getId());
    }

}
