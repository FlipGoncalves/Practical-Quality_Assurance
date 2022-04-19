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
	public String submitHome(@ModelAttribute("covidCountry") CovidData country, Model model) throws ParseException, IOException, APINotRespondsException {
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

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  

		Date Date_date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(Date_date);

		List<CovidDataCountry> covid_data = new ArrayList<>();

		log.info("Get data for country {}", country.getCountry());
		for (int i = 0; i < 5; i++) {
			String strDate = dateFormat.format(Date_date);
			log.info("Get data for day {}", strDate);

			CovidDataCountry data = service.getDataByCountry(country.getCountry(), strDate);

			if (data == null) {
				CovidDataCountry covid = new CovidDataCountry();
				covid.setDay(strDate);
				covid.setCountry(country.getCountry());
				covid.setNew_cases("0");
				covid.setNew_deaths("0");
				covid_data.add(covid);
			} else {
				covid_data.add(data);
			}

			calendar.add(Calendar.DATE, -1);
			Date_date = calendar.getTime();
		}

		log.info("Data for country {}: {}", country.getCountry(), covid_data);

		model.addAttribute("Country", covid_data);

		log.info("-- End -- Get data for home template");
		return "home";
	}

	@GetMapping("/")
	public String index(Model model) throws ParseException {
		log.info("Get index template");
		model.addAttribute("Country", service.getAllData());
		return "index";
	}
}
