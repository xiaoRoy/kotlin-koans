package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate>{
    override fun compareTo(other: MyDate) = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)

operator fun MyDate.plus(interval: TimeInterval): MyDate{
    return this.addTimeIntervals(interval, 1)
}

operator fun MyDate.plus(intervals: RepeatTimeIntervals): MyDate{
    return this.addTimeIntervals(intervals.timeInterval, intervals.times)
}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

class RepeatTimeIntervals(val timeInterval: TimeInterval, val times: Int)

operator fun TimeInterval.times(times: Int): RepeatTimeIntervals = RepeatTimeIntervals(this, times)


class DateRange(override val start: MyDate, override val endInclusive: MyDate):ClosedRange<MyDate>{

    operator fun iterator(): Iterator<MyDate> = object : Iterator<MyDate>{

        var current = start

        override fun hasNext(): Boolean = current <= endInclusive

        override fun next(): MyDate {
            val result = current
            current = current.addTimeIntervals(TimeInterval.DAY, 1)
            return result
        }
    }
}
