package TQS_HW1.HW1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Hw1Application {

	public static void main(String[] args) {
		SpringApplication.run(Hw1Application.class, args);
	}


	/* API CODE REQUEST

	https://rapidapi.com/gitgrupoift/api/covid-19-dados-abertos/

	OkHttpClient client = new OkHttpClient();

	Request request = new Request.Builder()
		.url("https://covid-19-dados-abertos.p.rapidapi.com/covid/v1.0/portugal") 
		.get()
		.addHeader("X-RapidAPI-Host", "covid-19-dados-abertos.p.rapidapi.com")
		.addHeader("X-RapidAPI-Key", "SIGN-UP-FOR-KEY")
		.build();

	Response response = client.newCall(request).execute();


	

	https://rapidapi.com/axisbits-axisbits-default/api/covid-19-statistics/

	OkHttpClient client = new OkHttpClient();

	Request request = new Request.Builder()
		.url("https://covid-19-statistics.p.rapidapi.com/reports/total?date=2020-04-07")
		.get()
		.addHeader("X-RapidAPI-Host", "covid-19-statistics.p.rapidapi.com")
		.addHeader("X-RapidAPI-Key", "SIGN-UP-FOR-KEY")
		.build();

	Response response = client.newCall(request).execute();
	*/

}
