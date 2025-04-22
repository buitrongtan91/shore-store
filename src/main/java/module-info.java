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

    opens sgu.beo to javafx.fxml;
    opens sgu.beo.model to org.hibernate.orm.core;

    exports sgu.beo;
}
