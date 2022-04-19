package TQS_HW1.HW1.HTTP;

import org.junit.jupiter.api.Test;

import TQS_HW1.HW1.Exceptions.APINotRespondsException;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class HttpAPITest {

    private HttpAPI httpClient = new HttpAPI();

    @Test
    void testGetUrl_APINotResponds() {
        assertThrows(APINotRespondsException.class, () -> {
            httpClient.doHttpGet("ahsgajfkabj");
        });
    }

    @Test
    void testGetURL_Valid() throws IOException, APINotRespondsException {
        assertThat(httpClient.doHttpGet("http://amazon.com")).isNotEmpty();
    }

}