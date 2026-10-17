package com.example.swpp.tododapp.tasks

import android.view.View
import android.widget.TextView
import androidx.test.annotation.UiThreadTest
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.swpp.tododapp.data.Task
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/** One test per Tip in specs/TasksListBindings.spec.md, in spec order. */
@SmallTest
@RunWith(AndroidJUnit4::class)
class TasksListBindingsTest {

    @Test
    @UiThreadTest
    fun setDueDateLabel_completedWithoutDueDate_showsClear() {
        // Given
        val task = Task(dueDate = null, isCompleted = true)
        val textView = newLabelView(View.GONE)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "CLEAR", View.VISIBLE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_completedWithDueDate_showsClear() {
        // Given
        val task = Task(dueDate = LocalDate.now().minusDays(1), isCompleted = true)
        val textView = newLabelView(View.GONE)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "CLEAR", View.VISIBLE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_dueToday_showsDDay() {
        // Given
        val task = Task(dueDate = LocalDate.now(), isCompleted = false)
        val textView = newLabelView(View.GONE)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "D-Day", View.VISIBLE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_dueInTenDays_showsDMinus10() {
        // Given
        val task = Task(dueDate = LocalDate.now().plusDays(10), isCompleted = false)
        val textView = newLabelView(View.GONE)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "D-10", View.VISIBLE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_overdueByTenDays_showsDPlus10() {
        // Given
        val task = Task(dueDate = LocalDate.now().minusDays(10), isCompleted = false)
        val textView = newLabelView(View.GONE)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "D+10", View.VISIBLE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_activeWithoutDueDate_clearsAndHidesLabel() {
        // Given
        val task = Task(dueDate = null, isCompleted = false)
        val textView = newLabelView(View.VISIBLE)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "", View.GONE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_nullTask_clearsAndHidesLabel() {
        // Given
        val task = null
        val textView = newLabelView(View.VISIBLE)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "", View.GONE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_dueYesterday_showsDPlus1() {
        // Given
        val task = Task(dueDate = LocalDate.now().minusDays(1), isCompleted = false)
        val textView = newLabelView(View.GONE)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "D+1", View.VISIBLE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_dueTomorrow_showsDMinus1() {
        // Given
        val task = Task(dueDate = LocalDate.now().plusDays(1), isCompleted = false)
        val textView = newLabelView(View.GONE)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "D-1", View.VISIBLE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_uncheckUndatedTask_clearsAndHidesClear() {
        // Given
        val task = Task(dueDate = null, isCompleted = true)
        val textView = newLabelView(View.GONE)
        setDueDateLabel(textView, task)
        assertLabel(textView, "CLEAR", View.VISIBLE)

        // When
        task.isCompleted = false
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "", View.GONE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_uncheckDatedTask_replacesClearWithDMinus3() {
        // Given
        val task = Task(dueDate = LocalDate.now().plusDays(3), isCompleted = true)
        val textView = newLabelView(View.GONE)
        setDueDateLabel(textView, task)
        assertLabel(textView, "CLEAR", View.VISIBLE)

        // When
        task.isCompleted = false
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "D-3", View.VISIBLE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_reuseRowForUndatedTask_clearsAndHidesOldLabel() {
        // Given
        val textView = newLabelView(View.GONE)
        val previousTask = Task(dueDate = LocalDate.now().plusDays(10), isCompleted = false)
        setDueDateLabel(textView, previousTask)
        assertLabel(textView, "D-10", View.VISIBLE)
        val task = Task(dueDate = null, isCompleted = false)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "", View.GONE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_reuseRowForNullTask_clearsAndHidesOldLabel() {
        // Given
        val textView = newLabelView(View.GONE)
        val previousTask = Task(dueDate = LocalDate.now().plusDays(10), isCompleted = false)
        setDueDateLabel(textView, previousTask)
        assertLabel(textView, "D-10", View.VISIBLE)
        val task = null

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "", View.GONE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_bindCompletedTaskToHiddenRow_showsClear() {
        // Given
        val textView = newLabelView(View.GONE)
        setDueDateLabel(textView, Task(dueDate = null, isCompleted = false))
        assertLabel(textView, "", View.GONE)
        val task = Task(dueDate = null, isCompleted = true)

        // When
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "CLEAR", View.VISIBLE)
    }

    @Test
    @UiThreadTest
    fun setDueDateLabel_checkDatedTask_replacesDMinus3WithClear() {
        // Given
        val task = Task(dueDate = LocalDate.now().plusDays(3), isCompleted = false)
        val textView = newLabelView(View.GONE)
        setDueDateLabel(textView, task)
        assertLabel(textView, "D-3", View.VISIBLE)

        // When
        task.isCompleted = true
        setDueDateLabel(textView, task)

        // Then
        assertLabel(textView, "CLEAR", View.VISIBLE)
    }

    private fun newLabelView(initialVisibility: Int): TextView =
        TextView(ApplicationProvider.getApplicationContext()).apply {
            text = ""
            visibility = initialVisibility
        }

    private fun assertLabel(textView: TextView, expectedText: String, expectedVisibility: Int) {
        assertEquals("label text", expectedText, textView.text.toString())
        assertEquals("label visibility", expectedVisibility, textView.visibility)
    }
}
