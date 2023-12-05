package com.panton.repository

import cats.effect.IO
import com.panton.domain.{Club, Id, Name}
import com.panton.repository.domain.TableInfo
import doobie.implicits._
import com.panton.repository.utils.DriverTransactor.xa
import doobie.util.update.Update
import doobie.implicits.toSqlInterpolator
import doobie._

object ClubRepository {

  def getAllClubs(): IO[List[Club]] =
    fr"""
      SELECT
        c.name AS club_name,
        c.founded,
        c.stadium,
        CONCAT(m.name, ' ', m.surname) AS manager_name
    FROM
        clubs c
    JOIN
        managers m ON c.manager_id = m.id;

    """
      .query[Club]
      .stream
      .compile
      .toList
      .transact(xa)

  def getClubByName(club: Name): IO[Option[Club]] =
    fr"""
      SELECT
            c.name AS club_name,
            c.founded,
            c.stadium,
            CONCAT(m.name, ' ', m.surname) AS manager_name
        FROM
            clubs c
        JOIN
            managers m ON c.manager_id = m.id
      WHERE
           c.name = ${club.value}
    """
      .query[Club]
      .option
      .transact(xa)
  def getTableInfo():IO[List[TableInfo]] =
    fr"""
        SELECT
             *
        FROM
             clubs_stat
        ORDER BY points DESC, wins DESC, goals_for DESC, goals_difference DESC
      """
      .query[TableInfo]
      .stream
      .compile
      .toList
      .transact(xa)

}
