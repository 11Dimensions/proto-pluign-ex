package com.dimensions

import com.dimensions.test._
import java.util.UUID
import com.dimensions.internalmodel.FailureReason

class MyTest extends munit.FunSuite {
    test("TestMessageFieldNums is generated") {
        val uuid = UUID.randomUUID()
        val translation = TestMessage(uuid.toString, 10, Color.BLUE, None)
        val fromOg: Either[FailureReason,InternalTestMessage] = InternalTestMessage.validate(translation)
        assertEquals(fromOg.map(_.a.id), Right(uuid))
    }
}
