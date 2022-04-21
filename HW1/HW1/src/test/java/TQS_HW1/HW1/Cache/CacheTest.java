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

        assertEquals(this.coviddata.getActiveCases(), cahedData.getActiveCases());
        assertEquals(this.coviddata.getCriticalCases(), cahedData.getCriticalCases());
        assertEquals(this.coviddata.getNewCases(), cahedData.getNewCases());
        assertEquals(this.coviddata.getRecoveredCases(), cahedData.getRecoveredCases());
        assertEquals(this.coviddata.getTotalCases(), cahedData.getTotalCases());
        assertEquals(this.coviddata.getTotalTests(), cahedData.getTotalTests());
        assertEquals(this.coviddata.getNewDeaths(), cahedData.getNewDeaths());
        assertEquals(this.coviddata.getTotalDeaths(), cahedData.getTotalDeaths());
        assertEquals(this.coviddata.getCountry(), cahedData.getCountry());
        assertEquals(this.coviddata.getContinent(), cahedData.getContinent());
        assertEquals(this.coviddata.getDay(), cahedData.getDay());

        assertEquals(1, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(1, Cache.getgetRequests());
        assertEquals(0, Cache.getsaveRequests());
        assertEquals(0, Cache.getdeleteRequests());

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
        assertEquals(1, Cache.getgetRequests());
        assertEquals(0, Cache.getsaveRequests());
        assertEquals(0, Cache.getdeleteRequests());

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
        assertEquals(1, Cache.getgetRequests());
        assertEquals(0, Cache.getsaveRequests());
        assertEquals(0, Cache.getdeleteRequests());

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
        assertEquals(1, Cache.getgetRequests());
        assertEquals(0, Cache.getsaveRequests());
        assertEquals(0, Cache.getdeleteRequests());

        verify(alldata_rep, times(1)).findAll();
    }

    @Test
    void testSaveCacheData() {

        when(coviddata_rep.saveAndFlush(this.coviddata)).thenReturn(this.coviddata);

        assertEquals(cache.saveDataCountry(this.coviddata), this.coviddata);

        assertEquals(1, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(0, Cache.getgetRequests());
        assertEquals(1, Cache.getsaveRequests());
        assertEquals(0, Cache.getdeleteRequests());

        verify(coviddata_rep, times(1)).saveAndFlush(this.coviddata);
    }

    @Test
    void testSaveCacheDataExistent() {

        when(coviddata_rep.findByCountryAndDay("Portugal", "2021-04-11"))
                .thenReturn(Optional.ofNullable(this.coviddata));

        assertEquals(cache.saveDataCountry(this.coviddata), this.coviddata);

        assertEquals(0, Cache.getHits());
        assertEquals(1, Cache.getMisses());
        assertEquals(0, Cache.getgetRequests());
        assertEquals(1, Cache.getsaveRequests());
        assertEquals(0, Cache.getdeleteRequests());

        verify(coviddata_rep, times(0)).saveAndFlush(this.coviddata);

    }

    @Test
    void testSaveCacheAllData() {

        when(alldata_rep.saveAllAndFlush(Arrays.asList(this.alldata))).thenReturn(Arrays.asList(this.alldata));

        assertEquals(cache.saveData(Arrays.asList(this.alldata)), Arrays.asList(this.alldata));

        assertEquals(1, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(0, Cache.getgetRequests());
        assertEquals(1, Cache.getsaveRequests());
        assertEquals(0, Cache.getdeleteRequests());

        verify(alldata_rep, times(1)).saveAllAndFlush(Arrays.asList(this.alldata));
    }

    @Test
    void testSaveCacheAllDataExistent() {

        when(alldata_rep.findAll()).thenReturn(Arrays.asList(this.alldata));

        assertEquals(cache.saveData(Arrays.asList(this.alldata)), Arrays.asList(this.alldata));

        assertEquals(0, Cache.getHits());
        assertEquals(1, Cache.getMisses());
        assertEquals(0, Cache.getgetRequests());
        assertEquals(1, Cache.getsaveRequests());
        assertEquals(0, Cache.getdeleteRequests());

        verify(alldata_rep, times(0)).saveAllAndFlush(Arrays.asList(this.alldata));

    }

    @Test
    void testDeleteCacheData() {

        cache.deleteDatafromCache(this.coviddata);

        assertEquals(0, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(0, Cache.getgetRequests());
        assertEquals(0, Cache.getsaveRequests());
        assertEquals(1, Cache.getdeleteRequests());

        verify(coviddata_rep, times(1)).delete(this.coviddata);

    }

    @Test
    void testDeleteCacheAllData() {

        cache.deleteDatafromCache(Arrays.asList(this.alldata));

        assertEquals(0, Cache.getHits());
        assertEquals(0, Cache.getMisses());
        assertEquals(0, Cache.getgetRequests());
        assertEquals(0, Cache.getsaveRequests());
        assertEquals(1, Cache.getdeleteRequests());

        verify(alldata_rep, times(1)).deleteAll(Arrays.asList(this.alldata));

    }

    @Test
    void testGetExpiredCacheData() throws ParseException {

        Date date = new Date(System.currentTimeMillis() - 605 * 1000); // A date with more that 600 sec
        this.coviddata.setObjectCreated(date);

        when(coviddata_rep.findByCountryAndDay("Portugal", "2021-04-11"))
                .thenReturn(Optional.of(this.coviddata));

        CovidDataCountry cahedData = cache.getDataByCountry("Portugal", "2021-04-11");

        assertNull(cahedData);

        assertEquals(0, Cache.getHits());
        assertEquals(1, Cache.getMisses());
        assertEquals(1, Cache.getgetRequests());
        assertEquals(0, Cache.getsaveRequests());
        assertEquals(1, Cache.getdeleteRequests());

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
        this.coviddata.setObjectCreated(date);
        assertTrue(cache.hasExpiredCountry(this.coviddata));
    }



    @Test
    void testGetExpiredCacheAllData() throws ParseException {

        Date date = new Date(System.currentTimeMillis() - 605 * 1000); // A date with more that 600 sec
        this.alldata.setObjectCreated(date);

        when(alldata_rep.findAll())
                .thenReturn(Arrays.asList(this.alldata));

        List<CovidData> cahedData = cache.getAllData();

        assertTrue(cahedData.isEmpty());

        assertEquals(0, Cache.getHits());
        assertEquals(1, Cache.getMisses());
        assertEquals(1, Cache.getgetRequests());
        assertEquals(0, Cache.getsaveRequests());
        assertEquals(1, Cache.getdeleteRequests());

        verify(alldata_rep, times(1)).findAll();
        verify(alldata_rep, times(1)).deleteAll(Arrays.asList(this.alldata));
    }

    @Test
    void testHasExpiredValidCacheAllData() throws ParseException {
        assertFalse(cache.hasExpiredCountry(Arrays.asList(this.alldata)));
    }

    @Test
    void testHasExpiredInvalidCacheAllData() throws ParseException {
        Date date = new Date(System.currentTimeMillis() - 605 * 1000); // A date with more that 600 sec
        this.alldata.setObjectCreated(date);
        assertTrue(cache.hasExpiredCountry(Arrays.asList(this.alldata)));
    }
}