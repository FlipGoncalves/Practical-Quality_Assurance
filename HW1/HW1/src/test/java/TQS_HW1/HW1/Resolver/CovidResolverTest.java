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
class CovidResolverTest {

    @Mock
    HttpAPI httpAPI;

    @Mock
    Environment environments;

    @InjectMocks
    CovidDataCountryResolver covid_resolver;

    @InjectMocks
    CovidDataResolver data_resolver;

    @Test
    void testGetValidData() throws APINotRespondsException, IOException, URISyntaxException, ParseException, BadRequestException, InterruptedException {
        when(httpAPI.doHttpGet("https://covid-193.p.rapidapi.com/history?country=Portugal&day=2021-04-16"))
                .thenReturn("{\"get\":\"history\",\"parameters\":{\"country\":\"Portugal\",\"day\":\"2021-04-16\"},\"errors\":[],\"results\":3,\"response\":[{\"continent\":\"Europe\",\"country\":\"Portugal\",\"population\":10173212,\"cases\":{\"new\":\"+553\",\"active\":25367,\"critical\":101,\"recovered\":787607,\"1M_pop\":\"81578\",\"total\":829911},\"deaths\":{\"new\":\"+4\",\"1M_pop\":\"1665\",\"total\":16937},\"tests\":{\"1M_pop\":\"948153\",\"total\":9645758},\"day\":\"2021-04-16\",\"time\":\"2021-04-16T22:00:03+00:00\"},{\"continent\":\"Europe\",\"country\":\"Portugal\",\"population\":10173212,\"cases\":{\"new\":\"+501\",\"active\":25414,\"critical\":109,\"recovered\":787011,\"1M_pop\":\"81524\",\"total\":829358},\"deaths\":{\"new\":\"+2\",\"1M_pop\":\"1664\",\"total\":16933},\"tests\":{\"1M_pop\":\"948153\",\"total\":9645758},\"day\":\"2021-04-16\",\"time\":\"2021-04-16T14:30:02+00:00\"},{\"continent\":\"Europe\",\"country\":\"Portugal\",\"population\":10173293,\"cases\":{\"new\":\"+501\",\"active\":25414,\"critical\":109,\"recovered\":787011,\"1M_pop\":\"81523\",\"total\":829358},\"deaths\":{\"new\":\"+2\",\"1M_pop\":\"1664\",\"total\":16933},\"tests\":{\"1M_pop\":\"948145\",\"total\":9645758},\"day\":\"2021-04-16\",\"time\":\"2021-04-16T00:00:03+00:00\"}]}");

        CovidDataCountry result = covid_resolver.getDataByCountry("Portugal", "2021-04-16");

        assertEquals(25367, result.getActiveCases());
        assertEquals(101, result.getCriticalCases());
        assertEquals(829911, result.getTotalCases());
        assertEquals("+553", result.getNewCases());
        assertEquals(787607, result.getRecoveredCases());
        assertEquals("+4", result.getNewDeaths());
        assertEquals(16937, result.getTotalDeaths());
        assertEquals(9645758, result.getTotalTests());

        assertEquals("Europe", result.getContinent());
        assertEquals("Portugal", result.getCountry());
        assertEquals("2021-04-16", result.getDay());

        verify(httpAPI, times(1)).doHttpGet(anyString());
    }

    @Test
    void testGetInValidData() throws APINotRespondsException, IOException, URISyntaxException, ParseException, BadRequestException, InterruptedException {
        when(httpAPI.doHttpGet("https://covid-193.p.rapidapi.com/history?country=Portugal&day=2021-04-16"))
                .thenThrow(InterruptedException.class);

        assertThrows(InterruptedException.class, () -> {
            covid_resolver.getDataByCountry("Portugal", "2021-04-16");
        });

        verify(httpAPI, times(1)).doHttpGet(anyString());
    }

    @Test
    void testGetBadRequest() throws APINotRespondsException, IOException, URISyntaxException, ParseException, BadRequestException, InterruptedException {
        when(httpAPI.doHttpGet("https://covid-193.p.rapidapi.com/history?country=Portugal&day=2021-04-16"))
                .thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> {
            covid_resolver.getDataByCountry("Portugal", "2021-04-16");
        });

        verify(httpAPI, times(1)).doHttpGet(anyString());
    }

    @Test
    void testDataToJsonErrorParse_Resolver1(){
        assertThrows(JSONException.class, () -> {
            covid_resolver.dataToJson("}{}{}");
        });
    }


    @Test
    void testGetValidAllData() throws APINotRespondsException, IOException, URISyntaxException, ParseException, BadRequestException, InterruptedException {
        when(httpAPI.doHttpGet("https://covid-193.p.rapidapi.com/statistics"))
                .thenReturn("{\"get\":\"statistics\",\"parameters\":[],\"errors\":[],\"results\":240,\"response\":[{\"continent\":\"Asia\",\"country\":\"China\",\"population\":1439323776,\"cases\":{\"new\":\"+2761\",\"active\":30773,\"critical\":116,\"recovered\":155684,\"1M_pop\":\"133\",\"total\":191112},\"deaths\":{\"new\":\"+7\",\"1M_pop\":\"3\",\"total\":4655},\"tests\":{\"1M_pop\":\"111163\",\"total\":160000000},\"day\":\"2022-04-20\",\"time\":\"2022-04-20T14:30:05+00:00\"},{\"continent\":\"Oceania\",\"country\":\"Nauru\",\"population\":10945,\"cases\":{\"new\":null,\"active\":0,\"critical\":null,\"recovered\":3,\"1M_pop\":\"274\",\"total\":3},\"deaths\":{\"new\":null,\"1M_pop\":null,\"total\":null},\"tests\":{\"1M_pop\":null,\"total\":null},\"day\":\"2022-04-20\",\"time\":\"2022-04-20T14:30:05+00:00\"}]}");

        List<CovidData> result = data_resolver.getOverallData();

        assertEquals(2, result.size());

        assertEquals("China", result.get(0).getCountry());
        assertEquals("Nauru", result.get(1).getCountry());

        verify(httpAPI, times(1)).doHttpGet(anyString());
    }

    @Test
    void testGetInValidAllData() throws APINotRespondsException, IOException, URISyntaxException, ParseException, BadRequestException, InterruptedException {
        when(httpAPI.doHttpGet("https://covid-193.p.rapidapi.com/statistics"))
                .thenThrow(InterruptedException.class);

        assertThrows(InterruptedException.class, () -> {
            data_resolver.getOverallData();
        });

        verify(httpAPI, times(1)).doHttpGet(anyString());
    }

    @Test
    void testGetBadRequestAllData() throws APINotRespondsException, IOException, URISyntaxException, ParseException, BadRequestException, InterruptedException {
        when(httpAPI.doHttpGet("https://covid-193.p.rapidapi.com/statistics"))
                .thenThrow(NullPointerException.class);

        assertThrows(NullPointerException.class, () -> {
            data_resolver.getOverallData();
        });

        verify(httpAPI, times(1)).doHttpGet(anyString());
    }

    @Test
    void testDataToJsonErrorParse_Resolver2(){
        assertThrows(JSONException.class, () -> {
            data_resolver.dataToJson("}{}{}");
        });
    }
}