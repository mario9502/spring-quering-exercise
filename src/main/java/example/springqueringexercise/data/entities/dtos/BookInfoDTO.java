package example.springqueringexercise.data.entities.dtos;

import example.springqueringexercise.data.entities.Author;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BookInfoDTO {

    private String title;
    private BigDecimal price;
    private LocalDate releaseDate;
    private Author author;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
