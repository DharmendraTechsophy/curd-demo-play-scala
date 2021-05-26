package repository

import javax.inject.{Inject, Singleton}
import models.Student
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

@Singleton()
class StudentRepository @Inject()(protected val dbConfigProvider: DatabaseConfigProvider) extends StudentTable with HasDatabaseConfigProvider[JdbcProfile] {

   import profile.api._

  def insert(student: Student): Future[Int] =
    db.run {
      studentTableQueryInc += student
    }

  def update(student: Student): Future[Int] =
    db.run {
      studentTableQuery.filter(_.id === student.id).update(student)
    }

  def delete(id: Int): Future[Int] =
    db.run {
      studentTableQuery.filter(_.id === id).delete
    }

  def getAll(): Future[List[Student]] =
    db.run {
      studentTableQuery.to[List].result
    }

  def getById(studentId: Int): Future[Option[Student]] =
    db.run {
      studentTableQuery.filter(_.id === studentId).result.headOption
    }


  def ddl = studentTableQuery.schema

}

private[repository] trait StudentTable{
  self: HasDatabaseConfigProvider[JdbcProfile] =>

  import profile.api._

  lazy protected val studentTableQuery = TableQuery[StudentTable]
  lazy protected val studentTableQueryInc = studentTableQuery returning studentTableQuery.map(_.id)

  private[StudentTable] class StudentTable(tag: Tag) extends Table[Student](tag, "student")
  {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val name = column[String]("name")
    val email = column[String]("email")
    val universityName = column[String]("university_name")

    def * = (name, email, universityName, id.?) <> (Student.tupled, Student.unapply)
  }

}

