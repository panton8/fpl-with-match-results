package com.panton.domain

import doobie.util.{Read, Write}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

import java.sql.Date

final case class Club(
  name: Name,
  founded: Date,
  stadium: Name,
  manager: Name,
)

object Club {

  implicit val jsonDecoder: Decoder[Club] = cursor =>
    for {
      name    <- cursor.get[String]("name")
      founded <- cursor.get[String]("founded")
      stadium <- cursor.get[String]("stadium")
      manager <- cursor.get[String]("manager")
    } yield Club(
      Name(name),
      Date.valueOf(founded),
      Name(stadium),
      Name(manager)
    )

  implicit val jsonEncoder: Encoder[Club] = Encoder.instance {
    case Club(name, founded, stadium, manager) => Json.obj(
      "name"      -> name.value.asJson,
      "founded"   -> founded.toString.asJson,
      "stadium"   -> stadium.value.asJson,
      "manager"   -> manager.value.asJson
    )
  }

  implicit val clubRead: Read[Club] = Read[(String, String, String, String)].map {
    case (name, founded, stadium, manager) =>
      Club(
        Name(name),
        Date.valueOf(founded),
        Name(stadium),
        Name(manager)
      )
  }

  implicit val clubWrite: Write[Club] = Write[(String, String, String, String)].
    contramap { club =>
      (
        club.name.value,
        club.founded.toString,
        club.stadium.value,
        club.manager.value
      )
    }
}
