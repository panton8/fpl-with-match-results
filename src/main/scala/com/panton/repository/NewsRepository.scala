package com.panton.repository

import cats.effect.IO
import doobie.implicits._
import com.panton.domain.{Id, Name, News, Text}
import com.panton.repository.utils.DriverTransactor.xa

object NewsRepository {

  def publishNews(title: Name, text: Text):IO[Int] =
    fr"""
        INSERT INTO
            news (title, text)
        VALUES(${title.value}, ${text.value})
      """
      .update
      .withUniqueGeneratedKeys[Int]("id")
      .transact(xa)

  def getNews: IO[List[News]] =
    fr"""
        SELECT
            title, text
        FROM
            news
      """
      .query[News]
      .stream
      .compile
      .toList
      .transact(xa)

  def getNewsById(newsId: Id): IO[Option[News]] =
    fr"""
        SELECT
            title, text
        FROM
            news
        WHERE id = ${newsId.value}
      """
      .query[News]
      .option
      .transact(xa)

  def updateNewsById(newsId: Id, text: Text): IO[Int] =
    fr"""
        Update news
        SET text = ${text.value}
        WHERE id = ${newsId.value}
      """
      .update
      .run
      .transact(xa)
}
