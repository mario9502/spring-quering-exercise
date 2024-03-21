package example.springqueringexercise;

import example.springqueringexercise.data.entities.*;
import example.springqueringexercise.data.entities.dtos.BookInfoDTO;
import example.springqueringexercise.data.repositories.projections.ReducedBook;
import example.springqueringexercise.data.services.AuthorService;
import example.springqueringexercise.data.services.BookService;
import example.springqueringexercise.data.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Component
public class ConsoleReader implements CommandLineRunner {

    private static final Scanner scanner = new Scanner(System.in);

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public ConsoleReader(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        ModelMapper mapper = new ModelMapper();

        Author author = new Author("Pencho", "Slaveikov");
        Set<Category> categories = this.categoryService.getRandomCategories();

        Book book = new Book(EditionType.GOLD, LocalDate.of(1999, 2, 13), 200, BigDecimal.TEN,
                                AgeRestriction.TEEN, "Vinetu", author, categories);

        BookInfoDTO dto = mapper.map(book, BookInfoDTO.class);

        System.out.println();
    }

    private void printReducedBookInfoByTitle() {
        String title = scanner.nextLine();

        System.out.println(this.bookService.findReducedBookByTitle(title));
    }

    private void printAllAuthorsByTotalBookCopiesOrderedDesc() {
        List<String> allByTotalBookCopies = this.authorService.getAllByTotalBookCopies();
    }

    private void printAllWithTitlesLongerThan() {
        int size = Integer.parseInt(scanner.nextLine());

        System.out.println(this.bookService.findAllBooksWithTitleLongerThan(size));
    }

    private void printAllTitlesWhichAuthorLastNameStartsWith() {
        String input = scanner.nextLine();

        this.bookService.findTitleWhichAuthorsLastNameStartsWith(input).forEach(System.out::println);
    }

    private void printAllTitlesThatContains() {
        this.bookService.findTitlesThatContains(scanner.next()).forEach(System.out::println);
    }

    private void printAllNamesThatEndsWith() {
        this.authorService.getAllAuthorsNamesThatEndsWith(scanner.nextLine()).forEach(System.out::println);
    }

    private void printAllTitlesWIthReleaseDateBefore() {
        LocalDate parsedDate = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        this.bookService.findTitlesWithReleaseDateBefore(parsedDate).forEach(System.out::println);


    }

    private void printAllTitlesWIthReleaseDateNotInYear() {
        String year = scanner.nextLine();

        this.bookService.findTitlesWithReleaseDateNotInRange(year).forEach(System.out::println);
    }

    private void printAllTitlesByPriceNotInRange() {

        this.bookService.findTitlesWithPriceLowerThanAndHigherThan(BigDecimal.valueOf(5), BigDecimal.valueOf(40))
                .forEach(System.out::println);
    }

    private void printAllTitlesByEditionTypeAndCopiesLessThan() {
        EditionType editionType = EditionType.valueOf("GOLD");

        bookService.findTitlesByEditionTypeAndCopiesLessThan(editionType, 5000).forEach(System.out::println);
    }

    private void printAllTitlesByAgeRestriction() {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(scanner.nextLine().toUpperCase());

        this.bookService.findTitlesByAgeRestriction(ageRestriction).forEach(System.out::println);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

//    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
//        bookService
//                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
//                .forEach(System.out::println);
//    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
