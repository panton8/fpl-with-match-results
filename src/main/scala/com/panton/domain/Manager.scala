package com.panton.domain

import doobie.util.{Read, Write}
import io.circe.syntax.EncoderOps
import io.circe.{Decoder, Encoder, Json}

final case class Manager(
    id: Id,
    name: Name,
    surname: Name,
    nationality: Nationality,
    age: Age
)


object Manager {

  implicit val jsonDecoder: Decoder[Manager] = cursor =>
    for {
      id <- cursor.get[Int]("id")
      name <- cursor.get[String]("name")
      surname <- cursor.get[String]("surname")
      nationality <- cursor.get[String]("nationality")
      age <- cursor.get[Int]("age")
    } yield Manager(
      Id(id),
      Name(name),
      Name(surname),
      Nationality.withName(nationality),
      Age(age)
    )

  implicit val jsonEncoder: Encoder[Manager] = Encoder.instance {
    case Manager(id, name, surname, nationality, age) => Json.obj(
      "id" -> id.value.asJson,
      "name" -> name.value.asJson,
      "surname" -> surname.value.asJson,
      "nationality" -> nationality.entryName.asJson,
      "age" -> age.value.asJson
    )
  }

  implicit val managerRead: Read[Manager] = Read[(Int, String, String, Nationality, Int)].map {
    case (id, name, surname, nationality, age) =>
      Manager(
        Id(id),
        Name(name),
        Name(surname),
        nationality,
        Age(age)
      )
  }

  implicit val managerWrite: Write[Manager] = Write[(Int, String, String, Nationality, Int)].
    contramap { manager =>
      (
        manager.id.value,
        manager.name.value,
        manager.surname.value,
        manager.nationality,
        manager.age.value
      )
    }
}
