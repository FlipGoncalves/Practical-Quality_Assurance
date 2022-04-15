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
import TQS_HW1.HW1.Cache.CacheAllData;
import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Resolver.CovidDataCountryResolver;
import TQS_HW1.HW1.Resolver.CovidDataResolver;
import TQS_HW1.HW1.Services.CovidDataCountryService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class ServiceTest {
    CovidDataCountry coviddata;

    @Mock
    private Cache cache;

    @Mock
    private CacheAllData cacheall;

    @Mock
    private CovidDataCountryResolver coviddata_resolver;

    @Mock
    private CovidDataResolver covid_resolver;

    @InjectMocks
    private CovidDataCountryService service;

    @BeforeEach
    void setUp() {
        this.coviddata = new CovidDataCountry();
        this.coviddata.setActive_cases(200);
        this.coviddata.setCritical_cases(5);
        this.coviddata.setNew_cases("+45");
        this.coviddata.setRecovered_cases(56);
        this.coviddata.setTotal_cases(5600);
        this.coviddata.setTotal_tests(4550000);
        this.coviddata.setNew_deaths("+6");
        this.coviddata.setTotal_deaths(45);
        this.coviddata.setCountry("Portugal");
        this.coviddata.setContinent("Europe");
        this.coviddata.setDay("2021-04-11");
    }


    @Test
    public void testGetValidCovidDataCached() throws ParseException, IOException {
        when(cache.getDataByCountry("Portugal", "2021-04-11")).thenReturn(this.coviddata);

        CovidDataCountry found = service.getDataByCountry("Portugal", "2021-04-11");

        verify(cache, times(1)).getDataByCountry(anyString(), anyString());
        assertEquals(found, this.coviddata);
    }

    @Test
    public void testGetValidAllCovidDataCached() throws IOException, ParseException {
        List<CovidData> mock = Arrays.asList(new CovidData[]{new CovidData("Portugal")});
        when(cacheall.getAllData()).thenReturn(mock);

        List<CovidData> found = service.getAllData();

        assertNotNull(found);
        assertThat(found.size()).isGreaterThan(0);

        verify(cacheall, times(1)).getAllData();
    }

    @Test
    public void testGetValidCovidData() throws ParseException, IOException {
        when(coviddata_resolver.getDataByCountry("Portugal", "2021-04-11")).thenReturn(this.coviddata);
        when(cache.getDataByCountry("Portugal", "2021-04-11")).thenReturn(null);

        CovidDataCountry found = service.getDataByCountry("Portugal", "2021-04-11");

        verify(coviddata_resolver, times(1)).getDataByCountry(anyString(), anyString());
        assertEquals(found, this.coviddata);
    }

    @Test
    public void testGetValidAllCovidData() throws IOException, ParseException {
        List<CovidData> mock = Arrays.asList(new CovidData[]{new CovidData("Portugal")});
        when(covid_resolver.getOverallData()).thenReturn(mock);
        when(cacheall.getAllData()).thenReturn(null);

        List<CovidData> found = service.getAllData();

        assertNotNull(found);
        assertThat(found.size()).isGreaterThan(0);

        verify(covid_resolver, times(1)).getOverallData();
    }

    @Test
    public void testGetCoviDataCountryError() throws IOException {
        when(coviddata_resolver.getDataByCountry("asasdasfaasd", "2021-04-11")).thenThrow(IOException.class);

        assertThrows(IOException.class, () -> { service.getDataByCountry("asasdasfaasd", "2021-04-11"); }, "Country not found.");
    }

    @Test
    public void testGetCoviDataDayError() throws IOException {
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
    public void testAPINotAvailable() throws ParseException, IOException {

        when(coviddata_resolver.getDataByCountry("Portugal", "2021-04-11")).thenThrow(BadRequest.class);

        assertThrows(BadRequest.class, () -> { service.getDataByCountry("Portugal", "2021-04-11"); }, "All services unavailable.");
    }
}