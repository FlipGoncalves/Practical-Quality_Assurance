package Integration;

import java.text.ParseException;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.Before;
import org.junit.Test;

import Geocode.Address;
import Geocode.AddressResolver;
import Geocode.ISimpleHttpClient;

public class AddressResolverIT {
    private ISimpleHttpClient client;
    private AddressResolver addresolver;

    @Before
    public void setUp() {
        addresolver = new AddressResolver(client);
    }

    @Test
    public void findAddressForLocationTest() throws ParseException, IOException, URISyntaxException, org.json.simple.parser.ParseException {
        
        Address addr = addresolver.findAddressForLocation(40.640661, -8.656688);
        assertEquals( addr, new Address( "Cais do Alboi", "Glória e Vera Cruz", "Centro", "3800-246", null) );
    }

    @Test
    public void findAddressForLocationBad() throws IOException, URISyntaxException, ParseException, org.json.simple.parser.ParseException {
        assertThrows(org.json.simple.parser.ParseException.class, () -> addresolver.findAddressForLocation(-300000, -8100000) );

    }
}
