package TQS_HW1.HW1.Cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataCountryRepository;

import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheTest {
    CovidDataCountry coviddata;

    @Mock
    CovidDataCountryRepository coviddata_rep;

    @InjectMocks
    Cache cache = new Cache(600);

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

    // make hits/misses/everything tests

    @Test
    void testGetValidCountryData() throws ParseException {

        // needed when running all tests simultaneously
        Cache.setAll();

        when(coviddata_rep.findByCountryAndDay("Portugal", "2021-04-11"))
                .thenReturn(Optional.of(this.coviddata));

        CovidDataCountry cahedData = cache.getDataByCountry("Portugal", "2021-04-11");

        assertNotNull(cahedData);

        assertEquals(this.coviddata.getActive_cases(), cahedData.getActive_cases());
        assertEquals(this.coviddata.getCritical_cases(), cahedData.getCritical_cases());
        assertEquals(this.coviddata.getNew_cases(), cahedData.getNew_cases());
        assertEquals(this.coviddata.getRecovered_cases(), cahedData.getRecovered_cases());
        assertEquals(this.coviddata.getTotal_cases(), cahedData.getTotal_cases());
        assertEquals(this.coviddata.getTotal_tests(), cahedData.getTotal_tests());
        assertEquals(this.coviddata.getNew_deaths(), cahedData.getNew_deaths());
        assertEquals(this.coviddata.getTotal_deaths(), cahedData.getTotal_deaths());
        assertEquals(this.coviddata.getCountry(), cahedData.getCountry());
        assertEquals(this.coviddata.getContinent(), cahedData.getContinent());
        assertEquals(this.coviddata.getDay(), cahedData.getDay());

        assertEquals(Cache.getHits(), 1);
        assertEquals(Cache.getMisses(), 0);
        assertEquals(Cache.getGet_requests(), 1);
        assertEquals(Cache.getSave_requests(), 0);
        assertEquals(Cache.getDelete_requests(), 0);

        verify(coviddata_rep, times(1)).findByCountryAndDay(anyString(), anyString());
    }

    @Test
    void testGetInValidCountryData() throws ParseException {

        // needed when running all tests simultaneously
        Cache.setAll();

        when(coviddata_rep.findByCountryAndDay("Portugal", "2021-04-11"))
                .thenReturn(Optional.empty());
        
        CovidDataCountry cahedData = cache.getDataByCountry("Portugal", "2021-04-11");

        assertNull(cahedData);

        assertEquals(Cache.getHits(), 0);
        assertEquals(Cache.getMisses(), 1);
        assertEquals(Cache.getGet_requests(), 1);
        assertEquals(Cache.getSave_requests(), 0);
        assertEquals(Cache.getDelete_requests(), 0);

        verify(coviddata_rep, times(1)).findByCountryAndDay(anyString(), anyString());
    }

    @Test
    void testGetExpiredCacheData() throws ParseException {

        // needed when running all tests simultaneously
        Cache.setAll();

        Date date = new Date(System.currentTimeMillis() - 605 * 1000); // A date with more that 600 sec
        this.coviddata.setObject_created(date);

        when(coviddata_rep.findByCountryAndDay("Portugal", "2021-04-11"))
                .thenReturn(Optional.of(this.coviddata));

        CovidDataCountry cahedData = cache.getDataByCountry("Portugal", "2021-04-11");

        assertNull(cahedData);

        assertEquals(Cache.getHits(), 0);
        assertEquals(Cache.getMisses(), 1);
        assertEquals(Cache.getGet_requests(), 1);
        assertEquals(Cache.getSave_requests(), 0);
        assertEquals(Cache.getDelete_requests(), 1);

        verify(coviddata_rep, times(1)).findByCountryAndDay(anyString(), anyString());
        verify(coviddata_rep, times(1)).delete(this.coviddata);
    }

    @Test
    void testHasExpiredValidCacheData() throws ParseException {
        assertFalse(cache.hasExpiredCountry(this.coviddata));
    }

    @Test
    void testHasExpiredInvalidCacheData() throws ParseException {
        Date date = new Date(System.currentTimeMillis() - 605 * 1000); // A date with more that 600 sec
        this.coviddata.setObject_created(date);
        assertTrue(cache.hasExpiredCountry(this.coviddata));
    }

    @Test
    void testDeleteCacheData() {

        // needed when running all tests simultaneously
        Cache.setAll();

        cache.deleteDatafromCache(this.coviddata);

        assertEquals(Cache.getHits(), 0);
        assertEquals(Cache.getMisses(), 0);
        assertEquals(Cache.getGet_requests(), 0);
        assertEquals(Cache.getSave_requests(), 0);
        assertEquals(Cache.getDelete_requests(), 1);

        verify(coviddata_rep, times(1)).delete(this.coviddata);

    }

    @Test
    void testSaveCacheData() {

        // needed when running all tests simultaneously
        Cache.setAll();

        when(coviddata_rep.saveAndFlush(this.coviddata)).thenReturn(this.coviddata);

        assertEquals(cache.saveDataCountry(this.coviddata), this.coviddata);

        assertEquals(Cache.getHits(), 1);
        assertEquals(Cache.getMisses(), 0);
        assertEquals(Cache.getGet_requests(), 0);
        assertEquals(Cache.getSave_requests(), 1);
        assertEquals(Cache.getDelete_requests(), 0);

        verify(coviddata_rep, times(1)).saveAndFlush(this.coviddata);
    }

    @Test
    void testSaveCacheDataExistent() {

        // needed when running all tests simultaneously
        Cache.setAll();

        when(coviddata_rep.findByCountryAndDay("Portugal", "2021-04-11"))
                .thenReturn(Optional.ofNullable(this.coviddata));

        assertEquals(cache.saveDataCountry(this.coviddata), this.coviddata);

        assertEquals(Cache.getHits(), 0);
        assertEquals(Cache.getMisses(), 1);
        assertEquals(Cache.getGet_requests(), 0);
        assertEquals(Cache.getSave_requests(), 1);
        assertEquals(Cache.getDelete_requests(), 0);

        verify(coviddata_rep, times(0)).saveAndFlush(this.coviddata);

    }
}