package database

import doobie.util.transactor.Transactor
import cats.effect.IO

object DatabaseConfig:

    def getConnection: Transactor[IO] = 
        val xa = Transactor.fromDriverManager[IO](
            "org.postgresql.Driver",
            "jdbc:postgresql:Inventory",
            "admin",
            "admin"
        )
        xa
    