package com.panton.repository

import com.panton.domain.{Name, Nationality, Age, Id, Manager}
import com.panton.repository.utils.DriverTransactor.xa
import cats.effect.IO
import doobie.implicits._

object ManagerRepository
{
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

  def getRefs: IO[List[Manager]] =
    fr"""
        SELECT
            *
        FROM
            referees
      """
      .query[Manager]
      .stream
      .compile
      .toList
      .transact(xa)
}
