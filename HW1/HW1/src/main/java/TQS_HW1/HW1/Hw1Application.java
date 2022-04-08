package TQS_HW1.HW1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import TQS_HW1.HW1.HTTP.HttpAPI;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

@SpringBootApplication
public class Hw1Application {

	public static void main(String[] args) throws URISyntaxException, IOException, ParseException, InterruptedException {
		SpringApplication.run(Hw1Application.class, args);

		HttpRequest request = HttpRequest.newBuilder()
			.uri(URI.create("https://api.covidtracking.com"))
			.method("GET", HttpRequest.BodyPublishers.noBody())
			.build();
		HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
		System.out.println(response.body());
	}
}
