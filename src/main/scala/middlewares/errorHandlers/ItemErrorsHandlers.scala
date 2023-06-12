package middlewares

import components.repositories.ItemRepo
import models.Item
import scala.util
import cats.effect.IO

case class ItemDoesNotExistsError(id: Int)

class ItemErrorsHandlers(repo: ItemRepo):


    def exists(id: Int): IO[Either[ItemDoesNotExistsError, Item]] =
        repo.getItemById(id).map {
            case Some(item) => Right(item)
            case None => Left(ItemDoesNotExistsError(id))
        }