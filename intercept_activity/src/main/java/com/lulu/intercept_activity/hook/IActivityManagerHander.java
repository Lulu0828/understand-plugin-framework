package com.lulu.intercept_activity.hook;

import android.content.ComponentName;
import android.content.Intent;
import android.util.Log;
import com.lulu.intercept_activity.StubActivity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class IActivityManagerHander implements InvocationHandler {

    private static final String TAG = "IActivityManagerHandler";

    Object mBase;

    public IActivityManagerHander(Object base) {
        mBase = base;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if ("startActivity".equals(method.getName())) {
            Intent raw;
            int index = 0;

            for (int i = 0; i < args.length; i ++) {
                if (args[i] instanceof Intent) {
                    index = i;
                    break;
                }
            }

            raw = (Intent) args[index];
            Intent newIntent = new Intent();

            ComponentName componentName = new ComponentName("com.lulu.intercept_activity",
                    StubActivity.class.getName());
            newIntent.setComponent(componentName);
            newIntent.putExtra(AMSHookHelper.Companion.getEXTRA_TARGET_INTENT(), raw);

            args[index] = newIntent;
            Log.d(TAG, "hook success");
        }
        return method.invoke(mBase, args);
    }
}
