package com.panton.repository

import cats.effect.IO
import com.panton.domain.{Age, Id, Name, Nationality, Referee}
import com.panton.repository.utils.DriverTransactor.xa
import doobie.implicits._

object RefereeRepository {

  def addRef(name: Name, surname: Name, nationality: Nationality, age: Age): IO[Int] =
    fr"""
        INSERT INTO
            referees(name, surname, nationality, age)
        VALUES (${name.value}, ${surname.value}, ${nationality.entryName}, ${age.value})
      """
      .update
      .withUniqueGeneratedKeys[Int]("id")
      .transact(xa)

  def deleteRef(refId: Id): IO[Int] =
    fr"""
        DELETE FROM
            referees
        WHERE
            id = ${refId.value}
      """
      .update
      .run
      .transact(xa)

  def getRefs:IO[List[Referee]] =
    fr"""
        SELECT
            *
        FROM
            referees
      """
      .query[Referee]
      .stream
      .compile
      .toList
      .transact(xa)
}
