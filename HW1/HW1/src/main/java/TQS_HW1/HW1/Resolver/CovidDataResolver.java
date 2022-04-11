package TQS_HW1.HW1.Resolver;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import TQS_HW1.HW1.HTTP.HttpAPI;
import TQS_HW1.HW1.Models.CovidData;

@Component
public class CovidDataResolver {
    @Autowired
    HttpAPI httpClient;

    public List<CovidData> getOverallData() throws IOException {
        String response = this.httpClient.doHttpGet("https://covid-193.p.rapidapi.com/statistics");

        return dataToJson(response);
    }

    public List<CovidData> dataToJson(String data) {
        List<CovidData> result = new ArrayList<CovidData>();

		JSONObject json = new JSONObject(data);
		JSONArray jsonArray = json.getJSONArray("response");
        for (int i = 0; i < jsonArray.length(); i++) {
            CovidData covid = new CovidData(jsonArray.getJSONObject(i).getString("country"));
            result.add(covid);
        }

        return result;
    }
}
