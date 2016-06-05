package com.github.kassadin.fragmenttag.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.github.kassadin.fragmenttag.B_Activity;
import com.github.kassadin.fragmenttag.R;
import com.orhanobut.logger.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    private String mParam1;

    TextView mTextView;


    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1) {
        Logger.d("新建 Fragment %s",param1);
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
        Logger.d("savedInstanceState == null : %s", savedInstanceState == null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mTextView = (TextView) view.findViewById(R.id.text);
        mTextView.setText(mParam1);
        Button button = (Button) view.findViewById(R.id.button_to_b);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                B_Activity.start(getActivity());
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.d("outState == null : %s", outState == null);
    }
}
