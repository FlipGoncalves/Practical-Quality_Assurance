package testcontainers.lab7_3.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import testcontainers.lab7_3.Models.Movie;

import java.util.List;

@Repository
public interface movieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findAllByAuthor(String author);
    List<Movie> findAllByTitle(String title);
}