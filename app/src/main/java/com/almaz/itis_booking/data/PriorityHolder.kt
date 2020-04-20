package com.almaz.itis_booking.data

import javax.inject.Inject

class PriorityHolder @Inject constructor() {

    private val priorities: MutableMap<Int, String> = mutableMapOf()

    init {
        priorities[0] = "Студент"
        priorities[1] = "Преподаватель"
        priorities[2] = "Администрация"
        priorities[3] = "Админ"
    }

    fun getPriorityNameByValue(value: Int): String = priorities[value]!!

    fun getPriorityValueByName(name: String): Int =
        priorities.filter { it.value == name }.keys.first()
}
