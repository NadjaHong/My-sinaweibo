package org.nadja.android.weibo.util;

/**
 * Log输出帮助文件
 */
public class Log {
    protected static final boolean DEBUG = true;
    protected static final String TAG = "Weibo";

    /**
     * Send a VERBOSE log message.
     * @param msg The message you would like logged.
     */
    public static void v(String msg) {
        if (DEBUG) {
            android.util.Log.v(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a VERBOSE log message and log the exception.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void v(String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.v(TAG, buildMessage(msg), tr);
        }
    }

    /**
     * Send a DEBUG log message.
     * @param msg log message
     */
    public static void d(String msg) {
        if (DEBUG) {
            android.util.Log.d(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a DEBUG log message and log the exception.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void d(String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.d(TAG, buildMessage(msg), tr);
        }
    }

    /**
     * Send an INFO log message.
     * @param msg The message you would like logged.
     */
    public static void i(String msg) {
        if (DEBUG) {
            android.util.Log.i(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a INFO log message and log the exception.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void i(String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.i(TAG, buildMessage(msg), tr);
        }
    }

    /**
     * Send an ERROR log message.
     * @param msg The message you would like logged.
     */
    public static void e(String msg) {
        if (DEBUG) {
            android.util.Log.e(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a WARN log message.
     * @param msg The message you would like logged.
     */
    public static void w(String msg) {
        if (DEBUG) {
            android.util.Log.w(TAG, buildMessage(msg));
        }
    }

    /**
     * Send a WARN log message and log the exception.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void w(String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.w(TAG, buildMessage(msg), tr);
        }
    }

    /**
     * Send an empty WARN log message and log the exception.
     * @param tr An exception to log
     */
    public static void w(Throwable tr) {
        if (DEBUG) {
            android.util.Log.w(TAG, buildMessage(""), tr);
        }
    }

    /**
     * Send an ERROR log message and log the exception.
     * @param msg The message you would like logged.
     * @param tr An exception to log
     */
    public static void e(String msg, Throwable tr) {
        if (DEBUG) {
            android.util.Log.e(TAG, buildMessage(msg), tr);
        }
    }

    /**
     * Building Message.
     * @param msg The message you would like logged.
     * @return Message String
     */
    protected static String buildMessage(String msg) {
        StackTraceElement ste = new Throwable().fillInStackTrace().getStackTrace()[2];

         return new StringBuilder()
                .append(ste.getClassName())
                .append(".")
                .append(ste.getMethodName())
                .append("(): ")
                .append(msg).toString();
    }
}

