package TQS_HW1.HW1.Repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import TQS_HW1.HW1.Models.CovidData;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CovidDataRepositoryTest {

    // get a test-friendly Entity Manager
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CovidDataRepository covid_country_rep;

    @Test
    public void findByCountryTest() {
        CovidData data = new CovidData("Portugal");

        entityManager.persistAndFlush(data);

        Optional<CovidData> data_get = covid_country_rep.findByCountry("Portugal");

        assertThat(data_get).isNotNull();
        assertThat(data_get.get()).isEqualTo(data);
    }

    // equals to bad argument -> it doesnt find any
    @Test
    public void findByCountryTest_NotFound() {
        Optional<CovidData> data_get = covid_country_rep.findByCountry("Spain");
        assertThat(data_get).isEmpty();
    }
}