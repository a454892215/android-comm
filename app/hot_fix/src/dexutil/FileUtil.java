package dexutil;
import java.io.File;
import java.util.ArrayList;
import java.util.List;


class FileUtil {

    /**
     * 获取当前目录下所有一级子文件的绝对路径列表
     */
    static List<String> getAllChildFileAbsolutePathList(String directionPath) {
        File file = new File(directionPath);
        List<String> list = new ArrayList<>();
        File[] files = file.listFiles();
        if (files == null) {
            LogUtil.e("空目录");
            return list;
        }
        for (File value : files) {
            list.add(value.getAbsolutePath());
        }
        return list;
    }

    /**
     * 删除目录 包含文件，只删除当前一级目录下的所有文件，多级会删除失败
     */
    static void deleteDir(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists()) {
            LogUtil.d("删除文件失败:" + dirPath + "不存在！");
            return;
        }
        int count = 0;
        if (file.isDirectory()) {
            List<String> allChildFileAbsolutePathList = getAllChildFileAbsolutePathList(dirPath);
            for (int i = 0; i < allChildFileAbsolutePathList.size(); i++) {
                String filePath = allChildFileAbsolutePathList.get(i);
                File childFile = new File(filePath);
                if (childFile.isFile()) {
                    boolean delete = childFile.delete();
                    LogUtil.d("===删除文件：" + filePath + "是否成功：" + delete);
                    if (delete) {
                        count++;
                    }
                }
            }
            LogUtil.d("删除目录完毕，共删除文件:" + count + "个");
        } else {
            LogUtil.e("不是目录");
        }
    }


    /**
     * 创建目录
     */
    static void createDir(File dir) {
        if (!dir.exists()) {
            LogUtil.d("======createDir:" + dir.mkdirs());
        }
    }

}
