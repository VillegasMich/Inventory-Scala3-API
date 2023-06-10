package components.services

import models.Item
import components.repositories.ItemRepo
import doobie.util.transactor.Transactor
import cats.effect.IO

class ItemService(xa: Transactor[IO]):

    def getAllItems: IO[List[Item]] = ItemRepo(xa).getAllItems