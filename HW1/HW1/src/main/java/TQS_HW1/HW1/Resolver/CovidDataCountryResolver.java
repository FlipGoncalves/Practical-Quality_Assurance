package TQS_HW1.HW1.Resolver;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.exceptions.TemplateInputException;

import TQS_HW1.HW1.Exceptions.APINotRespondsException;
import TQS_HW1.HW1.Exceptions.BadRequestException;
import TQS_HW1.HW1.HTTP.HttpAPI;
import TQS_HW1.HW1.Models.CovidDataCountry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CovidDataCountryResolver {
    private static final Logger log = LoggerFactory.getLogger(CovidDataCountryResolver.class);

    @Autowired
    HttpAPI httpClient;

    public CovidDataCountry getDataByCountry(String country, String date) throws IOException, APINotRespondsException, BadRequestException {
        String response = null;
        log.info("----- Start ----- Get data from the API");

        try {
            response = this.httpClient.doHttpGet("https://covid-193.p.rapidapi.com/history?country="+country+"&day="+date);
            log.info("-- Successfull: {}", response);
        } catch (InterruptedException e) {
            log.info("-- Error: {}", e.toString());
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.info("-- Error: {}", e.toString());
            throw new BadRequestException("Bad API Reuqest");
        }

        log.info("-- Transform data into CovidDataCountry object");
        CovidDataCountry datajson = dataToJson(response);

        log.info("----- End ----- Get data from the API");
        return datajson;
    }

    public CovidDataCountry dataToJson(String data) {
        CovidDataCountry covid = new CovidDataCountry();
        final String total = "total";
        try {
            JSONObject json = new JSONObject(data);
            JSONArray jsonArray = json.getJSONArray("response");

            covid.setCountry(jsonArray.getJSONObject(0).getString("country"));
            covid.setContinent(jsonArray.getJSONObject(0).getString("continent"));
            covid.setDay(jsonArray.getJSONObject(0).getString("day"));
            JSONObject cases = jsonArray.getJSONObject(0).getJSONObject("cases");
            covid.setNewCases(cases.getString("new"));
            covid.setRecoveredCases(cases.getInt("recovered"));
            covid.setTotalCases(cases.getInt(total));
            covid.setCriticalCases(cases.getInt("critical"));
            covid.setActiveCases(cases.getInt("active"));
            JSONObject tests = jsonArray.getJSONObject(0).getJSONObject("tests");
            covid.setTotalTests(tests.getInt(total));
            JSONObject deaths = jsonArray.getJSONObject(0).getJSONObject("deaths");
            covid.setTotalDeaths(deaths.getInt(total));
            covid.setNewDeaths(deaths.getString("new"));

        } catch (TemplateInputException e) {
            log.info("-- Error Tranforming data: {}", e.toString());
            return null;
        } catch (JSONException e) {
            log.info("-- Error Tranforming data: {}", e.toString());
            throw new JSONException("JSON Exception");
        }

        return covid;
    }
}
