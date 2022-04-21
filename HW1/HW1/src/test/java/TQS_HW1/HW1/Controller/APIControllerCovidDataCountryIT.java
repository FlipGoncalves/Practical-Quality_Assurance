package TQS_HW1.HW1.Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import TQS_HW1.HW1.Hw1Application;
import TQS_HW1.HW1.Cache.Cache;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataCountryRepository;
import TQS_HW1.HW1.Resolver.CovidDataResolver;
import TQS_HW1.HW1.Services.CovidDataCountryService;

import org.springframework.http.MediaType;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Hw1Application.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase

class APIControllerCovidDataCountryIT {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CovidDataCountryRepository rep;

    @Mock
    private CovidDataResolver resolver;

    @Mock
    private Cache cache;

    @InjectMocks
    private CovidDataCountryService service;

    @AfterEach
    void resetDb() {
        rep.deleteAll();
    }
    
    @Test
    void testGetCountryData() throws Exception {
        CovidDataCountry data = createTestCountry();

        mvc.perform(get("/api/get_data/")
                .param("country", "Portugal")
                .param("date", "2021-04-11")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("continent", is(data.getContinent())))
                .andExpect(jsonPath("country", is(data.getCountry())))
                .andExpect(jsonPath("newCases", is(data.getNewCases())))
                .andExpect(jsonPath("activeCases", is(data.getActiveCases())))
                .andExpect(jsonPath("recoveredCases", is(data.getRecoveredCases())))
                .andExpect(jsonPath("criticalCases", is(data.getCriticalCases())))
                .andExpect(jsonPath("totalCases", is(data.getTotalCases())))
                .andExpect(jsonPath("totalDeaths", is(data.getTotalDeaths())))
                .andExpect(jsonPath("newDeaths", is(data.getNewDeaths())))
                .andExpect(jsonPath("totalDeaths", is(data.getTotalTests())));

    }

