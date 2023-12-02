package com.panton.repository

import cats.effect.IO
import doobie.implicits._
import com.panton.domain.{GameWeek, Id, Name, Player, Point, Statistic, Team, Transfer}
import com.panton.repository.utils.DriverTransactor.xa
import doobie.util.update.Update
import doobie.implicits.toSqlInterpolator
import cats.data.NonEmptyList
import com.panton.repository.domain.{PlayerConnection, TeamConnection}
import doobie._

object TeamRepository {

  def listOfTeams(): IO[List[Team]] =
    fr"""
        SELECT
            id,
            name,
            points,
            available_transfers
        FROM
            teams
      """
      .query[Team]
      .stream
      .compile
      .toList
      .transact(xa)

  def createTeam(name: Name, userId: Id): IO[Int] =
    fr"""
        INSERT INTO
            teams (name, points, available_transfers, user_id)
        VALUES
            (${name.value}, ${0}, ${2}, ${userId.value})
      """
      .update
      .withUniqueGeneratedKeys[Int]("id")
      .transact(xa)

  def addPlayers(players: List[Id], teamId: Id): IO[Int] = {
    val teamWithPlayers =
      """
        INSERT INTO
            teams_players (player_id, team_id, is_captain, is_starter)
        VALUES
            (?, ?, ?, ?)
      """
    val tableValues = players.map(id => (id, teamId, false, true))
    Update[(Id, Id, Boolean, Boolean)](teamWithPlayers).updateMany(tableValues).transact(xa)
  }

  def deletePlayer(player: Id, teamId: Id): IO[Int] =
    fr"""
        DELETE FROM
            teams_players
        WHERE
            player_id = ${player.value}
            AND team_id = ${teamId.value}
      """
      .update
      .run
      .transact(xa)

  def insertPlayer(player: Id, teamId: Id): IO[Int] =
    fr"""
        INSERT INTO
            teams_players (team_id, player_id, is_captain, is_starter)
        VALUES
            (
                ${teamId.value},
                ${player.value},
                ${false},
                ${true}
            )
      """
      .update
      .run
      .transact(xa)

  def setCaptain(player: Id, teamId: Id): IO[Int] =
    fr"""
        UPDATE
            teams_players
        SET
            is_captain = ${true}
        WHERE
            player_id = ${player.value}
            AND team_id = ${teamId.value}
      """
      .update
      .run
      .transact(xa)

  def setOrdinary(teamId: Id, player: Id): IO[Int] =
    fr"""
        UPDATE
            teams_players
        SET
            is_captain = ${false}
        WHERE
            player_id != ${player.value}
            AND team_id = ${teamId.value}
      """
      .update
      .run
      .transact(xa)

  def teamOwner(teamId: Id): IO[Option[Id]] =
    fr"""
        SELECT
            user_id
        FROM
            teams
        WHERE
            id = ${teamId.value}
      """
      .query[Id]
      .option
      .transact(xa)

  def deleteTeam(teamId: Id) =
    fr"""
        DELETE FROM
            teams
        WHERE
            id = ${teamId.value}
      """
      .update
      .run
      .transact(xa)

  def teamById(id: Id): IO[Option[Team]] =
    fr"""
         SELECT
             *
         FROM
             teams
         WHERE
             id = ${id.value}
       """
      .query[Team]
      .option.
      transact(xa)

  def findByOwner(userId: Id): IO[Option[Id]] =
    fr"""
        SELECT
            id
        FROM
            teams
        WHERE
            user_id = ${userId.value}
      """
      .query[Id]
      .option
      .transact(xa)

  def teamCost(players: NonEmptyList[Id]): IO[Double] = {
    val q =
      fr"""
        SELECT
            SUM(price)
        FROM
            players
        WHERE
      """ ++ Fragments.in(fr"id", players.map(id => id.value))
    q.query[Double]
      .unique
      .transact(xa)
  }

  def makeTransfer(teamId: Id, currTransfers: Transfer): IO[Int] =
    fr"""
        UPDATE
            teams
        SET
            available_transfers = ${currTransfers.value - 1}
        WHERE
            id = ${teamId.value}
      """
      .update
      .run
      .transact(xa)

