package components.repositories

import doobie.util.transactor.Transactor
import doobie.implicits._
import models.{Item, ItemsRepoAlgebra}
import doobie.util.query.Query0
import models.ItemsRepoAlgebra
import cats.effect.IO

object ItemSQL:

    def selectAll: Query0[Item] =
        val query = sql"select * from item".query[Item]
        query

    def selectById(id: Int): Query0[Item] = 
        val query = sql"select * from item where id = $id".query[Item]
        query

class ItemRepo(xa: Transactor[IO]): 

    import ItemSQL._

    def getAllItems: IO[List[Item]] =
        selectAll.to[List].transact(xa)

    def getItemById(id: Int): IO[Option[Item]] =
        selectById(id).option.transact(xa)