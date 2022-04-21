package TQS_HW1.HW1.Controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import TQS_HW1.HW1.Exceptions.APINotRespondsException;
import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataRequestModel;
import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Services.CovidDataCountryService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class ViewController {
	private static final Logger log = LoggerFactory.getLogger(ViewController.class);

	@Autowired
    private CovidDataCountryService service;

	@GetMapping("/home")
	public String home(Model model) {
		log.info("Get home template");
		return "home";
	}

	@PostMapping("/home")
	public String submitHome(@ModelAttribute("covidCountry") CovidDataRequestModel country2, Model model) throws ParseException, IOException, APINotRespondsException, InterruptedException {
		log.info("-- Start -- Get data for home template");
		// date
		String date;
		int year = 2021;
		date = year+"-";
		int month = LocalDateTime.now().getMonth().getValue();
		if (month < 10) {
			date += "0"+month+"-";
		} else {
			date += month+"-";
		}
		int day = LocalDateTime.now().getDayOfMonth()-1;
		if (day < 10) {
			date += "0"+day;
		} else {
			date += day;
		}

		CovidData country = new CovidData(country2.getCountry());

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  

		Date dateDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateDate);

		List<CovidDataCountry> coviddata = new ArrayList<>();

		log.info("Get data for country {}", country.getCountry());
		for (int i = 0; i < 5; i++) {
			String strDate = dateFormat.format(dateDate);
			log.info("Get data for day {}", strDate);

			CovidDataCountry data = service.getDataByCountry(country.getCountry(), strDate);

			if (data == null) {
				CovidDataCountry covid = new CovidDataCountry();
				covid.setDay(strDate);
				covid.setCountry(country.getCountry());
				covid.setNewCases("0");
				covid.setNewDeaths("0");
				coviddata.add(covid);
			} else {
				coviddata.add(data);
			}

			calendar.add(Calendar.DATE, -1);
			dateDate = calendar.getTime();
		}

		log.info("Data for country {}: {}", country.getCountry(), coviddata);

		model.addAttribute("Country", coviddata);

		log.info("-- End -- Get data for home template");
		return "home";
	}

	@GetMapping("/")
	public String index(Model model) throws InterruptedException {
		log.info("Get index template");
		model.addAttribute("Country", service.getAllData());
		return "index";
	}
}
