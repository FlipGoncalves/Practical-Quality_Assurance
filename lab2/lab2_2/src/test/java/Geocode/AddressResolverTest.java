package Geocode;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddressResolverTest {

    @Mock
    private ISimpleHttpClient client;

    @InjectMocks
    private AddressResolver addresolver;


    @Test
    public void findAddressForLocationTest() throws ParseException, IOException, URISyntaxException, org.json.simple.parser.ParseException {

        // Encoded with: http://www.cheminfo.org/Utility/JSoN_text_parse_-_stringify/index.html
        String mockResult = "{\"info\":{\"statuscode\":0,\"copyright\":{\"text\":\"\\u00A9 2021 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\\u00A9 2021 MapQuest, Inc.\"},\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false},\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.640661,\"lng\":-8.656688}},\"locations\":[{\"street\":\"Cais do Alboi\",\"adminArea6\":\"\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Gl\\u00F3ria e Vera Cruz\",\"adminArea5Type\":\"City\",\"adminArea4\":\"\",\"adminArea4Type\":\"County\",\"adminArea3\":\"Centro\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3800-246\",\"geocodeQualityCode\":\"B1AAA\",\"geocodeQuality\":\"STREET\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.640524,\"lng\":-8.656713},\"displayLatLng\":{\"lat\":40.640524,\"lng\":-8.656713},\"mapUrl\":\"http://open.mapquestapi.com/staticmap/v5/map?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&type=map&size=225,160&locations=40.64052368145179,-8.656712986761146|marker-sm-50318A-1&scalebar=true&zoom=15&rand=-963779157\",\"roadMetadata\":null}]}]}";
        when(client.doHttpGet(contains("location=40.640661%2C-8.656688"))).thenReturn(mockResult);

        Address addr = addresolver.findAddressForLocation(40.640661, -8.656688);
        assertEquals( addr, new Address( "Cais do Alboi", "Glória e Vera Cruz", "Centro", "3800-246", null) );

        verify(client).doHttpGet(contains("location=40.640661%2C-8.656688"));
    }

    @Test
    public void findAddressForLocationBad() throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException {
        when(client.doHttpGet(contains("location=40.640661%2C-8.656688"))).thenThrow();
        assertThrows(org.json.simple.parser.ParseException.class, () -> addresolver.findAddressForLocation(-300000, -8100000) );

    }
}

