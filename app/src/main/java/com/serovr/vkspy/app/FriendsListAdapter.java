package com.serovr.vkspy.app;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class FriendsListAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ArrayList<Friend> objects;

    FriendsListAdapter(Context context, ArrayList<Friend> friends){
        this.context = context;
        objects = friends;
        lInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во друзей
    @Override
    public int getCount(){
        return objects.size();
    }

    // друг по позиции
    @Override
    public Object getItem(int position){
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position){
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = convertView;
        if (view == null){
            view = lInflater.inflate(R.layout.lf_friend_item, parent, false);
        }

        Friend friend = getFriend(position);

        ((TextView) view.findViewById(R.id.tvNameSurname)).setText(friend.getFirstName()+" "+friend.getLastName());
        //((ImageView) view.findViewById(R.id.ivImage)).setImageResource(friend.image);
        try {
            File file = new File (friend.getImageUrl()); //TODO: нужен рефакторинг
            if (file.exists())
                ((ImageView) view.findViewById(R.id.ivImage)).setImageDrawable(Drawable.createFromPath(friend.getImageUrl()));
            else
                ((ImageView) view.findViewById(R.id.ivImage)).setImageResource(R.drawable.user_placeholder);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    Friend getFriend(int position){
        return ((Friend) getItem(position));
    }



}
