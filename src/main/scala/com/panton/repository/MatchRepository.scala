package com.panton.repository

import java.sql.{Date, Time}
import cats.effect.IO
import doobie.implicits._
import com.panton.domain.{Id, Match}
import com.panton.repository.utils.DriverTransactor.xa

object MatchRepository {

  def addMatch(date: Date, time: Time, homeClub: Id, guestClub: Id, refId: Id) =
    fr"""
        INSERT INTO
            matches(date, time, home_club_id, guest_club_id, referee_id)
        VALUES(${date}, ${time}, ${homeClub.value}, ${guestClub.value}, ${refId.value})
      """
      .update
      .withUniqueGeneratedKeys[Int]("id")
      .transact(xa)

  def updateMatchResult(matchId: Id, homeGoals: Int, guestGoals: Int): IO[Int] =
    fr"""
        UPDATE
            matches
        SET
            home_club_goals = $homeGoals AND
            guest_club_goals = $guestGoals
        WHERE
            id = ${matchId.value}
      """
      .update
      .run
      .transact(xa)

  def updateMatchDateAndTime(matchId: Id, date: Date, time: Time):IO[Int] =
    fr"""
        UPDATE
            matches
        SET
            date = $date AND
            time = $time
        WHERE
            id = ${matchId.value}
      """
      .update
      .run
      .transact(xa)

  def updateMatchReferee(matchId: Id, refId: Id): IO[Int] =
    fr"""
        UPDATE
            matches
        SET
            referee_id = ${refId.value}
        WHERE
            id = ${matchId.value}
      """
      .update
      .run
      .transact(xa)

  def updateMatchInfo(matchId: Id, date: Date, time: Time, refId: Id): IO[Int] =
    fr"""
        UPDATE
            matches
        SET
            date = $date AND
            time = $time AND
            referee_id = ${refId.value}
        WHERE
            id = ${matchId.value}
      """
      .update
      .run
      .transact(xa)
}
