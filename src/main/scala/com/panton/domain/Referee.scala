package com.panton.domain

import doobie.Read
import doobie.util.Write
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

final case class Referee(
  id: Id,
  name: Name,
  surname: Name,
  nationality: Nationality,
  age: Age,
  appointments: Int
)

object Referee {

  implicit val jsonDecoder: Decoder[Referee] = cursor =>
    for {
      id       <- cursor.get[Int]("id")
      name     <- cursor.get[String]("name")
      surname  <- cursor.get[String]("surname")
      nationality <- cursor.get[String]("nationality")
      age    <- cursor.get[Int]("age")
      appointments <- cursor.get[Int]("appointments")
    } yield Referee(
      Id(id),
      Name(name),
      Name(surname),
      Nationality.withName(nationality),
      Age(age),
      appointments
    )

  implicit val jsonEncoder: Encoder[Referee] = Encoder.instance {
    case Referee(id, name, surname, nationality, age, appointments) => Json.obj(
      "id" -> id.value.asJson,
      "name"      -> name.value.asJson,
      "surname"   -> surname.value.asJson,
      "nationality" -> nationality.entryName.asJson,
      "age" -> age.value.asJson,
      "appointments" -> appointments.asJson
    )
  }

  implicit val refereeRead: Read[Referee] = Read[(Int, String, String, Nationality, Int, Int)].map {
    case (id, name, surname, nationality, age, appointments) =>
      Referee(
        Id(id),
        Name(name),
        Name(surname),
        nationality,
        Age(age),
        appointments
      )
  }

  implicit val refereeWrite: Write[Referee] = Write[(Int, String, String, Nationality, Int, Int)].
    contramap { referee =>
      (
        referee.id.value,
        referee.name.value,
        referee.surname.value,
        referee.nationality,
        referee.age.value,
        referee.appointments
      )
    }
}