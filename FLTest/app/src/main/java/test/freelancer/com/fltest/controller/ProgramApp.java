package test.freelancer.com.fltest.controller;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import test.freelancer.com.fltest.dao.DaoMaster;
import test.freelancer.com.fltest.dao.DaoSession;

public class ProgramApp extends Application {
    
    private RequestQueue mRequestQueue;

    private DaoSession mDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        mRequestQueue = Volley.newRequestQueue(this);
        DaoMaster.DevOpenHelper daoHelper = new DaoMaster.DevOpenHelper(this, "programs-db", null);
        DaoMaster daoMaster = new DaoMaster(daoHelper.getWritableDatabase());
        mDaoSession = daoMaster.newSession();
    }

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }
}
