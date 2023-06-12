package components.repositories

import doobie.util.transactor.Transactor
import doobie.implicits._
import doobie._
import models.{Item, ItemsRepoAlgebra}
import doobie.util.query.Query0
import models.ItemsRepoAlgebra
import doobie.util.update.Update0
import cats.effect.IO
import cats.data._
import cats.syntax.all._


object ItemSQL:

    def selectAll: Query0[Item] =
        sql"select * from item".query[Item]
        

    def selectById(id: Int): Query0[Item] = 
        sql"select * from item where id = $id".query[Item]
        

    def insert(item: Item): Update0 =
        sql"insert into item (name, price, location) values (${item.name}, ${item.price}, ${item.location})".update

    def deleteById(id: Int): Update0 =
        sql"delete from item where id = $id".update

class ItemRepo(xa: Transactor[IO]): 

    import ItemSQL._

    def getAllItems: IO[List[Item]] =
        selectAll.to[List].transact(xa)

    def getItemById(id: Int): IO[Option[Item]] =
        selectById(id).option.transact(xa)
    
    def createItem(item: Item): IO[Item] = 
        insert(item).withUniqueGeneratedKeys[Int]("id").map(id => item.copy()).transact(xa)
    
    def deleteItemById(id: Int): IO[Int] = 
        deleteById(id).run.transact(xa)