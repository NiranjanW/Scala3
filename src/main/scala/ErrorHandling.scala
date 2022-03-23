import zio.*
import zio.console._

sealed trait MyError 
case class ErrorA(code :Int) extends MyError
case class ErrorB(message:String) extends MyError

def a:IO[ErrorA, Unit] = IO.fail(ErrorA(42))

def b: IO[ErrorB, Unit] = IO.fail(ErrorB("💣"))

def aAndB: IO[MyError, Unit] = a *> b



object myApp extends zio.App {
 def run(args: List[String]) = ???
    // myAppLogic.exitCode
val program = aAndB.catchAll(e => IO.effectTotal(println(s"Got error: $e")))
    Runtime.default.unsafeRun(program)
}
