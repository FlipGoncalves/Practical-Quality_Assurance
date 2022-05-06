package lab3_2.CarInformationAPI;

import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.CoreMatchers.is;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@WebMvcTest(CarController.class)
class CarController_Test {

	@Autowired
    private MockMvc mvc;    //entry point to the web framework

    @MockBean
    private CarService service;

    @BeforeEach
    public void setUp() throws Exception {
        RestAssuredMockMvc.mockMvc(mvc);
    }

    @AfterEach 
    public void tearDown() throws Exception {
        RestAssuredMockMvc.reset();
    }

	@Test
	void POST_Test( ) throws Exception {
		Car car = new Car((long) 1100, "BMW", "A5"); 		// (CarId, CarMaker, CarModel)
		when(service.createCar(Mockito.any())).thenReturn(car);

        given().contentType( ContentType.JSON ).body( car ).post( "/api/car" ).then().assertThat().body( "maker", is( "BMW" ) );

        // mvc.perform(
        //         post("/api/car").contentType(MediaType.APPLICATION_JSON).content(asJsonString(car)))
        //         .andExpect(status().isCreated())
        //         .andExpect(jsonPath("$.maker", is("BMW")));

        verify(service, times(1)).createCar(Mockito.any());
    }

    @Test
	void GET_Test( ) throws Exception {
		Car car1 = new Car(1L, "BMW", "A5"); 		// (CarId, CarMaker, CarModel)

		given(service.getCarById(1L)).willReturn(Optional.of(car1));

        given().get( "/api/car/1" ).then().contentType( ContentType.JSON ).and().body( "maker", is( car1.getMaker() ) );

		// mvc.perform(
        //         get("/api/car/1")).andExpect(status().isOk())
        //         .andExpect(jsonPath("$.maker", is(car1.getMaker())));

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
