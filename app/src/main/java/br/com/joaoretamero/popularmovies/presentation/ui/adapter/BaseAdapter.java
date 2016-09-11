package br.com.joaoretamero.popularmovies.presentation.ui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<T, J extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<J> {

    private List<T> dataList;

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
}
