package com.panton.domain

import doobie.util.{Read, Write}
import io.circe.{Decoder, Encoder, Json}
import io.circe.syntax.EncoderOps

import java.sql.{Date, Time}

final case class Match(
  id: Id,
  date: Date,
  time: Time,
  homeClub: Name,
  guestClub: Name,
  homeClubGoals: Int,
  guestClubGoals: Int,
  referee: Name
)

object Match {

  implicit val jsonDecoder: Decoder[Match] = cursor =>
    for {
      id             <- cursor.get[Int]("id")
      dateStr        <- cursor.get[String]("date")
      timeStr        <- cursor.get[String]("time")
      homeClub       <- cursor.get[String]("homeClub")
      guestClub      <- cursor.get[String]("guestClub")
      homeClubGoals  <- cursor.get[Int]("homeClubGoals")
      guestClubGoals <- cursor.get[Int]("guestClubGoals")
      referee        <- cursor.get[String]("referee")
    } yield Match(
      Id(id),
      Date.valueOf(dateStr),
      Time.valueOf(timeStr),
      Name(homeClub),
      Name(guestClub),
      homeClubGoals,
      guestClubGoals,
      Name(referee)
    )

  implicit val jsonEncoder: Encoder[Match] = Encoder.instance {
    case Match(id, date, time, homeClub, guestClub, homeClubGoals, guestClubGoals, referee) => Json.obj(
      "id"      -> id.value.asJson,
      "date"           -> date.toString.asJson,
      "time"           -> time.toString.asJson,
      "homeClub"       -> homeClub.value.asJson,
      "guestClub"      -> guestClub.value.asJson,
      "homeClubGoals"  -> homeClubGoals.asJson,
      "guestClubGoals" -> guestClubGoals.asJson,
      "referee"        -> referee.value.asJson
    )
  }

  implicit val matchRead: Read[Match] = Read[(Int, String, String, String, String, Int, Int, String)].map {
    case (id, date, time, homeClub, guestClub, homeClubGoals, guestClubGoals, referee) =>
      Match(
        Id(id),
        Date.valueOf(date),
        Time.valueOf(time),
        Name(homeClub),
        Name(guestClub),
        homeClubGoals,
        guestClubGoals,
        Name(referee)
      )
  }

  implicit val matchWrite: Write[Match] = Write[(Int, String, String, String, String, Int, Int, String)].
    contramap { matchData =>
      (
        matchData.id.value,
        matchData.date.toString,
        matchData.time.toString,
        matchData.homeClub.value,
        matchData.guestClub.value,
        matchData.homeClubGoals,
        matchData.guestClubGoals,
        matchData.referee.value
      )
    }
}
