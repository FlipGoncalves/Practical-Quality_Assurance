package TQS_HW1.HW1.Cache;

import java.util.Date;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataCountryRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Cache {

    private static int hits;
    private static int misses;
    private static int get_requests;
    private static int save_requests;
    private static int delete_requests;

    @Autowired
    CovidDataCountryRepository covidcountry_rep;

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
            misses++;                                                   // review this 
            return null;
        }
    }

    public CovidDataCountry saveDataCountry(CovidDataCountry data) {
        CovidDataCountry datacheck = covidcountry_rep.findByCountryAndDay(data.getCountry(), data.getDay()).orElse(null);
        CovidDataCountry result;
        save_requests++;
        if (datacheck == null) {
            result = covidcountry_rep.saveAndFlush(data);
            log.info("Stored Data {} on cache", result);
            hits++;
        } else {
            result = datacheck;
            log.info("Measurement {} was already on cache", result);
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

    @Scheduled(fixedRate = 60 * 1000)
    public void cleanCacheTimeout() {
        log.info("Running scheduled method to clean expired cached data");
        Date expiredDate = new Date(System.currentTimeMillis() - this.timeToLive * 1000);
        List<CovidDataCountry> expiredCovidData = covidcountry_rep.findAllByDayIsLessThanEqual(expiredDate);

        for (CovidDataCountry data : expiredCovidData) {
            log.info("Deleting expired Covid Data: {}", data);
            covidcountry_rep.delete(data);
        }

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

}
