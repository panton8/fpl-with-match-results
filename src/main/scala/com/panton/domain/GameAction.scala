package com.panton.domain

import com.panton.domain.Position.{Defender, Forward, Goalkeeper, Midfielder}

abstract class GameAction{
  val points: (Int, Position) => Point
}

object Goal extends GameAction {
  override val points = (amount: Int, position: Position) =>
    position match {
      case Forward                => Point(4 * amount)
      case Midfielder             => Point(5 * amount)
      case Defender | Goalkeeper  => Point(6 * amount)
    }
}

object Assist extends GameAction {
  override val points = (amount: Int, position: Position) => Point(3 * amount)
}

object YellowCard extends GameAction {
  override val points= (amount: Int, position: Position) => Point(-1 * amount)
}

object RedCard extends GameAction {
  override val points = (amount: Int , position: Position) => Point(-3 * amount)
}

object OwnGoal extends GameAction {
  override val points = (amount: Int, position: Position) => Point(-2 * amount)
}

object Saves extends GameAction {
  override val points = (amount: Int, position: Position) => Point((1 * amount)/3)
}

object Minutes extends GameAction {
  override val points = (amount: Int, position: Position) => amount match {
    case value if value == 0  => Point(0)
    case value if value >= 60 => Point(2)
    case _                    => Point(1)
  }
}

object CleanSheet extends GameAction {
  override val points = (amount: Int, position: Position) => position match {
    case Forward               => Point(0 * amount)
    case Midfielder            => Point(1 * amount)
    case Defender | Goalkeeper => Point(4 * amount)
  }
}