/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.background.sync;


import android.content.Context;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ReminderUtilities {

    private static final int REMINDER_INTERVAL_IN_MINUTES = 1;
    private static final int REMINDER_INTERVAL_IN_SECONDS = (int) TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_IN_MINUTES);
    private static final int SYNC_FLEXTIME_IN_SECONDS = REMINDER_INTERVAL_IN_SECONDS;

    private static final String REMINDER_JOB_TAG = "hydration_reminder_tag";

    private static boolean sInitialized;

    public static synchronized void scheduleChargingReminder(Context context) {
        if (sInitialized) {
            return;
        }
        GooglePlayDriver googlePlayDriver = new GooglePlayDriver(context);
        FirebaseJobDispatcher jobDispatcher = new FirebaseJobDispatcher(googlePlayDriver);
        Job job = jobDispatcher.newJobBuilder()
                .setService(WaterReminderFirebaseJobService.class)
                .setTag(REMINDER_JOB_TAG)
                .setConstraints(Constraint.DEVICE_CHARGING)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_IN_SECONDS,
                        REMINDER_INTERVAL_IN_SECONDS + SYNC_FLEXTIME_IN_SECONDS))
                .setReplaceCurrent(true)
                .build();
        jobDispatcher.schedule(job);
        sInitialized = true;
    }
}
