package com.cokerj.collegecompanion.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cokerj.collegecompanion.Database.Repository;
import com.cokerj.collegecompanion.Entity.Note;
import com.cokerj.collegecompanion.Entity.Term;
import com.cokerj.collegecompanion.R;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    class NoteViewHolder extends RecyclerView.ViewHolder{
        private final TextView noteTitle;
        private NoteViewHolder(View itemView){
            super(itemView);
            noteTitle = itemView.findViewById(R.id.noteTitle);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    final Note current = mNotes.get(position);
                    Intent intent = new Intent(context, NoteDetailsScreen.class);
                    intent.putExtra("noteId", current.getNoteId());
                    context.startActivity(intent);
                }
            });
        }
    }
    private Repository mRepo;
    private List<Note> mNotes;
    private final Context context;
    private final LayoutInflater mInflater;
    public NoteAdapter(Context context){
        mInflater=LayoutInflater.from(context);
        this.context=context;
    }

    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.note_list_item,parent,false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        if(mNotes!=null){
            Note current = mNotes.get(position);
            String title = current.getTitle();
            holder.noteTitle.setText(current.getTitle());
        }else {
            holder.noteTitle.setText("There is no name");
        }
    }

    public void setNotes(List<Note> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    public void setRepository(Repository repo){
        mRepo = repo;
    }

    @Override
    public int getItemCount() {
        if (mNotes == null){
            return 0;
        } else {
            return mNotes.size();
        }
    }
}