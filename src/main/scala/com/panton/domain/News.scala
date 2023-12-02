package com.panton.domain

import doobie.util.{Read, Write}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

final case class News(
  title: Name,
  text: Text
)


object News {
  implicit val jsonDecoder: Decoder[News] = cursor =>
    for {
      title <- cursor.get[String]("title")
      text <- cursor.get[String]("text")
    } yield News(Name(title), Text(text))

  implicit val jsonEncoder: Encoder[News] = Encoder.instance {
    case News(title, text) => Json.obj(
      "title" -> title.value.asJson,
      "text" -> text.value.asJson
    )
  }

  implicit val userRead: Read[News] = Read[(String, String)].map {
    case (title, text) =>
      News(
        Name(title),
        Text(text)
      )
  }

  implicit val userWrite: Write[News] = Write[(String, String)].contramap {
    news =>
      (
        news.title.value,
        news.text.value
      )
  }
}