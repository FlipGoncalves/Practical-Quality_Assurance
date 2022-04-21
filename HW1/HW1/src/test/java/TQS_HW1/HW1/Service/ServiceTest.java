package TQS_HW1.HW1.Service;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import TQS_HW1.HW1.Cache.Cache;
import TQS_HW1.HW1.Exceptions.APINotRespondsException;
import TQS_HW1.HW1.Exceptions.BadRequestException;
import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Resolver.CovidDataCountryResolver;
import TQS_HW1.HW1.Resolver.CovidDataResolver;
import TQS_HW1.HW1.Services.CovidDataCountryService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class ServiceTest {
    CovidDataCountry coviddata;

    @Mock
    private Cache cache;

    @Mock
    private CovidDataCountryResolver coviddata_resolver;

    @Mock
    private CovidDataResolver covid_resolver;

    @InjectMocks
    private CovidDataCountryService service;

    @BeforeEach
    void setUp() {
        this.coviddata = new CovidDataCountry();
        this.coviddata.setActiveCases(200);
        this.coviddata.setCriticalCases(5);
        this.coviddata.setNewCases("+45");
        this.coviddata.setRecoveredCases(56);
        this.coviddata.setTotalCases(5600);
        this.coviddata.setTotalTests(4550000);
        this.coviddata.setNewDeaths("+6");
        this.coviddata.setTotalDeaths(45);
        this.coviddata.setCountry("Portugal");
        this.coviddata.setContinent("Europe");
        this.coviddata.setDay("2021-04-11");
    }


    @Test
    void testGetValidCovidDataCached() throws ParseException, IOException, APINotRespondsException, BadRequestException, InterruptedException {
        when(cache.getDataByCountry("Portugal", "2021-04-11")).thenReturn(this.coviddata);

        CovidDataCountry found = service.getDataByCountry("Portugal", "2021-04-11");

        verify(cache, times(1)).getDataByCountry(anyString(), anyString());
        assertEquals(found, this.coviddata);
    }

    @Test
    void testGetValidAllCovidDataCached() throws IOException, ParseException, InterruptedException {
        List<CovidData> mock = Arrays.asList(new CovidData[]{new CovidData("Portugal")});
        when(cache.getAllData()).thenReturn(mock);

        List<CovidData> found = service.getAllData();

        assertNotNull(found);
        assertFalse(found.isEmpty());

        verify(cache, times(1)).getAllData();
    }

    @Test
    void testGetValidCovidData() throws ParseException, IOException, APINotRespondsException, BadRequestException, InterruptedException {
        when(coviddata_resolver.getDataByCountry("Portugal", "2021-04-11")).thenReturn(this.coviddata);
        when(cache.getDataByCountry("Portugal", "2021-04-11")).thenReturn(null);

        CovidDataCountry found = service.getDataByCountry("Portugal", "2021-04-11");

        verify(coviddata_resolver, times(1)).getDataByCountry(anyString(), anyString());
        assertEquals(found, this.coviddata);
    }

    @Test
    void testGetValidAllCovidData() throws IOException, ParseException, APINotRespondsException, BadRequestException, InterruptedException {
        List<CovidData> mock = Arrays.asList(new CovidData[]{new CovidData("Portugal")});
        when(covid_resolver.getOverallData()).thenReturn(mock);
        when(cache.getAllData()).thenReturn(Arrays.asList());

        List<CovidData> found = service.getAllData();

        assertNotNull(found);
        assertFalse(found.isEmpty());

        verify(covid_resolver, times(1)).getOverallData();
    }

    @Test
    void testGetCoviDataCountryError() throws IOException, APINotRespondsException, BadRequestException, InterruptedException {
        when(coviddata_resolver.getDataByCountry("asasdasfaasd", "2021-04-11")).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> { service.getDataByCountry("asasdasfaasd", "2021-04-11"); }, "Country not found.");
    }

    @Test
    void testGetCoviDataDayError() throws IOException, APINotRespondsException, BadRequestException, InterruptedException {
        when(coviddata_resolver.getDataByCountry("Portugal", "2021-13-11")).thenThrow(IOException.class);
        when(coviddata_resolver.getDataByCountry("Portugal", "2021-04-37")).thenThrow(IOException.class);
        when(coviddata_resolver.getDataByCountry("Portugal", "0-04-11")).thenThrow(IOException.class);
        when(coviddata_resolver.getDataByCountry("Portugal", "2022-12-12")).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> { service.getDataByCountry("Portugal", "2021-13-11"); }, "Date not found.");
        assertThrows(IOException.class, () -> { service.getDataByCountry("Portugal", "2021-04-37"); }, "Date not found.");
        assertThrows(IOException.class, () -> { service.getDataByCountry("Portugal", "0-04-11"); }, "Date not found.");
        assertThrows(IOException.class, () -> { service.getDataByCountry("Portugal", "2022-12-12"); }, "Date not found.");
    }

    @Test
    void testAPINotAvailable() throws ParseException, IOException, APINotRespondsException, BadRequestException, InterruptedException {

        when(coviddata_resolver.getDataByCountry("Portugal", "2021-04-11")).thenThrow(BadRequest.class);

        assertThrows(BadRequest.class, () -> { service.getDataByCountry("Portugal", "2021-04-11"); }, "All services unavailable.");
    }

    @Test
    void testGetValidAllCovidData_Interrupted() throws IOException, ParseException, APINotRespondsException, BadRequestException, InterruptedException {
        Thread.currentThread().interrupt();
        when(covid_resolver.getOverallData()).thenThrow(InterruptedException.class);

        assertThrows(InterruptedException.class, () -> { service.getAllData(); });
    }

    @Test
    void testGetValidAllCovidData_Error() throws IOException, ParseException, APINotRespondsException, BadRequestException, InterruptedException {
        when(covid_resolver.getOverallData()).thenThrow(NullPointerException.class);
        when(cache.getAllData()).thenReturn(Arrays.asList());

        List<CovidData> found = service.getAllData();

        assertTrue(found.isEmpty());

        verify(covid_resolver, times(1)).getOverallData();
    }
}