package fr.esilv.probleme_co_assist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import fr.esilv.probleme_co_assist.Activity.DetailUserActivity;
import fr.esilv.probleme_co_assist.Models.UserData;
import fr.esilv.probleme_co_assist.R;

/**
 * Created by Charlotte on 01/03/2016.
 */
public class ListUsersRecylcerViewAdapter extends RecyclerView.Adapter<ListUsersRecylcerViewAdapter.ViewHolder> {

    private List<UserData> items;
    private Context mContext;

    public ListUsersRecylcerViewAdapter(List<UserData> items,Context context) {
        this.items = items;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_users_activity, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
       UserData item = items.get(position);
        holder.text.setText(item.getFirstname());
        holder.last_name.setText(item.getLastname());
        holder.date.setText(item.getRegisterDate());
        Glide
            .with(mContext)
            .load(item.getImage())
            .centerCrop()
            .crossFade()
            .into(holder.image);
    }

    @Override
    public int getItemCount() {return items.size();}

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView text;
        public TextView last_name;
        public TextView date;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.firstname_text); // Affiche le first name
            date = (TextView) itemView.findViewById(R.id.date);
            last_name = (TextView) itemView.findViewById(R.id.last_name_text);
            image = (ImageView) itemView.findViewById(R.id.ivUser);
            itemView.setOnClickListener(this); // permet de clicker sur les views
        }

        @Override
        public void onClick(View v) {
            Intent i = new Intent(v.getContext(),DetailUserActivity.class);
            UserData selectedUser = items.get(getAdapterPosition());
            i.putExtra("user",selectedUser);
            v.getContext().startActivity(i);
        }
    }
}
