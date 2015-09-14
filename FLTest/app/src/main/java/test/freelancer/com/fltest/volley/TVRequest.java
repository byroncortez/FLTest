package test.freelancer.com.fltest.volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

import test.freelancer.com.fltest.controller.ProgramApp;
import test.freelancer.com.fltest.dao.ProgramDao;
import test.freelancer.com.fltest.volley.listener.ResponseListener;


public class TVRequest extends Request<ResponsePrograms> {
    
    private static final String BASE_URL = "http://whatsbeef.net/wabz/guide.php?start=";
    
    ResponseListener<ResponsePrograms> responseListener;
    Context mContext;
    int index = 0;
    
    public TVRequest(Context context, int index, ResponseListener<ResponsePrograms> responseListener) {
        super(Request.Method.GET, getUrl(index), responseListener);
        this.responseListener = responseListener;
        this.mContext = context;
        this.index = index;
    }

    @Override
    protected Response<ResponsePrograms> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Gson gson = new Gson();
            ResponsePrograms responsePrograms = gson.fromJson(json, ResponsePrograms.class);

            ProgramApp app = (ProgramApp) mContext.getApplicationContext();
            ProgramDao programDao = app.getDaoSession().getProgramDao();
            if (index == 0) {
                Log.d("REQUEST", "deleteALl");
                programDao.deleteAll();
            }
            Log.d("REQUEST", "add ALL");
            programDao.insertInTx(responsePrograms.getPrograms());
            
            return Response.success(responsePrograms,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(ResponsePrograms response) {
        if (responseListener != null) {
            responseListener.onResponse(response);
        }
    }
    
    private static String getUrl(int index) {
        return BASE_URL + String.valueOf(index);
    }
}
