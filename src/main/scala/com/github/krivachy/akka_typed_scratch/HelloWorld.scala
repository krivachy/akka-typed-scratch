package com.github.krivachy.akka_typed_scratch

import akka.typed._
import akka.typed.scaladsl.Actor

import scala.annotation.tailrec


object HelloWorld {
  final case class Greet(whom: String, replyTo: ActorRef[Greeted])
  final case class Greeted(whom: String)

  val greeter: Actor.Immutable[Greet] = Actor.immutable[Greet] { (_, msg) ⇒
    println(s"Hello ${msg.whom}!")
    msg.replyTo ! Greeted(msg.whom)
    Actor.same
  }

  sealed trait CounterCommand {
    def replyTo: ActorRef[CounterResponse]
  }
  final case class Inc(replyTo: ActorRef[CounterResponse]) extends CounterCommand
  final case class Dec(replyTo: ActorRef[CounterResponse]) extends CounterCommand
  final case class Set(value: Int, replyTo: ActorRef[CounterResponse]) extends CounterCommand
  final case class Query(replyTo: ActorRef[CounterResponse]) extends CounterCommand

  sealed trait CounterResponse
  final case class Success(count: Int) extends CounterResponse
  final case class Failure(exception: Exception) extends CounterResponse

  val counter = counterBehaviour(0)
//  @tailrec
  def counterBehaviour(currentCount: Int): Behavior[CounterCommand] = Actor.immutable[CounterCommand] {
    (ctx, command) =>
//      ctx.()
      val updatedCount = command match {
        case _: Inc => currentCount + 1
        case _: Dec => currentCount - 1
        case Set(value, _) => value
        case _: Query => currentCount
      }

      command.replyTo ! Success(updatedCount)
      counterBehaviour(updatedCount)
  }
//  onSignal {
//    case () =>
//  }

}
