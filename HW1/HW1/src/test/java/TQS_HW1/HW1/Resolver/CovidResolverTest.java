package TQS_HW1.HW1.Resolver;

import org.json.JSONException;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.env.Environment;

import TQS_HW1.HW1.Exceptions.APINotRespondsException;
import TQS_HW1.HW1.Exceptions.BadRequestException;
import TQS_HW1.HW1.HTTP.HttpAPI;
import TQS_HW1.HW1.Models.CovidData;
import TQS_HW1.HW1.Models.CovidDataCountry;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CovidResolverTest {

    @Mock
    HttpAPI httpAPI;

    @Mock
    Environment environments;

    @InjectMocks
    CovidDataCountryResolver covid_resolver;

    @InjectMocks
    CovidDataResolver data_resolver;

    @Test
    public void testGetValidData() throws APINotRespondsException, IOException, URISyntaxException, ParseException, BadRequestException {
        when(httpAPI.doHttpGet("https://covid-193.p.rapidapi.com/history?country=Portugal&day=2021-04-16"))
                .thenReturn("{\"get\":\"history\",\"parameters\":{\"country\":\"Portugal\",\"day\":\"2021-04-16\"},\"errors\":[],\"results\":3,\"response\":[{\"continent\":\"Europe\",\"country\":\"Portugal\",\"population\":10173212,\"cases\":{\"new\":\"+553\",\"active\":25367,\"critical\":101,\"recovered\":787607,\"1M_pop\":\"81578\",\"total\":829911},\"deaths\":{\"new\":\"+4\",\"1M_pop\":\"1665\",\"total\":16937},\"tests\":{\"1M_pop\":\"948153\",\"total\":9645758},\"day\":\"2021-04-16\",\"time\":\"2021-04-16T22:00:03+00:00\"},{\"continent\":\"Europe\",\"country\":\"Portugal\",\"population\":10173212,\"cases\":{\"new\":\"+501\",\"active\":25414,\"critical\":109,\"recovered\":787011,\"1M_pop\":\"81524\",\"total\":829358},\"deaths\":{\"new\":\"+2\",\"1M_pop\":\"1664\",\"total\":16933},\"tests\":{\"1M_pop\":\"948153\",\"total\":9645758},\"day\":\"2021-04-16\",\"time\":\"2021-04-16T14:30:02+00:00\"},{\"continent\":\"Europe\",\"country\":\"Portugal\",\"population\":10173293,\"cases\":{\"new\":\"+501\",\"active\":25414,\"critical\":109,\"recovered\":787011,\"1M_pop\":\"81523\",\"total\":829358},\"deaths\":{\"new\":\"+2\",\"1M_pop\":\"1664\",\"total\":16933},\"tests\":{\"1M_pop\":\"948145\",\"total\":9645758},\"day\":\"2021-04-16\",\"time\":\"2021-04-16T00:00:03+00:00\"}]}");

        CovidDataCountry result = covid_resolver.getDataByCountry("Portugal", "2021-04-16");

        assertEquals(result.getActive_cases(), 25367);
        assertEquals(result.getCritical_cases(), 101);
        assertEquals(result.getTotal_cases(), 	829911);
        assertEquals(result.getNew_cases(), "+553");
        assertEquals(result.getRecovered_cases(), 		787607);
        assertEquals(result.getNew_deaths(), 	"+4");
        assertEquals(result.getTotal_deaths(), 16937);
        assertEquals(result.getTotal_tests(), 9645758);

        assertEquals(result.getContinent(), "Europe");
        assertEquals(result.getCountry(), "Portugal");
        assertEquals(result.getDay(), "2021-04-16");

        verify(httpAPI, times(1)).doHttpGet(anyString());
    }

    @Test
    public void testGetInValidData() throws APINotRespondsException, IOException, URISyntaxException, ParseException, BadRequestException {
        when(httpAPI.doHttpGet("https://covid-193.p.rapidapi.com/history?country=Portugal&day=2021-04-16"))
                .thenReturn("");

        assertThrows(JSONException.class, () -> {
            covid_resolver.getDataByCountry("Portugal", "2021-04-16");
        });

        verify(httpAPI, times(1)).doHttpGet(anyString());
    }

    @Test
    public void testDataToJsonErrorParse_Resolver1(){
        assertThrows(JSONException.class, () -> {
            covid_resolver.dataToJson("}{}{}");
        });
    }


    @Test
    public void testGetValidAllData() throws APINotRespondsException, IOException, URISyntaxException, ParseException, BadRequestException {
        when(httpAPI.doHttpGet("https://covid-193.p.rapidapi.com/statistics"))
                .thenReturn("{\"get\":\"statistics\",\"parameters\":[],\"errors\":[],\"results\":240,\"response\":[{\"continent\":\"Asia\",\"country\":\"China\",\"population\":1439323776,\"cases\":{\"new\":\"+2761\",\"active\":30773,\"critical\":116,\"recovered\":155684,\"1M_pop\":\"133\",\"total\":191112},\"deaths\":{\"new\":\"+7\",\"1M_pop\":\"3\",\"total\":4655},\"tests\":{\"1M_pop\":\"111163\",\"total\":160000000},\"day\":\"2022-04-20\",\"time\":\"2022-04-20T14:30:05+00:00\"},{\"continent\":\"Oceania\",\"country\":\"Nauru\",\"population\":10945,\"cases\":{\"new\":null,\"active\":0,\"critical\":null,\"recovered\":3,\"1M_pop\":\"274\",\"total\":3},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":null},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2022-04-20\",\"time\":\"2022-04-20T14:30:05+00:00\"}]}");

        List<CovidData> result = data_resolver.getOverallData();

        assertEquals(result.size(), 2);

        assertEquals(result.get(0).getCountry(), "China");
        assertEquals(result.get(1).getCountry(), "Nauru");

        verify(httpAPI, times(1)).doHttpGet(anyString());
    }

    @Test
    public void testGetInValidAllData() throws APINotRespondsException, IOException, URISyntaxException, ParseException, BadRequestException {
        when(httpAPI.doHttpGet("https://covid-193.p.rapidapi.com/statistics"))
                .thenReturn("");

        assertThrows(JSONException.class, () -> {
            data_resolver.getOverallData();
        });

        verify(httpAPI, times(1)).doHttpGet(anyString());
    }

    @Test
    public void testDataToJsonErrorParse_Resolver2(){
        assertThrows(JSONException.class, () -> {
            data_resolver.dataToJson("}{}{}");
        });
    }
}