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

        try (var connection = DriverManager.getConnection(url, "sa", "");
             var statement = connection.createStatement();
             var result = statement.executeQuery("SELECT COUNT(*) FROM product_review")) {
            result.next();
            assertThat(result.getInt(1)).isEqualTo(100);
        }

        try (var connection = DriverManager.getConnection(url, "sa", "");
             var statement = connection.createStatement();
             var result = statement.executeQuery("SELECT COUNT(*) FROM phone_variant")) {
            result.next();
            assertThat(result.getInt(1)).isEqualTo(64);
        }

        try (var connection = DriverManager.getConnection(url, "sa", "");
             var statement = connection.createStatement();
             var result = statement.executeQuery("""
                     SELECT MIN(variant_count) FROM (
                         SELECT phone_id, COUNT(*) AS variant_count
                         FROM phone_variant GROUP BY phone_id
                     ) variants_per_phone
                     """)) {
            result.next();
            assertThat(result.getInt(1)).isGreaterThanOrEqualTo(1);
        }
    }
}
