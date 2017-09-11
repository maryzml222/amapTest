package com.zt.station.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;


import com.zt.station.AppConstants;

import java.io.File;

/**
 * Created by ${Mary} on 2017/7/28.
 */

public class UploadImag {
    public static  void uploadHead(final Activity activity) {
        new ActionSheetDialog(activity)
                .builder()
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intent = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                        .fromFile(new File(Environment
                                                .getExternalStorageDirectory(),
                                                "head_temp_raw.jpg")));
                                activity.startActivityForResult(intent, AppConstants.TAKE_PHOTO);

                            }
                        })
                .addSheetItem("从相册中选择照片", ActionSheetDialog.SheetItemColor.Blue,
                        new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int which) {
                                Intent intent = new Intent(Intent.ACTION_PICK, null);

                                intent.setDataAndType(
                                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                                        "image/*");
                                activity.startActivityForResult(intent, AppConstants.SELECT_PIC);
                            }
                        })

                .show();
    }

    public static  void startPhotoZoom(Uri uri,Activity activity) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, AppConstants.ACTIVITY_RESULT_CROP_PICTURE);
    }
}
