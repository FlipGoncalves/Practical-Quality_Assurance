package TQS_HW1.HW1.Resolver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import TQS_HW1.HW1.Exceptions.APINotRespondsException;
import TQS_HW1.HW1.Exceptions.BadRequestException;
import TQS_HW1.HW1.HTTP.HttpAPI;
import TQS_HW1.HW1.Models.CovidData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CovidDataResolver {
    private static final Logger log = LoggerFactory.getLogger(CovidDataResolver.class);

    @Autowired
    HttpAPI httpClient;

    public List<CovidData> getOverallData() throws IOException, APINotRespondsException, BadRequestException {
        log.info("----- Start ----- Get data from the API");

        String response = null;

        try {
            response = this.httpClient.doHttpGet("https://covid-193.p.rapidapi.com/statistics");
            log.info("-- Successfull: {}", response);
        } catch (InterruptedException e) {
            log.info("-- Error {}", e.toString());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.info("-- Error {}", e.toString());
            throw new BadRequestException("Bad API Reuqest");
        }

        log.info("-- Transform data into CovidData object");
        List<CovidData> datajson = dataToJson(response);

        log.info("----- End ----- Get data from the API");

        return datajson;
    }

    public List<CovidData> dataToJson(String data) {
        List<CovidData> result = new ArrayList<>();

        try {
            JSONObject json = new JSONObject(data);
            JSONArray jsonArray = json.getJSONArray("response");
            for (int i = 0; i < jsonArray.length(); i++) {
                CovidData covid = new CovidData(jsonArray.getJSONObject(i).getString("country"));
                result.add(covid);
            }

            return result;
        } catch (JSONException e) {
            log.info("-- Error Tranforming data: {}", e.toString());
            throw new JSONException("JSON Exception");
        }
    }
}
