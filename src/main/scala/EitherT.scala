import java.nio.file.{Path, Paths}
import zio.*
import java.io.*
import java.util.NoSuchElementException
import java.sql.SQLException
// https://gist.github.com/quelgar/d383cd960d781fe72a1564203c280a03

opaque type Data = String

final class NoDataException(t: Throwable) extends Exception(t)

// def usingDb: Boolean = args.headOption.contains("db")
def usingDb: Boolean = ???

def loadFromDb: IO[SQLException | NoSuchElementException, Data] =
  // can fail with SQLException or NoSuchElementException
  IO.succeed("some sql data")

def loadFromFile(file: Path): IO[IOException, Data] =
  // can fail with IOException
  IO.succeed("some file data")

val myDataFile = Path.of("myfile")

def loadData: IO[NoDataException, Data] =
  if usingDb then
    loadFromDb.refineOrDie { case e: NoSuchElementException =>
      NoDataException(e)
    }
  else
    loadFromFile(myDataFile).refineOrDie { case e: FileNotFoundException =>
      NoDataException(e)
    }

val defaultData: Data = "default"

def useData(data: Data): IO[Nothing, Unit] = IO.unit

def program: IO[Nothing, Unit] =
  loadData
    .orElseSucceed(defaultData)
    .flatMap(data => useData(data))

// Runtime.default.unsafeRun(program)