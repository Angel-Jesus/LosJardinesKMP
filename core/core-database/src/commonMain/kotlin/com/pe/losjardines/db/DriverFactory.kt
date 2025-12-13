package com.pe.losjardines.db

import app.cash.sqldelight.db.SqlDriver

const val DB_NAME = "sqldelight"

expect class DriverFactory(){
    fun createDriver(): SqlDriver
}
