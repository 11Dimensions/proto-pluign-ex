package com.dimensions.internalmodel

import scalapb.GeneratedMessage

trait GeneratedInternalModel[I <: GeneratedMessage] {
  def toProto: I
}

trait GeneratedInternalModelCompanion[I <: GeneratedMessage, O <: GeneratedInternalModel[I]] {
  def validate(proto: I): Either[FailureReason, O]
}
