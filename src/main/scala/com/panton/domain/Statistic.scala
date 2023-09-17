package com.panton.domain

final case class Statistic(
    goals:Int,
    assists: Int,
    minutes: Int,
    ownGoals: Int,
    yellowCard: Int,
    redCard: Int,
    saves: Int,
    cleanSheet: Int
)
