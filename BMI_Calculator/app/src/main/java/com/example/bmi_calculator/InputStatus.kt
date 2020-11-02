package com.example.bmi_calculator

enum class InputStatus {
    PENDING {
        override fun message(): Int = R.string.pending
        override fun toMassInput(): Boolean = false
    },
    SUCCESS {
        override fun message(): Int = R.string.success
        override fun toMassInput(): Boolean = false
    },
    MASS_EMPTY {
        override fun message(): Int = R.string.input_is_empty
        override fun toMassInput(): Boolean = true
    },
    HEIGHT_EMPTY {
        override fun message(): Int = R.string.input_is_empty
        override fun toMassInput(): Boolean = false
    },
    MASS_TO_LOW {
        override fun message(): Int = R.string.mass_is_too_low
        override fun toMassInput(): Boolean = true
    },
    MASS_TO_HIGH {
        override fun message(): Int = R.string.mass_is_too_high
        override fun toMassInput(): Boolean = true
    },
    HEIGHT_TO_LOW {
        override fun message(): Int = R.string.height_is_too_low
        override fun toMassInput(): Boolean = false
    },
    HEIGHT_TO_HIGH {
        override fun message(): Int = R.string.height_is_too_high
        override fun toMassInput(): Boolean = false
    };

    abstract fun message(): Int
    abstract fun toMassInput(): Boolean
}