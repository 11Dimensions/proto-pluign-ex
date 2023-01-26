# internal-model

A Protoc plugin that generates...

# Using the plugin

To add the plugin to another project:

```
addSbtPlugin("com.thesamet" % "sbt-protoc" % "1.0.1")

libraryDependencies += "com.example" %% "internal-model-codegen" % "0.1.0"
```

and the following to your `build.sbt`:
```
PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value / "scalapb",
  com.dimensions.gen() -> (sourceManaged in Compile).value / "scalapb"
)
```

# Development and testing

Code structure:
- `core`: contains the runtime library for this plugin
- `code-gen`: contains the protoc plugin (code generator)
- `e2e`: an integration test for the plugin

To test the plugin, within SBT:

```
> e2eJVM2_13/test
```

or 

```
> e2eJVM2_12/test
```

Generated Code Example
```
package com.dimensions.test


case class InternalTestMessage(
  a: com.dimensions.Test,
  b: _root_.scala.Int,
  c: com.dimensions.test.Color,
  msg: _root_.scala.Option[com.dimensions.test.TestMessage.NestedMessage],
  unknownFields: _root_.scalapb.UnknownFieldSet = _root_.scalapb.UnknownFieldSet.empty
) extends com.dimensions.internalmodel.GeneratedInternalModel[com.dimensions.test.TestMessage]

object InternalTestMessage extends com.dimensions.internalmodel.GeneratedInternalModelCompanion[com.dimensions.test.TestMessage, InternalTestMessage] {

    implicit def messageCompanion: com.dimensions.internalmodel.GeneratedInternalModelCompanion[com.dimensions.test.TestMessage, InternalTestMessage] = this
    
    override def validate(v: com.dimensions.test.TestMessage): Either[com.dimensions.internalmodel.FailureReason, InternalTestMessage] = {
      for {
        a <- implicitly[com.dimensions.internalmodel.InternalTypeMapper[_root_.scala.Predef.String, com.dimensions.Test]].toCustom(v.a)
      } yield(InternalTestMessage(a, v.b, v.c, v.msg, v.unknownFields))
    }
    override def toProto(internal: InternalTestMessage): com.dimensions.test.TestMessage = {
      val a = implicitly[com.dimensions.internalmodel.InternalTypeMapper[_root_.scala.Predef.String, com.dimensions.Test]].toBase(internal.a)
      com.dimensions.test.TestMessage(a, internal.b, internal.c, internal.msg, internal.unknownFields)
    }
}
```