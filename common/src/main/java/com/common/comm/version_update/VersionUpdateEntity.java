package com.common.comm.version_update;

/**
 * Author: L
 * CreateDate: 2018/9/1 8:26
 * Description: No
 */
@SuppressWarnings("unused")
public class VersionUpdateEntity {
    /**
     * msg : success
     * code : 0
     * data : {"flag":"1","time":1535706107986,"version":"1.0.0","content":"初始化版本上线","url":"http://www.baidu.com"}
     * timestamp : 1535761486454
     */

    private String msg;
    private int code;
    private DataBean data;
    private long timestamp;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class DataBean {
        private String flag;
        private long time;
        private String version;
        private String content;
        private String url;
        private String size;
        private String md5;

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        String getMd5() {
            return md5;
        }

        public void setMd5(String md5) {
            this.md5 = md5;
        }


        String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
