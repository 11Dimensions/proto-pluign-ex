package com.dimensions.internalmodel

import scala.util.Either

trait InternalTypeMapper[I, O] {
  def toCustom(input: I): Either[FailureReason, O]
  def toBase(input: O): I
}

object InternalTypeMapper {
  def apply[I, O](to: I => Either[InvalidData, O])(from: O => I) =
    new InternalTypeMapper[I, O] {
      override def toCustom(input: I): Either[FailureReason, O] = to(input)

      override def toBase(input: O): I = from(input)

    }
}

sealed trait FailureReason {
  def underlying: Throwable
}

case class MissingRequired(message: String, underlying: Throwable) extends FailureReason

case class InvalidData(message: String, underlying: Throwable) extends FailureReason
