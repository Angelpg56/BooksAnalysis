module edu.psp.booksanalysis {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.psp.booksanalysis to javafx.fxml;
    exports edu.psp.booksanalysis;
    opens edu.psp.booksanalysis.model to javafx.fxml;
    exports edu.psp.booksanalysis.model;
    opens edu.psp.booksanalysis.utils to javafx.fxml;
    exports edu.psp.booksanalysis.utils;
}