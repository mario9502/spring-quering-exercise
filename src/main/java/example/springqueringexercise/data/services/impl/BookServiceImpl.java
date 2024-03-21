package example.springqueringexercise.data.services.impl;

import example.springqueringexercise.data.entities.*;
import example.springqueringexercise.data.repositories.BookRepository;
import example.springqueringexercise.data.repositories.projections.ReducedBook;
import example.springqueringexercise.data.services.AuthorService;
import example.springqueringexercise.data.services.BookService;
import example.springqueringexercise.data.services.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "D:\\SoftUni\\JavaDB-Projects\\spring-quering-exercise\\src\\main\\resources\\files\\books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

//    @Override
//    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
//        return bookRepository
//                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
//                .stream()
//                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
//                        book.getAuthor().getLastName()))
//                .distinct()
//                .collect(Collectors.toList());
//     }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findTitlesByAgeRestriction(AgeRestriction ageRestriction) {
        return this.bookRepository.findAllByAgeRestrictionLike(ageRestriction).stream()
                .map(Book::getTitle).toList();
    }

    @Override
    public List<String> findTitlesByEditionTypeAndCopiesLessThan(EditionType type, int copies) {
        return this.bookRepository.findAllByEditionTypeAndCopiesLessThan(type, copies).stream()
                .map(Book::getTitle).toList();

    }

    @Override
    public List<String> findTitlesWithPriceLowerThanAndHigherThan(BigDecimal lowPrice, BigDecimal highPrice) {
        return this.bookRepository.findAllByPriceIsLessThanOrPriceGreaterThan(lowPrice, highPrice).stream()
                .map(b -> String.format("%s - %.2f", b.getTitle(), b.getPrice()))
                .toList();
    }

    @Override
    public List<String> findTitlesWithReleaseDateNotInRange(String year) {
        String min = "01-01-" + year;
        String max = "31-12-" + year;

        LocalDate beforeDate = LocalDate.parse(min, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        LocalDate afterDate = LocalDate.parse(max, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        return this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(beforeDate, afterDate).stream()
                .map(Book::getTitle).toList();
    }

    @Override
    public List<String> findTitlesWithReleaseDateBefore(LocalDate date) {
        return this.bookRepository.findAllByReleaseDateBefore(date).stream()
                .map(l -> String.format("%s %s %.2f", l.getTitle(), l.getEditionType(), l.getPrice()))
                .toList();
    }

    @Override
    public List<String> findTitlesThatContains(String text) {
        return this.bookRepository.findAllByTitleContains(text).stream()
                .map(Book::getTitle).toList();
    }

    @Override
    public List<String> findTitleWhichAuthorsLastNameStartsWith(String text) {
        return this.bookRepository.findAllByAuthorLastNameStartingWith(text).stream()
                .map(Book::getTitle).toList();
    }

    @Override
    public int findAllBooksWithTitleLongerThan(int size) {
        return this.bookRepository.countTitleSizeGreaterThan(size);
    }

    @Override
    public String findReducedBookByTitle(String title) {
        ReducedBook book = this.bookRepository.findReducedBookInfoByTitle(title);

        return String.format("%s %s %s %.2f", book.getTitle(), book.getEditionType(), book.getAgeRestriction(), book.getPrice());
    }


}
