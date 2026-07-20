package pe.celucheck;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;

import static org.assertj.core.api.Assertions.assertThat;

class PostgresMigrationCompatibilityTest {

    @Test
    void postgresMigrationsBuildTheCompleteCatalog() throws Exception {
        String url = "jdbc:h2:mem:celucheck_postgres;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE;DB_CLOSE_DELAY=-1";

        Flyway.configure()
                .dataSource(url, "sa", "")
                .locations("classpath:db/migration-postgres", "classpath:db/migration")
                .load()
                .migrate();

        try (var connection = DriverManager.getConnection(url, "sa", "");
             var statement = connection.createStatement();
             var result = statement.executeQuery("SELECT COUNT(*) FROM phone")) {
            result.next();
            assertThat(result.getInt(1)).isEqualTo(26);
        }
    }
}
