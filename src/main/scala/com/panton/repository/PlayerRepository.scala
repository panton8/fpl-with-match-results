package com.panton.repository

import cats.data.NonEmptyList
import cats.effect.IO
import doobie.implicits._
import utils.DriverTransactor.xa
import com.panton.domain.{Club, GameWeek, Id, Name, Player, Position, Price, Statistic}
import com.panton.repository.domain.TopAction
import doobie.Fragments
import doobie.implicits.toSqlInterpolator

object PlayerRepository {

  def listOfPlayers: IO[List[Player]] =
    fr"""
        SELECT
            *
        FROM
            players
      """
      .query[Player]
      .stream
      .compile
      .toList
      .transact(xa)

  def playerById(id: Id): IO[Option[Player]] =
    fr"""
        SELECT
            *
        FROM
            players
        WHERE
            id = ${id.value}
      """
      .query[Player]
      .option
      .transact(xa)

  def playersByClub(club: Name): IO[List[Player]] =
    fr"""
        SELECT
            *
        FROM
            players
        WHERE
            club = ${club.value}
      """
      .query[Player]
      .stream
      .compile
      .toList
      .transact(xa)

    def changeClub(playerId: Id, club: Name): IO[Int] =
    fr"""
        UPDATE
            players
        SET
            club = ${club.value}
        WHERE
            id = ${playerId.value}
      """
      .update
      .run
      .transact(xa)

  def playersByPosition(position: Position): IO[List[Player]] =
    fr"""
        SELECT
            *
        FROM
            players
        WHERE
            pos = $position
      """
      .query[Player]
      .stream
      .compile
      .toList
      .transact(xa)

  def getInjured(playerId: Id): IO[Int] =
    fr"""
        UPDATE
            players
        SET
            is_healthy = ${false}
        WHERE
            id = ${playerId.value}
      """
      .update
      .run
      .transact(xa)

  def getRecovered(playerId: Id): IO[Int] =
    fr"""
        UPDATE
            players
        SET
            is_healthy = ${true}
        WHERE
            id = ${playerId.value}
      """
      .update
      .run
      .transact(xa)

  def changePrice(playerId: Id, price: Price): IO[Int] =
    fr"""
        UPDATE
            players
        SET
            price = ${price.value}
        WHERE
            id = ${playerId.value}
      """
      .update
      .run
      .transact(xa)

  def updateStatistics(statistic: Statistic, player: Id, gameWeek: GameWeek): IO[Option[Int]] =
    fr"""
        INSERT INTO
            statistics (
                game_week,
                goals,
                assists,
                minutes,
                own_goals,
                yellow_cards,
                red_cards,
                saves,
                clean_sheet,
                player_id
             )
        VALUES
            (
               ${gameWeek.value},
               ${statistic.goals},
               ${statistic.assists},
               ${statistic.minutes},
               ${statistic.ownGoals},
               ${statistic.yellowCard},
               ${statistic.redCard},
               ${statistic.saves},
               ${statistic.cleanSheet},
               ${player.value}
            )
      """
      .update
      .run
      .transact(xa)
      .option

  def showPlayerStatisticsByWeek(playerId: Id, gameWeek: GameWeek): IO[Option[Statistic]] =
    fr"""
        SELECT
            goals,
            assists,
            minutes,
            own_goals,
            yellow_cards,
            red_cards,
            saves,
            clean_sheet
        FROM
            statistics
        WHERE
            player_id = ${playerId.value}
            AND game_week = ${gameWeek.value}
      """
      .query[Statistic]
      .option
      .transact(xa)

  def showTotalPlayerStatistics(playerId: Id): IO[Option[Statistic]] =
    fr"""
        SELECT
            SUM(goals),
            SUM(assists),
            SUM(minutes),
            SUM(own_goals),
            SUM(yellow_cards),
            SUM(red_cards),
            SUM(saves),
            SUM(clean_sheet)
        FROM
            statistics
        WHERE
            player_id = ${playerId.value}
      """
      .query[Statistic]
      .option
      .transact(xa)

  def getTheBestTopScorers: IO[List[TopAction]] =
    fr"""
        SELECT
            players.name, players.surname, players.club, players.pos, SUM(statistics.goals) as total_goals
        FROM
            players
        JOIN statistics ON players.id = statistics.player_id
        GROUP BY
            players.name, players.surname, players.club, players.pos
        ORDER BY
            total_goals DESC
      """
      .query[TopAction]
      .to[List]
      .transact(xa)

  def getTheBestTopAssistans: IO[List[TopAction]] =
    fr"""
        SELECT
            players.name, players.surname, players.club, players.pos, SUM(statistics.assists) as total_assists
        FROM
            players
        JOIN statistics ON players.id = statistics.player_id
        GROUP BY
            players.name, players.surname, players.club, players.pos
        ORDER BY
            total_assists DESC
      """
      .query[TopAction]
      .to[List]
      .transact(xa)

  def getTheBestTopGoalkeepers: IO[List[TopAction]] =
    fr"""
        SELECT
            players.name, players.surname, players.club, players.pos, SUM(statistics.clean_sheet) as total_clean_sheets
        FROM
            players
        JOIN statistics ON players.id = statistics.player_id
        WHERE
            players.pos = 'GKP'
        GROUP BY
            players.name, players.surname, players.club, players.pos
        ORDER BY
            total_clean_sheets DESC
      """
      .query[TopAction]
      .to[List]
      .transact(xa)


  def deletePlayer(playerId: Id): IO[Int] =
    fr"""
         DELETE FROM
             players
         WHERE
             id = ${playerId.value}
      """
      .update
      .run
      .transact(xa)

  def addPlayer(name: Name, surname: Name, price: Price, club: Name, position: Position, isHealthy: Boolean): IO[Int] =
    fr"""
          INSERT INTO
               players (
                   name,
                   surname,
                   club,
                   price,
                   pos,
                   is_healthy
               )
      VALUES
          (
              ${name.value},
              ${surname.value},
              ${club.value},
              ${price.value},
              ${position.entryName},
              $isHealthy
          )
        """
      .update.withUniqueGeneratedKeys[Int]("id")
      .transact(xa)

  def playersPos(players: NonEmptyList[Id]): IO[List[Position]] = {
    val q =
      fr"""
         SELECT
             pos
         FROM
             players
         WHERE
       """ ++ Fragments.in(fr"id", players.map(id => id.value))
    q.query[Position]
      .stream
      .compile
      .toList
      .transact(xa)
  }

  def playerPos(player: Id): IO[Option[Position]] =
    fr"""
        SELECT
            pos
        FROM
            players
        WHERE
            id = ${player.value}
      """
      .query[Position]
      .option
      .transact(xa)

  def getPlayersClubs: IO[List[Name]] =
    fr"""
        SELECT
            name
        FROM
            clubs
      """
      .query[Name]
      .stream
      .compile
      .toList
      .transact(xa)
}

//object AppRunner extends IOApp {
//  implicit class Debugger[A](io: IO[A]) {
//    def debug: IO[A] = io.map { a =>
//      println(s"[${Thread.currentThread().getName}] $a")
//      a
//    }
//  }
//
//  override def run(args: List[String]): IO[ExitCode] = {
//   PlayerRepository. addPlayer(Name("Миша"), Name("Мудрик"), Price(6.7), Name("Chelsea"), Position.Midfielder, true).debug().as(ExitCode.Success)
//  }
//}