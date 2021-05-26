package utils
import models._
import play.api.libs.json.{JsString, Json, OFormat}

object JsonFormat {
  implicit val studentFormat: OFormat[Student] = Json.format[Student]


//  val student = Student("Dharmendra","d@gmail.com","uoh",Some(1))
//  val jsonString  = Json.toJson(student)

}



