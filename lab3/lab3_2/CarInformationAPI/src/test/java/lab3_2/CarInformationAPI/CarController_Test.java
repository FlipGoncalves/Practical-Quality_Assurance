package lab3_2.CarInformationAPI;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CarController.class)
class CarController_Test {

	@Autowired
    private MockMvc mvc;    //entry point to the web framework

    @MockBean
    private CarService service;

    @BeforeEach
    public void setUp() throws Exception {
    }

	@Test
	void POST_Test( ) throws Exception {
		Car car = new Car((long) 1100, "BMW", "A5"); 		// (CarId, CarMaker, CarModel)
		when(service.save(Mockito.any())).thenReturn(car);

        mvc.perform(
                post("/api/car").contentType(MediaType.APPLICATION_JSON).content(JsonUtils.toJson(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1100)));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
	void GET_Test( ) throws Exception {
		Car car1 = new Car((long) 1100, "BMW", "A5"); 		// (CarId, CarMaker, CarModel)
		Car car2 = new Car((long) 1101, "Ford", "Fiasco"); 
		Car car3 = new Car((long) 1102, "Toyota", "YAris"); 
		List<Car> cars = Arrays.asList(car1, car2, car3);
		when(service.getAllCars()).thenReturn(cars);

		mvc.perform(
                get("/api/getallcars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].id", is(1100)))
                .andExpect(jsonPath("$[1].id", is(1101)))
                .andExpect(jsonPath("$[2].id", is(1102)));

        verify(service, times(1)).save(Mockito.any());
    }
}
