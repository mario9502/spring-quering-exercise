package example.springqueringexercise.data.repositories.projections;

public interface NamesAndSumOnly {

    String getFirstName();
    String getLastName();
    Integer getCopiesSum();

    default String getFullName() {
        return getFirstName().concat(" ").concat(getLastName());
    }
}
