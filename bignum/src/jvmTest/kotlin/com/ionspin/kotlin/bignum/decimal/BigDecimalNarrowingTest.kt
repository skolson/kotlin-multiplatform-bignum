package com.ionspin.kotlin.bignum.decimal

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue
import org.junit.Test

/**
 * Unit tests (JVM for easy debugging) on Narrowing functionality for BigDecimal.  Short, Int,
 * Long are all tested around the edges using the respective MIN_VALUE and MAX_VALUE.  Float
 * and Double are a little different, but test similar edges.
 */
class JvmBigDecimalNarrowingTest {
    val someFloatValue = 12.12F

    @Test
    fun divideRoundingTest() {
        val dub = BigDecimal.fromDouble(Double.MIN_VALUE)
        val dub2 = dub.divide(BigDecimal.TEN)
        assertTrue(dub2 < dub)
        val dub3 = dub2.multiply(BigDecimal.TEN)
        val realDub = dub3.doubleValue(true)
        assertEquals(Double.MIN_VALUE, realDub)

        val bigNumber = "12345678901234567890.123456789"
        val divisior = "87654.7654"
        val maxPrecision = bigNumber.length

        val javaDub = java.math.BigDecimal(bigNumber)
        val javaResult = javaDub.divide(java.math.BigDecimal(divisior), java.math.MathContext(maxPrecision, java.math.RoundingMode.HALF_UP))
        val result = BigDecimal.parseStringWithMode(bigNumber)
        assertFailsWith<ArithmeticException> {
            result.divide(BigDecimal.parseStringWithMode(divisior))
        }
        val result2 = result.divide(BigDecimal.parseStringWithMode(divisior), DecimalMode(maxPrecision.toLong(), RoundingMode.ROUND_HALF_AWAY_FROM_ZERO))
        val javaStr = javaResult.toEngineeringString().trimEnd('0')
        val str = result2.toStringExpanded()
        assertEquals(javaStr, str)
        assertFailsWith<ArithmeticException> {
            result2.doubleValue(true)
        }
        assertFailsWith<ArithmeticException> {
            result2.floatValue(true)
        }

        val javaDubN = java.math.BigDecimal(bigNumber).negate()
        val javaResultN = javaDubN.divide(java.math.BigDecimal(divisior).negate(), java.math.MathContext(maxPrecision, java.math.RoundingMode.HALF_UP))
        val resultN = BigDecimal.parseStringWithMode(bigNumber).negate()
        assertFailsWith<ArithmeticException> {
            resultN.divide(BigDecimal.parseStringWithMode(divisior).negate())
        }
        val result2N = resultN.divide(BigDecimal.parseStringWithMode(divisior).negate(), DecimalMode(maxPrecision.toLong(), RoundingMode.ROUND_HALF_AWAY_FROM_ZERO))
        val javaStrN = javaResultN.toEngineeringString().trimEnd('0')
        val strN = result2N.toStringExpanded()
        assertEquals(javaStrN, strN)
        assertFailsWith<ArithmeticException> {
            result2N.doubleValue(true)
        }
        assertFailsWith<ArithmeticException> {
            result2N.floatValue(true)
        }
    }

    @Test
    fun doubleValueTest() {
        var dub = BigDecimal.fromDouble(Double.MAX_VALUE)
        assertEquals(Double.MAX_VALUE, dub.doubleValue(true))
        dub = BigDecimal.fromDouble(-Double.MAX_VALUE)
        assertEquals(-Double.MAX_VALUE, dub.doubleValue(true))
        dub = BigDecimal.fromDouble(Double.MIN_VALUE)
        assertEquals(Double.MIN_VALUE, dub.doubleValue(true))
        val dub2 = dub.divide(BigDecimal.TEN)
        assertFailsWith<ArithmeticException> {
            dub2.doubleValue(true)
        }
    }

    @Test
    fun floatValueTest() {
        var f = BigDecimal.fromFloat(someFloatValue)
        assertEquals(someFloatValue, f.floatValue(true))
        f = BigDecimal.fromFloat(Float.MAX_VALUE)
        assertEquals(Float.MAX_VALUE, f.floatValue(true))
        f = BigDecimal.fromFloat(-Float.MAX_VALUE)
        assertEquals(-Float.MAX_VALUE, f.floatValue(true))
        f = BigDecimal.fromFloat(Float.MIN_VALUE)
        assertEquals(Float.MIN_VALUE, f.floatValue(true))
        assertFailsWith<ArithmeticException> {
            f.divide(BigDecimal.TEN).floatValue(true)
        }
    }

    @Test
    fun byteValueTest() {
        val i = BigDecimal.fromByte(Byte.MAX_VALUE)
        assertEquals(Byte.MAX_VALUE, i.byteValue())
        assertFailsWith<ArithmeticException> {
            i.plus(BigDecimal.ONE).byteValue(true)
        }

        val iN = BigDecimal.fromByte(Byte.MIN_VALUE)
        assertEquals(Byte.MIN_VALUE, iN.byteValue())
        assertFailsWith<ArithmeticException> {
            iN.minus(BigDecimal.ONE).byteValue(true)
        }

        val f = BigDecimal.fromFloat(someFloatValue)
        assertFailsWith<ArithmeticException> {
            f.byteValue(true)
        }
        assertEquals(12, f.byteValue())
    }

