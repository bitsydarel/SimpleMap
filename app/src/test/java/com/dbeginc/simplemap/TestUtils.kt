package com.dbeginc.simplemap

import java.util.*

fun getFileAsStringJVM(fileName: String) : String {
    val builder = StringBuilder()

    val data = Scanner(Thread.currentThread().contextClassLoader.getResourceAsStream(fileName))

    while (data.hasNextLine()) builder.append(data.nextLine())

    return builder.toString()
}