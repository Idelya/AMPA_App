package com.example.bmi_calculator

import org.junit.Test

import org.junit.Assert.*
import org.junit.BeforeClass

class ExampleUnitTest {
    @Test
    fun bmi_count_value_for_correct() {
        val testBmi = BmiCalc()
        testBmi.countWithValidator(50.0,160.0)
        assertEquals("Bmi value is not correct", testBmi.bmi, 19.5, 0.1)

        testBmi.countWithValidator(35.0,143.0)
        assertEquals("Bmi value is not correct", testBmi.bmi, 17.1, 0.1)


        testBmi.countWithValidator(79.0,143.0)
        assertEquals("Bmi value is not correct", testBmi.bmi, 38.6, 0.1)
    }

    @Test
    fun bmi_count_value_for_correct_ln() {
        val testBmi = BmiCalc()
        testBmi.changeUnits()
        testBmi.countWithValidator(110.23,62.99)
        assertEquals("Bmi value is not correct", testBmi.bmi, 19.5, 0.1)

        testBmi.countWithValidator(77.16,56.3)
        assertEquals("Bmi value is not correct", testBmi.bmi, 17.1, 0.1)


        testBmi.countWithValidator(174.0,56.3)
        assertEquals("Bmi value is not correct", testBmi.bmi, 38.6, 0.1)
    }


    @Test
    fun bmi_category() {
        val testBmi = BmiCalc()
        testBmi.countWithValidator(50.0,160.0)
        assertEquals("Bmi category is not correct", testBmi.getResultStatus(testBmi.bmi), BmiResults.OPTIMUM)

        testBmi.countWithValidator(35.0,143.0)
        assertEquals("Bmi category is not correct", testBmi.getResultStatus(testBmi.bmi), BmiResults.UNDERWEIGHT)


        testBmi.countWithValidator(70.0,160.0)
        assertEquals("Bmi category is not correct", testBmi.getResultStatus(testBmi.bmi), BmiResults.OVERWEIGHT)

        testBmi.countWithValidator(79.0,143.0)
        assertEquals("Bmi category is not correct", testBmi.getResultStatus(testBmi.bmi), BmiResults.OBESITY)
    }

    @Test
    fun bmi_count_status() {
        val testBmi = BmiCalc()

        assertEquals("Bmi count status is not correct", testBmi.lastCountStatus, InputStatus.PENDING)
        var status = testBmi.countWithValidator(50.0,160.0)
        assertEquals("Bmi count status is not correct", status, InputStatus.SUCCESS)

        status = testBmi.countWithValidator(0.0,140.0)
        assertEquals("Bmi count status is not correct", status, InputStatus.MASS_TO_LOW)


        status = testBmi.countWithValidator(50.0,0.0)
        assertEquals("Bmi count status is not correct", status, InputStatus.HEIGHT_TO_LOW)


        status = testBmi.countWithValidator(50.0,null)
        assertEquals("Bmi count status is not correct", status, InputStatus.HEIGHT_EMPTY)


        status = testBmi.countWithValidator(null,140.0)
        assertEquals("Bmi count status is not correct", status, InputStatus.MASS_EMPTY)


        status = testBmi.countWithValidator(-10.0,140.0)
        assertEquals("Bmi count status is not correct", status, InputStatus.MASS_TO_LOW)


        status = testBmi.countWithValidator(50.0,-140.0)
        assertEquals("Bmi count status is not correct", status, InputStatus.HEIGHT_TO_LOW)


        status = testBmi.countWithValidator(1000.0,140.0)
        assertEquals("Bmi count status is not correct", status, InputStatus.MASS_TO_HIGH)


        status = testBmi.countWithValidator(100.0,1400.0)
        assertEquals("Bmi count status is not correct", status, InputStatus.HEIGHT_TO_HIGH)
    }

    @Test
    fun bmi_incorrect_data() {
        val testBmi = BmiCalc()
        testBmi.countWithValidator(0.0,140.0)
        assertEquals("Bmi shouldn't be counted, expected value is 0.0", testBmi.bmi, 0.0, 0.0)

        testBmi.countWithValidator(50.0,0.0)
        assertEquals("Bmi count status is not correct", testBmi.bmi, 0.0, 0.0)


        testBmi.countWithValidator(-10.0,0.0)
        assertEquals("Bmi count status is not correct", testBmi.bmi, 0.0, 0.0)


        testBmi.countWithValidator(10.0,-30.0)
        assertEquals("Bmi count status is not correct", testBmi.bmi, 0.0, 0.0)
    }
}