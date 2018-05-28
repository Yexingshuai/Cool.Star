package bean;

import java.util.List;

/**
 * Created by daixiankade on 2018/3/30.
 */

public class HomeItemBean {


    /**
     * data : {"curPage":3,"datas":[{"apkLink":"","author":"ibm","chapterId":245,"chapterName":"集合相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2675,"link":"https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/","niceDate":"2018-03-19","origin":"","projectLink":"","publishTime":1521467934000,"superChapterId":245,"superChapterName":"Java深入","tags":[],"title":"Java 8 中的 Streams API 详解","type":0,"visible":1,"zan":0},{"apkLink":"","author":"lanzry","chapterId":73,"chapterName":"面试相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2674,"link":"http://www.jianshu.com/p/5a272afb4c2e","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521383343000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"一年经验-有赞面经","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":67,"chapterName":"网络基础","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2497,"link":"http://www.jianshu.com/p/65605622234b","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380954000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"计算机网络：这是一份全面 & 详细 的TCP协议攻略","type":0,"visible":1,"zan":0},{"apkLink":"","author":"08_carmelo","chapterId":246,"chapterName":"保活","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2492,"link":"http://www.jianshu.com/p/53c4d8303e19","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380944000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"android进程保活实践","type":0,"visible":1,"zan":0},{"apkLink":"","author":"看书的小蜗牛","chapterId":171,"chapterName":"binder","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1974,"link":"http://www.jianshu.com/p/b30d5ff75176","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380822000,"superChapterId":173,"superChapterName":"framework","tags":[],"title":"从Toast显示原理初窥Android窗口管理","type":0,"visible":1,"zan":0},{"apkLink":"","author":"看书的小蜗牛","chapterId":186,"chapterName":"沉浸式","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1972,"link":"http://www.jianshu.com/p/28f1954812b3","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380813000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"全屏、沉浸式、fitSystemWindow使用及原理分析：全方位控制\u201c沉浸式\u201d的实现","type":0,"visible":1,"zan":0},{"apkLink":"","author":"看书的小蜗牛","chapterId":246,"chapterName":"保活","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1976,"link":"http://www.jianshu.com/p/9b69f3bf2893","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380802000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"Android进程保活-自\u201c裁\u201d或者耍流氓","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":98,"chapterName":"WebView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1766,"link":"http://www.jianshu.com/p/5e7075f4875f","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380786000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"Android：手把手教你构建 WebView 的缓存机制 & 资源预加载方案","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":93,"chapterName":"基础知识","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1775,"link":"http://www.jianshu.com/p/e9d8420b1b9c","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380776000,"superChapterId":99,"superChapterName":"自定义控件","tags":[],"title":"手把手教你写一个完整的自定义View","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":98,"chapterName":"WebView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1774,"link":"http://www.jianshu.com/p/3a345d27cd42","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380765000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"你不知道的 Android WebView 使用漏洞","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":98,"chapterName":"WebView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1773,"link":"http://www.jianshu.com/p/345f4d8a5cfa","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380761000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"最全面总结 Android WebView与 JS 的交互方式","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":77,"chapterName":"响应式编程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1944,"link":"http://www.jianshu.com/p/ceb48ed8719d","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380738000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"Android RxJava 背压策略：图文 + 实例 全面解析","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":99,"chapterName":"具体案例","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1655,"link":"http://blog.csdn.net/lmj623565791/article/details/78714705","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380652000,"superChapterId":99,"superChapterName":"自定义控件","tags":[],"title":"Android 仿知乎创意广告 广告还能这么玩？","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":95,"chapterName":"相机Camera","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1656,"link":"http://blog.csdn.net/lmj623565791/article/details/72170299","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380643000,"superChapterId":97,"superChapterName":"多媒体技术","tags":[],"title":"Android 仿火萤视频桌面 神奇的LiveWallPaper","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":228,"chapterName":"辅助 or 工具类","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1659,"link":"http://blog.csdn.net/lmj623565791/article/details/52506545","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380631000,"superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"Android 从StackTraceElement反观Log库","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":332,"chapterName":"嵌套滑动","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1660,"link":"http://blog.csdn.net/lmj623565791/article/details/52204039","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380617000,"superChapterId":55,"superChapterName":"5.+高新技术","tags":[],"title":"Android NestedScrolling机制完全解析 带你玩转嵌套滑动","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":86,"chapterName":"图片处理","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1670,"link":"http://blog.csdn.net/lmj623565791/article/details/49300989","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380608000,"superChapterId":86,"superChapterName":"图片加载","tags":[],"title":"Android 高清加载巨图方案 拒绝压缩图片","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":198,"chapterName":"基础概念","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1663,"link":"http://blog.csdn.net/lmj623565791/article/details/51503977","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380599000,"superChapterId":168,"superChapterName":"基础知识","tags":[],"title":"Android 探究 LayoutInflater setFactory","type":0,"visible":1,"zan":0},{"apkLink":"","author":"依然范特稀西","chapterId":99,"chapterName":"具体案例","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2672,"link":"http://www.jianshu.com/p/018935af69bd","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380284000,"superChapterId":99,"superChapterName":"自定义控件","tags":[],"title":"MPAndroidChart绘制曲线图、柱状图总结","type":0,"visible":1,"zan":0},{"apkLink":"","author":"niniloveyou","chapterId":331,"chapterName":"TextView","collect":false,"courseId":13,"desc":"自定义控件 ：drawable 跟随TextView居中 The drawable follows the text centered\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/4fc0cdb8-8254-445f-a534-13d1eb51fd18.png","fresh":false,"id":2489,"link":"http://www.wanandroid.com/blog/show/2084","niceDate":"2018-03-16","origin":"","projectLink":"https://github.com/niniloveyou/DrawableTextView","publishTime":1521132575000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=331"}],"title":"开源 Textview Drawable跟随文字居中","type":0,"visible":1,"zan":0}],"offset":40,"over":false,"pageCount":59,"size":20,"total":1179}
     * errorCode : 0
     * errorMsg :
     */

