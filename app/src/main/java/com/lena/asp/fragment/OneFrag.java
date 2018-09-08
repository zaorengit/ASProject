package com.lena.asp.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lena.asp.R;
import com.lena.asp.adapter.OneViewPagerAdapter;
import com.lena.asp.utils.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

public class OneFrag extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public String[] titles = new String[]{"首页", "标题"};
    Unbinder unbinder;
    @BindView(R.id.tl_nav)
    TabLayout tlNav;
    @BindView(R.id.vp_container)
    ViewPager vpContainer;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private OneViewPagerAdapter mPagerAdapter;

    public OneFrag() {
        // Required empty public constructor
    }


    /**
     * @param param1
     * @param param2
     * @return
     */
    public static OneFrag newInstance(String param1, String param2) {
        OneFrag fragment = new OneFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_one, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        loadMessage();
        return view;
    }

    private void initView() {
        LogUtil.i("initView");
        List<Fragment> frags = new ArrayList<>();
        frags.add(ChildIndexFrag.getInstance("none"));
        frags.add(new ChildChannelFrag());

        mPagerAdapter = new OneViewPagerAdapter(getChildFragmentManager(), frags);
        vpContainer.setAdapter(mPagerAdapter);
        tlNav.setupWithViewPager(vpContainer);

        tlNav.getTabAt(0).setText("首页");
        tlNav.getTabAt(1).setText("频道");
        tlNav.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void loadMessage() {
        File file = new File("http://imgone.jjsqzg.com//1c180c463ee24ecfb4e3afd7a70ea595//47c4ed15f87843cdbc4cf5510c749f43.jpg");
        Luban.with(getActivity())
                .load(file)
                .ignoreBy(100)
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return false;
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        LogUtil.i("onStart");
                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtil.i("onSuccess" + file.length() / 1024);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e("onerror=" + e.getMessage());
                    }
                }).launch();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }
}
