package TQS_HW1.HW1.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Resolver.CovidDataCountryResolver;

@Service
public class CovidDataCountryService {

    @Autowired
    CovidDataCountryResolver resolver;

    public CovidDataCountry getDataByCountry(String country, String date) {
        // Measurement result = cache.getMeasurement(lat, lon, location);
        // cache stuff here

        CovidDataCountry result = null;

        // if its not in cache
        try {
            result = resolver.getDataByCountry(country, date);
            // store in cache

        } catch (Exception e) {
            System.err.println(e);
        }

        return result;
    }
}
