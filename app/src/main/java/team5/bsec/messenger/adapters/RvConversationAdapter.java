package team5.bsec.messenger.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import team5.bsec.messenger.R;
import team5.bsec.messenger.entities.MsgSnippet;

/**
 * Created by sandman on 18/11/15.
 */
public class RvConversationAdapter extends RecyclerView.Adapter<RvConversationAdapter.SnippetViewHolder>{

    List<MsgSnippet> listOfMsgSnippet;

    public RvConversationAdapter(List<MsgSnippet> listOfMsgSnippet){
        this.listOfMsgSnippet = listOfMsgSnippet;
    }
    @Override
    public SnippetViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_snippet,parent,false);
        SnippetViewHolder snippetViewHolder = new SnippetViewHolder(view);
        return snippetViewHolder;
    }

    @Override
    public void onBindViewHolder(SnippetViewHolder holder, int position) {
        holder.textSender.setText(listOfMsgSnippet.get(position).sender);
        holder.textTime.setText(listOfMsgSnippet.get(position).time);
        holder.textSnippet.setText(listOfMsgSnippet.get(position).snippet);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
        return listOfMsgSnippet.size();
    }

    public static class SnippetViewHolder extends RecyclerView.ViewHolder{
        TextView textSender;
        TextView textTime;
        TextView textSnippet;

        public SnippetViewHolder(View itemView) {
            super(itemView);
            textSender = (TextView) itemView.findViewById(R.id.textSender);
            textTime = (TextView) itemView.findViewById(R.id.textTime);
            textSnippet = (TextView) itemView.findViewById(R.id.textSnippet);
        }
    }

}
