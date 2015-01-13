package com.tianyalei.communitynews.adapter;

import android.content.Context;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tianyalei.communitynews.R;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MyReplyListAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private List<Reply> replyList = new ArrayList<Reply>();

    public MyReplyListAdapter(Context mContext) {
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    public void updataData(List<Reply> replies) {
        if (replies != null) {
            replyList.clear();
            this.replyList.addAll(replies);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return replyList == null ? 0 : replyList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return replyList == null ? null : replyList.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder holder;
        if (arg1 == null) {
            arg1 = mInflater.inflate(R.layout.umeng_fb_list_item, null);
            holder = new ViewHolder();
            holder.replyDate = (TextView) arg1
                    .findViewById(R.id.umeng_fb_reply_date);
            holder.replyContent = (TextView) arg1
                    .findViewById(R.id.umeng_fb_reply_content);
            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        Reply reply = replyList.get(arg0);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        if (reply instanceof DevReply) {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            holder.replyContent.setLayoutParams(layoutParams);
            holder.replyContent
                    .setBackgroundResource(R.drawable.umeng_fb_reply_left_bg);

        } else {
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.replyContent.setLayoutParams(layoutParams);
            holder.replyContent
                    .setBackgroundResource(R.drawable.umeng_fb_reply_right_bg);

        }
        holder.replyDate.setText(SimpleDateFormat.getDateInstance().format(
                reply.getDatetime()));
        holder.replyContent.setText(reply.getContent());
        CharSequence text = holder.replyContent.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) text;
            URLSpan urls[] = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();

            for (URLSpan urlSpan : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(urlSpan.getURL());
                style.setSpan(myURLSpan, sp.getSpanStart(urlSpan),
                        sp.getSpanEnd(urlSpan),
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

            }
            holder.replyContent.setText(style);
        }

        return arg1;
    }

    class ViewHolder {
        TextView replyDate;
        TextView replyContent;

    }

    private class MyURLSpan extends ClickableSpan {

        private String url;

        public MyURLSpan(String url) {
            this.url = url;
        }

        @Override
        public void onClick(View arg0) {
            /*Intent intent = new Intent(mContext, WebActivity.class);
			Bundle bundle = new Bundle();
			bundle.putString("url", url);
			intent.putExtras(bundle);
			mContext.startActivity(intent);*/
        }

    }

}
