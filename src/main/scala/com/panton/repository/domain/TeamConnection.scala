package com.panton.repository.domain
import com.panton.domain.{Club, GameWeek, Id, Name, Position, Price, Transfer}
import doobie.util.Read

final case class TeamConnection(
                                 teamId: Id,
                                 teamName: Name,
                                 freeTransfers: Transfer,
                                 playerId: Id,
                                 playerName: Name,
                                 playerSurname: Name,
                                 club: Name,
                                 price: Price,
                                 position: Position,
                                 isHealthy: Boolean,
                                 isCaptain: Boolean,
                                 isStarter: Boolean,
                                 gameWeek: GameWeek,
                                 goals: Int,
                                 assists: Int,
                                 minutes: Int,
                                 ownGoals: Int,
                                 yellowCards: Int,
                                 redCards: Int,
                                 saves: Int,
                                 cleanSheet: Int
                               )

object TeamConnection {
  implicit val teamConnectionRead: Read[TeamConnection] = Read[(Int, String, Int, Int, String, String, String, Double, Position, Boolean, Boolean, Boolean, Int, Int, Int, Int, Int, Int, Int, Int, Int)].map{
    case (
      teamId,
      teamName,
      transfers,
      playerId,
      playerName,
      playerSurname,
      club,
      price,
      position,
      healthStatus,
      role,
      gamePlace,
      gameWeek,
      goals,
      assists,
      minutes,
      ownGoals,
      yellowCards,
      redCards,
      saves,
      cleanSheet
      ) =>
      TeamConnection(
        Id(teamId),
        Name(teamName),
        Transfer(transfers),
        Id(playerId),
        Name(playerName),
        Name(playerSurname),
        Name(club),
        Price(price),
        position,
        healthStatus,
        role,
        gamePlace,
        GameWeek(gameWeek),
        goals,
        assists,
        minutes,
        ownGoals,
        yellowCards,
        redCards,
        saves,
        cleanSheet
      )
  }
}
