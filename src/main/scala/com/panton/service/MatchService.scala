package com.panton.service

import cats.effect.IO
import com.panton.domain.{Id, Match, Name}
import com.panton.repository.MatchRepository
import com.panton.repository.domain.MatchSchedule

final case class MatchService() {
  def addMatch(date: String, time: String, home_club: Name, guest_club: Name, referee_id: Id): IO[Int] =
    MatchRepository.addMatchToSchedule(date, time, home_club, guest_club, referee_id)

  def addMatchResult(matchId: Id, home_club_goals: Int, guest_club_goals: Int): IO[Int] =
    MatchRepository.insertResult(matchId, home_club_goals, guest_club_goals)

  def getSchedule(): IO[List[MatchSchedule]] =
    MatchRepository.getAllMatchesWithoutRes

  def getResult(): IO[List[Match]] =
    MatchRepository.getAllMatchesWithResult

  def getScheduleByDate(date: String): IO[List[MatchSchedule]] =
    MatchRepository.getAllMatchesWithoutResByDate(date)

  def getResultByDate(date: String): IO[List[Match]] =
    MatchRepository.getAllMatchesWithResultByDate(date)
}
