package utils

import java.util.Calendar

object CalendarUtil {

	def calendarFor(year: Int, month: Int, day: Int, hour: Int, minute: Int): Calendar = {
		val calendar = Calendar.getInstance()
		calendar.set(Calendar.YEAR, year)
		calendar.set(Calendar.MONTH, month - 1)
		calendar.set(Calendar.DAY_OF_MONTH, day)
		calendar.set(Calendar.HOUR_OF_DAY, hour)
		calendar.set(Calendar.MINUTE, minute)
		calendar.set(Calendar.SECOND, 0)
		calendar.set(Calendar.MILLISECOND, 0)
		calendar
	}
}
