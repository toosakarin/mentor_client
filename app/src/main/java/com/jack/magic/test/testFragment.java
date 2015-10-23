package com.jack.magic.test;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jack.magic.R;

/**
 * Created by jacktseng on 2015/10/7.
 */
public class testFragment extends Fragment {

    private static String TAG = testFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.frag_func_test, container, false);

        init(root);

        return root;
    }

    private void init(View root) {
        root.findViewById(R.id.frag_func_test_btn_test_register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "test register...");
                new testRegister(getActivity()).test(getActivity());

            }
        });
        root.findViewById(R.id.frag_func_test_btn_test_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "test login...");
                new testLogin().test(getActivity());

            }
        });
        root.findViewById(R.id.frag_func_test_btn_test_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "test send...");
                new testSend().test(getActivity());

            }
        });
    }

}
