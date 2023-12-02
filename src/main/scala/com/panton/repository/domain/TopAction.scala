package com.panton.repository.domain

import com.panton.domain.{Name, Position}
import doobie.util.Read

final case class TopAction(name: Name, surname: Name, club: Name, position: Position, actionResult: Int)

object TopAction {
  implicit val topActionRead: Read[TopAction] = Read[(String, String, String, Position, Int)].map {
    case (
      name,
      surname,
      club,
      position,
      actionRes
      ) =>
      TopAction(
        Name(name),
        Name(surname),
        Name(club),
        position,
        actionRes
      )
  }
}
