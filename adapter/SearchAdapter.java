package testproject.com.app.testproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import testproject.com.app.testproject.DetailListActivity;
import testproject.com.app.testproject.R;
import testproject.com.app.testproject.model.Pages;


public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.PagesHolder> {

    private Context context;
    private ArrayList<Pages> pages;

    public SearchAdapter(Context context, ArrayList<Pages> pages) {
        this.context = context;
        this.pages = pages;
    }

    @Override
    public PagesHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new PagesHolder(view);
    }


    @Override
    public void onBindViewHolder(PagesHolder holder, final int position) {
        holder.tvTitle.setText(pages.get(position).getTitle());
        if(pages.get(position).getTerms()!=null){
            holder.tvRedirected.setText(pages.get(position).getTerms().getDescription().get(0));
        }else {
            holder.tvRedirected.setVisibility(View.GONE);
        }

        if(pages.get(position).getThumbnail()!=null){
            Picasso.with(context)
                    .load(pages.get(position).getThumbnail().getSource())
                    .placeholder(android.R.drawable.sym_def_app_icon)
                    .error(android.R.drawable.sym_def_app_icon)
                    .into(holder.ivThumbnail);
        }

        holder.cvSingleItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, DetailListActivity.class);
                intent.putExtra("PAGE_ID",pages.get(position).getPageid());
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        if(pages!=null){
            return pages.size();
        }else {
            return 0;
        }

    }

    public static class PagesHolder extends RecyclerView.ViewHolder {

        private CardView cvSingleItem;
        private ImageView ivThumbnail;
        private TextView tvTitle;
        private TextView tvRedirected;

        public PagesHolder(View v) {
            super(v);
            cvSingleItem=v.findViewById(R.id.cvSingleItem);
            ivThumbnail=v.findViewById(R.id.ivThumbnail);
            tvTitle=v.findViewById(R.id.tvTitle);
            tvRedirected=v.findViewById(R.id.tvRedirected);
        }
    }
}

