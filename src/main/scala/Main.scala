import scala.util.CommandLineParser
object main

//When pattern matching an object whose type is a sealed trait, Scala will check at compile-time that all cases are 'exhaustively matched':
sealed trait Shape
case class Square(height:Int , width:Int) extends Shape
case class Circle(radius:Int) extends Shape
case object Point extends Shape

def matchShape(shape:Shape):String = shape match {
  case Square(height , width) => "Its a square"
  case Circle(radius) => "Its a circle"
}

enum Colors:
  case red, blue, green

given CommandLineParser.FromString[Colors] with
  def fromString(s: String): Colors = Colors.valueOf(s)

@main def run(color: Colors): Unit =
  println(s"The color is ${color.toString}")
  println("Hello world!")
  println(msg)

def msg = "I was compiled by Scala 3. :)"
