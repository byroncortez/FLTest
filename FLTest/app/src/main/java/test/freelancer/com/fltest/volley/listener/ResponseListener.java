package test.freelancer.com.fltest.volley.listener;

import com.android.volley.Response;

/**
 * @author by Emarc Magtanong on 9/14/15.
 */
public interface ResponseListener<T> extends Response.ErrorListener {
    public void onResponse(T response);
}
