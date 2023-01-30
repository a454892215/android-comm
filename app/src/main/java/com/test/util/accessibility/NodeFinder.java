package com.test.util.accessibility;

import android.os.SystemClock;
import android.view.accessibility.AccessibilityNodeInfo;

import com.test.util.utils.AppLog;

/**
 * 新页面节点加载器...
 * 在即将进入新页面之前调用
 */
public class NodeFinder {

    public static final int perSleepTime = 400;
    public static final int maxLoadTimes = (int) (6000d / perSleepTime);
    private AccessibilityNodeInfo tarNode;
    private String id;
    private String text;
    private int lastPageWindId;
    private boolean isWindIdChange = true; // 目标节点的windID 所在页面是否会改变当前的WinId

    public AccessibilityNodeInfo loadById(String id, boolean isWindIdChange, int lastPageWindId) {
        reset();
        this.id = id;
        this.isWindIdChange = isWindIdChange;
        this.lastPageWindId = lastPageWindId;
        return load();
    }

    public AccessibilityNodeInfo loadByText(String text, boolean isWindIdChange, int lastPageWindId) {
        reset();
        this.text = text;
        this.isWindIdChange = isWindIdChange;
        this.lastPageWindId = lastPageWindId;
        return load();
    }

    public void reset() {
        id = null;
        text = null;
        tarNode = null;
        lastPageWindId = 0;
    }

    private AccessibilityNodeInfo load() {
        for (int i = 0; i < maxLoadTimes; i++) {
            if (isWindIdChange) {
                if (getWinId() != lastPageWindId) {
                    tarNode = getNode();
                }
            } else {
                tarNode = getNode();
            }
            if (tarNode != null) {
                AppLog.INSTANCE.e("找到目标节点：" + getLoadedNodeInfo() + " costTime:" + (maxLoadTimes * perSleepTime));
                return tarNode;
            }
            SystemClock.sleep(perSleepTime);
        }
        AppLog.INSTANCE.e("异常，没有找到目标节点：" + getLoadedNodeInfo() + " costTime:" + (maxLoadTimes * perSleepTime));
        return null;
    }

    private String getLoadedNodeInfo() {
        return " tarNode" + tarNode.hashCode() + " lastPageWindId:" + lastPageWindId;
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

    public int getWinId() {
        // TODO
        return 0;
    }
}