    @Test
    void testGetCountryData_BadRequest_Country() throws Exception {
        mvc.perform(get("/api/get_data")
                .param("country", "ajshakjsghdh")
                .param("date", "2021-04-11")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountryData_BadRequest_Date() throws Exception {
        mvc.perform(get("/api/get_data")
                .param("country", "Portugal")
                .param("date", "2020-2020-2020")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountryData_BadRequest_NoParameters() throws Exception {
        mvc.perform(get("/api/get_data")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountryData_BadRequest_OnlyCountry() throws Exception {
        mvc.perform(get("/api/get_data")
                .param("country", "Portugal")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCountryData_BadRequest_OnlyDate() throws Exception {
        mvc.perform(get("/api/get_data")
                .param("date", "2020-04-11")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetCacheData() throws Exception {
        mvc.perform(get("/api/cache_statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("hits", is(Cache.getHits())))
                .andExpect(jsonPath("misses", is(Cache.getMisses())))
                .andExpect(jsonPath("saveRequests", is(Cache.getsaveRequests())))
                .andExpect(jsonPath("getRequests", is(Cache.getgetRequests())))
                .andExpect(jsonPath("deleteRequests", is(Cache.getdeleteRequests())));
    }

    @Test
    void testGetAllCacheData() throws Exception {
        CovidDataCountry data = createTestCountry();
        
        mvc.perform(get("/api/cache_data")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].continent", is(data.getContinent())))
                .andExpect(jsonPath("$[0].country", is(data.getCountry())))
                .andExpect(jsonPath("$[0].newCases", is(data.getNewCases())))
                .andExpect(jsonPath("$[0].activeCases", is(data.getActiveCases())))
                .andExpect(jsonPath("$[0].recoveredCases", is(data.getRecoveredCases())))
                .andExpect(jsonPath("$[0].criticalCases", is(data.getCriticalCases())))
                .andExpect(jsonPath("$[0].totalCases", is(data.getTotalCases())))
                .andExpect(jsonPath("$[0].totalDeaths", is(data.getTotalDeaths())))
                .andExpect(jsonPath("$[0].newDeaths", is(data.getNewDeaths())))
                .andExpect(jsonPath("$[0].totalTests", is(data.getTotalTests())));
    }

    @Test
    void testGetData_Country() throws Exception {
        CovidDataCountry data = createTestCountry();

        mvc.perform(get("/api/get/country")
                .param("country", "Portugal")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].continent", is(data.getContinent())))
                .andExpect(jsonPath("$[0].country", is(data.getCountry())))
                .andExpect(jsonPath("$[0].newCases", is(data.getNewCases())))
                .andExpect(jsonPath("$[0].activeCases", is(data.getActiveCases())))
                .andExpect(jsonPath("$[0].recoveredCases", is(data.getRecoveredCases())))
                .andExpect(jsonPath("$[0].criticalCases", is(data.getCriticalCases())))
                .andExpect(jsonPath("$[0].totalCases", is(data.getTotalCases())))
                .andExpect(jsonPath("$[0].totalDeaths", is(data.getTotalDeaths())))
                .andExpect(jsonPath("$[0].newDeaths", is(data.getNewDeaths())))
                .andExpect(jsonPath("$[0].totalTests", is(data.getTotalTests())));
    }

    @Test
    void testGetData_Country_BadRequest_Country() throws Exception {
        mvc.perform(get("/api/get/country")
                .param("country", "sdfsdfgdfg")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetData_Country_BadRequest_NoParameters() throws Exception {
        mvc.perform(get("/api/get/country")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetData_Continent() throws Exception {
        CovidDataCountry data = createTestCountry();

        mvc.perform(get("/api/get/continent")
                .param("continent", "Europe")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].continent", is(data.getContinent())))
                .andExpect(jsonPath("$[0].country", is(data.getCountry())))
                .andExpect(jsonPath("$[0].newCases", is(data.getNewCases())))
                .andExpect(jsonPath("$[0].activeCases", is(data.getActiveCases())))
                .andExpect(jsonPath("$[0].recoveredCases", is(data.getRecoveredCases())))
                .andExpect(jsonPath("$[0].criticalCases", is(data.getCriticalCases())))
                .andExpect(jsonPath("$[0].totalCases", is(data.getTotalCases())))
                .andExpect(jsonPath("$[0].totalDeaths", is(data.getTotalDeaths())))
                .andExpect(jsonPath("$[0].newDeaths", is(data.getNewDeaths())))
                .andExpect(jsonPath("$[0].totalTests", is(data.getTotalTests())));
    }

    @Test
    void testGetData_Continent_BadRequest_Country() throws Exception {
        mvc.perform(get("/api/get/continent")
                .param("continent", "sdfsdfgdfg")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetData_Continent_BadRequest_NoParameters() throws Exception {
        mvc.perform(get("/api/get/continent")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetData_Day() throws Exception {
        CovidDataCountry data = createTestCountry();

        mvc.perform(get("/api/get/day")
                .param("day", "2021-04-11")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].continent", is(data.getContinent())))
                .andExpect(jsonPath("$[0].country", is(data.getCountry())))
                .andExpect(jsonPath("$[0].newCases", is(data.getNewCases())))
                .andExpect(jsonPath("$[0].activeCases", is(data.getActiveCases())))
                .andExpect(jsonPath("$[0].recoveredCases", is(data.getRecoveredCases())))
                .andExpect(jsonPath("$[0].criticalCases", is(data.getCriticalCases())))
                .andExpect(jsonPath("$[0].totalCases", is(data.getTotalCases())))
                .andExpect(jsonPath("$[0].totalDeaths", is(data.getTotalDeaths())))
                .andExpect(jsonPath("$[0].newDeaths", is(data.getNewDeaths())))
                .andExpect(jsonPath("$[0].totalTests", is(data.getTotalTests())));
    }

    @Test
    void testGetData_Day_BadRequest_Country() throws Exception {
        mvc.perform(get("/api/get/day")
                .param("day", "sdfsdfgdfg")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetData_Day_BadRequest_NoParameters() throws Exception {
        mvc.perform(get("/api/get/day")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAllData() throws Exception {
        mvc.perform(get("/api/all_data")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    

    private CovidDataCountry createTestCountry() {
        CovidDataCountry data = new CovidDataCountry("Portugal", "Europe", "2021-04-11");

        data.setActiveCases(4);
        data.setCriticalCases(4);
        data.setNewCases("+2");
        data.setTotalCases(10);
        data.setRecoveredCases(2);
        data.setNewDeaths("+2");
        data.setTotalDeaths(4);
        data.setTotalTests(4);

        return rep.saveAndFlush(data);
    }

}
