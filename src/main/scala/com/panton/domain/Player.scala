package com.panton.domain

import doobie.util.{Read, Write}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

final case class Player(
  id: Id,
  name: Name,
  surname: Name,
  club: Name,
  price: Price,
  position: Position,
  isHealthy: Boolean = true,
)

object Player {

  implicit val jsonDecoder: Decoder[Player] = cursor =>
    for {
      id       <- cursor.get[Int]("id")
      name     <- cursor.get[String]("name")
      surname  <- cursor.get[String]("surname")
      club     <- cursor.get[String]("club")
      price    <- cursor.get[Double]("price")
      position <- cursor.get[String]("position")
      status   <- cursor.get[Boolean]("isHealthy")
    } yield Player(
      Id(id),
      Name(name),
      Name(surname),
      Name(club),
      Price(price),
      Position.withName(position),
      status,
    )

  implicit val jsonEncoder: Encoder[Player] = Encoder.instance {
    case Player(id, name, surname, club, price, position, status) => Json.obj(
      "id" -> id.value.asJson,
      "name"      -> name.value.asJson,
      "surname"   -> surname.value.asJson,
      "club"      -> club.value.asJson,
      "price"     -> price.value.asJson,
      "position"  -> position.entryName.asJson,
      "isHealthy"    -> status.asJson,
    )
  }

  implicit val playerRead: Read[Player] = Read[(Int, String, String, String, Double, Position, Boolean)].map {
    case (id, name, surname, club, price, position, status) =>
      Player(
        Id(id),
        Name(name),
        Name(surname),
        Name(club),
        Price(price),
        position,
        status
      )
  }

  implicit val playerWrite: Write[Player] = Write[(Int, String, String, String, Double, Position, Boolean)].
    contramap { player =>
      (
        player.id.value,
        player.name.value,
        player.surname.value,
        player.club.value,
        player.price.value,
        player.position,
        player.isHealthy
      )
    }
}