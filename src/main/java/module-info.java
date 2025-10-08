module cda.bibliotheque {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens cda.bibliotheque.controller to javafx.fxml;
    exports cda.bibliotheque;
}
