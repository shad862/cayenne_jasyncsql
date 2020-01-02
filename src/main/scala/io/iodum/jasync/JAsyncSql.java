package io.iodum.jasync;

import com.github.jasync.sql.db.Configuration;
import com.github.jasync.sql.db.ConnectionPoolConfiguration;
import com.github.jasync.sql.db.ConnectionPoolConfigurationBuilder;
import com.github.jasync.sql.db.ResultSet;
import com.github.jasync.sql.db.mysql.MySQLConnection;
import com.github.jasync.sql.db.mysql.pool.MySQLConnectionFactory;
import com.github.jasync.sql.db.pool.ConnectionPool;
import com.github.jasync.sql.db.pool.PoolConfiguration;

import java.util.concurrent.CompletableFuture;

import static com.github.jasync.sql.db.mysql.MySQLConnectionBuilder.createConnectionPool;
import static java.util.concurrent.TimeUnit.MINUTES;
import static java.util.concurrent.TimeUnit.SECONDS;

public class JAsyncSql {

    private ConnectionPool<MySQLConnection> getPool1() {
        //fixme: this example doesn't work
        var poolConfig = new PoolConfiguration(100, MINUTES.toMillis(15), 10_000, SECONDS.toMillis(30));
        var config = new Configuration("root", "localhost", 3306, "kb313554", "test");

        //fixme: why we use same config for ConnectionPool creation
        return new ConnectionPool<>(new MySQLConnectionFactory(config), getConnPoolConfig());
    }

    private ConnectionPoolConfigurationBuilder getConnPoolConfigBuilder() {
        var builder = new ConnectionPoolConfigurationBuilder();
        builder.setPort(3306);
        builder.setUsername("root");
        builder.setPassword("kb313554");
        builder.setHost("localhost");
        builder.setMaxActiveConnections(1000);
        builder.setMaxPendingQueries(1000);
        return builder;
    }

    private ConnectionPoolConfiguration getConnPoolConfig() {
        return getConnPoolConfigBuilder().build();
    }

    private String getDBUrl() {
        return Consts.dbUrl;
    }

    public CompletableFuture<CompletableFuture<ResultSet>> select() {
        //??? createConnectionPool(getDBUrl)
        //??? pool.sendQuery() what is the difference?
        return createConnectionPool(getConnPoolConfigBuilder())
                .connect().handleAsync((conn, connTh) ->
                        conn.sendQuery(Consts.sql).handleAsync((result, qryTh) ->
                                result.getRows()));
    }
}
