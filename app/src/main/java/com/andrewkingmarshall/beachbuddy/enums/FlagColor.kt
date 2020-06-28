package com.andrewkingmarshall.beachbuddy.enums

enum class FlagColor {
    GREEN,
    YELLOW,
    PURPLE,
    RED,
    DOUBLE_RED,
    UNKNOWN
}

fun String?.getFlagColorEnum(): FlagColor {
    if (this == null) {
        return FlagColor.UNKNOWN
    }

    when {
        this.contains("green", true) -> {
            return FlagColor.GREEN
        }
        this.contains("yellow", true) -> {
            return FlagColor.YELLOW
        }
        this.contains("red", true) -> {
            return if (this.contains("double", true)) {
                FlagColor.DOUBLE_RED
            } else {
                FlagColor.RED
            }
        }
        this.contains("purple", true) -> {
            return FlagColor.PURPLE
        }
        else -> {
            return FlagColor.UNKNOWN
        }
    }
}