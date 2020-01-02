package io.iodum.sasync

import java.net.InetSocketAddress
import java.nio.ByteBuffer
import java.nio.ByteBuffer.allocate
import java.nio.channels.{AsynchronousSocketChannel, CompletionHandler}
import java.nio.channels.AsynchronousSocketChannel.open
import java.nio.channels.spi.AsynchronousChannelProvider
import java.util.concurrent.CompletableFuture

class Raw {
  def select(): Unit = {
    val channel = open()
    val address = new InetSocketAddress("localhost", 3306)
    val buffer = allocate(1000)

    channel.connect[(ByteBuffer, AsynchronousSocketChannel)](address, (buffer, channel), new CompletionHandler[Void, (ByteBuffer, AsynchronousSocketChannel)] {
      override def failed(exc: Throwable, attachment: (ByteBuffer, AsynchronousSocketChannel)): Unit = println(s"Error: ${exc.toString}")
      override def completed(result: Void, attachment: (ByteBuffer, AsynchronousSocketChannel)): Unit = {
        var assert1 = buffer == attachment._1
        var read = attachment._2.read(attachment._1).get()
        print(read)
      }
    })
  }
}
