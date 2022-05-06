package lab3_2.CarInformationAPI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CarInformationApiApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase

// switch AutoConfigureTestDatabase with TestPropertySource to use a real database
//@TestPropertySource( locations = "application-integrationtest.properties")
class CarAPI_TestIT {

    // will need to use the server port for the invocation url
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private MockMvc mvc;

    // a REST client that is test-friendly
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository car_rep;

    @MockBean
    private CarService service;

    @AfterEach
    public void resetDb() {
        car_rep.deleteAll();
    }


    @Test
     void createCar_fromSuccess() throws IOException, Exception {
        Car seat = new Car(1L, "Seat", "Ibiza");
    
        mvc.perform(post("/api/car").contentType( MediaType.APPLICATION_JSON).content(asJsonString(seat)));

        List<Car> found = car_rep.findAll();

        assertThat(found).extracting(Car::getMaker).containsOnly(seat.getMaker());
    }

    @Test
     void status200()  {
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

    public static String asJsonString( final Object obj ) {
        try {
          return new ObjectMapper().writeValueAsString( obj );
        } catch (Exception e) {
          throw new RuntimeException( e );
        }
    }
}

