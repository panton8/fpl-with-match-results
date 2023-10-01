package com.panton.domain

import java.sql.{Date, Time}

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
