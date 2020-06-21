package com.udacity.gradle.builditbigger;

import android.support.test.runner.AndroidJUnit4;
import android.text.TextUtils;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class EndpointsAsyncTaskTest {

    private String result = null; //joke returned from asynctask

    @Test
    public void ExecuteEndPointsAsyncTask_CheckJokeLoaded() {
        final CountDownLatch countDownLatch = new CountDownLatch(1); //initialize the countdown before server fetch

        new EndpointsAsyncTask(new JokeListener() {
            @Override
            public void onJokeLoaded(String joke) { //waiting for joke returned from GCE
                result = joke;
                countDownLatch.countDown(); //start countdown. This countdown must finish before the await function is executed
            }
        }).execute(); //execute the asyncTask

        try {
            countDownLatch.await(); //at this point, we are sure the result is fetched from server. The await method waits for the countdown to finish before moving to the next line of code to execute
            assertNotNull("joke is null", result); //check if the result is not null, if null it throws an exception
            assertFalse("joke is empty", TextUtils.isEmpty(result)); //check if isEmpty is false, them we print the message
        } catch (InterruptedException e) {
            e.printStackTrace();

        }
    }
}
