package com.panton.domain

import doobie.util.{Read, Write}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

final case class Table(
  club: Name,
  played: Int,
  wins: Int,
  draws: Int,
  losses: Int,
  goalsFor: Int,
  goalsAgainst: Int,
  goalsDifference: Int,
  points: Point
)

object Table {

  implicit val jsonDecoder: Decoder[Table] = cursor =>
    for {
      club        <- cursor.get[String]("club")
      played      <- cursor.get[Int]("played")
      wins    <- cursor.get[Int]("wins")
      draws <- cursor.get[Int]("draws")
      losses <- cursor.get[Int]("losses")
      goalsFor <- cursor.get[Int]("goalsFor")
      goalsAgainst <- cursor.get[Int]("goalsAgainst")
      goalsDifference <- cursor.get[Int]("goalsDifference")
      points <- cursor.get[Int]("points")
    } yield Table(Name(club), played, wins, draws, losses, goalsFor, goalsAgainst, goalsDifference, Point(points))

  implicit val jsonEncoder: Encoder[Table] = Encoder.instance {
    case Table(club, played, wins, draws, losses, goalsFor, goalsAgainst, goalsDifference, points) => Json.obj(
      "club"     -> club.value.asJson,
      "played"          -> played.asJson,
      "wins"        -> wins.asJson,
      "draws" -> draws.asJson,
      "losses" -> losses.asJson,
      "goalsFor" -> goalsFor.asJson,
      "goalsAgainst" -> goalsAgainst.asJson,
      "goalsDifference" -> goalsDifference.asJson,
      "points" -> points.value.asJson
    )
  }

  implicit val tableRead: Read[Table] = Read[(String, Int, Int, Int, Int, Int, Int, Int, Int)].map {
    case (club, played, wins, draws, losses, goalsFor, goalsAgainst, goalsDifference, point) =>
      Table(Name(club), played, wins, draws, losses, goalsFor, goalsAgainst, goalsDifference, Point(point))
  }

  implicit val tableWrite: Write[Table] = Write[(String, Int, Int, Int, Int, Int, Int, Int, Int)].contramap { table =>
    (table.club.value, table.played, table.wins, table.draws, table.losses, table.goalsFor, table.goalsAgainst, table.goalsDifference, table.points.value)
  }
}