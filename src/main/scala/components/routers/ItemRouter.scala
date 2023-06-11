package components.routers

import models.Item
import components.services.ItemService
import cats.effect.IO
import cats.syntax.all._
import org.http4s.EntityEncoder
import cats.Monad
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import doobie.util.transactor.Transactor
import io.circe.syntax._
import io.circe.generic.auto._

class ItemController(xa: Transactor[IO]): 

    implicit def fullItemEncoder: EntityEncoder[IO, Item] = ???

    private def getAllItems(itemService: ItemService): HttpRoutes[IO] =
        val dsl = Http4sDsl[IO]
        import dsl._
        HttpRoutes.of[IO]: 
            case GET -> Root / "item" => itemService.getAllItems.flatMap {
                case items => Ok(items.asJson)
                case null => Conflict(s"Something went wrong")
            }
    
    private def getItemById(itemService: ItemService): HttpRoutes[IO] =
        val dsl = Http4sDsl[IO]
        import dsl._
        HttpRoutes.of[IO]:
            case GET -> Root / "item" / IntVar(id) => itemService.getItemById(id).flatMap {
                case item => Ok(item.asJson)
                case null => Conflict(s"Item with id $id was not found")
            }        

    def endpoints(itemService: ItemService): HttpRoutes[IO] =
        getAllItems(itemService) <+> getItemById(itemService)

object ItemRouter:
    def endpoints(xa: Transactor[IO], itemService: ItemService): HttpRoutes[IO] =
        ItemController(xa).endpoints(itemService)