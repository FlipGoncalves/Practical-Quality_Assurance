package lab3_2.CarInformationAPI;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;
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
		when(service.createCar(Mockito.any())).thenReturn(car);

        mvc.perform(
                post("/api/car").contentType(MediaType.APPLICATION_JSON).content(asJsonString(car)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.maker", is("BMW")));

        verify(service, times(1)).createCar(Mockito.any());
    }

    @Test
	void GET_Test( ) throws Exception {
		Car car1 = new Car(1L, "BMW", "A5"); 		// (CarId, CarMaker, CarModel)

		given(service.getCarById(1L)).willReturn(Optional.of(car1));

		mvc.perform(
                get("/api/car/1")).andExpect(status().isOk())
                .andExpect(jsonPath("$.maker", is(car1.getMaker())));

        verify(service, VerificationModeFactory.times(1)).getCarById(Mockito.any());
    }

    public static String asJsonString( final Object obj ) {
        try {
          return new ObjectMapper().writeValueAsString( obj );
        } catch (Exception e) {
          throw new RuntimeException( e );
        }
    }
}
