package com.dimensions

import com.dimensions.test.InternalTestMessage
import java.util.UUID
import com.dimensions.internalmodel.FailureReason
import com.dimensions.internalmodel.implicits._
import com.dimensions.test.TestMessage
import com.dimensions.test.Color
// impoty com.dimensions.internalmodel.

class MyTest extends munit.FunSuite {


    test("TestMessageFieldNums is generated") {
        val uuid = UUID.randomUUID()
        val translation = TestMessage(uuid.toString, 10, Color.BLUE, None)
        val fromOg: Either[FailureReason,InternalTestMessage] = InternalTestMessage.validate(translation)
        assertEquals(fromOg.map(_.a.id), Right(uuid))
    }

    test("RoundTrip") {
        val uuid = UUID.randomUUID()
        val translation = TestMessage(uuid.toString, 10, Color.BLUE, None)
        val message = translation.toInternal[InternalTestMessage]
        val finalResult = message.map(_.toProto)

        assertEquals(finalResult, Right(translation))
    }
}
