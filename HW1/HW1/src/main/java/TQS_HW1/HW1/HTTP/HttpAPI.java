package TQS_HW1.HW1.HTTP;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.springframework.stereotype.Component;

@Component
public class HttpAPI {
    public HttpAPI() {}

    public String doHttpGet(String url) throws IOException {
        // url = https://covid-193.p.rapidapi.com/statistics
        // url = https://covid-193.p.rapidapi.com/history?country=usa&day=2022-04-07

        HttpRequest request;
        HttpResponse<String> response = null;

        try {
            request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("X-RapidAPI-Host", "covid-193.p.rapidapi.com")
				.header("X-RapidAPI-Key", "a018835eb8mshd880c64e6cbdf04p1c10f4jsn952450b38f9f")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (Exception e) {
            System.err.println(e);
        }

        return response.body();
    }
}