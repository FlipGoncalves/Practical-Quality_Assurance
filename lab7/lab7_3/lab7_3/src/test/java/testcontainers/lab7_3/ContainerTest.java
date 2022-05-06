package testcontainers.lab7_3;

import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import testcontainers.lab7_3.Models.Movie;
import testcontainers.lab7_3.Repository.movieRepository;

@Testcontainers
@SpringBootTest
public class ContainerTest {
  
  @Container
  public static PostgreSQLContainer container = new PostgreSQLContainer( "postgres:11.1" )
    .withDatabaseName( "db-test-lab-7-3" )
    .withUsername( "sa" )
    .withPassword( "sa" );
  
  @Autowired
  private movieRepository rep;
  
  // requires Spring Boot >= 2.2.6
  @DynamicPropertySource
  static void properties( DynamicPropertyRegistry registry ) {
    registry.add( "spring.datasource.url", container::getJdbcUrl );
    registry.add( "spring.datasource.password", container::getPassword );
    registry.add( "spring.datasource.username", container::getUsername );
  }
  
  @Test
  void contextLoads() {
    Movie book = new Movie("Uma Aventura", "Filipe Gonçalves");
    rep.save( book );
  }

}