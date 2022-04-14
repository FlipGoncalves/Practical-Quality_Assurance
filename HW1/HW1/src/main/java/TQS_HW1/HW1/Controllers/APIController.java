package TQS_HW1.HW1.Controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataRepository;
import TQS_HW1.HW1.Services.CovidDataCountryService;

@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    private CovidDataCountryService service;

    @Autowired
    private CovidDataRepository repository_data;

    
    @GetMapping("/all_data")
    public List<CovidData> getData(@RequestParam(value = "data", required = false) String data) {
        List<CovidData> covid = service.getAllData();
        repository_data.saveAll(covid);
        return covid;
    }

    @GetMapping("/get_data/{country}/{date}")
    public CovidDataCountry getDataByCountry(@PathVariable(value = "country" ) String country, @PathVariable(value = "date" ) String date) throws ParseException {
        return service.getDataByCountry(country, date);
    }
}