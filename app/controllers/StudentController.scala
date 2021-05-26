package controllers

import com.google.inject.Inject
import play.api.libs.json.{JsError, JsValue, Json}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}
import repository.StudentRepository
import models.Student
import utils.JsonFormat._
import scala.concurrent.{ExecutionContext, Future}

class StudentController @Inject()(
                                    cc: ControllerComponents,
                                    studentRepository: StudentRepository
                                  )(implicit ec: ExecutionContext)
  extends AbstractController(cc) {


  def list: Action[AnyContent] =
    Action.async {
      studentRepository.getAll().map { res =>
        Ok(Json.toJson(res))
      }
    }

  def create: Action[JsValue] =
    Action.async(parse.json) {
      request =>
      request.body.validate[Student].fold(
        error => Future.successful(BadRequest(JsError.toJson(error))),
        {
          student =>studentRepository.insert(student).map { createdStudentId =>
              Ok(Json.toJson(Map("id" -> createdStudentId)))
            }
        }
      )
    }

  def delete(studentId: Int): Action[AnyContent] =
    Action.async { _ =>
      studentRepository.delete(studentId).map { res =>
         Ok(Json.toJson(res))
      }
    }

  def get(studentId: Int): Action[AnyContent] =
    Action.async { _ =>
      studentRepository.getById(studentId).map { studentOpt =>
        studentOpt.fold(Ok(Json.toJson("No record with this id...!")))(student =>
          Ok(Json.toJson(student)))
      }
    }

  def update: Action[JsValue] =
    Action.async(parse.json) { request =>
      request.body.validate[Student].fold(
        error => Future.successful(BadRequest(JsError.toJson(error))),
        {
          student => studentRepository.update(student).map {
            res =>Ok(Json.toJson(res))
          }
        }
      )
    }


}

