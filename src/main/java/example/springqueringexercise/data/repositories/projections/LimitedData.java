package example.springqueringexercise.data.repositories.projections;

import example.springqueringexercise.data.entities.EditionType;

import java.math.BigDecimal;

public interface LimitedData {

    String getTitle();
    EditionType getEditionType();
    BigDecimal getPrice();
}
