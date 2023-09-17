package com.panton.domain

final case class Table(
  club: Club,
  played: Int,
  wins: Int,
  draws: Int,
  losses: Int,
  goalsFor: Int,
  goalsAgainst: Int,
  goalsDifference: Int,
  points: Point
)
