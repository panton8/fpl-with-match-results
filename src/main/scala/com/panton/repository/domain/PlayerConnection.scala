package com.panton.repository.domain

import com.panton.domain.{Id, Name, Position, Price}
import doobie.util.Read
import io.circe.{Encoder,Json}
import io.circe.syntax.EncoderOps

final case class PlayerConnection(
   id: Id,
   name: Name,
   surname: Name,
   club: Name,
   price: Price,
   position: Position,
   isHealthy: Boolean,
   isCaptain: Boolean,
   isStarter: Boolean,
)

object PlayerConnection {
  implicit val playerConnectionRead: Read[PlayerConnection] = Read[(Int, String, String, String, Double, Position, Boolean, Boolean, Boolean)].map {
    case (
      id,
      name,
      surname,
      club,
      price,
      position,
      isHealthy,
      isCaptain,
      isStarter
      ) =>
      PlayerConnection(
        Id(id),
        Name(name),
        Name(surname),
        Name(club),
        Price(price),
        position,
        isHealthy,
        isCaptain,
        isStarter
      )
  }

  implicit val jsonEncoder: Encoder[PlayerConnection] = Encoder.instance {
    case PlayerConnection(id, name, surname, club, price, position, isHealthy, isCaptain, isStarter) => Json.obj(
      "id" -> id.value.asJson,
      "name" -> name.value.asJson,
      "surname" -> surname.value.asJson,
      "club" -> club.value.asJson,
      "price" -> price.value.asJson,
      "position" -> position.entryName.asJson,
      "isHealthy" -> isHealthy.asJson,
      "isCaptain" -> isCaptain.asJson,
      "isStarter" -> isStarter.asJson,
    )
  }
}