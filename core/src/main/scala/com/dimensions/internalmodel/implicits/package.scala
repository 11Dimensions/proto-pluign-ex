package com.dimensions.internalmodel
import scalapb.GeneratedMessage

package object implicits {
  implicit class ExternalExtension[I <: GeneratedMessage](message: I) {
    def toInternal[O <: GeneratedInternalModel[I]](implicit
        companion: GeneratedInternalModelCompanion[I, O]
    ): Either[FailureReason, O] =
      companion.validate(message)
  }

}