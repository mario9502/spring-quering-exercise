package example.springqueringexercise.data.services;

import example.springqueringexercise.data.entities.AgeRestriction;
import example.springqueringexercise.data.entities.Book;
import example.springqueringexercise.data.entities.EditionType;
import example.springqueringexercise.data.repositories.projections.ReducedBook;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

//    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findTitlesByAgeRestriction(AgeRestriction ageRestriction);

    List<String> findTitlesByEditionTypeAndCopiesLessThan(EditionType type, int copies);

    List<String> findTitlesWithPriceLowerThanAndHigherThan(BigDecimal lowPrice, BigDecimal highPrice);

    List<String> findTitlesWithReleaseDateNotInRange(String year);

    List<String> findTitlesWithReleaseDateBefore(LocalDate date);

    List<String> findTitlesThatContains(String text);

    List<String> findTitleWhichAuthorsLastNameStartsWith(String text);

    int findAllBooksWithTitleLongerThan(int size);

    String findReducedBookByTitle(String title);
}
