package com.example.mq.volleytest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    TextView context;
    ImageView imageView;
    Button button;
    ImageLoader.ImageListener listener;
    ImageLoader imageLoader;
    NetworkImageView networkImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = (TextView) findViewById(R.id.context);
        imageView = (ImageView) findViewById(R.id.image);
        button = (Button) findViewById(R.id.but);
        networkImageView = (NetworkImageView) findViewById(R.id.network);
//        button.setOnClickListener(new btttonListener());
        RequestQueue mQueue = Volley.newRequestQueue(this);
        //StringRequest的用法
        /*
        1. 创建一个RequestQueue对象。
        2. 创建一个StringRequest对象。
        3. 将StringRequest对象添加到RequestQueue里面。
         */
//        StringRequest stringRequest=new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String s) {
//                Log.d("TAG", s);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.e("TAG", volleyError.getMessage(), volleyError);
//            }
//        });
//        mQueue.add(stringRequest);
        //JsonRequest的用法
//        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                Log.d("TAG", jsonObject.toString());
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                Log.e("TAG", volleyError.getMessage(), volleyError);
//            }
//        });
//        mQueue.add(jsonObjectRequest);

        //ImageRequest的用法
//        ImageRequest imageRequest=new ImageRequest("http://10.14.18.209:8080/picture/b.jpg", new Response.Listener<Bitmap>() {
//            @Override
//            public void onResponse(Bitmap bitmap) {
//                imageView.setImageBitmap(bitmap);
//            }
//        }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                imageView.setImageResource(R.mipmap.icon);
//            }
//        });
//        mQueue.add(imageRequest);

        //ImageLoader的用法
        /*
        1. 创建一个RequestQueue对象。
        2. 创建一个ImageLoader对象。
        3. 获取一个ImageListener对象。
        4. 调用ImageLoader的get()方法加载网络上的图片。
         */
//        imageLoader=new ImageLoader(mQueue, new ImageLoader.ImageCache() {
//            @Override
//            public Bitmap getBitmap(String s) {
//                return null;
//            }
//
//            @Override
//            public void putBitmap(String s, Bitmap bitmap) {
//
//            }
//        });
//        listener = ImageLoader.getImageListener(imageView,
//                R.mipmap.a, R.mipmap.icon);
////        imageLoader.get("http://10.14.18.209:8080/picture/d.jpg", listener,200,200);

//        imageLoader = new ImageLoader(mQueue, new BitmapCache());

        //NetworkImageView的用法
        /*
        1. 创建一个RequestQueue对象。
        2. 创建一个ImageLoader对象。
        3. 在布局文件中添加一个NetworkImageView控件。
        4. 在代码中获取该控件的实例。
        5. 设置要加载的图片地址。
         */
//        networkImageView.setDefaultImageResId(R.mipmap.a);
//        networkImageView.setErrorImageResId(R.mipmap.icon);
//        networkImageView.setImageUrl("http://10.14.18.209:8080/picture/d.jpg",imageLoader);



        XMLRequest xmlRequest=new XMLRequest("http://10.14.25.83:8080/xml/weather.xml",
                new Response.Listener<XmlPullParser>() {
                    @Override
                    public void onResponse(XmlPullParser xmlPullParser) {
                        try {
                            int eventType=xmlPullParser.getEventType();
                            while (eventType != xmlPullParser.END_DOCUMENT) {
                                switch (eventType) {
                                    case XmlPullParser.START_TAG:
                                        String nodeName = xmlPullParser.getName();
                                        if ("city".equals(nodeName)) {
                                            String pName = xmlPullParser.getAttributeValue(0);
                                            Log.d("TAG", "pName is " + pName);
                                        }
                                        break;
                                }
                                eventType = xmlPullParser.next();
                            }
                        } catch (XmlPullParserException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TAG", volleyError.getMessage(), volleyError);
            }
        });
        mQueue.add(xmlRequest);
    }
//    public class btttonListener implements View.OnClickListener{
//        @Override
//        public void onClick(View view) {
////            imageLoader.get("http://10.14.18.209:8080/picture/a.jpg", listener,200,200);
//            networkImageView.setDefaultImageResId(R.mipmap.a);
//            networkImageView.setErrorImageResId(R.mipmap.icon);
//            networkImageView.setImageUrl("http://10.14.25.83:8080/picture/d.jpg",imageLoader);
//        }
//    }
    //充分利用ImageLoader--->自己定义
//    public class BitmapCache implements ImageLoader.ImageCache{
//    private LruCache<String, Bitmap> mCache;
//    public BitmapCache() {
//        int maxSize = 10 * 1024 * 1024;
//        mCache = new LruCache<String, Bitmap>(maxSize){
//            @Override
//            protected int sizeOf(String key, Bitmap value) {
//                return value.getRowBytes()*value.getHeight();
//            }
//        };
//    }
//    @Override
//    public Bitmap getBitmap(String s) {
//        return mCache.get(s);
//    }
//
//    @Override
//    public void putBitmap(String s, Bitmap bitmap) {
//        mCache.put(s, bitmap);
//    }
//}





}
