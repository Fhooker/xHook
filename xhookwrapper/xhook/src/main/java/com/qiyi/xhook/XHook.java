package com.qiyi.xhook;

import android.content.Context;
import android.util.Log;

/**
 * Created by caikelun on 18/01/2018.
 */

public class XHook {
    private static final XHook ourInstance = new XHook();
    private static boolean inited = false;

    public static XHook getInstance() {
        return ourInstance;
    }

    private XHook() {
    }

    public synchronized boolean isInited() {
        return inited;
    }

    public synchronized void init(Context ctx) {
        try {
            System.loadLibrary("xhook");
            inited = true;
        } catch (Throwable e) {
            try {
                System.load(ctx.getFilesDir().getParent() + "/lib/libxhook.so");
                inited = true;
            } catch (Throwable ex) {
                ex.printStackTrace();
                Log.e("xhook", "load libxhook.so failed");
            }
        }
    }

    public synchronized void enableDebug(boolean flag) {
        if(!inited) {
            return;
        }

        try {
            com.qiyi.xhook.NativeHandler.getInstance().enableDebug(flag);
        } catch (Throwable ex) {
            ex.printStackTrace();
            Log.e("xhook", "xhook native enableDebug failed");
        }
    }

    public synchronized int refresh() {
        if(!inited) {
            return 100;
        }

        int ret;
        try {
            ret = com.qiyi.xhook.NativeHandler.getInstance().refresh();
        } catch (Throwable ex) {
            ex.printStackTrace();
            Log.e("xhook", "xhook native refresh failed");
            ret = 101;
        }
        return ret;
    }
}
