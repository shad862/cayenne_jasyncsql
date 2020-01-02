package io.iodum.sasync

import java.sql.DriverManager

import io.iodum.jasync.Consts

class Jdbc {
  def select (): Unit = {
    Class.forName("com.mysql.cj.jdbc.Driver")
    val connection = DriverManager.getConnection(Consts.dbUrl)
    var result: Boolean = false
    val stmt = connection.createStatement()
    result = stmt.execute(Consts.sql)
  }
}
