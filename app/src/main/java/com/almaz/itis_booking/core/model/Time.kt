package com.almaz.itis_booking.core.model

enum class Time : TimeValue {
    FirstClass {
        override fun getStringTime() = "8:30 - 10:00"
    },
    SecondClass {
        override fun getStringTime() = "10:10 - 11:40"
    },
    ThirdClass {
        override fun getStringTime() = "11:50 - 13:20"
    },
    FourthClass {
        override fun getStringTime() = "14:00 - 15:30"
    },
    FifthClass {
        override fun getStringTime() = "15:40 - 17:10"
    },
    SixthClass {
        override fun getStringTime() = "17:50 - 19:20"
    }
}

interface TimeValue {
    fun getStringTime(): String
}