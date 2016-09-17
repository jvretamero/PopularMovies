package br.com.joaoretamero.popularmovies.presentation.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

public abstract class BaseAdapter<T, J extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<J> {

    private static final String TAG = BaseAdapter.class.getSimpleName();
    protected OnItemClickListener<T> itemClickListener;
    private List<T> dataList;

    public void setOnItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public T getItem(int position) {
        if (dataList != null) {
            return dataList.get(position);
        } else {
            return null;
        }
    }

    protected List<T> getData() {
        return this.dataList;
    }

    public void updateData(List<T> data) {
        this.dataList = data;
        this.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return (dataList == null) ? 0 : dataList.size();
    }

    @Override
    public void onBindViewHolder(J holder, int position) {
        final T item = getItem(position);

        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(item);
                }
            });
        }

        onBindItemHolder(holder, item);
    }

    protected abstract void onBindItemHolder(J holder, T item);

    public interface OnItemClickListener<K> {
        void onItemClick(K item);
    }
}
