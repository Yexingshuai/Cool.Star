package adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapp.R;

import java.util.ArrayList;

import bean.ThemeColor;
import view.CircleImageView;

/**
 * Created by daixiankade on 2018/5/8.
 */

public class DesginAdapter extends RecyclerView.Adapter {
    private ArrayList<ThemeColor> themeColorList;
    private OnItemClickListener onItemClickListener;

    public DesginAdapter(ArrayList<ThemeColor> themeColorList) {
        this.themeColorList = themeColorList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme_color, parent, false);
        return new DesignViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder vholder, int position) {
        DesignViewHolder holder = (DesignViewHolder) vholder;
        holder.setPosition(position);
        ThemeColor themeColor = themeColorList.get(position);
        holder.them_color.setImageResource(themeColor.getColor());
        if (themeColor.isChosen()) {
            holder.chosen.setVisibility(View.VISIBLE);

        } else {
            holder.chosen.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return themeColorList.size();
    }

    class DesignViewHolder extends RecyclerView.ViewHolder {
        de.hdodenhof.circleimageview.CircleImageView them_color;
        ImageView chosen;
        private int position;

        public DesignViewHolder(View itemView) {
            super(itemView);
            chosen = itemView.findViewById(R.id.choose);
            them_color = itemView.findViewById(R.id.them_color);
            them_color.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onClick(position);
                    }
                }
            });
        }

        public void setPosition(int position) {

            this.position = position;
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {

        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }

}
