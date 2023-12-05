package com.panton.repository.domain

import com.panton.domain.Name
import doobie.util.Read
import io.circe.syntax.EncoderOps
import io.circe.{Encoder, Json}

final case class MatchSchedule(date: String, time: String, home_club: Name, guest_club: Name, referee: Name)

object MatchSchedule {
  implicit val topActionRead: Read[MatchSchedule] = Read[(String, String, String, String, String)].map {
    case (
      date,
      time,
      home_club,
      guest_club,
      referee
      ) =>
      MatchSchedule(
        date,
        time,
        Name(home_club),
        Name(guest_club),
        Name(referee)
      )
  }

  implicit val jsonEncoder: Encoder[MatchSchedule] = Encoder.instance {
    case MatchSchedule(date, time, home_club, guest_club, referee) => Json.obj(
      "date" -> date.asJson,
      "time" -> time.asJson,
      "home_club" -> home_club.value.asJson,
      "guest_club" -> guest_club.value.asJson,
      "referee" -> referee.value.asJson
    )
  }
}
