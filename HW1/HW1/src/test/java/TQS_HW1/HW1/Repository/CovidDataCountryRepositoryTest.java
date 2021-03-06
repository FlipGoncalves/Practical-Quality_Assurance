package TQS_HW1.HW1.Repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import TQS_HW1.HW1.Models.CovidDataCountry;

@RunWith(SpringRunner.class)
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
        data.setActiveCases(2);
        data.setCriticalCases(1);
        data.setNewCases("+0");
        data.setRecoveredCases(1);
        data.setTotalCases(4);
        data.setTotalTests(8);
        data.setTotalDeaths(2);
        data.setNewDeaths("+1");

        entityManager.persistAndFlush(data);

        Optional<CovidDataCountry> data_get = covid_country_rep.findByCountryAndDay("Portugal", "2021-04-11");

        assertThat(data_get).isNotEmpty();
        assertEquals(data, data_get.get());
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
    public void findByCountryAndDayTest_NotFound() {
        Optional<CovidDataCountry> data_get = covid_country_rep.findByCountryAndDay("Portugal", "2020-04-11");

        assertThat(data_get).isEmpty();
    }

    @Test
    public void findByCountryTest() {
        CovidDataCountry data = new CovidDataCountry("Portugal", "Europe", "2021-04-11");
        data.setActiveCases(2);
        data.setCriticalCases(1);
        data.setNewCases("+0");
        data.setRecoveredCases(1);
        data.setTotalCases(4);
        data.setTotalTests(8);
        data.setTotalDeaths(2);
        data.setNewDeaths("+1");

        entityManager.persistAndFlush(data);

        List<CovidDataCountry> data_get = covid_country_rep.findAllByCountry("Portugal");

        assertNotNull(data_get);
        assertThat(data_get).isEqualTo(Arrays.asList(data));
    }

    // equals to bad argument -> it doesnt find any
    @Test
    public void findByCountryTest_NotFound() {
        List<CovidDataCountry> data_get = covid_country_rep.findAllByCountry("Spain");
        assertThat(data_get).isEmpty();
    }

    @Test
    public void findByContinentTest() {
        CovidDataCountry data = new CovidDataCountry("Portugal", "Europe", "2021-04-11");
        data.setActiveCases(2);
        data.setCriticalCases(1);
        data.setNewCases("+0");
        data.setRecoveredCases(1);
        data.setTotalCases(4);
        data.setTotalTests(8);
        data.setTotalDeaths(2);
        data.setNewDeaths("+1");

        entityManager.persistAndFlush(data);

        List<CovidDataCountry> data_get = covid_country_rep.findAllByContinent("Europe");

        assertNotNull(data_get);
        assertThat(data_get).isEqualTo(Arrays.asList(data));
    }

    // equals to bad argument -> it doesnt find any
    @Test
    public void findByContinentTest_NotFound() {
        List<CovidDataCountry> data_get = covid_country_rep.findAllByContinent("Europe");
        assertThat(data_get).isEmpty();
    }

    @Test
    public void findByDayTest() {
        CovidDataCountry data = new CovidDataCountry("Portugal", "Europe", "2021-04-11");
        data.setActiveCases(2);
        data.setCriticalCases(1);
        data.setNewCases("+0");
        data.setRecoveredCases(1);
        data.setTotalCases(4);
        data.setTotalTests(8);
        data.setTotalDeaths(2);
        data.setNewDeaths("+1");

        entityManager.persistAndFlush(data);

        List<CovidDataCountry> data_get = covid_country_rep.findAllByDay("2021-04-11");

        assertNotNull(data_get);
        assertThat(data_get).isEqualTo(Arrays.asList(data));
    }

    // equals to bad argument -> it doesnt find any
    @Test
    public void findByDayTest_NotFound() {
        List<CovidDataCountry> data_get = covid_country_rep.findAllByDay("2020-04-11");
        assertThat(data_get).isEmpty();
    }
}