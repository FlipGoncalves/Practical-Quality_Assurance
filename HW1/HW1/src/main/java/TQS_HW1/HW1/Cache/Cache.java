package TQS_HW1.HW1.Cache;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.text.ParseException;

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
    private static int get_requests = 0;
    private static int save_requests = 0;
    private static int delete_requests = 0;

    @Autowired
    CovidDataCountryRepository covidcountry_rep;

    @Autowired
    CovidDataRepository covid_rep;

    private static final Logger log = LoggerFactory.getLogger(Cache.class);
    private int timeToLive; // in seconds

    public Cache(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public Cache() {
        this.timeToLive = 120;
    }

    public CovidDataCountry getDataByCountry(String country, String date) throws ParseException {
        get_requests++;

        log.info("Getting Covid data for {} for the day {} in the cache", country, date);
        CovidDataCountry data = covidcountry_rep.findByCountryAndDay(country, date).orElse(null);

        if (data != null) {
            if (hasExpiredCountry(data)) {
                log.info("Data expired in the cache");
                misses++;
                deleteDatafromCache(data);
                return null;
            } else {
                log.info("Data retrieved from cache");
                hits++;
                return data;
            }
        } else {
            log.info("Covid Data not found in the cache");
            misses++;                                               
            return null;
        }
    }

    public CovidDataCountry saveDataCountry(CovidDataCountry data) {
        save_requests++;

        CovidDataCountry datacheck = covidcountry_rep.findByCountryAndDay(data.getCountry(), data.getDay()).orElse(null);
        CovidDataCountry result;
    
        if (datacheck == null) {
            result = covidcountry_rep.saveAndFlush(data);
            log.info("Stored Data {} on cache", result);
            hits++;
        } else {
            result = datacheck;
            log.info("Covid Data {} was already on cache", result);
            misses++;
        }
        return result;
    }

    public void deleteDatafromCache(CovidDataCountry data)  {
        delete_requests++;

        covidcountry_rep.delete(data);
    }

    public boolean hasExpiredCountry(CovidDataCountry data) throws ParseException {
        log.info("Checking if data {} is expired", data);
        Date expiredDate = new Date(System.currentTimeMillis() - this.timeToLive * 1000);
        Date dataDate = data.getObject_created();
        return dataDate.before(expiredDate);
    }

    public List<CovidData> getAllData() throws ParseException {
        get_requests++;

        log.info("Getting All Covid data in the cache");
        List<CovidData> d = covid_rep.findAll();

        if (!d.isEmpty()) {
            if (hasExpiredCountry(d)) {
                log.info("Data {} expired in the cache", d.getClass());
                misses++;
                deleteDatafromCache(d);
                return Arrays.asList();
            } else {
                log.info("Data {} retrieved from cache", d.getClass());
                hits++;
                return d;
            }
        } else {
            log.info("Covid Data not found in the cache");
            misses++;          
            return Arrays.asList();                               
        }
    }

    public List<CovidData> saveData(List<CovidData> data) {
        List<CovidData> datacheck = covid_rep.findAll();
        List<CovidData> result = null;
        save_requests++;

        if (datacheck.isEmpty()) {
            result = covid_rep.saveAllAndFlush(data);
            log.info("Stored Data {} on cache", result.getClass());
            hits++;
        } else {
            result = datacheck;
            log.info("Covid Data {} was already on cache", result.getClass());
            misses++;
        }

        return result;
    }

    public void deleteDatafromCache(List<CovidData> data)  {
        delete_requests++;
        covid_rep.deleteAll(data);
    }

    public boolean hasExpiredCountry(List<CovidData> data) throws ParseException {
        log.info("Checking if data {} is expired", data.getClass());
        Date expiredDate = new Date(System.currentTimeMillis() - this.timeToLive * 1000);
        Date dataDate = data.get(0).getObject_created();
        return dataDate.before(expiredDate);
    }




    public static int getHits() {
        return hits;
    }

    public static int getMisses() {
        return misses;
    }

    public static int getGet_requests() {
        return get_requests;
    }

    public static int getSave_requests() {
        return save_requests;
    }

    public static int getDelete_requests() {
        return delete_requests;
    }

    // Testing issues
    public static void setAll() {
        Cache.setHits(0);
        Cache.setDelete_requests(0);
        Cache.setGet_requests(0);
        Cache.setMisses(0);
        Cache.setSave_requests(0);
    }

    public static void setHits(int hits) {
        Cache.hits = hits;
    }

    public static void setMisses(int misses) {
        Cache.misses = misses;
    }

    public static void setGet_requests(int get_requests) {
        Cache.get_requests = get_requests;
    }

    public static void setSave_requests(int save_requests) {
        Cache.save_requests = save_requests;
    }

    public static void setDelete_requests(int delete_requests) {
        Cache.delete_requests = delete_requests;
    }
}
