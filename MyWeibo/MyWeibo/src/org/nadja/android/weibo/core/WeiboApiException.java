package org.nadja.android.weibo.core;

import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import org.nadja.android.weibo.core.models.ApiError;

import java.io.UnsupportedEncodingException;

import retrofit.RetrofitError;
import retrofit.mime.TypedByteArray;

public class WeiboApiException extends WeiboException {
    private static final String ERROR_CODE = "code";

    private static final int DEFAULT_ERROR_CODE = 0;

    private final RetrofitError retrofitError;

    private final int errorCode;

    WeiboApiException(int errorCode, RetrofitError retrofitError) {
        super(retrofitError.getMessage());
        this.retrofitError = retrofitError;
        this.errorCode = errorCode;
    }

    WeiboApiException(RetrofitError retrofitError) {
        super(createExceptionMessage(retrofitError));
        setStackTrace(retrofitError.getStackTrace());

        this.retrofitError = retrofitError;
        this.errorCode = readErrorCode(retrofitError);
    }

    private static String createExceptionMessage(RetrofitError retrofitError) {
        if (retrofitError.getMessage() != null) {
            return retrofitError.getMessage();
        }
        if (retrofitError.getResponse() != null) {
            return "Status: " + retrofitError.getResponse().getStatus();
        }
        return "unknown error";
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public boolean canRetry() {
        int status = this.retrofitError.getResponse().getStatus();
        return (status < 400) || (status > 499);
    }

    public RetrofitError getRetrofitError() {
        return this.retrofitError;
    }

    public static final WeiboApiException convert(RetrofitError retrofitError) {
        return new WeiboApiException(retrofitError);
    }

    public static int readErrorCode(RetrofitError retrofitError) {
        if ((retrofitError == null) || (retrofitError.getResponse() == null)
                || (retrofitError.getResponse().getBody() == null)) {
            return 0;
        }
        byte[] responseBytes = ((TypedByteArray) retrofitError.getResponse().getBody()).getBytes();

        if (responseBytes == null)
            return 0;
        try {
            String response = new String(responseBytes, "UTF-8");
            return parseErrorCode(response);
        } catch (UnsupportedEncodingException e) {
            VolleyLog.e("Twitter", "Failed to convert to string", e);
        }
        return 0;
    }

    static int parseErrorCode(String response) {
        Gson gson = new Gson();
        try {
            JsonObject responseObj = new JsonParser().parse(response).getAsJsonObject();
            ApiError[] apiErrors = (ApiError[]) gson.fromJson(responseObj.get("errors"),
                    ApiError[].class);

            if (apiErrors.length == 0) {
                return 0;
            }

            return apiErrors[0].getCode();
        } catch (JsonSyntaxException e) {
            VolleyLog.e("Twitter", "Invalid json: " + response, e);
        }
        return 0;
    }
}
