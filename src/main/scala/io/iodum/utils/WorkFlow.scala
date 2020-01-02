package io.iodum.utils

object WorkFlow {
  implicit def repeat(count: Int)(f:() => Unit):Unit = { var i = 0; while (i < count ) { f(); i += 1 } }
}
