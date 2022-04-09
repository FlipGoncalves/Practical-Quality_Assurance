package TQS_HW1.HW1.Resolver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;

import TQS_HW1.HW1.HTTP.HttpAPI;
import TQS_HW1.HW1.Models.CovidDataCountry;

public class CovidDataResolver {
    @Autowired
    HttpAPI httpClient;

    public CovidDataCountry getOverallData() throws IOException {
        String response = this.httpClient.doHttpGet("https://covid-193.p.rapidapi.com/statistics");

        return dataToJson(response);
    }

    public CovidDataCountry dataToJson(String data) {
        HashMap<String, JSONObject> result = new HashMap<>();

		JSONObject json = new JSONObject(data);
		JSONArray jsonArray = json.getJSONArray("response");

		for (int i = 0; i < jsonArray.length(); i++) {
			result.put(jsonArray.getJSONObject(i).getString("country"), jsonArray.getJSONObject(i));
		}

        // tranform data in CovidData


        // must do

        return new CovidDataCountry();
    }
}
