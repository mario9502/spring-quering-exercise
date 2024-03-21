package example.springqueringexercise.data.repositories;


import example.springqueringexercise.data.entities.AgeRestriction;
import example.springqueringexercise.data.entities.Book;
import example.springqueringexercise.data.entities.EditionType;
import example.springqueringexercise.data.repositories.projections.LimitedData;
import example.springqueringexercise.data.repositories.projections.ReducedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    //List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findAllByAgeRestrictionLike(AgeRestriction ageRestriction);

    List<Book> findAllByEditionTypeAndCopiesLessThan(EditionType type, int copies);

    List<Book> findAllByPriceIsLessThanOrPriceGreaterThan(BigDecimal lowPrice, BigDecimal highPrice);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate beforeDate, LocalDate afterDate);

    List<LimitedData> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByTitleContains(String text);

    List<Book> findAllByAuthorLastNameStartingWith(String text);

    @Query("SELECT COUNT(b.title) FROM Book b WHERE LENGTH(b.title) > :size")
    int countTitleSizeGreaterThan(int size);

    @Query("FROM Book b WHERE b.title LIKE :title")
    ReducedBook findReducedBookInfoByTitle(String title);
}
