package TQS_HW1.HW1.HTTP;

import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

public class Resolver {
    private HttpClient httpClient;
    private URIBuilder uriBuilder;

    public Resolver(HttpClient client) {}

    public String getData() throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException {
        uriBuilder = new URIBuilder("https://covid-19-dados-abertos.p.rapidapi.com/covid/v1.0/portugal");

        System.err.println(" url is --> " + uriBuilder.build().toString());

        String response = this.httpClient.doHttpGet(uriBuilder.build().toString());

        // get parts from response till reaching the address
        JSONObject obj = (JSONObject) new JSONParser().parse(response);
        obj =(JSONObject)((JSONArray) obj.get("results")).get(0);

        System.out.println(obj);

        // JSONObject address =(JSONObject)((JSONArray) obj.get("locations")).get(0);

        // String road = (String) address.get("street");
        // String city = (String) address.get("adminArea5");
        // String state = (String) address.get("adminArea3");
        // String zip = (String) address.get("postalCode");
        return null;
    }
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