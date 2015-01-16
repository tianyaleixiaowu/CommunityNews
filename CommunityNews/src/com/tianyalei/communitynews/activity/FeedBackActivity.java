package com.tianyalei.communitynews.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import com.ab.util.AbToastUtil;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.tianyalei.communitynews.R;
import com.tianyalei.communitynews.adapter.MyReplyListAdapter;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.fb.model.Conversation;
import com.umeng.fb.model.DevReply;
import com.umeng.fb.model.Reply;

import java.util.List;

/**
 * @author WangJintao E-mail:wangjintao@747.cn
 * @version 创建时间：2014-7-9 下午2:28:18
 *          意见反馈
 */
public class FeedBackActivity extends Activity {


    @ViewInject(R.id.umeng_fb_reply_list)
    private ListView replyListView;
    private MyReplyListAdapter adapter;

    private FeedbackAgent agent;
    private Conversation defaultConversation;
    private List<Reply> replies;
    @ViewInject(R.id.umeng_fb_reply_content)
    private EditText et_reply;

    @OnClick(R.id.umeng_fb_send)
    public void send(View v) {
        String con = et_reply.getText().toString().trim();
        if (TextUtils.isEmpty(con)) {
            AbToastUtil.showToast(this, "输入您的建议吧");
            return;
        }
        MobclickAgent.onEvent(this, "feedback_submit");
        et_reply.setText("");
        defaultConversation.addUserReply(con);
        replies = defaultConversation.getReplyList();
        adapter.updataData(replies);
        sync();
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_feedback);
        ViewUtils.inject(this);

        adapter = new MyReplyListAdapter(this);
        replyListView.setAdapter(adapter);

        initData();
    }

    private void initData() {

        try {
            agent = new FeedbackAgent(this);
            defaultConversation = agent.getDefaultConversation();
            sync();
            replies = defaultConversation.getReplyList();

            adapter.updataData(replies);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    void sync() {
        Conversation.SyncListener listener = new Conversation.SyncListener() {

            @Override
            public void onSendUserReply(List<Reply> replyList) {
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onReceiveDevReply(List<DevReply> replyList) {
                adapter.notifyDataSetChanged();
            }
        };
        defaultConversation.sync(listener);
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onPause(this);
    }


    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
