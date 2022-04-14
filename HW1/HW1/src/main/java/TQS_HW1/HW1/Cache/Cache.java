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
        log.info("Getting Covid data for {} for the day {} in the cache", country, date);
        CovidDataCountry data = covidcountry_rep.findByCountryAndDay(country, date).orElse(null);

        if (data != null) {
            if (hasExpiredCountry(data)) {
                log.info("Data expired in the cache");
                covidcountry_rep.delete(data);
                return null;
            } else {
                log.info("Data retrieved from cache");
                return data;
            }
        } else {
            log.info("Covid Data not found in the cache");
            return null;
        }
    }

    public CovidDataCountry saveDataCountry(CovidDataCountry data) {
        CovidDataCountry datacheck = covidcountry_rep.findByCountryAndDay(data.getCountry(), data.getDay()).orElse(null);
        CovidDataCountry result;
        if (datacheck == null) {
            result = covidcountry_rep.saveAndFlush(data);
            log.info("Stored Data {} on cache", result);
        } else {
            result = datacheck;
            log.info("Measurement {} was already on cache", result);
        }
        return result;
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
}