    private DataBean data;
    private int errorCode;
    private String errorMsg;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public static class DataBean {
        /**
         * curPage : 3
         * datas : [{"apkLink":"","author":"ibm","chapterId":245,"chapterName":"集合相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2675,"link":"https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/","niceDate":"2018-03-19","origin":"","projectLink":"","publishTime":1521467934000,"superChapterId":245,"superChapterName":"Java深入","tags":[],"title":"Java 8 中的 Streams API 详解","type":0,"visible":1,"zan":0},{"apkLink":"","author":"lanzry","chapterId":73,"chapterName":"面试相关","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2674,"link":"http://www.jianshu.com/p/5a272afb4c2e","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521383343000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"一年经验-有赞面经","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":67,"chapterName":"网络基础","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2497,"link":"http://www.jianshu.com/p/65605622234b","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380954000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"计算机网络：这是一份全面 & 详细 的TCP协议攻略","type":0,"visible":1,"zan":0},{"apkLink":"","author":"08_carmelo","chapterId":246,"chapterName":"保活","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2492,"link":"http://www.jianshu.com/p/53c4d8303e19","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380944000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"android进程保活实践","type":0,"visible":1,"zan":0},{"apkLink":"","author":"看书的小蜗牛","chapterId":171,"chapterName":"binder","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1974,"link":"http://www.jianshu.com/p/b30d5ff75176","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380822000,"superChapterId":173,"superChapterName":"framework","tags":[],"title":"从Toast显示原理初窥Android窗口管理","type":0,"visible":1,"zan":0},{"apkLink":"","author":"看书的小蜗牛","chapterId":186,"chapterName":"沉浸式","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1972,"link":"http://www.jianshu.com/p/28f1954812b3","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380813000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"全屏、沉浸式、fitSystemWindow使用及原理分析：全方位控制\u201c沉浸式\u201d的实现","type":0,"visible":1,"zan":0},{"apkLink":"","author":"看书的小蜗牛","chapterId":246,"chapterName":"保活","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1976,"link":"http://www.jianshu.com/p/9b69f3bf2893","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380802000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"Android进程保活-自\u201c裁\u201d或者耍流氓","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":98,"chapterName":"WebView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1766,"link":"http://www.jianshu.com/p/5e7075f4875f","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380786000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"Android：手把手教你构建 WebView 的缓存机制 & 资源预加载方案","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":93,"chapterName":"基础知识","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1775,"link":"http://www.jianshu.com/p/e9d8420b1b9c","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380776000,"superChapterId":99,"superChapterName":"自定义控件","tags":[],"title":"手把手教你写一个完整的自定义View","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":98,"chapterName":"WebView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1774,"link":"http://www.jianshu.com/p/3a345d27cd42","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380765000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"你不知道的 Android WebView 使用漏洞","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":98,"chapterName":"WebView","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1773,"link":"http://www.jianshu.com/p/345f4d8a5cfa","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380761000,"superChapterId":98,"superChapterName":"网络访问","tags":[],"title":"最全面总结 Android WebView与 JS 的交互方式","type":0,"visible":1,"zan":0},{"apkLink":"","author":"Carson_Ho","chapterId":77,"chapterName":"响应式编程","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1944,"link":"http://www.jianshu.com/p/ceb48ed8719d","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380738000,"superChapterId":195,"superChapterName":"热门专题","tags":[],"title":"Android RxJava 背压策略：图文 + 实例 全面解析","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":99,"chapterName":"具体案例","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1655,"link":"http://blog.csdn.net/lmj623565791/article/details/78714705","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380652000,"superChapterId":99,"superChapterName":"自定义控件","tags":[],"title":"Android 仿知乎创意广告 广告还能这么玩？","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":95,"chapterName":"相机Camera","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1656,"link":"http://blog.csdn.net/lmj623565791/article/details/72170299","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380643000,"superChapterId":97,"superChapterName":"多媒体技术","tags":[],"title":"Android 仿火萤视频桌面 神奇的LiveWallPaper","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":228,"chapterName":"辅助 or 工具类","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1659,"link":"http://blog.csdn.net/lmj623565791/article/details/52506545","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380631000,"superChapterId":135,"superChapterName":"项目必备","tags":[],"title":"Android 从StackTraceElement反观Log库","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":332,"chapterName":"嵌套滑动","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1660,"link":"http://blog.csdn.net/lmj623565791/article/details/52204039","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380617000,"superChapterId":55,"superChapterName":"5.+高新技术","tags":[],"title":"Android NestedScrolling机制完全解析 带你玩转嵌套滑动","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":86,"chapterName":"图片处理","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1670,"link":"http://blog.csdn.net/lmj623565791/article/details/49300989","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380608000,"superChapterId":86,"superChapterName":"图片加载","tags":[],"title":"Android 高清加载巨图方案 拒绝压缩图片","type":0,"visible":1,"zan":0},{"apkLink":"","author":"鸿洋","chapterId":198,"chapterName":"基础概念","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":1663,"link":"http://blog.csdn.net/lmj623565791/article/details/51503977","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380599000,"superChapterId":168,"superChapterName":"基础知识","tags":[],"title":"Android 探究 LayoutInflater setFactory","type":0,"visible":1,"zan":0},{"apkLink":"","author":"依然范特稀西","chapterId":99,"chapterName":"具体案例","collect":false,"courseId":13,"desc":"","envelopePic":"","fresh":false,"id":2672,"link":"http://www.jianshu.com/p/018935af69bd","niceDate":"2018-03-18","origin":"","projectLink":"","publishTime":1521380284000,"superChapterId":99,"superChapterName":"自定义控件","tags":[],"title":"MPAndroidChart绘制曲线图、柱状图总结","type":0,"visible":1,"zan":0},{"apkLink":"","author":"niniloveyou","chapterId":331,"chapterName":"TextView","collect":false,"courseId":13,"desc":"自定义控件 ：drawable 跟随TextView居中 The drawable follows the text centered\r\n","envelopePic":"http://www.wanandroid.com/blogimgs/4fc0cdb8-8254-445f-a534-13d1eb51fd18.png","fresh":false,"id":2489,"link":"http://www.wanandroid.com/blog/show/2084","niceDate":"2018-03-16","origin":"","projectLink":"https://github.com/niniloveyou/DrawableTextView","publishTime":1521132575000,"superChapterId":294,"superChapterName":"开源项目主Tab","tags":[{"name":"项目","url":"/project/list/1?cid=331"}],"title":"开源 Textview Drawable跟随文字居中","type":0,"visible":1,"zan":0}]
         * offset : 40
         * over : false
         * pageCount : 59
         * size : 20
         * total : 1179
         */

        private int curPage;
        private int offset;
        private boolean over;
        private int pageCount;
        private int size;
        private int total;
        private List<DatasBean> datas;

        public int getCurPage() {
            return curPage;
        }

        public void setCurPage(int curPage) {
            this.curPage = curPage;
        }

        public int getOffset() {
            return offset;
        }

        public void setOffset(int offset) {
            this.offset = offset;
        }

        public boolean isOver() {
            return over;
        }

        public void setOver(boolean over) {
            this.over = over;
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<DatasBean> getDatas() {
            return datas;
        }

        public void setDatas(List<DatasBean> datas) {
            this.datas = datas;
        }

        public static class DatasBean {
            /**
             * apkLink :
             * author : ibm
             * chapterId : 245
             * chapterName : 集合相关
             * collect : false
             * courseId : 13
             * desc :
             * envelopePic :
             * fresh : false
             * id : 2675
             * link : https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/
             * niceDate : 2018-03-19
             * origin :
             * projectLink :
             * publishTime : 1521467934000
             * superChapterId : 245
             * superChapterName : Java深入
             * tags : []
             * title : Java 8 中的 Streams API 详解
             * type : 0
             * visible : 1
             * zan : 0
             */

            private String apkLink;
            private String author;
            private int chapterId;
            private String chapterName;
            private boolean collect;
            private int courseId;
            private String desc;
            private String envelopePic;
            private boolean fresh;
            private int id;
            private String link;
            private String niceDate;
            private String origin;
            private String projectLink;
            private long publishTime;
            private int superChapterId;
            private String superChapterName;
            private String title;
            private int type;
            private int visible;
            private int zan;
            private List<?> tags;

            public String getApkLink() {
                return apkLink;
            }

            public void setApkLink(String apkLink) {
                this.apkLink = apkLink;
            }

            public String getAuthor() {
                return author;
            }

            public void setAuthor(String author) {
                this.author = author;
            }

            public int getChapterId() {
                return chapterId;
            }

            public void setChapterId(int chapterId) {
                this.chapterId = chapterId;
            }

            public String getChapterName() {
                return chapterName;
            }

            public void setChapterName(String chapterName) {
                this.chapterName = chapterName;
            }

            public boolean isCollect() {
                return collect;
            }

            public void setCollect(boolean collect) {
                this.collect = collect;
            }

            public int getCourseId() {
                return courseId;
            }

            public void setCourseId(int courseId) {
                this.courseId = courseId;
            }

            public String getDesc() {
                return desc;
            }

            public void setDesc(String desc) {
                this.desc = desc;
            }

            public String getEnvelopePic() {
                return envelopePic;
            }

            public void setEnvelopePic(String envelopePic) {
                this.envelopePic = envelopePic;
            }

            public boolean isFresh() {
                return fresh;
            }

            public void setFresh(boolean fresh) {
                this.fresh = fresh;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLink() {
                return link;
            }

            public void setLink(String link) {
                this.link = link;
            }

            public String getNiceDate() {
                return niceDate;
            }

            public void setNiceDate(String niceDate) {
                this.niceDate = niceDate;
            }

            public String getOrigin() {
                return origin;
            }

            public void setOrigin(String origin) {
                this.origin = origin;
            }

            public String getProjectLink() {
                return projectLink;
            }

            public void setProjectLink(String projectLink) {
                this.projectLink = projectLink;
            }

            public long getPublishTime() {
                return publishTime;
            }

            public void setPublishTime(long publishTime) {
                this.publishTime = publishTime;
            }

            public int getSuperChapterId() {
                return superChapterId;
            }

            public void setSuperChapterId(int superChapterId) {
                this.superChapterId = superChapterId;
            }

            public String getSuperChapterName() {
                return superChapterName;
            }

            public void setSuperChapterName(String superChapterName) {
                this.superChapterName = superChapterName;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public int getVisible() {
                return visible;
            }

            public void setVisible(int visible) {
                this.visible = visible;
            }

            public int getZan() {
                return zan;
            }

            public void setZan(int zan) {
                this.zan = zan;
            }

            public List<?> getTags() {
                return tags;
            }

            public void setTags(List<?> tags) {
                this.tags = tags;
            }
        }
    }
}
