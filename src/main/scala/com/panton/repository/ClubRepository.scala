package com.panton.repository

import cats.effect.IO
import com.panton.domain.Id
import doobie.implicits._
import com.panton.repository.utils.DriverTransactor.xa
import doobie.util.update.Update
import doobie.implicits.toSqlInterpolator
import doobie._

//object ClubRepository {
//
//  def getClubTableInfo():IO[]
//
//  def updateTable(matchId: Id, homeGoals: Int, guestGoals: Int): IO[Int] =
//    fr"""
//         UPDATE clubs_stat
//         SET
//      """
//
//}
