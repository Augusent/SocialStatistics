package com.serovr.vkspy.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;


import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.methods.VKApiFriends;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKUser;
import com.vk.sdk.api.model.VKUsersArray;

import java.util.ArrayList;

public class FriendsListFragment extends ListFragment {

    private OnFragmentInteractionListener mListener;

    ArrayList<Friend> friends = new ArrayList<Friend>();
    ArrayList<String> links = new ArrayList<String>();
    FriendsListAdapter flAdapter;
    ImageManager downloadImages = new ImageManager();

    public FriendsListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("FriendsListFragment", "onCreate()");
        fillFriendsList();
        flAdapter = new FriendsListAdapter(getActivity(), friends);
        setListAdapter(flAdapter);

        // setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
        //        android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_friends_list, null);
    }

    @Override
    public void onStart(){
        super.onStart();
        Log.d("FriendsListFragment", "onStart()");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("FriendsListFragment", "onDestroy()");
    }

    void fillFriendsList(){
        Log.d("FriendsListFragment", "fillFriendList()");
        if (!MainActivity.isAuthorized) {   //заглушка, если не авторизировался
            //for (int i = 1; i <= 20; i++)
            //    friends.add(new Friend("Имечко", "Фамилия", R.drawable.ic_launcher));
        }else{  // если авторизировался
            friends.clear();   // очистили старый список (если был другой аккаунт)
            links.clear(); //очистили список ссылок на картинки
            // запрос списка друзей (20 первых), запрашиваем имя, фамилию, статус онлайн. сортируем по рейтингу
            VKRequest request = VKApi.friends().get(VKParameters.from(VKApiConst.FIELDS, "online,photo_100,last_seen",
                                                    "order", "hints"));

            request.executeWithListener(new VKRequest.VKRequestListener() {
                @Override
                public void onComplete(VKResponse response) {
                    int count=((VKUsersArray)response.parsedModel).getCount();
                    //когда пришёл ответ - засовываем друзей в наш список
                    for (int i=0; i<count; i++) {
                        links.add(((VKUsersArray) response.parsedModel).get(i).photo_100);
                        String[] tokens = ((VKUsersArray) response.parsedModel).get(i).photo_100.split("/");
                        String fileName = tokens[tokens.length - 1];
                        friends.add(new Friend(((VKUsersArray) response.parsedModel).get(i).id,((VKUsersArray) response.parsedModel).get(i).first_name,
                                ((VKUsersArray) response.parsedModel).get(i).last_name, MainActivity.path + "/" + fileName));

                    }
                    // запускаем асинхронное задание в которое передаём путь к кешу и ссылки на картинки
                    if (!downloadImages.getStatus().toString().equals("RUNNING")) {
                        downloadImages.setParams(MainActivity.path, links);//TODO: проверить на медленнном соединении
                        downloadImages.execute();
                    }
                    // и апдейтим его
                    setListAdapter(flAdapter);

                }
                @Override
                public void onError(VKError error) {
                    //Do error stuff
                }
                @Override
                public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                    //I don't really believe in progress
                }
            });
        }
    }
/*
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
*/

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String currName, currSurname, currImage;
        int currID;
        currID = friends.get(position).getId();
        currName = friends.get(position).getFirstName();
        currSurname = friends.get(position).getLastName();
        currImage = friends.get(position).getImageUrl();

        // Добавление передаваемых параметров в интент
        Intent intent = new Intent(getActivity(),FunctionsMenuActivity.class);
        intent.putExtra("friendID", currID);
        intent.putExtra("friendName", currName);
        intent.putExtra("friendSurname", currSurname);
        intent.putExtra("friendPhoto", currImage);

//        Log.i("FLF.java", ""+currID);

        // В дальнейшем изменить метод на startActivityForResult()
        startActivity(intent);
    }


    /**
    * This interface must be implemented by activities that contain this
    * fragment to allow an interaction in this fragment to be communicated
    * to the activity and potentially other fragments contained in that
    * activity.
    * <p>
    * See the Android Training lesson <a href=
    * "http://developer.android.com/training/basics/fragments/communicating.html"
    * >Communicating with Other Fragments</a> for more information.
    */

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

}
