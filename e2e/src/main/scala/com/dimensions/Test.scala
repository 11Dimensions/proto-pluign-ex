package com.dimensions

import java.util.UUID
import scala.util.Try
import scala.util.Failure

final case class Test(id: UUID)

object Test {
    implicit val test = new com.dimensions.InternalValidator[_root_.scala.Predef.String, com.dimensions.Test] {

      override def validate(item: String): Try[Test] = {
        Try(UUID.fromString(item)).map(Test(_))
      }

      override def validate(item: Option[String]): Try[Test] = {
        item match {
          case None => Failure(new IllegalArgumentException("Missing value"))
          case Some(value) => validate(value)
        }
      }

    }
}



trait InternalValidator[I,O] {
    def validate(item:I): Try[O]
    def validate(item:Option[I]): Try[O]
}