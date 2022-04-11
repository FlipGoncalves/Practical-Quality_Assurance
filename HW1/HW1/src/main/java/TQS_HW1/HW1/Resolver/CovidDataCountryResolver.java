package TQS_HW1.HW1.Resolver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import TQS_HW1.HW1.HTTP.HttpAPI;
import TQS_HW1.HW1.Models.CovidDataCountry;

@Component
public class CovidDataCountryResolver {
    @Autowired
    HttpAPI httpClient;

    public CovidDataCountry getDataByCountry(String country, String date) throws IOException {
        String response = this.httpClient.doHttpGet("https://covid-193.p.rapidapi.com/history?country="+country+"&day="+date);

        return dataToJson(response);
    }

    public CovidDataCountry dataToJson(String data) {
        CovidDataCountry covid = new CovidDataCountry();

		JSONObject json = new JSONObject(data);
		JSONArray jsonArray = json.getJSONArray("response");

        covid.setCountry(jsonArray.getJSONObject(0).getString("country"));
        covid.setContinent(jsonArray.getJSONObject(0).getString("continent"));
        covid.setDay(jsonArray.getJSONObject(0).getString("day"));
        //System.out.println(jsonArray);
        JSONObject cases = jsonArray.getJSONObject(0).getJSONObject("cases");
        //System.out.println(cases);
        covid.setNew_cases(cases.getString("new"));
        covid.setRecovered_cases(cases.getInt("recovered"));
        covid.setTotal_cases(cases.getInt("total"));
        covid.setCritical_cases(cases.getInt("critical"));
        covid.setActive_cases(cases.getInt("active"));

        JSONObject tests = jsonArray.getJSONObject(0).getJSONObject("tests");
        //System.out.println(tests);
        covid.setTotal_tests(tests.getInt("total"));

        JSONObject deaths = jsonArray.getJSONObject(0).getJSONObject("deaths");
        //System.out.println(deaths);
        covid.setTotal_deaths(deaths.getInt("total"));
        covid.setNew_deaths(deaths.getString("new"));

        return covid;
    }
}
