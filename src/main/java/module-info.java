module sgu.beo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires static lombok;
    requires java.naming;
    requires org.apache.logging.log4j;
    requires org.apache.logging.log4j.core;
    requires javafx.base;
    requires javafx.graphics;
    requires com.google.common;
    requires org.controlsfx.controls;

    opens sgu.beo to javafx.fxml;
    opens sgu.beo.Controller to javafx.fxml, javafx.base;
    opens sgu.beo.model to org.hibernate.orm.core;

    exports sgu.beo.Controller to com.google.common;
    exports sgu.beo;
}
