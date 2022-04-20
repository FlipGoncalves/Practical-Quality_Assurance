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
        } catch (Exception e) {
            System.err.println(e);
            log.info("-- Error {}", e);
            throw new BadRequestException("Bad API Reuqest");
        }

        log.info("-- Transform data into CovidDataCountry object");
        CovidDataCountry datajson = dataToJson(response);

        log.info("----- End ----- Get data from the API");
        return datajson;
    }

    public CovidDataCountry dataToJson(String data) {
        CovidDataCountry covid = new CovidDataCountry();
        try {
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
        } catch (TemplateInputException e) {
            log.info("-- Error Tranforming data: {}", e);
            return null;
        } catch (JSONException e) {
            log.info("-- Error Tranforming data: {}", e);
            throw new JSONException("JSON Exception");
        }

        return covid;
    }
}
