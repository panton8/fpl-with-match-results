package com.panton.domain

final case class Match(
  id: Id,
  date: Date,
  time: Time,
  homeClub: Club,
  guestClub: Club,
  homeClubGoals: Int,
  guestClubGoals: Int,
  referee: Referee
)
