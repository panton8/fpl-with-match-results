package com.panton.service

import cats.effect.IO
import com.panton.domain.{Club, GameWeek, Id, Name, Player, Position, Statistic}
import com.panton.repository._

final case class PlayerService() {

  def showListOfPlayers(): IO[List[Player]] = {
    //implicit val playerOrdering1: Ordering[Player] = Ordering.fromLessThan(_.price.value < _.price.value)
    implicit val playerOrdering2: Ordering[Player] = Ordering.fromLessThan(_.price.value > _.price.value)
    PlayerRepository.listOfPlayers.map(_.sorted(playerOrdering2))
  }

  def showListOfPlayersByPosition(position: Position): IO[List[Player]] =
    PlayerRepository.playersByPosition(position)

  def showListOfPlayersByClub(club: Name): IO[List[Player]] =
    PlayerRepository.playersByClub(club)

  def findById(id: Id): IO[Option[Player]] =
    PlayerRepository.playerById(id)

  def addMatchActions(playerId: Id, statistic: Statistic, gameWeek: GameWeek): IO[Option[Int]] =
    PlayerRepository.updateStatistics(statistic, playerId, gameWeek)

  def takeTotalStatistic(playerId: Id): IO[Option[Statistic]] =
    PlayerRepository.showTotalPlayerStatistics(playerId)

  def takeWeekStatistic(playerId: Id, gameWeek: GameWeek): IO[Option[Statistic]] =
    PlayerRepository.showPlayerStatisticsByWeek(playerId, gameWeek)

  def getInjured(playerId: Id): IO[Int] =
    PlayerRepository.getInjured(playerId)

  def getRecovered(playerId: Id): IO[Int] =
    PlayerRepository.getRecovered(playerId)

  def giveTotalPoints(playerId: Id): IO[Int] = for {
    stat   <- PlayerRepository.showTotalPlayerStatistics(playerId)
    player <- PlayerRepository.playerById(playerId)
    points = (player, stat) match {
      case (Some(player), Some(stat)) => Statistic.countPoints(stat, player.position)
      case (_, _)                     => 0
    }
  } yield points

  def givePointsByWeek(playerId: Id, gameWeek: GameWeek): IO[Int] = for {
    stat   <- PlayerRepository.showPlayerStatisticsByWeek(playerId, gameWeek)
    player <- PlayerRepository.playerById(playerId)
    points = (player, stat) match {
      case (Some(player), Some(stat)) => Statistic.countPoints(stat, player.position)
      case (_, _)                     => 0
    }
  } yield points

  def giveAllClubs(): IO[List[Name]] =
    PlayerRepository.getPlayersClubs
}
