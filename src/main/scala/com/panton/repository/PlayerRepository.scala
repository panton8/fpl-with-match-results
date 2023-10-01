package com.panton.repository

import cats.effect.IO
import doobie.implicits._
import com.panton.repository.utils.DriverTransactor.xa
import com.panton.domain.{GameWeek, Id, Name, Player, Position, Price, Statistic}

class PlayerRepository {

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
                   health_status,
                   game_place
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
}
