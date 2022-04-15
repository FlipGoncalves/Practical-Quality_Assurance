package TQS_HW1.HW1.Services;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TQS_HW1.HW1.Cache.Cache;
import TQS_HW1.HW1.Cache.CacheAllData;
import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Resolver.CovidDataCountryResolver;
import TQS_HW1.HW1.Resolver.CovidDataResolver;

@Service
public class CovidDataCountryService {

    @Autowired
    CovidDataCountryResolver resolver;

    @Autowired
    CovidDataResolver resolver_all;

    @Autowired
    Cache cache;

    @Autowired
    CacheAllData cacheall;

    public CovidDataCountry getDataByCountry(String country, String date) throws ParseException, IOException {
        CovidDataCountry cachedData = cache.getDataByCountry(country, date);
        CovidDataCountry result = null;

        if (cachedData == null) {
            try {
                result = resolver.getDataByCountry(country, date);    
            } catch (JSONException e) {
                System.err.println(e);
                return null;
            }

            cache.saveDataCountry(result);
            return result;
        }

        return cachedData;
    }

    public List<CovidData> getAllData() throws ParseException {

        List<CovidData> cachedData = cacheall.getAllData();
        List<CovidData> result = null;

        if (cachedData == null) {
            try {
                result = resolver_all.getOverallData();
            } catch (Exception e) {
                System.err.println(e);
            }

            cacheall.saveData(result);
            return result;
        }

        return cachedData;
    }
}
