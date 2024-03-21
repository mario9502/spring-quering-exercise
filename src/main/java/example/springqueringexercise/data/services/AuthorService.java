package example.springqueringexercise.data.services;

import example.springqueringexercise.data.entities.Author;

import java.io.IOException;
import java.util.List;

public interface AuthorService {
    void seedAuthors() throws IOException;

    Author getRandomAuthor();

    List<String> getAllAuthorsOrderByCountOfTheirBooks();

    List<String> getAllAuthorsNamesThatEndsWith(String end);

    List<String> getAllByTotalBookCopies();
}
