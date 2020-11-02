package com.example.bmi_calculator

enum class BmiResults {
    UNDERWEIGHT {
        override fun getColor(): Int = R.color.colorUnderweight
        override fun getDescribe(): Int = R.string.describeUnderweight
    },
    OPTIMUM {
        override fun getColor(): Int = R.color.colorOptimum
        override fun getDescribe(): Int = R.string.describeOptimum
    },
    OVERWEIGHT {
        override fun getColor(): Int = R.color.colorOverweight
        override fun getDescribe(): Int = R.string.describeOverweight
    },
    OBESITY {
        override fun getColor(): Int = R.color.colorObesity
        override fun getDescribe(): Int = R.string.describeObesity
    };

    abstract fun getColor(): Int
    abstract fun getDescribe(): Int
}