package team5.bsec.messenger.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import team5.bsec.messenger.R;
import team5.bsec.messenger.adapters.RvConversationAdapter;
import team5.bsec.messenger.entities.MsgObject;
import team5.bsec.messenger.entities.MsgSnippet;
import team5.bsec.messenger.items.DividerItemDecoration;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ConversationsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ConversationsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConversationsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    RecyclerView rvConversations;
    List<MsgSnippet> msgSnippetList;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConversationsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ConversationsFragment newInstance(String param1, String param2) {
        ConversationsFragment fragment = new ConversationsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public ConversationsFragment() {
        // Required empty public constructor
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
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);
        setup(view);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    public void setup(View view){

        msgSnippetList = new ArrayList<>();
        rvConversations = (RecyclerView) view.findViewById(R.id.rvConversations);
        rvConversations.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        rvConversations.setLayoutManager(llm);
        getMessages(getActivity().getApplicationContext());
    }

    public void getMessages(Context context){
        Uri uri = Uri.parse("content://sms/conversations");
       // String[] reqCols = new String[] { "_id","message_count", "snippet", "date"};
        String[] reqCols = new String[] { "msg_count","thread_id", "snippet"};
        Cursor cursor = context.getContentResolver().query(uri, reqCols
                , null, null, "date DESC");

        if (cursor != null && cursor.getCount() > 0) {




            while (cursor.moveToNext()) {

                String thread_id = cursor.getString(cursor.getColumnIndexOrThrow(reqCols[1]));
               // long dateInt = cursor.getLong(cursor.getColumnIndexOrThrow(reqCols[3]));
                String snippet = cursor.getString(cursor.getColumnIndexOrThrow(reqCols[2]));


                String args[] = {thread_id};
                MsgObject msgObject = getAddress(context,args);
              //  String address = getAddress(context, args);
                String address = msgObject.address;
                long dateInt = 0;
                if(msgObject.date != null)
                    dateInt= Long.parseLong(msgObject.date);
                String dateString = (DateUtils.getRelativeTimeSpanString(dateInt)).toString();


                MsgSnippet msgSnippet = new MsgSnippet(address,snippet,dateString);
                msgSnippetList.add(msgSnippet);

               // break;

            }
        }
        cursor.close();
        RvConversationAdapter rvConversationAdapter = new RvConversationAdapter(msgSnippetList);
        rvConversations.setAdapter(rvConversationAdapter);

    }

    public MsgObject getAddress(Context context,String[] args){
        Uri uri = Uri.parse("content://sms");
        String[] reqCols = new String[] { "thread_id","address","date"};
        Cursor cursor = context.getContentResolver().query(uri, reqCols, "thread_id=?",args, null);
        String address = "Unknown";
        String date = null;
        MsgObject msgObject;
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                address = cursor.getString(cursor.getColumnIndexOrThrow(reqCols[1]));
                date = cursor.getString(cursor.getColumnIndexOrThrow(reqCols[2]));

            }
        }
        cursor.close();
        msgObject = new MsgObject(date,address);

        return msgObject;
    }

}
