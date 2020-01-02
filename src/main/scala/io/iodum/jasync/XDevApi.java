package io.iodum.jasync;

import com.mysql.cj.xdevapi.ClientFactory;

import java.io.IOException;
import java.util.Properties;

import static java.lang.System.out;

public class XDevApi {
    public static void select() throws IOException, InterruptedException {
        final var props = new Properties();
        //noinspection ConstantConditions
        props.load(XDevApi.class.getClassLoader().getResourceAsStream("pooling.properties"));
        final var client = new ClientFactory().getClient("mysqlx://root:kb313554@localhost:33070", props);
        final var session = client.getSession();

        int times = 10_000_000;

        for (int i = 0; i < 100; i++)
            client.getSession().sql("select benchmark("+ times +", md5('when will it end?'))").executeAsync().handleAsync((rows, throwable) -> null);

        final var db = session.getSchema("test");
        final var tbl = db.getTable("one", true);
        final var res = tbl.select("doc", "_id").executeAsync().handleAsync((rows, throwable) -> {
            out.println(rows);
            return null;
        });

        out.println(res);
        Thread.sleep(1000);
    }
}
