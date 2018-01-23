package com.leanplum.segmentexample;

import android.app.Application;
import android.util.Log;

import com.leanplum.Leanplum;
import com.leanplum.LeanplumActivityHelper;
import com.leanplum.LeanplumPushService;
import com.leanplum.callbacks.StartCallback;
import com.leanplum.callbacks.VariablesChangedCallback;
import com.leanplum.segment.LeanplumIntegration;
import com.segment.analytics.Analytics;

/**
 * Created by fede on 1/18/17.
 */

public class ApplicationClass extends Application {

    private static final String SEGMENT_WRITE_KEY = String.valueOf(R.string.SEGMENT_KEY);

    @Override
    public void onCreate(){
        super.onCreate();

        Leanplum.setApplicationContext(this);
        LeanplumActivityHelper.enableLifecycleCallbacks(this);

        // Enabling GCM
        LeanplumPushService.setGcmSenderId(LeanplumPushService.LEANPLUM_SENDER_ID);

        // Enabling Firebase
//        LeanplumPushService.enableFirebase();


        Analytics analytics = new Analytics
                .Builder(this, SEGMENT_WRITE_KEY)
                .use(LeanplumIntegration.FACTORY)
                .build();
//

        analytics.onIntegrationReady(LeanplumIntegration.LEANPLUM_SEGMENT_KEY,
                new Analytics.Callback() {
                    @Override
                    public void onReady(Object instance) {
                        Leanplum.addVariablesChangedHandler(new VariablesChangedCallback() {
                            @Override
                            public void variablesChanged() {
                                Log.i("### ", "Leanplum started");
                                Leanplum.setUserId("FedePluto_1");
                            }
                        });
                    }
                });

        Leanplum.addStartResponseHandler(new StartCallback() {
            @Override
            public void onResponse(boolean b) {
                Log.i("### ", "Leanplum started 2");
            }
        });

// Set the initialized instance as a globally accessible instance.
        Analytics.setSingletonInstance(analytics);
    }
}
