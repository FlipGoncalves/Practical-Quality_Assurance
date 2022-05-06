package TQS_HW1.HW1.Cache;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataCountryRepository;
import TQS_HW1.HW1.Repository.CovidDataRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Cache {

    private static int hits = 0;
    private static int misses = 0;
    private static int getRequests = 0;
    private static int saveRequests = 0;
    private static int deleteRequests = 0;

    @Autowired
    CovidDataCountryRepository covidcountryRepository;

    @Autowired
    CovidDataRepository covidRepository;

    private static final Logger log = LoggerFactory.getLogger(Cache.class);
    private int timeToLive; // in seconds

    public Cache(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public Cache() {
        this.timeToLive = 120;
    }

    public CovidDataCountry getDataByCountry(String country, String date) {
        incrGet();

        log.info("Getting Covid data for {} for the day {} in the cache", country, date);
        CovidDataCountry data = covidcountryRepository.findByCountryAndDay(country, date).orElse(null);

        if (data != null) {
            if (hasExpiredCountry(data)) {
                log.info("Data expired in the cache");
                incrMiss();
                deleteDatafromCache(data);
                return null;
            } else {
                log.info("Data retrieved from cache");
                incrHits();
                return data;
            }
        } else {
            log.info("Covid Data not found in the cache");
            incrMiss();                                               
            return null;
        }
    }

    public CovidDataCountry saveDataCountry(CovidDataCountry data) {
        incrSave();

        CovidDataCountry datacheck = covidcountryRepository.findByCountryAndDay(data.getCountry(), data.getDay()).orElse(null);
        CovidDataCountry result;
    
        if (datacheck == null) {
            result = covidcountryRepository.saveAndFlush(data);
            log.info("Stored Data {} on cache", result);
            incrHits();
        } else {
            result = datacheck;
            log.info("Covid Data {} was already on cache", result);
            incrMiss();
        }
        return result;
    }

    public void deleteDatafromCache(CovidDataCountry data)  {
        incrDel();

        covidcountryRepository.delete(data);
    }

    public boolean hasExpiredCountry(CovidDataCountry data) {
        log.info("Checking if data {} is expired", data);
        Date expiredDate = new Date(System.currentTimeMillis() - this.timeToLive * 1000);
        Date dataDate = data.getObjectCreated();
        return dataDate.before(expiredDate);
    }

    public List<CovidData> getAllData() {
        incrGet();

        log.info("Getting All Covid data in the cache");
        List<CovidData> d = covidRepository.findAll();

        if (!d.isEmpty()) {
            if (hasExpiredCountry(d)) {
                log.info("Data {} expired in the cache", d.getClass());
                incrMiss();
                deleteDatafromCache(d);
                return Arrays.asList();
            } else {
                log.info("Data {} retrieved from cache", d.getClass());
                incrHits();
                return d;
            }
        } else {
            log.info("Covid Data not found in the cache");
            incrMiss();          
            return Arrays.asList();                               
        }
    }

    public List<CovidData> saveData(List<CovidData> data) {
        List<CovidData> datacheck = covidRepository.findAll();
        List<CovidData> result = null;
        incrSave();

        if (datacheck.isEmpty()) {
            result = covidRepository.saveAllAndFlush(data);
            log.info("Stored Data {} on cache", result.getClass());
            incrHits();
        } else {
            result = datacheck;
            log.info("Covid Data {} was already on cache", result.getClass());
            incrMiss();
        }

        return result;
    }

    public void deleteDatafromCache(List<CovidData> data)  {
        incrDel();
        covidRepository.deleteAll(data);
    }

    public boolean hasExpiredCountry(List<CovidData> data) {
        log.info("Checking if data {} is expired", data.getClass());
        Date expiredDate = new Date(System.currentTimeMillis() - this.timeToLive * 1000);
        Date dataDate = data.get(0).getObjectCreated();
        return dataDate.before(expiredDate);
    }


    public static void incrHits() {
        hits++;
    }
    public static void incrMiss() {
        misses++;
    }
    public static void incrGet() {
        getRequests++;
    }
    public static void incrSave() {
        saveRequests++;
    }
    public static void incrDel() {
        deleteRequests++;
    }


    public static int getHits() {
        return hits;
    }

    public static int getMisses() {
        return misses;
    }

    public static int getgetRequests() {
        return getRequests;
    }

    public static int getsaveRequests() {
        return saveRequests;
    }

    public static int getdeleteRequests() {
        return deleteRequests;
    }

    // Testing issues
    public static void setAll() {
        Cache.setHits(0);
        Cache.setdeleteRequests(0);
        Cache.setgetRequests(0);
        Cache.setMisses(0);
        Cache.setsaveRequests(0);
    }

    public static void setHits(int hits) {
        Cache.hits = hits;
    }

    public static void setMisses(int misses) {
        Cache.misses = misses;
    }

    public static void setgetRequests(int getRequests) {
        Cache.getRequests = getRequests;
    }

    public static void setsaveRequests(int saveRequests) {
        Cache.saveRequests = saveRequests;
    }

    public static void setdeleteRequests(int deleteRequests) {
        Cache.deleteRequests = deleteRequests;
    }
}
