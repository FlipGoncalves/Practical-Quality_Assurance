package TQS_HW1.HW1.Controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import TQS_HW1.HW1.Hw1Application;
import TQS_HW1.HW1.Cache.Cache;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataCountryRepository;

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
                .andExpect(jsonPath("new_cases", is(data.getNew_cases())))
                .andExpect(jsonPath("active_cases", is(data.getActive_cases())))
                .andExpect(jsonPath("recovered_cases", is(data.getRecovered_cases())))
                .andExpect(jsonPath("critical_cases", is(data.getCritical_cases())))
                .andExpect(jsonPath("total_cases", is(data.getTotal_cases())))
                .andExpect(jsonPath("total_deaths", is(data.getTotal_deaths())))
                .andExpect(jsonPath("new_deaths", is(data.getNew_deaths())))
                .andExpect(jsonPath("total_deaths", is(data.getTotal_tests())));
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
                .andExpect(jsonPath("save_requests", is(Cache.getSave_requests())))
                .andExpect(jsonPath("get_requests", is(Cache.getGet_requests())))
                .andExpect(jsonPath("delete_requests", is(Cache.getDelete_requests())));
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
                .andExpect(jsonPath("$[0].new_cases", is(data.getNew_cases())))
                .andExpect(jsonPath("$[0].active_cases", is(data.getActive_cases())))
                .andExpect(jsonPath("$[0].recovered_cases", is(data.getRecovered_cases())))
                .andExpect(jsonPath("$[0].critical_cases", is(data.getCritical_cases())))
                .andExpect(jsonPath("$[0].total_cases", is(data.getTotal_cases())))
                .andExpect(jsonPath("$[0].total_deaths", is(data.getTotal_deaths())))
                .andExpect(jsonPath("$[0].new_deaths", is(data.getNew_deaths())))
                .andExpect(jsonPath("$[0].total_deaths", is(data.getTotal_tests())));
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
                .andExpect(jsonPath("$[0].new_cases", is(data.getNew_cases())))
                .andExpect(jsonPath("$[0].active_cases", is(data.getActive_cases())))
                .andExpect(jsonPath("$[0].recovered_cases", is(data.getRecovered_cases())))
                .andExpect(jsonPath("$[0].critical_cases", is(data.getCritical_cases())))
                .andExpect(jsonPath("$[0].total_cases", is(data.getTotal_cases())))
                .andExpect(jsonPath("$[0].total_deaths", is(data.getTotal_deaths())))
                .andExpect(jsonPath("$[0].new_deaths", is(data.getNew_deaths())))
                .andExpect(jsonPath("$[0].total_deaths", is(data.getTotal_tests())));
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
                .andExpect(jsonPath("$[0].new_cases", is(data.getNew_cases())))
                .andExpect(jsonPath("$[0].active_cases", is(data.getActive_cases())))
                .andExpect(jsonPath("$[0].recovered_cases", is(data.getRecovered_cases())))
                .andExpect(jsonPath("$[0].critical_cases", is(data.getCritical_cases())))
                .andExpect(jsonPath("$[0].total_cases", is(data.getTotal_cases())))
                .andExpect(jsonPath("$[0].total_deaths", is(data.getTotal_deaths())))
                .andExpect(jsonPath("$[0].new_deaths", is(data.getNew_deaths())))
                .andExpect(jsonPath("$[0].total_deaths", is(data.getTotal_tests())));
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
                .andExpect(jsonPath("$[0].new_cases", is(data.getNew_cases())))
                .andExpect(jsonPath("$[0].active_cases", is(data.getActive_cases())))
                .andExpect(jsonPath("$[0].recovered_cases", is(data.getRecovered_cases())))
                .andExpect(jsonPath("$[0].critical_cases", is(data.getCritical_cases())))
                .andExpect(jsonPath("$[0].total_cases", is(data.getTotal_cases())))
                .andExpect(jsonPath("$[0].total_deaths", is(data.getTotal_deaths())))
                .andExpect(jsonPath("$[0].new_deaths", is(data.getNew_deaths())))
                .andExpect(jsonPath("$[0].total_deaths", is(data.getTotal_tests())));
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

        data.setActive_cases(4);
        data.setCritical_cases(4);
        data.setNew_cases("+2");
        data.setTotal_cases(10);
        data.setRecovered_cases(2);
        data.setNew_deaths("+2");
        data.setTotal_deaths(4);
        data.setTotal_tests(4);

        return rep.saveAndFlush(data);
    }

}
