package com.example.swpp.tododapp.tasks

import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test

/** One test per Tip in specs/DueDateUtils.spec.md, in spec order. */
class DueDateUtilsTest {

    @Test
    fun dueDateLabel_completedWithoutDueDate_returnsClear() {
        // Given
        val today = LocalDate.parse("2026-10-18")
        val dueDate = null

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = true)

        // Then
        assertEquals("CLEAR", label)
    }

    @Test
    fun dueDateLabel_completedWithAnyDueDate_returnsClear() {
        // Given
        val today = LocalDate.parse("2026-10-18")
        val dueDates = listOf("2026-10-17", "2026-10-18", "2026-10-19")
            .map { LocalDate.parse(it) }

        for (dueDate in dueDates) {
            // When
            val label = dueDateLabel(dueDate, today, isCompleted = true)

            // Then
            assertEquals("dueDate=$dueDate", "CLEAR", label)
        }
    }

    @Test
    fun dueDateLabel_activeWithoutDueDate_returnsEmpty() {
        // Given
        val today = LocalDate.parse("2026-10-18")
        val dueDate = null

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("", label)
    }

    @Test
    fun dueDateLabel_dueToday_returnsDDay() {
        // Given
        val today = LocalDate.parse("2026-10-18")
        val dueDate = LocalDate.parse("2026-10-18")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-Day", label)
    }

    @Test
    fun dueDateLabel_dueInTenDays_returnsDMinus10() {
        // Given
        val today = LocalDate.parse("2026-10-18")
        val dueDate = LocalDate.parse("2026-10-28")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-10", label)
    }

    @Test
    fun dueDateLabel_overdueByTenDays_returnsDPlus10() {
        // Given
        val today = LocalDate.parse("2026-10-18")
        val dueDate = LocalDate.parse("2026-10-08")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D+10", label)
    }

    @Test
    fun dueDateLabel_dueYesterday_returnsDPlus1() {
        // Given
        val today = LocalDate.parse("2026-10-18")
        val dueDate = LocalDate.parse("2026-10-17")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D+1", label)
    }

    @Test
    fun dueDateLabel_dueTomorrow_returnsDMinus1() {
        // Given
        val today = LocalDate.parse("2026-10-18")
        val dueDate = LocalDate.parse("2026-10-19")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-1", label)
    }

    @Test
    fun dueDateLabel_dueAcrossMonthWithRemainder_returnsDMinus35() {
        // Given
        val today = LocalDate.parse("2026-10-16")
        val dueDate = LocalDate.parse("2026-11-20")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-35", label)
    }

    @Test
    fun dueDateLabel_dueAcrossShortMonth_returnsDMinus29() {
        // Given
        val today = LocalDate.parse("2026-01-31")
        val dueDate = LocalDate.parse("2026-03-01")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-29", label)
    }

    @Test
    fun dueDateLabel_dueAtMonthEnd_returnsDMinus30() {
        // Given
        val today = LocalDate.parse("2026-01-01")
        val dueDate = LocalDate.parse("2026-01-31")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-30", label)
    }

    @Test
    fun dueDateLabel_dueAtNextMonthStart_returnsDMinus31() {
        // Given
        val today = LocalDate.parse("2026-01-01")
        val dueDate = LocalDate.parse("2026-02-01")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-31", label)
    }

    @Test
    fun dueDateLabel_overdueFromPreviousMonthStart_returnsDPlus28() {
        // Given
        val today = LocalDate.parse("2026-03-01")
        val dueDate = LocalDate.parse("2026-02-01")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D+28", label)
    }

    @Test
    fun dueDateLabel_overdueFromPreviousMonthEnd_returnsDPlus29() {
        // Given
        val today = LocalDate.parse("2026-03-01")
        val dueDate = LocalDate.parse("2026-01-31")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D+29", label)
    }

    @Test
    fun dueDateLabel_overdueAcrossMonthWithRemainder_returnsDPlus35() {
        // Given
        val today = LocalDate.parse("2026-11-20")
        val dueDate = LocalDate.parse("2026-10-16")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D+35", label)
    }

    @Test
    fun dueDateLabel_dueAtYearEnd_returnsDMinus30() {
        // Given
        val today = LocalDate.parse("2026-12-01")
        val dueDate = LocalDate.parse("2026-12-31")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-30", label)
    }

    @Test
    fun dueDateLabel_dueAtNextYearStart_returnsDMinus31() {
        // Given
        val today = LocalDate.parse("2026-12-01")
        val dueDate = LocalDate.parse("2027-01-01")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-31", label)
    }

    @Test
    fun dueDateLabel_overdueAcrossYearEnd_returnsDPlus1() {
        // Given
        val today = LocalDate.parse("2027-01-01")
        val dueDate = LocalDate.parse("2026-12-31")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D+1", label)
    }

    @Test
    fun dueDateLabel_dueOnLeapDay_returnsDMinus1() {
        // Given
        val today = LocalDate.parse("2028-02-28")
        val dueDate = LocalDate.parse("2028-02-29")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-1", label)
    }

    @Test
    fun dueDateLabel_dueAfterLeapDay_returnsDMinus2() {
        // Given
        val today = LocalDate.parse("2028-02-28")
        val dueDate = LocalDate.parse("2028-03-01")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-2", label)
    }

    @Test
    fun dueDateLabel_overdueAcrossLeapDay_returnsDPlus2() {
        // Given
        val today = LocalDate.parse("2028-03-01")
        val dueDate = LocalDate.parse("2028-02-28")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D+2", label)
    }

    @Test
    fun dueDateLabel_dueAfterNonLeapFebruary_returnsDMinus1() {
        // Given
        val today = LocalDate.parse("2027-02-28")
        val dueDate = LocalDate.parse("2027-03-01")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-1", label)
    }

    @Test
    fun dueDateLabel_dueOneYearAhead_returnsDMinus365() {
        // Given
        val today = LocalDate.parse("2026-10-18")
        val dueDate = LocalDate.parse("2027-10-18")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-365", label)
    }

    @Test
    fun dueDateLabel_dueOneLeapYearAhead_returnsDMinus366() {
        // Given
        val today = LocalDate.parse("2028-02-01")
        val dueDate = LocalDate.parse("2029-02-01")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D-366", label)
    }

    @Test
    fun dueDateLabel_overdueOneYear_returnsDPlus365() {
        // Given
        val today = LocalDate.parse("2026-10-18")
        val dueDate = LocalDate.parse("2025-10-18")

        // When
        val label = dueDateLabel(dueDate, today, isCompleted = false)

        // Then
        assertEquals("D+365", label)
    }
}
