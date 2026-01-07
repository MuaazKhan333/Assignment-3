package com.example.dataviewerapp;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dataviewerapp.models.Post;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private List<Post> list;
    private Context context;

    public PostAdapter(List<Post> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.post_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Post p = list.get(position);
        holder.t.setText(p.getTitle());
        holder.b.setText(p.getBody());
        holder.itemView.setOnClickListener(v -> {
            Intent i = new Intent(context, WebViewActivity.class);
            i.putExtra("url", "https://jsonplaceholder.typicode.com/posts/" + p.getId());
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView t, b;
        public ViewHolder(View itemView) { super(itemView); t = itemView.findViewById(R.id.textTitle); b = itemView.findViewById(R.id.textBody); }
    }
}