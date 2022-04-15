package TQS_HW1.HW1.Cache;

import java.util.Date;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Repository.CovidDataRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CacheAllData {

    private static int hits;
    private static int misses;
    private static int get_requests;
    private static int save_requests;
    private static int delete_requests;

    @Autowired
    CovidDataRepository covidcountry_rep;

    private static final Logger log = LoggerFactory.getLogger(Cache.class);
    private int timeToLive; // in seconds

    public CacheAllData(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public CacheAllData() {
        this.timeToLive = 120;
    }

    public List<CovidData> getAllData() throws ParseException {
        get_requests++;

        log.info("Getting All Covid data in the cache");
        List<CovidData> d = covidcountry_rep.findAll();

        if (!d.isEmpty()) {
            if (hasExpired(d)) {
                log.info("Data {} expired in the cache", d.getClass());
                misses++;
                deleteDatafromCache(d);
                return null;
            } else {
                log.info("Data {} retrieved from cache", d.getClass());
                hits++;
                return d;
            }
        } else {
            log.info("Covid Data not found in the cache");
            misses++;          
            return null;                                       
        }
    }

    public List<CovidData> saveData(List<CovidData> data) {
        List<CovidData> datacheck = covidcountry_rep.findAll();
        List<CovidData> result = null;
        save_requests++;

        if (datacheck.isEmpty()) {
            result = covidcountry_rep.saveAllAndFlush(data);
            log.info("Stored Data {} on cache", result.getClass());
            hits++;
        } else if (datacheck.size() != data.size()) {
            // do add for the different ones
        } else {
            result = datacheck;
            log.info("Covid Data {} was already on cache", result.getClass());
            misses++;
        }

        return result;
    }

    public void deleteDatafromCache(List<CovidData> data)  {
        delete_requests++;
        covidcountry_rep.deleteAll(data);
    }

    public boolean hasExpired(List<CovidData> data) throws ParseException {
        log.info("Checking if data {} is expired", data.getClass());
        Date expiredDate = new Date(System.currentTimeMillis() - this.timeToLive * 1000);
        Date dataDate = data.get(0).getObject_created();
        return dataDate.before(expiredDate);
    }

    @Scheduled(fixedRate = 60 * 1000)
    public void cleanCacheTimeout() {
        log.info("Running scheduled method to clean expired cached data");
        Date expiredDate = new Date(System.currentTimeMillis() - this.timeToLive * 1000);
        List<CovidData> expiredCovidData = covidcountry_rep.findAllByDateCreatedIsLessThanEqual(expiredDate);

        for (CovidData data : expiredCovidData) {
            log.info("Deleting expired Covid Data: {}", data.getClass());
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
