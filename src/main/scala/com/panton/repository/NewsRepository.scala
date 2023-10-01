package com.panton.repository

import cats.effect.IO
import doobie.implicits._
import com.panton.domain.{Id, Name, News, Text}
import com.panton.repository.utils.DriverTransactor.xa

object NewsRepository {

  def publishNews(title: Name, text: Text, matchId: Id):IO[Int] =
    fr"""
        INSERT INTO
            news (title, text, match_id)
        VALUES(${title.value}, ${text.value}, ${matchId.value})
      """
      .update
      .withUniqueGeneratedKeys[Int]("id")
      .transact(xa)

  def getNews(): IO[List[News]] =
    fr"""
        SELECT
            *
        FROM
            news
      """
      .query[News]
      .stream
      .compile
      .toList
      .transact(xa)

  def netNewsById(newsId: Id): IO[Option[News]] =
    fr"""
        SELECT
            *
        FROM
            news
        WHERE id = ${newsId.value}
      """
      .query[News]
      .option
      .transact(xa)
}
