package io.iodum.jasync;

public interface Consts {
    String dbUrl = "jdbc:mysql://localhost:3306/billing-sct?user=root&password=kb313554";
    String sql = "select 1 as val, benchmark(" + 1_000_000 + ", md5('when will it end?')) as stuff";
}
