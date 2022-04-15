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

import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataCountry;
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
	public String submitHome(@ModelAttribute("covidCountry") CovidData country, Model model) throws ParseException, IOException {

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

		for (int i = 0; i < 5; i++) {
			String strDate = dateFormat.format(Date_date);
			System.out.println(strDate);

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

		System.out.println(covid_data);

		model.addAttribute("Country", covid_data);

		return "home";
	}

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("Country", service.getAllData());
		return "index";
	}
}
