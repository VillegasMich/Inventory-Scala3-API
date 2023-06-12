import cats._
import cats.effect._
import cats.implicits._
import org.http4s._
import org.http4s.ember.server.EmberServerBuilder
import com.comcast.ip4s.ipv4
import com.comcast.ip4s.port
import doobie.util.transactor.Transactor
import components.routers.ItemRouter
import models.Item
import database.DatabaseConfig
import components.services.ItemService
import components.repositories.ItemRepo

object Main extends IOApp:

  def allRoutesComplete(xa: Transactor[IO]): HttpApp[IO] = ItemRouter.endpoints(xa, ItemService(xa), ItemRepo(xa)).orNotFound
  def run(args: List[String]): IO[ExitCode] = 

    EmberServerBuilder
      .default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(allRoutesComplete(DatabaseConfig.getConnection))
      .build
      .use(_ => {
        println("Server started correctly on port [ 8080 ] 🚀")
        IO.never
      })
      .as(ExitCode.Success)
