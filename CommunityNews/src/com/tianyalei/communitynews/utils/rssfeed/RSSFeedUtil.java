package com.tianyalei.communitynews.utils.rssfeed;

import com.ab.http.wolf.GetDataCallBack;

/**
 * Created by 毅涵 on 15/1/19.
 */
public class RSSFeedUtil {
    private String URL = "http://news.163.com/special/00011K6L/rss_newsspecial.xml";
    private String content;

    public RSSFeedUtil() {

    }

    public static void getRSSFeed(GetDataCallBack callBack) {
        RSSHttpUtil.sendRequest(100, null, callBack);
    }
}
