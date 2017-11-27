package com.github.krivachy.akka_typed_scratch

import akka.typed._
import akka.typed.scaladsl.AskPattern._
import com.github.krivachy.akka_typed_scratch.HelloWorld._
import akka.typed._
import akka.typed.scaladsl.Actor
import akka.typed.scaladsl.AskPattern._
import akka.util.Timeout

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.Await
import scala.concurrent.Future

object Main extends App {
  // using global pool since we want to run tasks after system.terminate
  import scala.concurrent.ExecutionContext.Implicits.global
  implicit val timeout = Timeout(1.second)

  val system: ActorSystem[Greet] = ActorSystem(greeter, "hello")
  implicit val scheduler = system.scheduler

  val future: Future[Greeted] = system ? (Greet("world", _))

  for {
    greeting ← future.recover { case ex ⇒ ex.getMessage }
    _ ← { println(s"result: $greeting"); system.terminate() }
  } println("system terminated")

}
