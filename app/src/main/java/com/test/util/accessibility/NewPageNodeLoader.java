package com.test.util.accessibility;

import android.os.SystemClock;
import android.view.accessibility.AccessibilityNodeInfo;

import com.test.util.utils.AppLog;

/**
 * 新页面节点加载器...
 * 在即将进入新页面之前调用
 */
public class NewPageNodeLoader {

    public static final int perSleepTime = 400;
    public static final int maxLoadTimes = (int) (6000d / perSleepTime);
    private AccessibilityNodeInfo firstNode;
    private AccessibilityNodeInfo tarNode;
    private String id;
    private String text;

    public AccessibilityNodeInfo loadById(String id) {
        reset();
        this.id = id;
        return load();
    }

    public AccessibilityNodeInfo loadByText(String text) {
        reset();
        this.text = text;
        return load();
    }

    public void reset() {
        id = null;
        text = null;
        firstNode = null;
        tarNode = null;
    }

    private AccessibilityNodeInfo load() {
        for (int i = 0; i < maxLoadTimes; i++) {
            if (i == 0) {
                firstNode = getNode();
            } else {
                tarNode = getNode();
            }
            AppLog.INSTANCE.d(getLoadedNodeInfo() + " costTime:" + (i * perSleepTime));
            if (tarNode != null) {
                if (tarNode != firstNode) {
                    AppLog.INSTANCE.d("找到目标节点成功：id:" + id + " text:" + text + " costTime:" + (i * perSleepTime));
                    return tarNode;
                } else {
                    AppLog.INSTANCE.e("异常firstNode和tarNode是同一个对象：");
                }
            }
            SystemClock.sleep(perSleepTime);
        }
        AppLog.INSTANCE.e("异常，没有找到目标节点：" + getLoadedNodeInfo() + " costTime:" + (maxLoadTimes * perSleepTime));
        return null;
    }

    private String getLoadedNodeInfo() {
        String firstNodeInfo = "firstNode:" + firstNode.getText() + " hashCode" + firstNode.hashCode();
        String tarNodeInfo = "tarNode:" + tarNode.getText() + " tarNode" + tarNode.hashCode();
        return firstNodeInfo + " " + tarNodeInfo;
    }

    public AccessibilityNodeInfo getNode() {
        if (id != null) {
            // TODO
            // AccessibilityUtil.getNodeById(id);
            return null;
        } else if (text != null) {
            // TODO
            // AccessibilityUtil.getNodeByText(text);
            return null;
        } else {
            AppLog.INSTANCE.e("id 和 text不能都是null");
        }
        return null;
    }
}
