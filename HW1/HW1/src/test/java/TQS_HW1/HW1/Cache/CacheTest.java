package TQS_HW1.HW1.Cache;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataCountryRepository;
import TQS_HW1.HW1.Repository.CovidDataRepository;

import static org.mockito.Mockito.*;

import java.text.ParseException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CacheTest {
    CovidDataCountry coviddata;
    CovidData alldata;

    @Mock
    CovidDataCountryRepository coviddata_rep;

    @Mock
    CovidDataRepository alldata_rep;

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


        this.alldata = new CovidData("Portugal");
    }

    @AfterEach
    void tear() {

        // when running all the tests at the same time
        Cache.setAll();
    }

    @Test
    void testGetValidCountryData() throws ParseException {

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

        assertEquals(1, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(1, Cache.getGet_requests());
        assertEquals(0, Cache.getSave_requests());
        assertEquals(0, Cache.getDelete_requests());

        verify(coviddata_rep, times(1)).findByCountryAndDay(anyString(), anyString());
    }

    @Test
    void testGetInValidCountryData() throws ParseException {

        when(coviddata_rep.findByCountryAndDay("Portugal", "2021-04-11"))
                .thenReturn(Optional.empty());
        
        CovidDataCountry cahedData = cache.getDataByCountry("Portugal", "2021-04-11");

        assertNull(cahedData);

        assertEquals(0, Cache.getHits());
        assertEquals(1, Cache.getMisses());
        assertEquals(1, Cache.getGet_requests());
        assertEquals(0, Cache.getSave_requests());
        assertEquals(0, Cache.getDelete_requests());

        verify(coviddata_rep, times(1)).findByCountryAndDay(anyString(), anyString());
    }

    @Test
    void testGetValidAllData() throws ParseException {
        when(alldata_rep.findAll())
                .thenReturn(Arrays.asList(this.alldata));

        List<CovidData> cahedData = cache.getAllData();

        assertFalse(cahedData.isEmpty());
        assertEquals(1, cahedData.size());

        assertEquals(this.alldata.getCountry(), cahedData.get(0).getCountry());

        assertEquals(1, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(1, Cache.getGet_requests());
        assertEquals(0, Cache.getSave_requests());
        assertEquals(0, Cache.getDelete_requests());

        verify(alldata_rep, times(1)).findAll();
    }

    @Test
    void testGetInValidAllData() throws ParseException {
        when(alldata_rep.findAll())
                .thenReturn(Arrays.asList());

        List<CovidData> cahedData = cache.getAllData();

        assertTrue(cahedData.isEmpty());

        assertEquals(0, Cache.getHits());
        assertEquals(1, Cache.getMisses());
        assertEquals(1, Cache.getGet_requests());
        assertEquals(0, Cache.getSave_requests());
        assertEquals(0, Cache.getDelete_requests());

        verify(alldata_rep, times(1)).findAll();
    }

    @Test
    void testSaveCacheData() {

        when(coviddata_rep.saveAndFlush(this.coviddata)).thenReturn(this.coviddata);

        assertEquals(cache.saveDataCountry(this.coviddata), this.coviddata);

        assertEquals(1, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(0, Cache.getGet_requests());
        assertEquals(1, Cache.getSave_requests());
        assertEquals(0, Cache.getDelete_requests());

        verify(coviddata_rep, times(1)).saveAndFlush(this.coviddata);
    }

    @Test
    void testSaveCacheDataExistent() {

        when(coviddata_rep.findByCountryAndDay("Portugal", "2021-04-11"))
                .thenReturn(Optional.ofNullable(this.coviddata));

        assertEquals(cache.saveDataCountry(this.coviddata), this.coviddata);

        assertEquals(0, Cache.getHits());
        assertEquals(1, Cache.getMisses());
        assertEquals(0, Cache.getGet_requests());
        assertEquals(1, Cache.getSave_requests());
        assertEquals(0, Cache.getDelete_requests());

        verify(coviddata_rep, times(0)).saveAndFlush(this.coviddata);

    }

    @Test
    void testSaveCacheAllData() {

        when(alldata_rep.saveAllAndFlush(Arrays.asList(this.alldata))).thenReturn(Arrays.asList(this.alldata));

        assertEquals(cache.saveData(Arrays.asList(this.alldata)), Arrays.asList(this.alldata));

        assertEquals(1, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(0, Cache.getGet_requests());
        assertEquals(1, Cache.getSave_requests());
        assertEquals(0, Cache.getDelete_requests());

        verify(alldata_rep, times(1)).saveAllAndFlush(Arrays.asList(this.alldata));
    }

    @Test
    void testSaveCacheAllDataExistent() {

        when(alldata_rep.findAll()).thenReturn(Arrays.asList(this.alldata));

        assertEquals(cache.saveData(Arrays.asList(this.alldata)), Arrays.asList(this.alldata));

        assertEquals(0, Cache.getHits());
        assertEquals(1, Cache.getMisses());
        assertEquals(0, Cache.getGet_requests());
        assertEquals(1, Cache.getSave_requests());
        assertEquals(0, Cache.getDelete_requests());

        verify(alldata_rep, times(0)).saveAllAndFlush(Arrays.asList(this.alldata));

    }


    // same code for CovidDataCountry and CovidData
    @Test
    void testGetExpiredCacheData() throws ParseException {

        Date date = new Date(System.currentTimeMillis() - 605 * 1000); // A date with more that 600 sec
        this.coviddata.setObject_created(date);

        when(coviddata_rep.findByCountryAndDay("Portugal", "2021-04-11"))
                .thenReturn(Optional.of(this.coviddata));

        CovidDataCountry cahedData = cache.getDataByCountry("Portugal", "2021-04-11");

        assertNull(cahedData);

        assertEquals(0, Cache.getHits());
        assertEquals(1, Cache.getMisses());
        assertEquals(1, Cache.getGet_requests());
        assertEquals(0, Cache.getSave_requests());
        assertEquals(1, Cache.getDelete_requests());

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

        cache.deleteDatafromCache(this.coviddata);

        assertEquals(0, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(0, Cache.getGet_requests());
        assertEquals(0, Cache.getSave_requests());
        assertEquals(1, Cache.getDelete_requests());

        verify(coviddata_rep, times(1)).delete(this.coviddata);

    }
}