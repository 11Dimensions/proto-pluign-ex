package com.dimensions

import com.dimensions.test._

class MyTest extends munit.FunSuite {
    test("TestMessageFieldNums is generated") {
        assertEquals(InternalTestMessage.a, 1)
        assertEquals(InternalTestMessage.b, 2)
    }

    test("NestedMessage is generated") {
        assertEquals(InternalTestMessage.InternalNestedMessage.color, 1)
    }
}
