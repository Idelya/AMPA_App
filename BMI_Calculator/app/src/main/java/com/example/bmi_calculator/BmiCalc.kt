package com.example.bmi_calculator

class BmiCalc {
    var unitsEn = false
    var bmi = 0.0
    var result =  BmiResults.UNDERWEIGHT
    var lastCountStatus = InputStatus.PENDING
    private val MIN_MASS = 20.0
    private val MAX_MASS = 300.0
    private val MIN_HEIGHT = 80.0
    private val MAX_HEIGHT = 300.0
    private val KG_TO_LB = 2.20462262
    private val CM_TO_IN = 0.393700787

    private fun changeMassUnits(currentValue: Double): Double
            = if(unitsEn) currentValue * KG_TO_LB else currentValue;

    private fun changeHeightUnits(currentValue: Double): Double
            = if(unitsEn) currentValue * CM_TO_IN else currentValue;

    private fun setResult() {
        result = getResultStatus(bmi)
    }
    private fun count(mass: Double, height: Double) {
        bmi = if(unitsEn)  mass*703/(height*height) else mass/(height*height*0.0001)
        setResult()
    }

    fun countWithValidator(mass: Double?, height: Double?): InputStatus {
        lastCountStatus = checkData(mass, height)
        if(lastCountStatus == InputStatus.SUCCESS && height != null && mass != null) count(mass, height)
        return lastCountStatus;
    }

    fun changeUnits() {
        unitsEn = !unitsEn
    }

    private fun checkData(mass: Double?, height: Double?): InputStatus {
        return when {
            mass == null -> InputStatus.MASS_EMPTY
            height == null -> InputStatus.HEIGHT_EMPTY
            mass < changeMassUnits(MIN_MASS) -> InputStatus.MASS_TO_LOW
            height < changeHeightUnits(MIN_HEIGHT) -> InputStatus.HEIGHT_TO_LOW
            mass > changeMassUnits(MAX_MASS) -> InputStatus.MASS_TO_HIGH
            height > changeHeightUnits(MAX_HEIGHT) -> InputStatus.HEIGHT_TO_HIGH
            else -> InputStatus.SUCCESS
        }
    }

    fun getResultStatus(userBmi: Double): BmiResults {
        return when{
            userBmi < 18.5 -> BmiResults.UNDERWEIGHT
            userBmi < 25 -> BmiResults.OPTIMUM
            userBmi < 30 -> BmiResults.OVERWEIGHT
            else -> BmiResults.OBESITY
        }
    }

    fun setLastCountSuccess(lastBmi: Double) {
        if(lastBmi > 0 && lastBmi < 100) {
            bmi = lastBmi
            lastCountStatus = InputStatus.SUCCESS
        }
    }
}