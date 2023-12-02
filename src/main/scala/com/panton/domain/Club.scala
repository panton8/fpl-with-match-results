package com.panton.domain

import doobie.util.{Read, Write}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

import java.sql.Date

final case class Club(
  id: Id,
  name: Name,
  founded: Date,
  stadium: Name,
  manager: Name,
)

object Club {

  implicit val jsonDecoder: Decoder[Club] = cursor =>
    for {
      id      <- cursor.get[Int]("id")
      name    <- cursor.get[String]("name")
      founded <- cursor.get[String]("founded")
      stadium <- cursor.get[String]("stadium")
      manager <- cursor.get[String]("manager")
    } yield Club(
      Id(id),
      Name(name),
      Date.valueOf(founded),
      Name(stadium),
      Name(manager)
    )

  implicit val jsonEncoder: Encoder[Club] = Encoder.instance {
    case Club(id, name, founded, stadium, manager) => Json.obj(
      "id" -> id.value.asJson,
      "name"      -> name.value.asJson,
      "founded"   -> founded.toString.asJson,
      "stadium"   -> stadium.value.asJson,
      "manager"   -> manager.value.asJson
    )
  }

  implicit val clubRead: Read[Club] = Read[(Int, String, String, String, String)].map {
    case (id, name, founded, stadium, manager) =>
      Club(
        Id(id),
        Name(name),
        Date.valueOf(founded),
        Name(stadium),
        Name(manager)
      )
  }

  implicit val clubWrite: Write[Club] = Write[(Int, String, String, String, String)].
    contramap { club =>
      (
        club.id.value,
        club.name.value,
        club.founded.toString,
        club.stadium.value,
        club.manager.value
      )
    }
}
