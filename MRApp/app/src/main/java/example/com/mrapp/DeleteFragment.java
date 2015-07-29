package example.com.mrapp;


import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class DeleteFragment extends android.support.v4.app.Fragment {

    MyListAdapter listAdapter;
    RecyclerView recyclerView;
    Database pat;


    public DeleteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View rootView = inflater.inflate(R.layout.fragment_delete, container, false);

        pat = new Database(getActivity());
        try {
            pat.open();
            listAdapter = new MyListAdapter(getActivity(), pat.getList());
            //adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);
            pat.close();
        }catch (Exception e){
            e.printStackTrace();
        }
        recyclerView = (RecyclerView)rootView.findViewById(R.id.list_view);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }


}
