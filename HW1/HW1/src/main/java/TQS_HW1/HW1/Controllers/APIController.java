package TQS_HW1.HW1.Controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TQS_HW1.HW1.Cache.Cache;
import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataCountryRepository;
import TQS_HW1.HW1.Repository.CovidDataRepository;
import TQS_HW1.HW1.Services.CovidDataCountryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api")
public class APIController {
    private static final Logger log = LoggerFactory.getLogger(APIController.class);

    @Autowired
    private CovidDataCountryService service;

    @Autowired
    private CovidDataRepository repository_data;

    @Autowired
    private CovidDataCountryRepository country_rep;
    
    @GetMapping("/all_data")
    public List<CovidData> getData(@RequestParam(value = "data", required = false) String data) throws ParseException {
        log.info("GET Request -> All Covid Data");
        List<CovidData> covid = service.getAllData();
        repository_data.saveAll(covid);
        return covid;
    }

    @GetMapping("/get_data/{country}/{date}")
    public CovidDataCountry getDataByCountry(@PathVariable(value = "country" ) String country, @PathVariable(value = "date" ) String date) throws ParseException, IOException {
        log.info("GET Request -> Covid Data by country: {}, and date: {}", country, date);
        return service.getDataByCountry(country, date);
    }

    @GetMapping("/cache_statistics")
    public String getCacheStatistics() {
        log.info("GET Request -> All Cache Statistics");
        String jsonString = new JSONObject()
                  .put("hits", Cache.getHits())
                  .put("misses", Cache.getMisses())
                  .put("save_requests", Cache.getSave_requests())
                  .put("get_requests", Cache.getGet_requests())
                  .put("delete_requests", Cache.getDelete_requests())
                  .toString();
        return jsonString;
    }

    @GetMapping("/cache_data")
    public List<CovidDataCountry> getCachedData() {
        log.info("GET Request -> All Cached Data");
        return country_rep.findAll();
    }

    @GetMapping("/get/country/{country}")
    public List<CovidDataCountry> getDataByCountry(@PathVariable(value = "country" ) String country) {
        log.info("GET Request -> Covid Data by country: {}", country);
        return country_rep.findAllByCountry(country);
    }

    @GetMapping("/get/continent/{continent}")
    public List<CovidDataCountry> getDataByContinent(@PathVariable(value = "continent" ) String continent) {
        log.info("GET Request -> Covid Data by continent: {}", continent);
        return country_rep.findAllByContinent(continent);
    }

    @GetMapping("/get/day/{day}")
    public List<CovidDataCountry> getDataByDay(@PathVariable(value = "day" ) String day) throws ParseException {
        log.info("GET Request -> Covid Data by day: {}", day);
        return country_rep.findAllByDay(day);
    }
}