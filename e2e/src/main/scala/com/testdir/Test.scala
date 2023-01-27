package com.testdir

import java.util.UUID
import scala.util.Try
import scala.util.Failure
import com.dimensions.internalmodel.InternalTypeMapper
import com.dimensions.internalmodel.FailureReason
import com.dimensions.internalmodel.InvalidData

final case class Test(id: UUID)

object Test {
    implicit val test = InternalTypeMapper[String, com.testdir.Test](item => Try(UUID.fromString(item)).fold(t => Left(InvalidData(s"${item} is not a UUID",t)), id => Right(Test(id))))(_.id.toString())
}
