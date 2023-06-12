package components.routers

import models._
import components.services.ItemService
import doobie.util.transactor.Transactor
import cats.effect.IO
import cats.syntax.all._
import cats.Monad
import org.http4s._
import org.http4s.EntityEncoder
import org.http4s.HttpRoutes
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import org.http4s.EntityDecoder
import io.circe.syntax._
import io.circe.generic.auto._
import components.repositories.ItemRepo

import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.circe.CirceEntityEncoder.circeEntityEncoder

class ItemController(xa: Transactor[IO]): 

    implicit def fullItemEncoder: EntityEncoder[IO, Item] = ???
    implicit def fullItemDecoder: EntityDecoder[IO, Item] = jsonOf

    private def getAllItems(itemService: ItemService, itemRepo: ItemRepo): HttpRoutes[IO] =
        val dsl = Http4sDsl[IO]
        import dsl._
        HttpRoutes.of[IO]: 
            case GET -> Root / "item" => itemService.getAllItems(itemRepo).flatMap {
                case items => Ok(items.asJson)
            }
    
    private def getItemById(itemService: ItemService, itemRepo: ItemRepo): HttpRoutes[IO] =
        val dsl = Http4sDsl[IO]
        import dsl._
        HttpRoutes.of[IO]:
            case GET -> Root / "item" / IntVar(id) => itemService.getItemById(itemRepo, id).flatMap {
                case Left(itemNotFound) => NotFound(s"Item with id ${itemNotFound.id} was not found")
                case Right(item) => Ok(item.asJson)
            }

    private def createItem(itemService: ItemService, itemRepo: ItemRepo): HttpRoutes[IO] =
        val dsl = Http4sDsl[IO]
        import dsl._
        HttpRoutes.of[IO]:
            case req @ POST -> Root / "item" =>  
                val action = for {
                    item <- req.as[Item]
                    res <- itemService.createItem(itemRepo, item)
                } yield (res)

                action.flatMap(item => Ok(item.asJson)) //TODO make the id visible

        
    
    private def deleteItemById(itemService: ItemService, itemRepo: ItemRepo): HttpRoutes[IO] =
        val dsl = Http4sDsl[IO]
        import dsl._
        HttpRoutes.of[IO]:
            case DELETE -> Root / "item" / IntVar(id) => itemService.deleteItemById(itemRepo, id).flatMap {
                case Left(itemNotFound) => NotFound(s"Item with id ${itemNotFound.id} was not found or could't be deleted")
                case Right(_) => Ok("Item successfully deleted")
            }
  

    def endpoints(itemService: ItemService, itemRepo: ItemRepo): HttpRoutes[IO] =
        getAllItems(itemService, itemRepo) <+> 
        getItemById(itemService, itemRepo) <+>
        createItem(itemService, itemRepo) <+>
        deleteItemById(itemService, itemRepo)

object ItemRouter:
    def endpoints(xa: Transactor[IO], itemService: ItemService, itemRepo: ItemRepo): HttpRoutes[IO] =
        ItemController(xa).endpoints(itemService, itemRepo)