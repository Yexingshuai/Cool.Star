package fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.example.myapp.R;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import adapter.DesginAdapter;
import bean.ThemeColor;
import utils.SPUtils;

/**
 * Created by daixiankade on 2018/5/8.
 */

public class DesignDialog extends AppCompatDialogFragment implements View.OnClickListener {
    private ArrayList<ThemeColor> themeColorList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_desgin, container, false);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EventBus.getDefault().register(this);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light_Dialog_MinWidth);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        themeColorList.add(new ThemeColor(R.color.theme_red_base));
        themeColorList.add(new ThemeColor(R.color.theme_blue));
        themeColorList.add(new ThemeColor(R.color.theme_blue_light));
        themeColorList.add(new ThemeColor(R.color.theme_balck));
        themeColorList.add(new ThemeColor(R.color.theme_teal));
        themeColorList.add(new ThemeColor(R.color.theme_brown));
        themeColorList.add(new ThemeColor(R.color.theme_green));
        themeColorList.add(new ThemeColor(R.color.theme_red));
        RecyclerView rv = view.findViewById(R.id.rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 4);
        rv.setLayoutManager(gridLayoutManager);
        final DesginAdapter desginAdapter = new DesginAdapter(themeColorList);
        rv.setAdapter(desginAdapter);
        desginAdapter.setOnItemClickListener(new DesginAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                for (ThemeColor themeColor : themeColorList) {
                    themeColor.setChosen(false);
                }
                themeColorList.get(position).setChosen(true);
                SPUtils.put(view.getContext(),"color",themeColorList.get(position).getColor());
                desginAdapter.notifyDataSetChanged();
            }
        });
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(this);

    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);

        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_confirm:
                Message message = new Message();
                message.what=2;
                EventBus.getDefault().post(message);
                dismiss();
                break;
        }
    }
}
