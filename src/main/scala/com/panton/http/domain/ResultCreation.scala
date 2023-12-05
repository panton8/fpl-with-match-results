package com.panton.http.domain

import com.panton.domain.Id
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

final case class ResultCreation(matchId: Id, home_goals: Int, guest_goals: Int)

object ResultCreation {

  implicit val jsonDecoder: Decoder[ResultCreation] = cursor =>
    for {
      matchId <- cursor.get[Int]("matchId")
      home_goals <- cursor.get[Int]("home_goals")
      guest_goals <- cursor.get[Int]("guest_goals")
    } yield ResultCreation(Id(matchId), home_goals, guest_goals)

  implicit val jsonEncoder: Encoder[ResultCreation] = Encoder.instance {
    case ResultCreation(date, home_club, guest_club) => Json.obj(
      "matchId" -> date.value.asJson,
      "home_goals" -> home_club.asJson,
      "guest_goals" -> guest_club.asJson
    )
  }

}

