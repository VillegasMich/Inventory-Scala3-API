package models

trait ItemsRepoAlgebra[F[_]]:
    def getAllItems: F[List[Item]]
    def getItemById(id: Int): F[Option[Item]]
    def updateItem: F[Item]
    def deleteItem(id: Int): F[Int] 