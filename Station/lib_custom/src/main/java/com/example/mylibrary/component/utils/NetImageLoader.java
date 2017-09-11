package com.example.mylibrary.component.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.X509TrustManager;

public class NetImageLoader {

    private static NetImageLoader sInstance;
    LruCache<String, Bitmap> mMemoryCache;
    private ExecutorService mImageThreadPool = Executors.newFixedThreadPool(1);

    private NetImageLoader() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        int cacheSize = maxMemory / 2;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {

            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }

            @Override
            protected void entryRemoved(boolean evicted, String key,
                                        Bitmap oldValue, Bitmap newValue) {
            }
        };
    }

    public synchronized static NetImageLoader getInstance() {
        if (sInstance == null) {
            sInstance = new NetImageLoader();
            return sInstance;
        }
        return sInstance;
    }

    @SuppressLint("HandlerLeak")
    public Bitmap loadImage(final String path, final NetImageCallBack callBack, final String fileDir) {
        Bitmap bitmap = getBitmapFromMemCache(path);

        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (callBack != null) {
                    callBack.onImageLoader((Bitmap) msg.obj, path);
                }
            }
        };

        if (bitmap == null) {
            mImageThreadPool.execute(new Runnable() {

                @Override
                public void run() {

                    Bitmap bitmap;
                    String fileName = path;

                    if (!isNullOrEmpty(fileName)) {
                        File file = new File(fileDir + fileName);
                        if (file.exists()) {
                            bitmap = getImageBitmapFromSdcard(fileDir, fileName);
                        } else {
                            bitmap = getImageBitmapFromNet(path);
                            if (bitmap != null) {
                                saveBitmapToSdcard(bitmap, fileDir, fileName);
                            }

                        }
                    } else {
                        bitmap = getImageBitmapFromNet(path);
                        if (bitmap != null) {
                            saveBitmapToSdcard(bitmap, fileDir, fileName);
                        }
                    }

                    Message msg = handler.obtainMessage();
                    msg.obj = bitmap;
                    handler.sendMessage(msg);

                    addBitmapToMemoryCache(path, bitmap);
                }
            });
        }
        return bitmap;
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null && bitmap != null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

    public interface NetImageCallBack {
        public void onImageLoader(Bitmap bitmap, String path);
    }

    public void clearCache() {
        if (mMemoryCache != null) {
            if (mMemoryCache.size() > 0) {
                mMemoryCache.evictAll();
            }
            mMemoryCache = null;
        }
    }

    public synchronized void removeImageCache(String key) {
        if (key != null) {
            if (mMemoryCache != null) {
                Bitmap bm = mMemoryCache.remove(key);
                if (bm != null)
                    bm.recycle();
            }
        }
    }


    @SuppressWarnings({"TryWithIdenticalCatches", "ResultOfMethodCallIgnored"})
    public static void saveBitmapToSdcard(Bitmap bitmap, String fileDir, String fileName) {

        try {
            File file = new File(fileDir + fileName);

            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            bos.flush(); // save the bitmap to sdcard
            bos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Bitmap getImageBitmapFromSdcard(String fileDir, String fileName) {
        return BitmapFactory.decodeFile(fileDir + fileName);
    }

    @SuppressWarnings({"ConstantConditions", "TryWithIdenticalCatches"})
    public static Bitmap getImageBitmapFromNet(String address) {

        byte[] data = new byte[0];

        try {

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });

            SSLContext context = SSLContext.getInstance("TLS");
            context.init(null, new X509TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, new SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(
                    context.getSocketFactory());

            InputStream inputStream = null;

            String urlStr = address;

            URL url = new URL(urlStr);
            if (urlStr.startsWith("http")) {
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(60 * 1000);
                inputStream = conn.getInputStream();
            } else if (urlStr.startsWith("https")) {
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(60 * 1000);
                inputStream = conn.getInputStream();
            }

            byte[] buffer = new byte[1024];
            int len;
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            bos.close();
            data = bos.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }

        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public static boolean isNullOrEmpty(String s) {
        return s == null || s.trim().length() == 0;
    }

}