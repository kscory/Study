package com.example.kyung.basicfragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.kyung.basicfragment.domain.Contact;
import com.example.kyung.basicfragment.domain.Loader;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    TextView textNo, textName, textNumber;
    ImageButton imageCall;
    String number = null;
    Contact contact;
    Context context;

    public DetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
        init(view);
        imageCall.setOnClickListener(onClickListener);
        return view;
    }

    private void init(View view){
        textNo = (TextView) view.findViewById(R.id.textNo);
        textName = (TextView) view.findViewById(R.id.textName);
        textNumber = (TextView) view.findViewById(R.id.textNumber);
        imageCall = (ImageButton) view.findViewById(R.id.imageCall);

        // Argument 로 전달된 값 꺼내기
        Bundle bundle = getArguments();
        if(bundle != null) {
            int no = bundle.getInt("id",-1);
            contact = loaddata(no);
            setTextNo(contact.getId());
            setTextName(contact.getName());
            setTextNumber(contact.getNumber());
            number = contact.getNumber();
        }
    }
    public void setTextNo(int no){
        textNo.setText(no+"");
    }

    public void setTextName(String name){
        textName.setText(name);
    }

    public void setTextNumber(String number){
        textNumber.setText(number);
    }

    public Contact loaddata(int id){
        Contact data = null;
        Loader loader = new Loader(context);
        data = loader.detailLoad(id);
        return data;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @SuppressWarnings("MissingPermission")
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imageCall:
                    if(number != null){
                        String num = "tel:" +number;
                        Uri uri = Uri.parse(num);
                        Intent intent = new Intent(Intent.ACTION_CALL,uri);
                        v.getContext().startActivity(intent);
                    }
                    break;
            }
        }
    };

}
