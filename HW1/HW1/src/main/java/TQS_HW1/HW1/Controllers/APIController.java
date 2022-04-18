package TQS_HW1.HW1.Controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TQS_HW1.HW1.Cache.Cache;
import TQS_HW1.HW1.Exceptions.BadRequestException;
import TQS_HW1.HW1.Models.CacheView;
import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataCountryRepository;
import TQS_HW1.HW1.Repository.CovidDataRepository;
import TQS_HW1.HW1.Services.CovidDataCountryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public ResponseEntity<List<CovidData>> getData(@RequestParam(value = "data", required = false) String data) throws ParseException, BadRequestException {
        log.info("GET Request -> All Covid Data");
        List<CovidData> covid = service.getAllData();
        if (covid.isEmpty()) {
            throw new BadRequestException("Get All Data ERROR");
        }
        repository_data.saveAll(covid);
        return new ResponseEntity<>(covid, HttpStatus.OK);
    }

    @GetMapping("/get_data")
    public ResponseEntity<CovidDataCountry> getDataByCountry(@RequestParam(value = "country", required = false) String country, @RequestParam(value = "date", required = false) String date) throws ParseException, IOException, BadRequestException {
        log.info("GET Request -> Covid Data by country: {}, and date: {}", country, date);

        CovidDataCountry data = service.getDataByCountry(country, date);
        if (data == null) {
            throw new BadRequestException("Get Data ERROR");
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/cache_statistics")
    public ResponseEntity<CacheView> getCacheStatistics() {
        log.info("GET Request -> All Cache Statistics");
        CacheView jsonString = new CacheView(Cache.getHits(), Cache.getMisses(), Cache.getGet_requests(), Cache.getSave_requests(), Cache.getDelete_requests());

        return new ResponseEntity<>(jsonString, HttpStatus.OK);
    }

    @GetMapping("/cache_data")
    public ResponseEntity<List<CovidDataCountry>> getCachedData() {
        log.info("GET Request -> All Cached Data");
        List<CovidDataCountry> data = country_rep.findAll();

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/get/country")
    public ResponseEntity<List<CovidDataCountry>> getDataByCountry(@RequestParam(value = "country", required = true) String country) throws BadRequestException {
        log.info("GET Request -> Covid Data by country: {}", country);
        List<CovidDataCountry> data = country_rep.findAllByCountry(country);
        if (data.isEmpty()) {
            throw new BadRequestException("Get Country ERROR");
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/get/continent")
    public ResponseEntity<List<CovidDataCountry>> getDataByContinent(@RequestParam(value = "continent", required = true) String continent) throws BadRequestException {
        log.info("GET Request -> Covid Data by continent: {}", continent);
        List<CovidDataCountry> data = country_rep.findAllByContinent(continent);
        if (data.isEmpty()) {
            throw new BadRequestException("Get Continent ERROR");
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/get/day")
    public ResponseEntity<List<CovidDataCountry>> getDataByDay(@RequestParam(value = "day", required = true) String day) throws ParseException, BadRequestException {
        log.info("GET Request -> Covid Data by day: {}", day);
        List<CovidDataCountry> data = country_rep.findAllByDay(day);
        if (data.isEmpty()) {
            throw new BadRequestException("Get Day ERROR");
        }
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}