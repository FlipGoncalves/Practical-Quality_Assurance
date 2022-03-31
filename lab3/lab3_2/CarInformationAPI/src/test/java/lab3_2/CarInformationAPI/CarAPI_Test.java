package lab3_2.CarInformationAPI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase

// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
//@TestPropertySource( locations = "application-integrationtest.properties")
class CarAPI_Test {

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository car_rep;

    @AfterEach
    public void resetDb() {
        car_rep.deleteAll();
    }


    @Test
     void whenValidInput_thenCreateEmployee() {
        Car car1 = new Car((long) 1000, "BMW", "A5"); 
        ResponseEntity<Car> entity = restTemplate.postForEntity("/api/car", car1, Car.class);
        System.out.println(entity);
        
        List<Car> found = car_rep.findAll();
        assertThat(found).extracting(Car::getId).containsOnly(1000L);
    }

    @Test
     void givenEmployees_whenGetEmployees_thenStatus200()  {
        Car car1 = new Car((long) 1000, "BMW", "A5"); 
        car_rep.saveAndFlush(car1);
        Car car2 = new Car((long) 1001, "Ford", "Fiasco"); 
        car_rep.saveAndFlush(car2);

        ResponseEntity<List<Car>> response = restTemplate
                .exchange("/api/car", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getId).containsExactly(1000L, 1001L);

    }
}

