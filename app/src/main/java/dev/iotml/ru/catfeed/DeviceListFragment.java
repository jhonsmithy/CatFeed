package dev.iotml.ru.catfeed;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DeviceListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */

public class DeviceListFragment extends Fragment {

    //объявим интерфейс для передачи данных активити
    public interface FragmentToActivity {
        public void callBack(String data);
    }

    public FragmentToActivity msg_to_activity;//создадим экземпляр интерфейса

    ListView lv;//список для отображения устройств
    ArrayAdapter<String> adapter ;//адаптер списка
    GlobalClass sharedData = GlobalClass.getInstance();//обратимся к базовому классу переменных
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
/*
    interface OnFragmentSendDataListener {
        void onSendData(String data);
    }
*/
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            msg_to_activity = (FragmentToActivity) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " должен реализовывать интерфейс OnFragmentInteractionListener");
        }
    }





    public DeviceListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DeviceListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DeviceListFragment newInstance(String param1, String param2) {
        DeviceListFragment fragment = new DeviceListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_device_list,container,false);
        lv = (ListView) view.findViewById(R.id.list_view_id);
        //sharedData.addToList("one");
        //sharedData.addToList("teo");
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,sharedData.getList());

        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getContext(), "click"+position, Toast.LENGTH_SHORT).show();
                // получаем выбранный элемент
                String selectedItem = (String)parent.getItemAtPosition(position);
                // Посылаем выбранные данные Activity
                SendData(selectedItem);
            }
        });
        return view;
    }

    @Override
    public void onDetach() {
        msg_to_activity = null;
        super.onDetach();
    }

    private void SendData(String text)
    {
        msg_to_activity.callBack(text);
    }
}