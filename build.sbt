import scalapb.compiler.Version.scalapbVersion

val Scala213 = "2.13.10"

val Scala212 = "2.12.17"

ThisBuild / organization := "com.example"

ThisBuild / scalaVersion := Scala213
val pgvVersion = "0.6.13"
lazy val core = (projectMatrix in file("core"))
  .defaultAxes()
  .settings(
    name := "internal-model-core",
    Compile / PB.targets := Seq(
      PB.gens.java                        -> (Compile / sourceManaged).value / "scalapb",
      scalapb.gen(javaConversions = true) -> (Compile / sourceManaged).value / "scalapb"
    ),
    libraryDependencies ++= Seq(
      "com.thesamet.scalapb.common-protos" %% "pgv-proto-scalapb_0.11" % (pgvVersion + "-0"),
      "com.thesamet.scalapb.common-protos" %% "pgv-proto-scalapb_0.11" % (pgvVersion + "-0") % "protobuf",
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapbVersion % "protobuf"
    )
  )
  .jvmPlatform(scalaVersions = Seq(Scala212, Scala213))

lazy val codeGen = (projectMatrix in file("code-gen"))
  .enablePlugins(BuildInfoPlugin)
  .defaultAxes()
  .settings(
    buildInfoKeys    := Seq[BuildInfoKey](name, organization, version, scalaVersion, sbtVersion),
    buildInfoPackage := "com.dimensions.compiler",
    libraryDependencies ++= Seq(
      "com.thesamet.scalapb" %% "compilerplugin"  % scalapb.compiler.Version.scalapbVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion,
      "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
    ),
    Compile / PB.protoSources += core.base / "src" / "main" / "protobuf",
    Compile / PB.targets := Seq(
      PB.gens.java                        -> (Compile / sourceManaged).value / "scalapb",
      scalapb.gen(javaConversions = true) -> (Compile / sourceManaged).value / "scalapb"
    )
  )
  .jvmPlatform(scalaVersions = Seq(Scala212, Scala213))

lazy val codeGenJVM212 = codeGen.jvm(Scala212)

lazy val protocGenInternalmodel = protocGenProject("protoc-gen-internal-model", codeGenJVM212)
  .settings(
    Compile / mainClass := Some("com.dimensions.compiler.CodeGenerator"),
    scalaVersion        := Scala212
  )

lazy val e2e = (projectMatrix in file("e2e"))
  .dependsOn(core)
  .enablePlugins(LocalCodeGenPlugin)
  .defaultAxes()
  .settings(
    skip in publish  := true,
    codeGenClasspath := (codeGenJVM212 / Compile / fullClasspath).value,
    libraryDependencies ++= Seq(
      "org.scalameta" %% "munit" % "0.7.9" % Test
    ),
    testFrameworks += new TestFramework("munit.Framework"),
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value / "scalapb",
      genModule(
        "com.dimensions.compiler.CodeGenerator$"
      ) -> (sourceManaged in Compile).value / "scalapb"
    )
  )
  .jvmPlatform(scalaVersions = Seq(Scala212, Scala213))

lazy val root: Project =
  project
    .in(file("."))
    .settings(
      publishArtifact := false,
      publish         := {},
      publishLocal    := {}
    )
    .aggregate(protocGenInternalmodel.agg)
    .aggregate(
      codeGen.projectRefs ++
        core.projectRefs: _*
    )
