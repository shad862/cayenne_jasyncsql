import java.sql.DriverManager

import com.github.jasync.sql.db.Connection
import com.github.jasync.sql.db.mysql.MySQLConnectionBuilder
import utest._



abstract class JMysql extends TestSuite {
  val times = 10_000_000
  val dbUrl = "jdbc:mysql://localhost:3306/billing-sct?user=root&password=root"
  val sql = s"select benchmark(${times}, md5('when will it end?'))"
}

object JAsyncMysql extends JMysql {
  val tests = Tests {
    test("jasync_sql") {
      val connection = MySQLConnectionBuilder.createConnectionPool(dbUrl)
      connection.sendQuery(sql)
    }
  }
}

object JdbcMysql extends JMysql {
  val tests = Tests {
    test("java.sql"){
      Class.forName("com.mysql.cj.jdbc.Driver")
      val connection = DriverManager.getConnection(dbUrl)
      val stmt = connection.createStatement()
      val result = stmt.execute(sql)
    }
  }
}
