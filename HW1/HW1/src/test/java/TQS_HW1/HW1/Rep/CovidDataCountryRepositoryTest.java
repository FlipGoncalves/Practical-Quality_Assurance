package TQS_HW1.HW1.Rep;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import TQS_HW1.HW1.Models.CovidDataCountry;
import TQS_HW1.HW1.Repository.CovidDataCountryRepository;

/**
 * DataJpaTest limits the test scope to the data access context (no web environment loaded, for example)
 * tries to autoconfigure the database, if possible (e.g.: in memory db)
 */
@DataJpaTest
public class CovidDataCountryRepositoryTest {

    // get a test-friendly Entity Manager
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CovidDataCountryRepository covid_country_rep;

    @Test
    public void findByCountryAndDayTest() {
        
        CovidDataCountry data = new CovidDataCountry("Portugal", "Europe", "2021-04-11");
        data.setActive_cases(2);
        data.setCritical_cases(1);
        data.setNew_cases("+0");
        data.setRecovered_cases(1);
        data.setTotal_cases(4);
        data.setTotal_tests(8);
        data.setTotal_deaths(2);
        data.setNew_deaths("+1");

        entityManager.persistAndFlush(data);

        Optional<CovidDataCountry> data_get = covid_country_rep.findByCountryAndDay("Portugal", "2020-04-11");

        assertThat(data_get).isNotNull();
        assertThat(data_get.get()).isEqualTo(data);
    }

    @Test
    public void findByCountryAndDayTest_BadCountry() {  
        CovidDataCountry data_get = covid_country_rep.findByCountryAndDay("akjsbkshdg", "2020-04-11").orElse(null);
        assertThat(data_get).isNull();
    }

    @Test
    public void findByCountryAndDayTest_BadDate() {
        CovidDataCountry data_get = covid_country_rep.findByCountryAndDay("Portugal", "kagsdhgasbd").orElse(null);
        assertThat(data_get).isNull();
    }

    @Test
    public void findByCountryTest() {
        CovidDataCountry data = new CovidDataCountry("Portugal", "Europe", "2021-04-11");
        data.setActive_cases(2);
        data.setCritical_cases(1);
        data.setNew_cases("+0");
        data.setRecovered_cases(1);
        data.setTotal_cases(4);
        data.setTotal_tests(8);
        data.setTotal_deaths(2);
        data.setNew_deaths("+1");

        entityManager.persistAndFlush(data);

        List<CovidDataCountry> data_get = covid_country_rep.findAllByCountry("Portugal");

        assertThat(data_get).isNotNull();
        assertThat(data_get).isEqualTo(data);
    }
    
}
