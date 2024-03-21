package example.springqueringexercise.data.repositories.projections;

import example.springqueringexercise.data.entities.AgeRestriction;
import example.springqueringexercise.data.entities.EditionType;

import java.math.BigDecimal;

public interface ReducedBook {

    String getTitle();
    EditionType getEditionType();
    AgeRestriction getAgeRestriction();
    BigDecimal getPrice();
}
