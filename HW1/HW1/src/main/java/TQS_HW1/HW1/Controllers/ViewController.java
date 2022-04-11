package TQS_HW1.HW1.Controllers;

import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Services.CovidDataCountryService;

@Controller
public class ViewController {
	@Autowired
    private CovidDataCountryService service;

	@GetMapping("/home")
	public String home(Model model) {
		return "home";
	}

	@PostMapping("/home")
	public String submitHome(@ModelAttribute("covidCountry") CovidData country, Model model) {

		String date;
		int year = 2020;
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
		try {
			model.addAttribute("Country1", service.getDataByCountry(country.getCountry(), date));
		} catch(Exception e) {
			return "index";
		}

		return "home";
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("Country", service.getAllData());
		return "index";
	}
}
