package com.dimensions.internalmodel

import scalapb.GeneratedMessage

trait GeneratedInternalModel[I <: GeneratedMessage]

trait GeneratedInternalModelCompanion[I <: GeneratedMessage, O <: GeneratedInternalModel[I]] {
  def validate(proto: I): Either[FailureReason, O]
  def toProto(internal: O): I
}
