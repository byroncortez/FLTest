package test.freelancer.com.fltest.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;

import butterknife.Bind;
import butterknife.ButterKnife;
import test.freelancer.com.fltest.R;
import test.freelancer.com.fltest.adapter.TVAdapter;
import test.freelancer.com.fltest.controller.ProgramApp;
import test.freelancer.com.fltest.util.EndlessRecyclerOnScrollListener;
import test.freelancer.com.fltest.volley.ResponsePrograms;
import test.freelancer.com.fltest.volley.TVRequest;
import test.freelancer.com.fltest.volley.listener.ResponseListener;

/**
 * List that displays the TV Programmes
 */
public class ListFragment extends Fragment implements ResponseListener<ResponsePrograms> {
    
    private static final String TAG = ListFragment.class.getSimpleName();

    @Bind(R.id.list) RecyclerView mList;
    
    LinearLayoutManager mLayoutManager;
    TVAdapter mAdapter;
    
    private int currentIndex = 0;
    private int totalCount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, view);
        mList.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mList.setLayoutManager(mLayoutManager);
        mAdapter = new TVAdapter();
        mList.setAdapter(mAdapter);
        mList.addOnScrollListener(new EndlessRecyclerOnScrollListener(
                mLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                currentIndex += 10;
                if (currentIndex < totalCount) {
                    requestPrograms();
                }
            }

        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        requestPrograms();
    }
    
    private void requestPrograms() {
        ProgramApp app = (ProgramApp) getActivity().getApplication();
        TVRequest tvRequest = new TVRequest(currentIndex, this);
        app.getRequestQueue().add(tvRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onResponse(ResponsePrograms response) {
        mAdapter.add(response.getPrograms());
        totalCount = response.getCount();
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (mAdapter.getItemCount() > 0) {
            // show error toast
        }
        Log.d(TAG, "Error in response");
    }
}
