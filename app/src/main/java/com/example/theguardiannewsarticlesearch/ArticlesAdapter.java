package com.example.theguardiannewsarticlesearch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder> {
    private List<Article> articles;
    private OnArticleClickListener listener;
    private boolean isFavoritesList;

    public ArticlesAdapter(List<Article> articles, OnArticleClickListener listener, boolean isFavoritesList) {
        this.articles = articles;
        this.listener = listener;
        this.isFavoritesList = isFavoritesList;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    public void setIsFavoritesList(boolean isFavoritesList) {
        this.isFavoritesList = isFavoritesList;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        Article article = articles.get(position);
        holder.textViewTitle.setText(article.getWebTitle());
        holder.textViewSection.setText(article.getSectionName());
        holder.textViewUrl.setText(article.getWebUrl());

        if (isFavoritesList) {
            holder.favoriteButton.setText("Remove from Favorites");
            holder.favoriteButton.setOnClickListener(v -> listener.onRemoveFavorite(article));
        } else {
            holder.favoriteButton.setText("Add to Favorites");
            holder.favoriteButton.setOnClickListener(v -> listener.onFavoriteClick(article));
        }

        holder.itemView.setOnClickListener(v -> listener.onArticleClick(article.getWebUrl()));
    }

    @Override
    public int getItemCount() {
        return articles != null ? articles.size() : 0;
    }

    public interface OnArticleClickListener {
        void onArticleClick(String url);
        void onFavoriteClick(Article article);
        void onRemoveFavorite(Article article);
    }

    public static class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewSection, textViewUrl;
        Button favoriteButton;

        public ArticleViewHolder(View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewSection = itemView.findViewById(R.id.textViewSection);
            textViewUrl = itemView.findViewById(R.id.textViewUrl);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }
}