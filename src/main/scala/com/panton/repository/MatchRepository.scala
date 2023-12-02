package com.panton.repository

import java.sql.{Date, Time}
import cats.effect.{ExitCode, IO, IOApp}
import doobie.implicits._
import com.panton.domain.{Id, Match, Name}
import com.panton.repository.utils.DriverTransactor.xa

//object MatchRepository {
//  def getAllMatchesByDate(date: String): IO[List[Match]] =
//    fr"""
//      SELECT *
//      FROM matches
//      WHERE date = ${Date.valueOf(date)}
//    """
//      .query[Match]
//      .stream
//      .compile
//      .toList
//      .transact(xa)
//
//  def addMatch(date: String, time: Time, homeClub: Id, guestClub: Id, refId: Id) =
//    fr"""
//        INSERT INTO
//            matches(date, time, home_club_id, guest_club_id, referee_id)
//        VALUES(${Date.valueOf(date)}, ${time}, ${homeClub.value}, ${guestClub.value}, ${refId.value})
//      """
//      .update
//      .withUniqueGeneratedKeys[Int]("id")
//      .transact(xa)
//
//  def addMatchResult(matchId: Id, homeGoals: Int, guestGoals: Int): IO[Int] =
//    fr"""
//        UPDATE
//            matches
//        SET
//            home_club_goals = $homeGoals AND
//            guest_club_goals = $guestGoals
//        WHERE
//            id = ${matchId.value}
//      """
//      .update
//      .run
//      .transact(xa)
//
//  def updateMatchDateAndTime(matchId: Id, date: String, time: Time):IO[Int] =
//    fr"""
//        UPDATE
//            matches
//        SET
//            date = ${Date.valueOf(date)} AND
//            time = $time
//        WHERE
//            id = ${matchId.value}
//      """
//      .update
//      .run
//      .transact(xa)
//
//  def updateMatchReferee(matchId: Id, refId: Id): IO[Int] =
//    fr"""
//        UPDATE
//            matches
//        SET
//            referee_id = ${refId.value}
//        WHERE
//            id = ${matchId.value}
//      """
//      .update
//      .run
//      .transact(xa)
//
//  def updateMatchInfo(matchId: Id, date: Date, time: Time, refId: Id): IO[Int] =
//    fr"""
//        UPDATE
//            matches
//        SET
//            date = $date AND
//            time = $time AND
//            referee_id = ${refId.value}
//        WHERE
//            id = ${matchId.value}
//      """
//      .update
//      .run
//      .transact(xa)
//
//  def getMatchClubs(matchId: Id): IO[List[Name]] =
//    fr"""
//        SELECT home_club, guest_club
//        FROM matches
//        WHERE id = ${matchId.value}
//      """
//      .query[Name]
//      .stream
//      .compile
//      .toList
//      .transact(xa)
//}

