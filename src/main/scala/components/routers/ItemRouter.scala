package components.routers

import models.Item
import cats.effect.IO
import org.http4s.EntityEncoder
import cats.Monad
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import components.services.ItemService
import doobie.util.transactor.Transactor
import io.circe.syntax._
import io.circe.generic.auto._

class ItemController: 

    implicit def itemEncoder: EntityEncoder[IO, Item] = ???

    private def getAllItems(xa: Transactor[IO]): HttpRoutes[IO] =
        val dsl = Http4sDsl[IO]
        import dsl._
        HttpRoutes.of[IO]: 
            case GET -> Root / "item" => ItemService(xa).getAllItems.flatMap {
                case items => Ok(items.asJson)
                case null => Conflict(s"Something went wrong")
            }

    def endpoints(xa: Transactor[IO]): HttpRoutes[IO] =
        getAllItems(xa)

object ItemRouter:
    def endpoints(xa: Transactor[IO]): HttpRoutes[IO] =
        new ItemController().endpoints(xa)