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

    def deleteItemById(itemRepo: ItemRepo, id: Int) =
        val action = for {
            posErr <- ItemErrorsHandlers(itemRepo).exists(id)
        } yield posErr

        action.map(either => either match {
            case Left(value) => Left(ItemDoesNotExistsError(id)) 
            case Right(value) => Right(itemRepo.deleteItemById(id))
        })
    
    def updateItem(itemRepo: ItemRepo, item: Item)=
        val action = for {
            posErr <- ItemErrorsHandlers(itemRepo).exists(item.id.getOrElse(Int.MaxValue))
        } yield posErr

        action.map( either => 
            either match {
                case Left(value) => Left(ItemDoesNotExistsError(Int.MaxValue))
                case Right(value) => Right(itemRepo.updateItem(item))
            }  
        )
        