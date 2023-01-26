package com.dimensions

import protocbridge.Artifact
import scalapb.GeneratorOption
import protocbridge.SandboxedJvmGenerator

object gen {
  def apply(
      options: GeneratorOption*
  ): (SandboxedJvmGenerator, Seq[String]) =
    (
      SandboxedJvmGenerator.forModule(
        "scala",
        Artifact(
          com.dimensions.compiler.BuildInfo.organization,
          "internal-model-codegen_2.12",
          com.dimensions.compiler.BuildInfo.version
        ),
        "com.dimensions.compiler.CodeGenerator$",
        com.dimensions.compiler.CodeGenerator.suggestedDependencies
      ),
      options.map(_.toString)
    )

  def apply(
      options: Set[GeneratorOption] = Set.empty
  ): (SandboxedJvmGenerator, Seq[String]) = apply(options.toSeq: _*)
}
