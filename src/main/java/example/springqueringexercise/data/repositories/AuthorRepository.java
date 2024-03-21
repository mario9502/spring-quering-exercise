package example.springqueringexercise.data.repositories;


import example.springqueringexercise.data.entities.Author;
import example.springqueringexercise.data.repositories.projections.NamesAndSumOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a ORDER BY SIZE(a.books) DESC")
    List<Author> findAllByBooksSizeDESC();

    List<Author> findAllByFirstNameEndingWith(String end);

    @Query("SELECT SUM(b.copies) as CopiesSum FROM Author a " +
            "JOIN a.books b " +
            "GROUP BY CONCAT_WS(' ', a.firstName, a.lastName) " +
            "ORDER BY SUM(b.copies) DESC")
    List<NamesAndSumOnly> findAllByTotalBookCopies();
}
