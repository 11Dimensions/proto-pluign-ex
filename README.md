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

import scala.util.Try

case class InternalTestMessage(
  a: com.dimensions.Test,
  b: _root_.scala.Int,
  c: com.dimensions.test.Color,
  msg: _root_.scala.Option[com.dimensions.test.TestMessage.NestedMessage],
)

object InternalTestMessage {
  def fromOriginal(v: com.dimensions.test.TestMessage): Try[InternalTestMessage] = {
    for {
    a <- implicitly[com.dimensions.InternalValidator[_root_.scala.Predef.String, com.dimensions.Test]].validate(v.a)
    } yield(InternalTestMessage(a, v.b, v.c, v.msg))
  }
}
```