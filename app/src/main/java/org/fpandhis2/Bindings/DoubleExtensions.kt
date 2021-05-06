package org.fpandhis2.Bindings

import java.math.RoundingMode

const val MAX_LENGTH = 5

fun Double.truncate() =
    this.toBigDecimal().setScale(MAX_LENGTH, RoundingMode.DOWN).toDouble()
