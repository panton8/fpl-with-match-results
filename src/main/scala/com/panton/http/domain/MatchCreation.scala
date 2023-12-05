package com.panton.http.domain

import com.panton.domain.{Id, Name}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

final case class MatchCreation(date: String, time: String, home_club: Name, guest_club: Name, referee_id: Id)

object MatchCreation {

  implicit val jsonDecoder: Decoder[MatchCreation] = cursor =>
    for {
      date <- cursor.get[String]("date")
      time <- cursor.get[String]("time")
      home_club <- cursor.get[String]("home_club")
      guest_club <- cursor.get[String]("guest_club")
      referee_id <- cursor.get[Int]("referee_id")
    } yield MatchCreation(date, time, Name(home_club), Name(guest_club), Id(referee_id))

  implicit val jsonEncoder: Encoder[MatchCreation] = Encoder.instance {
    case  MatchCreation(date, time, home_club, guest_club, referee_id) => Json.obj(
      "date" -> date.asJson,
      "time" -> time.asJson,
      "home_club" -> home_club.value.asJson,
      "guest_club" -> guest_club.value.asJson,
      "referee_id" -> referee_id.value.asJson
    )
  }
}
