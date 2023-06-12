package models

case class Item(
    id: Option[Int],
    name: String,
    price: Int,
    location: String
  )