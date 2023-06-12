package components.services

import models._
import middlewares._
import components.repositories.ItemRepo
import doobie.util.transactor.Transactor
import cats.effect.IO


class ItemService(xa: Transactor[IO]):

    def getAllItems(itemRepo: ItemRepo): IO[List[Item]] = itemRepo.getAllItems

    def getItemById(itemRepo: ItemRepo, id: Int): IO[Either[ItemDoesNotExistsError, Item]] = 
        ItemErrorsHandlers(itemRepo).exists(id)
        
    def createItem(itemRepo: ItemRepo, item: Item): IO[Item] = itemRepo.createItem(item)

    def deleteItemById(itemRepo: ItemRepo, id: Int): IO[Either[ItemDoesNotExistsError, Item]] = 
        ItemErrorsHandlers(itemRepo).exists(id)