    @Test
    fun shortValueTest() {
        val i = BigDecimal.fromShort(Short.MAX_VALUE)
        assertEquals(Short.MAX_VALUE, i.shortValue())
        assertFailsWith<ArithmeticException> {
            i.plus(BigDecimal.ONE).shortValue(true)
        }

        val iN = BigDecimal.fromShort(Short.MIN_VALUE)
        assertEquals(Short.MIN_VALUE, iN.shortValue())
        assertFailsWith<ArithmeticException> {
            iN.minus(BigDecimal.ONE).shortValue(true)
        }

        val f = BigDecimal.fromFloat(someFloatValue)
        assertFailsWith<ArithmeticException> {
            f.shortValue(true)
        }
        assertEquals(12, f.shortValue())
    }

    @Test
    fun intValueTest() {
        val i = BigDecimal.fromInt(Int.MAX_VALUE)
        assertEquals(Int.MAX_VALUE, i.intValue())
        assertFailsWith<ArithmeticException> {
            i.plus(BigDecimal.ONE).intValue(true)
        }

        val iN = BigDecimal.fromInt(Int.MIN_VALUE)
        assertEquals(Int.MIN_VALUE, iN.intValue())
        assertFailsWith<ArithmeticException> {
            iN.minus(BigDecimal.ONE).intValue(true)
        }

        val f = BigDecimal.fromFloat(someFloatValue)
        assertFailsWith<ArithmeticException> {
            f.intValue(true)
        }
        assertEquals(12, f.intValue())
    }

    @Test
    fun longValueTest() {
        val i = BigDecimal.fromLong(Long.MAX_VALUE)
        assertEquals(Long.MAX_VALUE, i.longValue())
        assertFailsWith<ArithmeticException> {
            i.plus(BigDecimal.ONE).longValue(true)
        }

        val iN = BigDecimal.fromLong(Long.MIN_VALUE)
        assertEquals(Long.MIN_VALUE, iN.longValue())
        assertFailsWith<ArithmeticException> {
            iN.minus(BigDecimal.ONE).longValue(true)
        }

        val f = BigDecimal.fromFloat(someFloatValue)
        assertFailsWith<ArithmeticException> {
            f.longValue(true)
        }
        assertEquals(12L, f.longValue())
    }

    @Test
    fun ubyteValueTest() {
        val i = BigDecimal.fromUByte(UByte.MAX_VALUE)
        assertEquals(UByte.MAX_VALUE, i.ubyteValue())
        assertFailsWith<ArithmeticException> {
            i.plus(BigDecimal.ONE).ubyteValue(true)
        }

        val f = BigDecimal.fromFloat(someFloatValue)
        assertFailsWith<ArithmeticException> {
            f.ubyteValue(true)
        }
        assertEquals(12.toUByte(), f.ubyteValue())
    }

    @Test
    fun ushortValueTest() {
        val i = BigDecimal.fromUShort(UShort.MAX_VALUE)
        assertEquals(UShort.MAX_VALUE, i.ushortValue())
        assertFailsWith<ArithmeticException> {
            i.plus(BigDecimal.ONE).ushortValue(true)
        }

        val f = BigDecimal.fromFloat(someFloatValue)
        assertFailsWith<ArithmeticException> {
            f.ushortValue(true)
        }
        assertEquals(12.toUShort(), f.ushortValue())
    }

    @Test
    fun uintValueTest() {
        val i = BigDecimal.fromUInt(UInt.MAX_VALUE)
        assertEquals(UInt.MAX_VALUE, i.uintValue())
        assertFailsWith<ArithmeticException> {
            i.plus(BigDecimal.ONE).uintValue(true)
        }

        val f = BigDecimal.fromFloat(someFloatValue)
        assertFailsWith<ArithmeticException> {
            f.uintValue(true)
        }
        assertEquals(12.toUInt(), f.uintValue())
    }

    @Test
    fun ulongValueTest() {
        val i = BigDecimal.fromULong(ULong.MAX_VALUE)
        assertEquals(ULong.MAX_VALUE, i.ulongValue())
        assertFailsWith<ArithmeticException> {
            i.plus(BigDecimal.ONE).ulongValue(true)
        }

        val f = BigDecimal.fromFloat(someFloatValue)
        assertFailsWith<ArithmeticException> {
            f.ulongValue(true)
        }
        assertEquals(12.toULong(), f.ulongValue())
    }

    @Test
    fun signCheckTest() {
        val x = BigDecimal.fromInt(123456)
        assertTrue(!x.isNegative)
        assertTrue(x.isPositive)
        assertTrue(x.signum() > 0)
        val x1 = x.negate()
        assertTrue(x1.isNegative)
        assertTrue(!x1.isPositive)
        assertTrue(x1.signum() < 0)
        val zero = BigDecimal.ZERO
        assertTrue(!zero.isPositive)
        assertTrue(!zero.isNegative)
        assertTrue(zero.isZero())
    }
}
