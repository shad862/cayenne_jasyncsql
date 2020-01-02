package io.iodum.test.jasync

import io.iodum.jasync.JAsyncSql
import io.iodum.sasync.Jdbc
import io.iodum.utils.WorkFlow
import org.scalameter.Bench
import org.scalameter.api._

object JAsyncMeter extends Bench.OfflineRegressionReport {

  override def measurer = new Executor.Measurer.MemoryFootprint with Executor.Measurer.Timer

  private val counts = Gen.range("count")(10, 30, 10)

  performance of "jAsyncSQL" in {
    measure method "select" in {
      using(counts) config (exec.independentSamples -> 1) in { count =>
        val jAsyncSql = new JAsyncSql()
        WorkFlow.repeat(1) { () =>
          jAsyncSql.select()
          ()
        }
      }
    }
  }

  performance of "connector/j:jdbc" in {
    measure method "select" in {
      using(counts) config (exec.independentSamples -> 1) in { count =>
        val jdbc = new Jdbc()
        WorkFlow.repeat(1) { () =>
          jdbc.select()
        }
      }
    }
  }
}