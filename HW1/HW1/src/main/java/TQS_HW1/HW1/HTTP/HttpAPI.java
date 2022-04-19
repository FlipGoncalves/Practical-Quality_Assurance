package TQS_HW1.HW1.HTTP;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import TQS_HW1.HW1.Exceptions.APINotRespondsException;

@Component
public class HttpAPI {
    private static final Logger log = LoggerFactory.getLogger(HttpAPI.class);

    public HttpAPI() {}

    public String doHttpGet(String url) throws IOException, APINotRespondsException {
        // url = https://covid-193.p.rapidapi.com/statistics
        // url = https://covid-193.p.rapidapi.com/history?country=usa&day=2022-04-07

        HttpRequest request;
        HttpResponse<String> response = null;

        log.info("-- Start -- API call to {}", url);

        try {
            request = HttpRequest.newBuilder()
				.uri(URI.create(url))
				.header("X-RapidAPI-Host", "covid-193.p.rapidapi.com")
				.header("X-RapidAPI-Key", "a018835eb8mshd880c64e6cbdf04p1c10f4jsn952450b38f9f")
				.method("GET", HttpRequest.BodyPublishers.noBody())
				.build();
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            log.info("Successful API call");
        } catch (Exception e) {
            System.err.println(e);
            log.info("Error");
            throw new APINotRespondsException("URL ("+url+") does not respond");
        }

        log.info("-- End -- API call to {}", url);

        return response.body();
    }
}