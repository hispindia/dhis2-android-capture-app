package org.dhis2_haparent.commons.extensions

import java.math.RoundingMode

const val MAX_LENGTH = 5

fun Double.truncate() =
    this.toBigDecimal().setScale(MAX_LENGTH, RoundingMode.DOWN).toDouble()
