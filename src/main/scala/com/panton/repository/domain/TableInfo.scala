package com.panton.repository.domain

import com.panton.domain.Name
import doobie.util.Read
import io.circe.syntax.EncoderOps
import io.circe.{Encoder, Json}

final case class TableInfo(
  club: Name,
  played: Int,
  wins: Int,
  draws: Int,
  losses: Int,
  goals_for: Int,
  goals_against: Int,
  goals_difference: Int,
  points: Int
)

object TableInfo {
  implicit val topActionRead: Read[TableInfo] = Read[(String, Int, Int, Int, Int, Int, Int, Int, Int )].map {
    case (
      club,
      played,
      wins,
      draws,
      losses,
      goals_for,
      goals_against,
      goals_difference,
      points
      ) =>
      TableInfo(
        Name(club),
        played,
        wins,
        draws,
        losses,
        goals_for,
        goals_against,
        goals_difference,
        points
      )
  }

  implicit val jsonEncoder: Encoder[TableInfo] = Encoder.instance {
    case TableInfo(name, played, wins, draws, losses, goals_for, goals_against, goals_difference, points) => Json.obj(
      "club" -> name.value.asJson,
      "played" -> played.asJson,
      "wins" -> wins.asJson,
      "draws" -> draws.asJson,
      "losses" -> losses.asJson,
      "goals_for" -> goals_for.asJson,
      "goals_against" -> goals_against.asJson,
      "goals_difference" -> goals_difference.asJson,
      "points" -> points.asJson
    )
  }
}