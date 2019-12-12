import java.sql.DriverManager
import java.util.concurrent.CompletableFuture

import com.github.jasync.sql.db.{Connection, QueryResult}
import com.github.jasync.sql.db.mysql.MySQLConnectionBuilder
import org.slf4j.LoggerFactory
import utest._



abstract class JMysql extends TestSuite {
  val times = 10000000
  val dbUrl = "jdbc:mysql://localhost:3306/billing-sct?user=root&password=root"
  val sql = s"select benchmark($times, md5('when will it end?'))"
}

object JAsyncMysql extends JMysql {
  private val logger = LoggerFactory.getLogger("Common logger")
  val tests = Tests {
    test("jasync_sql") {
      val connection = MySQLConnectionBuilder.createConnectionPool(dbUrl)
      var futures = List[CompletableFuture[QueryResult]]()
      var i = 0
      while (i < 100){
        futures = connection.sendQuery(sql) +: futures
        i += 1
      }
      futures(0).join()
      logger.warn(futures.size.toString)
    }
  }
}

object JdbcMysql extends JMysql {
  val tests = Tests {
    test("java.sql"){
      Class.forName("com.mysql.cj.jdbc.Driver")
      val connection = DriverManager.getConnection(dbUrl)
      var i = 0
      var result: Boolean = null
      while (i < 100) {
        val stmt = connection.createStatement()
        result = stmt.execute(sql)
        i += 1
      }
    }
  }
}
