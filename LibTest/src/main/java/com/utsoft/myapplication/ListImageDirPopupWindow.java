package com.utsoft.myapplication;

import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.bumptech.glide.Glide;
import com.utsoft.myapplication.model.ImageFloder;


import java.util.List;

public class ListImageDirPopupWindow extends BasePopupWindowForListView<ImageFloder> {
    private ListView mListDir;

    public ListImageDirPopupWindow(int width, int height,
                                   List<ImageFloder> datas, View convertView) {
        super(convertView, width, height, true, datas);
    }

    @Override
    public void initViews() {
        mListDir = (ListView) findViewById(R.id.id_list_dir);
        findViewById(R.id.rl_list).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mListDir.setAdapter(new CommonAdapter<ImageFloder>(context, mDatas,
                R.layout.list_dir_item) {
            @Override
            public void convert(ViewHolder helper, ImageFloder item, int postion) {
                helper.setText(R.id.id_dir_item_name, item.getName());
                if (item.is_choosed()) {
                    helper.setImageResource(R.id.iv_choose,
                            R.drawable.icon_radio_s);
                } else {
                    helper.setImageResource(R.id.iv_choose,
                            R.drawable.icon_radio_n);
                }

//				helper.setImageByUrl(R.id.id_dir_item_image,
//						item.getFirstImagePath());


                Glide.with(mContext)
                        .load(item.getFirstImagePath())
                        .centerCrop()
                        .placeholder(R.drawable.loading).error(R.drawable.pic_placeholder_littile)
                        .crossFade()
                        .into((ImageView) helper.getView(R.id.id_dir_item_image));
                helper.setText(R.id.id_dir_item_count, item.getCount() + "张");
            }
        });
    }

    public interface OnImageDirSelected {
        void selected(ImageFloder floder);
    }

    private OnImageDirSelected mImageDirSelected;

    public void setOnImageDirSelected(OnImageDirSelected mImageDirSelected) {
        this.mImageDirSelected = mImageDirSelected;
    }

    @Override
    public void initEvents() {
        mListDir.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                if (mImageDirSelected != null) {

                    mImageDirSelected.selected(mDatas.get(position));

                }
            }
        });
    }

    @Override
    public void init() {
        // TODO Auto-generated method stub

    }

    @Override
    protected void beforeInitWeNeedSomeParams(Object... params) {
        // TODO Auto-generated method stub
    }

}