  def updateTeamInfo(teamId: Id, currPoints: Point, weekPoints: Point, transfers: Transfer): IO[Int] =
    fr"""
        UPDATE
            teams
        SET
            points = ${currPoints.value + weekPoints.value},
            available_transfers = CASE
                WHEN ${transfers.value == 2} THEN ${2}
                ELSE ${transfers.value + 1}
            END
        WHERE
            id = ${teamId.value}
      """
      .update
      .run
      .transact(xa)

  def changeGamePlace(player: Id, team: Id, toStart: Boolean): IO[Int] =
    fr"""
        UPDATE
            teams_players
        SET
            is_starter = $toStart
        WHERE
             team_id = ${team.value}
             AND player_id = ${player.value}
      """
      .update
      .run
      .transact(xa)

  def playersFromTeam(teamId: Id): IO[List[PlayerConnection]] =
    fr"""
        SELECT
            p.id AS player_id,
            p.name,
            p.surname,
            p.club,
            p.price,
            p.pos,
            p.is_healthy,
            tp.is_captain,
            tp.is_starter
        FROM
            teams t
            INNER JOIN teams_players tp ON t.id = tp.team_id
            INNER JOIN players p ON tp.player_id = p.id
        WHERE
            t.id = ${teamId.value}
      """
      .query[PlayerConnection]
      .stream
      .compile
      .toList
      .transact(xa)

  def teamByName(name: Name): IO[Option[Team]] =
    fr"""
          SELECT
              id,
              name,
              points,
              available_transfers
          FROM
              teams
          WHERE
              name = ${name.value}
        """
      .query[Team]
      .option
      .transact(xa)

  def getRole(playerId: Id, teamId: Id): IO[Option[Boolean]] =
    fr"""
          SELECT
              is_captain
          FROM
              teams_players
          WHERE
              team_id = ${teamId.value}
              AND player_id = ${playerId.value}
        """
      .query[Boolean]
      .option
      .transact(xa)

  def teamPoints(teamId: Id, gameWeek: GameWeek): IO[Team] = {
    fr"""
        SELECT
            t.id AS team_id,
            t.name,
            t.available_transfers,
            p.id AS player_id,
            p.name,
            p.surname,
            p.club,
            p.price,
            p.pos,
            p.is_healthy,
            tp.is_captain,
            tp.is_starter,
            s.game_week,
            s.goals,
            s.assists,
            s.minutes,
            s.own_goals,
            s.yellow_cards,
            s.red_cards,
            s.saves,
            s.clean_sheet
        FROM
            teams t
            INNER JOIN teams_players tp ON t.id = tp.team_id
            INNER JOIN players p ON tp.player_id = p.id
            INNER JOIN statistics s ON tp.player_id = s.player_id
        WHERE
            t.id = ${teamId.value}
            AND s.game_week = ${gameWeek.value}
      """
      .query[TeamConnection]
      .to[List]
      .map(
        teamC =>
          Team(
            teamC.head.teamId,
            teamC.head.teamName,
            Point(teamC.map(stat =>
              Statistic.countPoints(
                Statistic(
                  stat.goals,
                  stat.assists,
                  stat.minutes,
                  stat.ownGoals,
                  stat.yellowCards,
                  stat.redCards,
                  stat.saves,
                  stat.cleanSheet),
                stat.position,
                stat.isStarter,
                stat.isCaptain)).sum),
            teamC.head.freeTransfers,
          )
      )
      .transact(xa)
  }

  def getGamePlace(playerId: Id, teamId: Id): IO[Option[Boolean]] =
    fr"""
         SELECT
             is_starter
         FROM
             teams_players
         WHERE
             team_id = ${teamId.value}
             AND player_id = ${playerId.value}
       """
      .query[Boolean]
      .option
      .transact(xa)


  def deleteTeamPlayers(teamId: Id): IO[Int] =
    fr"""
        DELETE FROM
            teams_players
        WHERE
            team_id = ${teamId.value}
      """
      .update
      .run
      .transact(xa)
}
