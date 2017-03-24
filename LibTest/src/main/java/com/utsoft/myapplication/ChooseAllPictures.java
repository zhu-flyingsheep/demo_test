package com.utsoft.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.orhanobut.logger.Logger;
import com.utsoft.myapplication.Utils.ImageUtils;
import com.utsoft.myapplication.Utils.PermissionCheckAndRequest;
import com.utsoft.myapplication.Utils.StringUtils;
import com.utsoft.myapplication.model.ImageFloder;


import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class ChooseAllPictures extends Activity implements ListImageDirPopupWindow.OnImageDirSelected {
    private ProgressDialog mProgressDialog;

    /**
     * 存储文件夹中的图片数量
     */
    private int mPicsSize;
    /**
     * 图片数量最多的文件夹
     */
    private File mImgDir;
    /**
     * 所有的图片
     */
    private List<String> mImgs;
    private List<String> file_names = new ArrayList<String>();
    private GridView mGirdView;
    private MyAdapter mAdapter;
    private String take_photo = "take_native_photo";
    /**
     * 临时的辅助类，用于防止同一个文件夹的多次扫描
     */
    private HashSet<String> mDirPaths = new HashSet<String>();

    /**
     * 扫描拿到所有的图片文件夹
     */
    private List<ImageFloder> mImageFloders = new ArrayList<ImageFloder>();


    private TextView mChooseDir;
    int totalCount = 0;

    private int mScreenHeight;

    private ListImageDirPopupWindow mListImageDirPopupWindow;
    private Intent intent;
    private String is_only_one = "";
    private boolean is_from_release_tracepic = false;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mProgressDialog.dismiss();
            // 为View绑定数据
            data2View();
            // 初始化展示文件夹的popupWindw


            // 初始化imageFloder
            ImageFloder imageFloder = new ImageFloder();
            imageFloder.setDir("All_Pictures_url");
            imageFloder.setCount(totalCount);
            imageFloder.setName("所有图片");
            if (All_images.size() > 0)
                imageFloder.setFirstImagePath(All_images.get(0));
            mImageFloders.add(0, imageFloder);

            initListDirPopupWindw();
        }
    };

    /**
     * 临时保存所有图片
     */
    private List<String> All_images = new ArrayList<String>();

    /**
     * 为View绑定数据
     */
    private void data2View() {
        if (mImgDir == null) {
            Toast.makeText(getApplicationContext(), getString(R.string.no_picture),
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String file_path = mImgDir.getAbsolutePath();
        file_names.clear();
        file_names.add(take_photo);
        file_names.addAll(All_images);
//        for (int i = 0; i < All_images.size(); i++) {
//            if (All_images.get(i).contains(file_path)) {
//                file_names.add(All_images.get(i));
//            }
//        }
//        mImgs = Arrays.asList(mImgDir.list());
//        for (int i = mImgs.size() - 1; i >= 0; i--) {
//            String filename = mImgs.get(i);
//            if (filename.endsWith(".jpg")
//                    || filename.endsWith(".png")
//                    || filename.endsWith(".jpeg")) {
//                file_names.add(filename);
//            }
//
//        }
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        Logger.i("mDirPath", "构造adapter");
        mAdapter = new MyAdapter(getApplicationContext(), file_names,
                R.layout.grid_item, mImgDir.getAbsolutePath());
        mGirdView.setAdapter(mAdapter);
        Logger.i("mDirPath", "构造adapter完毕" + totalCount);
    }


    /**
     * 初始化展示文件夹的popupWindw
     */
    private void initListDirPopupWindw() {
        mListImageDirPopupWindow = new ListImageDirPopupWindow(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT,
                mImageFloders, LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.list_dir, null));

        mListImageDirPopupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        // 设置选择文件夹的回调
        mListImageDirPopupWindow.setOnImageDirSelected(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.choose_all_pictures);

        DisplayMetrics outMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
        mScreenHeight = outMetrics.heightPixels;
        intent = getIntent();
        is_from_release_tracepic = intent.getBooleanExtra("is_from_release_tracepic", false);
        is_only_one = intent.getStringExtra("is_only_one");
        if (null != intent.getStringArrayListExtra("CHECKIMGS"))
            mSelectedImage = intent.getStringArrayListExtra("CHECKIMGS");

        initTitle();
        initView();
        getImages();

    }

    private View titleView;
    private TextView tv_pic_selected;

    public void initTitle() {
        this.titleView = this.findViewById(R.id.tittle);
        ((TextView) this.titleView.findViewById(R.id.id_choose_dir))
                .setText("选择图片");
        this.titleView.findViewById(R.id.detail_back_ll).setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        tv_pic_selected = (TextView) this.findViewById(R.id.tv_pic_selected);
        this.titleView.setBackgroundColor(getResources().getColor(R.color.white));
        this.titleView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                mListImageDirPopupWindow
                        .setAnimationStyle(R.style.popWindow_anim_style_secnic);
                mListImageDirPopupWindow.showAsDropDown(titleView, 0, 0);

                // 设置背景颜色变暗
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);

            }
        });
        LinearLayout linearLayout = (LinearLayout) titleView.findViewById(R.id.ll_reight);
        linearLayout.setVisibility(View.VISIBLE);

        linearLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                if ("is_only_one".equals(is_only_one)) {
//                    if (mSelectedImage.size() > 0) {
//                        intent.putExtra("cover_url", mSelectedImage.get(0));
//                        setResult(200, intent);
//                    }
//
//                    finish();
//                } else {
//                    intent.putStringArrayListExtra("urls", mSelectedImage);
//                    setResult(200, intent);
//                    finish();
//                }

                if (is_from_release_tracepic) {
                    finish();
                } else {
                    if (mSelectedImage.size() == 0) {
                        Toast.makeText(ChooseAllPictures.this, "至少选择一张照片！", Toast.LENGTH_SHORT).show();
                        return;
                    }
//                    Intent intent = new Intent(ChooseAllPictures.this, ReleaseTracePictures.class);
//                    intent.putExtra("CHECKIMGS", (ArrayList) mSelectedImage);
//                    startActivityForResult(intent, 1);
                    finish();
                }


            }
        });
    }

    public static void deleteAllFilesOfDir(File path) {
        if (!path.exists())
            return;
        if (path.isFile()) {
            path.delete();
            return;
        }
        File[] files = path.listFiles();
        for (int i = 0; i < files.length; i++) {
            deleteAllFilesOfDir(files[i]);
        }
        path.delete();
    }

    @Override
    public void finish() {
//        new Thread() {
//            @Override
//            public void run() {
//                File dir = new File(NetUrl.trace_pic_path + File.separator);
//
//                deleteAllFilesOfDir(dir);
//            }
//        }.start();
        intent.putExtra("CHECKIMGS", (ArrayList) mSelectedImage);
        setResult(200, intent);
        super.finish();
    }

    /**
     * 把毫秒转化成日期
     *
     * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
     * @param millSec(毫秒数)
     * @return
     */
    private String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }

    /**
     * 利用ContentProvider扫描手机中的图片，此方法在运行在子线程中 完成图片的扫描，最终获得jpg最多的那个文件夹
     */
    private void getImages() {

        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            Toast.makeText(this, "暂无外部存储", Toast.LENGTH_SHORT).show();
            return;
        }
        // 显示进度条
        mProgressDialog = ProgressDialog.show(this, null, "正在加载...");


        new Thread(new Runnable() {
            @Override
            public void run() {

                String firstImage = null;

                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = ChooseAllPictures.this
                        .getContentResolver();

                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?",
                        new String[]{"image/jpeg", "image/png"},
                        MediaStore.Images.Media.DATE_MODIFIED + " DESC");


                Logger.e("TAG", mCursor.getCount() + "");
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                    All_images.add(path);
                    String time = mCursor.getString(mCursor
                            .getColumnIndex(MediaStore.Images.Media.DATE_ADDED));
                    Logger.i("shoottime", time);
                    String transform_time = transferLongToDate("yyyy-MM-dd HH:mm:ss", Long.parseLong(time) * 1000);//转换时间得用毫秒
                    Logger.i("shoottime", transform_time);
                    Logger.e("TAG", path);
                    // 拿到第一张图片的路径
                    if (firstImage == null)
                        firstImage = path;
                    // 获取该图片的父路径名
                    File parentFile = new File(path).getParentFile();
                    if (parentFile == null)
                        continue;
                    String dirPath = parentFile.getAbsolutePath();
                    ImageFloder imageFloder = null;
                    // 利用一个HashSet防止多次扫描同一个文件夹（不加这个判断，图片多起来还是相当恐怖的~~）
                    if (mDirPaths.contains(dirPath)) {
                        continue;
                    } else {
                        mDirPaths.add(dirPath);
                        // 初始化imageFloder
                        imageFloder = new ImageFloder();
                        imageFloder.setDir(dirPath);
                        imageFloder.setFirstImagePath(path);
                    }
                    if (parentFile.list() == null) continue;
                    int picSize = parentFile.list(new FilenameFilter() {
                        @Override
                        public boolean accept(File dir, String filename) {
                            if (filename.endsWith(".jpg")
                                    || filename.endsWith(".png")
                                    || filename.endsWith(".jpeg"))
                                return true;
                            return false;
                        }
                    }).length;
                    totalCount += picSize;

                    imageFloder.setCount(picSize);
                    mImageFloders.add(imageFloder);

                    if (picSize > mPicsSize) {
                        mPicsSize = picSize;
                        mImgDir = parentFile;
                    }
                }
                mCursor.close();

                // 扫描完成，辅助的HashSet也就可以释放内存了
                mDirPaths = null;

                // 通知Handler扫描图片完成
                mHandler.sendEmptyMessage(0x110);

            }
        }).start();

    }

    /**
     * 初始化View
     */
    private void initView() {
        mGirdView = (GridView) findViewById(R.id.id_gridView);
        mChooseDir = (TextView) findViewById(R.id.id_choose_dir);

    }


    @Override
    public void selected(ImageFloder floder) {
        for (ImageFloder imageFloder : mImageFloders) {
            if (imageFloder.getDir().equals(floder.getDir())) {

                imageFloder.setIs_choosed(true);

            } else {
                imageFloder.setIs_choosed(false);
            }
        }


        file_names.clear();
        file_names.add(take_photo);
        String file_path = floder.getDir();
        if ("All_Pictures_url".equals(file_path)) {


            file_names.addAll(All_images);
        } else {


            mImgDir = new File(file_path);
            for (int i = 0; i < All_images.size(); i++) {
                if (All_images.get(i).contains(file_path)) {
                    String last = All_images.get(i).substring(file_path.length() + 1);
                    if (last.contains(File.separator)) {
                    } else {


                        file_names.add(All_images.get(i));

                    }
                }

            }
        }


//        mImgs = Arrays.asList(mImgDir.list());
//        file_names.clear();
//        for (int i = mImgs.size() - 1; i >= 0; i--) {
//            String filename = mImgs.get(i);
//            if (filename.endsWith(".jpg")
//                    || filename.endsWith(".png")
//                    || filename.endsWith(".jpeg")) {
//                file_names.add(filename);
//            }
//        }
        Logger.i("mDirPath", "构造adapter");
        /**
         * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
         */
        mAdapter = new MyAdapter(getApplicationContext(), file_names,
                R.layout.grid_item, mImgDir.getAbsolutePath());
        mGirdView.setAdapter(mAdapter);
        // mAdapter.notifyDataSetChanged();
        mChooseDir.setText(floder.getName());
        mListImageDirPopupWindow.dismiss();

    }


    /**
     * 用户选择的图片，存储为图片的完整路径
     */
    public ArrayList<String> mSelectedImage = new ArrayList<String>();


    public class MyAdapter extends CommonAdapter<String> {


        /**
         * 文件夹路径
         */
        private String mDirPath;

        public MyAdapter(Context context, List<String> mDatas, int itemLayoutId,
                         String dirPath) {
            super(context, mDatas, itemLayoutId);
            this.mDirPath = dirPath;
        }

        @Override
        public void convert(final ViewHolder helper, final String item, final int position) {

//		//设置no_pic
//		helper.setImageResource(R.id.id_item_image, R.drawable.ic_launcher);
            //设置no_selected
            helper.setImageResource(R.id.id_item_select,
                    R.drawable.btn_checkbox_n);
            //设置图片
//		helper.setImageByUrl(R.id.id_item_image, mDirPath + "/" + item);

            Logger.i("mDirPath", item);
            final ImageView mSelect = helper.getView(R.id.id_item_select);
            final ImageView mImageView = helper.getView(R.id.id_item_image);

            if (item.equals(take_photo)) {
                mSelect.setVisibility(View.GONE);
                mImageView.setImageResource(R.drawable.xiangce_icon_paizhao);
            } else {
                mSelect.setVisibility(View.VISIBLE);

                Glide.with(mContext)
                        .load(item)
                        .centerCrop()
                        .placeholder(R.drawable.pic_placeholder_littile).error(R.drawable.pic_placeholder_littile)
                        .crossFade().diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into((ImageView) helper.getView(R.id.id_item_image));
            }


            mImageView.setColorFilter(Color.parseColor("#00000000"));

            mImageView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (item.equals(take_photo)) {
                        if (mSelectedImage.size() == 9) {
                            Toast.makeText(ChooseAllPictures.this, "一次只能选择9张图片", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!PermissionCheckAndRequest.check_permission(ChooseAllPictures.this, Manifest.permission.CAMERA)) {
                            PermissionCheckAndRequest.requese_permission(ChooseAllPictures.this, 99,
                                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                            Toast.makeText(ChooseAllPictures.this, "无摄像头使用权限，请设置", Toast.LENGTH_SHORT).show();
                            return;

                        }
                        startTakePhoto();
                    } else {
//                        Intent intent = new Intent(ChooseAllPictures.this, ChooseAllPicDetialShow.class);
//                        intent.putStringArrayListExtra("ALLIMG", (ArrayList) file_names);
//                        intent.putExtra("DIRPATH", mDirPath);
//                        intent.putExtra("index", position);
//                        intent.putExtra("CHECKIMGS", (ArrayList) mSelectedImage);
//                        intent.putExtra("is_only_one", is_only_one);
//
//                        startActivityForResult(intent, 3);
                    }


                }
            });
            //设置ImageView的点击事件
            mSelect.setOnClickListener(new OnClickListener() {
                //选择，则将图片变暗，反之则反之
                @Override
                public void onClick(View v) {
                    Logger.i("setOnClickListener", item);
                    // 已经选择过该图片
                    if (mSelectedImage.contains(item)) {
                        mSelectedImage.remove(item);
                        mSelect.setImageResource(R.drawable.btn_checkbox_n);
                        mImageView.setColorFilter(Color.parseColor("#00000000"));
                    } else
                    // 未选择该图片
                    {
                        if (mSelectedImage.size() == 9) {
                            Toast.makeText(ChooseAllPictures.this, "一次只能选择9张图片", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mSelectedImage.add(item);
                        mSelect.setImageResource(R.drawable.btn_checkbox_s);
                        mImageView.setColorFilter(Color.parseColor("#77000000"));
                        if (null != is_only_one) {
                            if ("is_only_one".equals(is_only_one)) {
                                intent.putExtra("cover_url", item);
                                setResult(200, intent);
                                finish();
                            }
                        }
                    }

                    if (mSelectedImage.size() > 0)
                        tv_pic_selected.setText("下一步(" + mSelectedImage.size() + ")");
                    else {
                        tv_pic_selected.setText("下一步");
                    }
                }
            });

            /**
             * 已经选择过的图片，显示出选择过的效果
             */
            if (mSelectedImage.contains(item)) {
                mSelect.setImageResource(R.drawable.btn_checkbox_s);
                mImageView.setColorFilter(Color.parseColor("#77000000"));
            }

        }


    }

    private String theLarge;
    private Uri origUri;

    private void startTakePhoto() {
        Intent intent;
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath();
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (StringUtils.isEmpty(savePath)) {
            Toast.makeText(ChooseAllPictures.this, "无法保存照片，请检查SD卡是否挂载", Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(new Date());
        String fileName = "/DCIM/" + timeStamp + ".jpg";// 照片命名
        File out = new File(savePath, fileName);
        Uri uri = Uri.fromFile(out);
        origUri = uri;

        theLarge = savePath + fileName;// 该照片的绝对路径
        Logger.i(theLarge);
        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent,
                ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA);

    }

    @Override
    protected void onActivityResult(int requesecode, int resultcode, Intent paramIntent) {
        super.onActivityResult(requesecode, resultcode, paramIntent);
        if (requesecode == 3 && resultcode == 4) {
            mSelectedImage.clear();
            mSelectedImage.addAll(paramIntent.getStringArrayListExtra("CHOOSEIMGS"));
            mAdapter.notifyDataSetChanged();
        }
        if (506 == resultcode) {
            setResult(506, intent);
            finish();
        }
        if (requesecode == ImageUtils.REQUEST_CODE_GETIMAGE_BYCAMERA) {
            if (resultcode != RESULT_OK) {        //此处的 RESULT_OK 是系统自定义得一个常量
                return;
            }
            ImageUtils.scanPhoto(ChooseAllPictures.this, theLarge);

            Logger.i("theLarge", theLarge);
//            Intent intent = new Intent(ChooseAllPictures.this, ReleaseTracePictures.class);
//
//            mSelectedImage.add(theLarge);
//            intent.putExtra("CHECKIMGS", (ArrayList) mSelectedImage);
//            startActivityForResult(intent, 1);
        }


    }
}
