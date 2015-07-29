package example.com.mrapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by TADZ AMEN on 28/07/2015.
 */
public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {


    Context context;
    List<People> data = Collections.emptyList();
    LayoutInflater inflater;

    public MyListAdapter(Context context, ArrayList<People> data){
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view  = inflater.inflate(R.layout.people_list_item, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        People currentPerson = data.get(position);
        holder.name.setText(currentPerson.getNames());
        holder.id.setText(""+currentPerson.getId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void delete(int position){
        data.remove(position);

        People personClicked = data.get(position);
        Toast.makeText(context, "" + personClicked.getId(), Toast.LENGTH_LONG).show();
        Database delete = new Database(context);

        try{
            delete.open();
            delete.deleteEntry(personClicked.getId());
            delete.close();
            Toast.makeText(context,""+ personClicked.getId() + "deleted", Toast.LENGTH_LONG).show();
        }catch (Exception e){
            e.printStackTrace();
        }

        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView name;
        TextView id;
        public MyViewHolder(View item){
            super(item);
            name = (TextView) item.findViewById(R.id.personName);
            id = (TextView) item.findViewById(R.id.personId);
            name.setOnClickListener(this);
        }



        @Override
        public void onClick(View v) {
            delete(getPosition() );
        }
    }
}
