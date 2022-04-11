package library;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class StepDefinitions {
    Library library = new Library();
	List<Book> result = new ArrayList<>();

	@Given("a(nother) book with the title {string}, written by {string}, published in {int} {word} {int}")
	public void addNewBook(String string, String string2, Integer int1, String month, Integer int3) {
		Book book = new Book(string, string2, LocalDateTime.of(int1, Month.valueOf(month.toUpperCase()).getValue(), int3, 0, 0));
		library.addBook(book);
	}

	@When("the customer searches for books published between {int} and {int}")
	public void setSearchParameters(final int year, final int year1) {
		result = library.findBooks(LocalDateTime.of(year,1, 1, 0, 0), LocalDateTime.of(year1, 12, 31, 0, 0));
		System.out.println(result);
	}

	@Then("{int} books should have been found")
	public void verifyAmountOfBooksFound(final int booksFound) {
		assertEquals(result.size(), booksFound);
	}
 
	@Then("Book {int} should have the title {string}")
	public void verifyBookAtPosition(final int position, final String title) {
		assertEquals(result.get(position - 1).getTitle(), title);
	}
}