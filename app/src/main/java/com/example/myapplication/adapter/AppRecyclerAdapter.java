package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myapplication.R;
import com.example.myapplication.bean.CacheListItem;
import java.util.List;

public class AppRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<CacheListItem> mDatas;

    public AppRecyclerAdapter(Context context, List<CacheListItem> datas) {
        mContext = context;
        mDatas = datas;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.item_app, parent, false);
        return new NormalHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        NormalHolder normalHolder = (NormalHolder) holder;
        normalHolder.mTV.setText(mDatas.get(position).getApplicationName()+"  "+mDatas.get(position).getPackageName());
        normalHolder.mIcon.setImageDrawable(mDatas.get(position).getApplicationIcon());

        if(mOnClickListener != null) {
            normalHolder.mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onClick(v,normalHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public class NormalHolder extends RecyclerView.ViewHolder{
        public ImageView mIcon;
        public TextView mTV;
        public View mRoot;

        public NormalHolder(View itemView) {
            super(itemView);

            mRoot = itemView;
            mIcon = (ImageView) itemView.findViewById(R.id.image_view);
            mTV = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    private OnClickListener mOnClickListener;
    public interface OnClickListener{
        void onClick(View view,int position);
    }

    public void setOnClickListener(OnClickListener onClickListener){
        mOnClickListener = onClickListener;
    }
}
