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

object Statistic {
  def countPoints(statistic: Statistic, position: Position, isStarter: Boolean = true, isCaptain: Boolean = false): Int = {
    val points = Goal.points(statistic.goals, position).value +
      Assist.points(statistic.assists, position).value +
      Minutes.points(statistic.minutes, position).value +
      YellowCard.points(statistic.yellowCard, position).value +
      RedCard.points(statistic.redCard, position).value +
      CleanSheet.points(statistic.cleanSheet, position).value +
      OwnGoal.points(statistic.ownGoals, position).value +
      Saves.points(statistic.saves, position).value
    if (isCaptain) points * 2 else if (isStarter) points else 0
  }
}
