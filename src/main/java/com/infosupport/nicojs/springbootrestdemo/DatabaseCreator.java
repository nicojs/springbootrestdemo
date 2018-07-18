package com.infosupport.nicojs.springbootrestdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class DatabaseCreator {

    private static final String DATABASE_URL = "NICO_DATABASE_CREATE_URL";
    private static final String DATABASE_CREATE_NAME = "NICO_DATABASE_CREATE_NAME";
    public static final int SLEEP_BETWEEN_RETRIES_MS = 5000;

    public static void declareDatabase() throws SQLException {
        var stopwatch = new StopWatch();
        stopwatch.start();
        var databaseUrl = Optional.ofNullable(System.getenv(DATABASE_URL));
        var databaseName = Optional.ofNullable(System.getenv(DATABASE_CREATE_NAME));
        if (databaseUrl.isPresent() && databaseName.isPresent()) {
            try (var connection = createConnection(databaseUrl.get())) {
                if (databaseExists(connection, databaseName.get())) {
                    log.info("Database '{}' already exists in {}", databaseName.get(), databaseUrl.get());
                } else {
                    log.info("Database '{}' does not exist, creating... in {}", databaseName.get(), databaseUrl.get());
                    createDatabase(connection, databaseName.get());
                }
            } catch (InterruptedException e) {
                log.info("Db declare got interrupted, skipping");
            }
        }
        stopwatch.stop();
        log.info("Database declare took {} second(s).", stopwatch.getTotalTimeSeconds());
    }

    private static Connection createConnection(String dbUrl) throws SQLException, InterruptedException {
        int tries = 10;
        SQLException lastException = null;
        while(--tries > 0){
            try {
                final var connection = DriverManager.getConnection(dbUrl, System.getenv("SPRING_DATASOURCE_USERNAME"), System.getenv("SPRING_DATASOURCE_PASSWORD"));
                return connection;
            } catch (SQLException sqlEx) {
                log.info("Database connection to {} failed, retrying {} times", dbUrl, tries, sqlEx);
                lastException = sqlEx;
                Thread.sleep(SLEEP_BETWEEN_RETRIES_MS);
            }
        }
        throw lastException;
    }

    private static void createDatabase(Connection connection, String dbName) throws SQLException {
        try (var statement = connection.createStatement()) {
            statement.execute(String.format("CREATE DATABASE [%s]", dbName));
        }
    }

    private static boolean databaseExists(Connection connection, String dbName) throws SQLException {
        try (var statement = connection.createStatement()) {
            final var query = String.format("SELECT 1 FROM master.dbo.sysdatabases WHERE name = '%s'", dbName);
            if (statement.execute(query)) {
                var resultSet = statement.getResultSet();
                return resultSet.next();
            } else {
                return false;
            }
        }
    }
}
