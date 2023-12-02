package com.panton.service

import cats.effect.IO
import com.panton.domain.{Id, Name, News, Text}
import com.panton.repository.NewsRepository

final case class NewsService() {

  def showListOfNews(): IO[List[News]] = {
    NewsRepository.getNews
  }

  def NewsById(newsId: Id): IO[Option[News]] =
    NewsRepository.getNewsById(newsId)

  def publishNews(title: Name, text: Text) =
    NewsRepository.publishNews(title, text)
}
