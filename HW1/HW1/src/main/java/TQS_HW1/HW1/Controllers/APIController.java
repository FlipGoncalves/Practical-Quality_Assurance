package TQS_HW1.HW1.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataCountryRepository;
import TQS_HW1.HW1.Services.CovidDataCountryService;

@RestController
@RequestMapping("/api")
public class APIController {
    @Autowired
    private CovidDataCountryService service;

    @Autowired
    private CovidDataCountryRepository repository;

    // @Autowired
    // private UserRepository user_rep;

    @GetMapping("/all_data")
    public List<CovidDataCountry> getData(@RequestParam(value = "data", required = false) String data) {
        return repository.findAll();
    }

    @GetMapping("/get_data/{country}/{date}")
    public CovidDataCountry getDataByCountry(@PathVariable(value = "country" ) String country, @PathVariable(value = "date" ) String date) {
        return service.getDataByCountry(country, date);
    }

    // @PostMapping("/insert")
    // public Tweet insertTweet(@Valid @RequestBody Data data){
    //     return service.saveData(data);
    // }
}