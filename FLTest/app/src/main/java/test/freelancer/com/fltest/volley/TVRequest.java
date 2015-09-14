package test.freelancer.com.fltest.volley;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

import test.freelancer.com.fltest.volley.listener.ResponseListener;


public class TVRequest extends Request<ResponsePrograms> {
    
    private static final String BASE_URL = "http://whatsbeef.net/wabz/guide.php?start=";
    
    ResponseListener<ResponsePrograms> responseListener;
    
    public TVRequest(int index, ResponseListener<ResponsePrograms> responseListener) {
        super(Request.Method.GET, getUrl(index), responseListener);
        this.responseListener = responseListener;
    }

    @Override
    protected Response<ResponsePrograms> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));
            Gson gson = new Gson();
            ResponsePrograms responsePrograms = gson.fromJson(json, ResponsePrograms.class);
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
