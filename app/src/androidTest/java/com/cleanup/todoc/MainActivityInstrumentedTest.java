package com.cleanup.todoc;

import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cleanup.todoc.ui.MainActivity;
import com.cleanup.todoc.utils.DeleteViewAction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Objects;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.cleanup.todoc.utils.TestUtils.withRecyclerView;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @author Gaëtan HERFRAY
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> rule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void checkIfAddTaskIsWorking() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);
        int tasksCount = Objects.requireNonNull(listTasks.getAdapter()).getItemCount();
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        // Check that lblTa sk is not displayed anymore
        assertThat(lblNoTask.getVisibility(), equalTo(View.GONE));
        // Check that recyclerView is displayed
        assertThat(listTasks.getVisibility(), equalTo(View.VISIBLE));
        // Check that it contains one more element
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 1, new DeleteViewAction()));
    }

    @Test
    public void checkIfDeleteTaskIsWorking() {
        MainActivity activity = rule.getActivity();
        TextView lblNoTask = activity.findViewById(R.id.lbl_no_task);
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        int tasksCount = Objects.requireNonNull(listTasks.getAdapter()).getItemCount();
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 1, new DeleteViewAction()));
        assertThat(listTasks.getAdapter().getItemCount(), equalTo(tasksCount));

        if (tasksCount == 0) {
            // Check that lblTask is displayed
            assertThat(lblNoTask.getVisibility(), equalTo(View.VISIBLE));
            // Check that recyclerView is not displayed anymore
            assertThat(listTasks.getVisibility(), equalTo(View.GONE));
        }
    }


    @Test
    public void sortTasksAlphabeticalAZ() {
        MainActivity activity = rule.getActivity();
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        int tasksCount = Objects.requireNonNull(listTasks.getAdapter()).getItemCount();
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 3, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort alphabetical
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 3, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 1, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));

        //Delete tested items
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 3, new DeleteViewAction()));
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 3, new DeleteViewAction()));
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 3, new DeleteViewAction()));
    }

    @Test
    public void sortTasksAlphabeticalZA() {
        MainActivity activity = rule.getActivity();
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        int tasksCount = Objects.requireNonNull(listTasks.getAdapter()).getItemCount();
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 3, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort alphabetical inverted
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_alphabetical_invert)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 3, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 2, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 1, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));

        //Delete tested items
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 3, new DeleteViewAction()));
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 3, new DeleteViewAction()));
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 3, new DeleteViewAction()));
    }

    @Test
    public void sortTasksRecentFirst() {
        MainActivity activity = rule.getActivity();
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        int tasksCount = Objects.requireNonNull(listTasks.getAdapter()).getItemCount();
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 3, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort recent first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_recent_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 3, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 1, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));

        //Delete tested items
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(0, new DeleteViewAction()));
    }

    public void sortTasksOldFirst() {
        MainActivity activity = rule.getActivity();
        RecyclerView listTasks = activity.findViewById(R.id.list_tasks);
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("aaa Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("zzz Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.fab_add_task)).perform(click());
        onView(withId(R.id.txt_task_name)).perform(replaceText("hhh Tâche example"));
        onView(withId(android.R.id.button1)).perform(click());

        int tasksCount = Objects.requireNonNull(listTasks.getAdapter()).getItemCount();
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 3, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount + 1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        // Sort old first
        onView(withId(R.id.action_filter)).perform(click());
        onView(withText(R.string.sort_oldest_first)).perform(click());
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 3, R.id.lbl_task_name))
                .check(matches(withText("aaa Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 2, R.id.lbl_task_name))
                .check(matches(withText("zzz Tâche example")));
        onView(withRecyclerView(R.id.list_tasks).atPositionOnView(tasksCount - 1, R.id.lbl_task_name))
                .check(matches(withText("hhh Tâche example")));

        //Delete tested items
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 3, new DeleteViewAction()));
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 3, new DeleteViewAction()));
        onView(withId(R.id.list_tasks)).perform(RecyclerViewActions.actionOnItemAtPosition(tasksCount - 3, new DeleteViewAction()));
    }
}
