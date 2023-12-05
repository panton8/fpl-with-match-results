package com.panton.repository

import cats.effect.{ExitCode, IO, IOApp}
import doobie.implicits._
import com.panton.domain.{Id, Match, Name}
import com.panton.repository.domain.MatchSchedule
import com.panton.repository.utils.DriverTransactor.xa

object MatchRepository{
  def addMatchToSchedule(date: String, time: String, home_club: Name, guest_club: Name, referee_id: Id) =
    fr""" SELECT public.insert_match_without_goals(
          $date::date,
          $time::time,
          ${home_club.value},
          ${guest_club.value},
          ${referee_id.value}
        )
    """
      .query[Int]
      .unique
      .transact(xa)


  def getAllMatchesWithoutRes: IO[List[MatchSchedule]] =
    fr"""
     SELECT m.date, m.time, m.home_club, m.guest_club, r.name || ' ' || r.surname as referee_name
        FROM matches m
        INNER JOIN referees r ON m.referee_id = r.id
        WHERE m.home_club_goals IS NULL
    """
      .query[MatchSchedule]
      .stream
      .compile
      .toList
      .transact(xa)

  def insertResult(matchId: Id, home_goals: Int, guest_goals: Int): IO[Int] =
    fr"""
        UPDATE matches
        SET home_club_goals = $home_goals, guest_club_goals = $guest_goals
        WHERE id = ${matchId.value}
      """
      .update
      .run
      .transact(xa)

  def getAllMatchesWithResult: IO[List[Match]] =
    fr"""
         SELECT m.id, m.date, m.time, m.home_club, m.guest_club, m.home_club_goals, m.guest_club_goals, r.name || ' ' || r.surname as referee_name
            FROM matches m
            INNER JOIN referees r ON m.referee_id = r.id
            WHERE m.home_club_goals IS NOT NULL
    """
      .query[Match]
      .stream
      .compile
      .toList
      .transact(xa)

  def getAllMatchesWithoutResByDate(date:String): IO[List[MatchSchedule]] =
    fr"""
     SELECT m.date, m.time, m.home_club, m.guest_club, r.name || ' ' || r.surname as referee_name
        FROM matches m
        INNER JOIN referees r ON m.referee_id = r.id
        WHERE m.home_club_goals IS NULL AND m.date = to_date($date, 'YYYY-MM-DD')
    """
      .query[MatchSchedule]
      .stream
      .compile
      .toList
      .transact(xa)

  def getAllMatchesWithResultByDate(date:String): IO[List[Match]] =
    fr"""
         SELECT m.id, m.date, m.time, m.home_club, m.guest_club, m.home_club_goals, m.guest_club_goals, r.name || ' ' || r.surname as referee_name
            FROM matches m
            INNER JOIN referees r ON m.referee_id = r.id
            WHERE m.home_club_goals IS NOT NULL  AND m.date = to_date($date, 'YYYY-MM-DD')
    """
      .query[Match]
      .stream
      .compile
      .toList
      .transact(xa)
}