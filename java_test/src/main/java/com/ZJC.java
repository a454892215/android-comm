package com;

import com.test.java.util.LogUtil;

import java.util.HashSet;

public class ZJC {

    public static void main(String[] args) {
        System.out.println("单词数组是否不重复：" + areAllWordsUnique(words) + " size:" + words.length);
        System.out.println("汉字大全是否不重复：" + areAllCharactersUnique(TEXT) + " size:" + TEXT.length());
        /// 建议输入10 - 258（作为初次乱序加密处理）。
        /// 最终生成的汉字，再使用个人记在心里的数字序列，做最终乱序加密处理。
        words = reverseGroups(words, 257);
        for (int i = 0; i < words.length; i += 8) {
            try {
                String word = words[i];
                String word1 = words[i + 1];
                String word2 = words[i + 2];
                String word3 = words[i + 3];
                String word4 = words[i + 4];
                String word5 = words[i + 5];
                String word6 = words[i + 6];
                String word7 = words[i + 7];
                System.out.println(format(i + ":" + word + " " + getChineseCharacterForNumber(i))
                        + format("  " + (i + 1) + ":" + word1 + " " + getChineseCharacterForNumber(i + 1))
                        + format("  " + (i + 2) + ":" + word2 + " " + getChineseCharacterForNumber(i + 2))
                        + format("  " + (i + 3) + ":" + word3 + " " + getChineseCharacterForNumber(i + 3))
                        + format("  " + (i + 4) + ":" + word4 + " " + getChineseCharacterForNumber(i + 4))
                        + format("  " + (i + 5) + ":" + word5 + " " + getChineseCharacterForNumber(i + 5))
                        + format("  " + (i + 6) + ":" + word6 + " " + getChineseCharacterForNumber(i + 6))
                        + format("  " + (i + 7) + ":" + word7 + " " + getChineseCharacterForNumber(i + 7))
                );
            } catch (Exception e) {
                LogUtil.e(e.toString());
                break;
            }
        }
    }

    public static boolean areAllCharactersUnique(String text) {
        HashSet<Character> uniqueCharacters = new HashSet<>();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            // 如果添加失败，表示该汉字已经存在于集合中
            if (!uniqueCharacters.add(ch)) {
                LogUtil.d("重复汉字：" + ch);
                return false;
            }
        }
        return true;
    }

    public static boolean areAllWordsUnique(String[] words) {
        HashSet<String> uniqueWords = new HashSet<>();
        for (String word : words) {
            if (!uniqueWords.add(word)) {
                return false;
            }
        }
        return true;
    }

    private static String getChineseCharacterForNumber(int number) {
        return TEXT.substring(number, number + 1);
    }

    /**
     * 将输入字符串格式化为宽度为20的字符串，不足部分用空格填充
     *
     * @param text 要格式化的字符串
     * @return 格式化后的字符串
     */
    public static String format(String text) {
        int width = 18;
        // 使用 String.format 方法进行格式化
        return String.format("%-" + width + "s", text);
    }

    // 把一个String数组内的所有元素，划分为N个组， 每组元素都逆序重排
    public static String[] reverseGroups(String[] array, int numberOfGroups) {
        int length = array.length;
        int groupSize = (int) Math.ceil((double) length / numberOfGroups);

        String[] result = new String[length];
        int index = 0;

        for (int i = 0; i < numberOfGroups; i++) {
            int start = i * groupSize;
            int end = Math.min(start + groupSize, length);

            reverseSubarray(array, start, end - 1);

            for (int j = start; j < end; j++) {
                result[index++] = array[j];
            }
        }

        return result;
    }

    private static void reverseSubarray(String[] array, int start, int end) {
        while (start < end) {
            String temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            start++;
            end--;
        }
    }

    private static final String TEXT = "阿啊哀唉挨矮爱碍安岸按案暗昂袄傲奥八巴扒吧疤拔把坝爸罢霸白百柏摆" +
            "胀障招找召兆赵照罩遮占战站张章涨掌丈仗帐诊枕阵振镇震争征挣睁折哲者这浙贞针侦珍真汁芝枝知织肢脂蜘执侄筝蒸整正证郑政症之支指至志制帜治质秩致智直值职植殖止只旨址纸舟周洲粥" +
            "昌长肠尝偿常厂场敞畅倡唱抄钞超朝潮吵炒车扯彻撤尘臣沉辰陈晨闯衬称趁撑成呈承池匙尺齿耻斥赤翅充冲诚城乘惩程秤吃驰迟持臭出初除厨锄础储楚处虫崇抽仇绸愁稠筹酬丑" +
            "触畜川穿传船喘串疮窗床创吹炊垂锤春纯唇蠢聪丛凑粗促醋窜催摧脆词慈辞磁此次刺从匆葱大呆代带待怠贷袋逮戴翠村存寸错曾搭达答打蛋当挡党荡档刀叨导岛丹单担耽胆旦但诞弹淡" +
            "倒蹈到悼盗道稻得德的灯登等凳低堤滴敌笛底抵地弟帝递第颠典点电店垫殿叼雕吊钓调掉爹跌叠蝶丁叮盯钉顶订定丢东冬董懂动冻栋洞都斗抖陡豆逗督毒读独堵赌杜肚度渡端短段断缎" +
            "朵躲惰鹅蛾额恶饿恩儿而耳二发乏伐罚阀法帆番翻凡烦繁反返犯泛饭范贩方坊芳防妨房仿访纺放飞非肥匪废沸肺费分吩纷芬坟粉份奋愤粪丰风封疯峰" +
            "败拜班般斑搬板版办半伴扮拌帮绑榜膀傍棒包胞宝饱保堡报抱暴爆杯悲碑北贝备背倍被辈奔本笨蹦逼鼻比彼笔鄙币必毕闭碧蔽壁避臂边编扁便变遍辨辩辫标" +
            "物误悟沃卧握乌污呜屋无吴五稀溪锡熄膝习席袭洗喜雾夕西吸希析息牺悉惜吓夏厦仙先纤掀鲜闲弦戏系细隙虾瞎峡狭霞下宪陷馅羡献乡相香箱详贤咸衔显险县现线限削消销小晓孝效校" +
            "笑祥享响想向巷项象像橡泻卸屑械谢心辛欣新薪些歇协邪胁斜鞋写泄姓幸性凶兄胸雄熊休修信兴星腥刑行形型醒杏许序叙绪续絮蓄宣悬旋羞朽秀绣袖锈须虚需徐训讯迅压呀押鸦鸭牙芽选" +
            "表别宾滨冰兵丙柄饼并病拨波玻剥脖播伯驳泊博卜补捕不布步怖部擦猜才材财采彩睬踩菜参餐残蚕惭惨灿仓苍舱藏操槽草册侧厕测策层叉插查茶察岔差拆柴馋缠产铲" +
            "练炼恋链良凉临淋伶灵岭铃陵零龄领僚了料列劣烈猎裂邻林笼聋隆垄拢楼搂漏露芦令另溜刘流留榴柳六龙旅屡律虑率绿卵乱掠略炉虏鲁陆录鹿碌路驴落妈麻马码蚂骂吗埋买轮论" +
            "罗萝锣箩骡螺络骆忙芒盲茫猫毛矛茅茂冒迈麦卖脉蛮馒瞒满慢漫美妹门闷们萌盟猛蒙孟贸帽貌么没眉梅煤霉每棉免勉面苗描秒妙庙灭梦迷谜米眯秘密蜜眠绵摩磨魔抹末沫莫漠墨默" +
            "锋蜂逢缝讽凤奉佛否夫肤伏扶服俘浮符幅福抚府斧俯辅腐父付妇负附咐复赴副傅富腹覆该改盖溉概干甘纲缸钢港杠高膏糕搞稿杆肝竿秆赶敢感冈刚岗葛隔个各给根跟更耕" +
            "工告哥胳鸽割搁歌阁革格弓公功攻供宫恭躬巩共贡勾沟钩狗构购够估姑孤辜古谷股骨鼓固故顾瓜刮挂乖拐怪关观官冠馆管贯惯灌罐光广归龟规轨鬼柜贵桂跪滚棍锅国果裹过哈孩" +
            "授瘦耍衰摔甩帅拴双霜爽谁鼠属薯术束述树竖数刷斯撕死四寺似饲肆松宋水税睡顺说丝司私思速宿塑酸蒜算虽随岁碎诵送颂搜艘苏俗诉肃素它塌塔踏台抬太态泰贪孙损笋缩所索锁她他" +
            "摊滩坛谈痰毯叹炭探锻堆队对吨蹲盾顿多夺涛掏滔逃桃陶淘萄讨套汤唐堂塘糖倘躺烫趟惕替天添田甜填挑条跳特疼腾梯踢提题蹄体剃通同桐铜童统桶筒痛偷贴铁帖厅听亭庭停挺艇土吐兔团" +
            "推腿退吞屯托头投透秃突图徒涂途屠外弯湾丸完玩顽挽晚碗拖脱驼妥娃挖蛙瓦袜歪危威微为围违唯维伟伪万汪亡王网往妄忘旺望温文纹闻蚊稳问翁窝我尾委卫未位味畏胃喂慰午伍武侮舞勿务" +
            "海害含寒喊汉汗旱航毫豪好号浩贺黑痕很狠恨恒横衡轰耗喝禾合何和河核荷盒哄烘红宏洪虹喉猴吼后厚候乎呼忽狐胡壶湖糊化划画话怀槐坏欢还环蝴虎互户护花华哗滑猾晃" +
            "灰恢挥辉回悔汇会缓幻唤换患荒慌皇黄煌活火伙或货获祸惑击饥绘贿惠毁慧昏婚浑魂混吉级即极急疾集籍几己圾机肌鸡迹积基绩激及既济继寄加夹佳家嘉甲挤脊计记纪忌技" +
            "际剂季间肩艰兼监煎拣俭茧捡价驾架假嫁稼奸尖坚歼健舰渐践鉴键箭江姜将减剪检简见件建剑荐贱郊娇浇骄胶椒焦蕉角狡浆僵疆讲奖桨匠降酱交皆接揭街节劫杰洁结捷绞饺脚搅缴" +
            "叫轿较教阶今斤金津筋仅紧谨锦尽截竭姐解介戒届界借巾晶睛精井颈景警净径竞劲近进晋浸禁京经茎惊酒旧救就舅居拘鞠局菊竟敬境静镜纠究揪九久橘举矩句巨拒具俱剧惧据距锯" +
            "聚捐卷倦绢决绝觉掘嚼军君均菌俊卡开凯慨刊堪砍看康糠扛抗炕考烤靠科棵颗壳咳可渴克刻客课肯垦恳坑空孔恐控口扣寇枯哭苦库裤酷夸垮挎跨块快宽款筐狂况旷矿框亏葵愧昆捆困扩括" +
            "阔垃拉啦喇腊懒烂滥郎狼廊朗浪捞劳蜡辣来赖兰拦栏蓝篮览累冷厘梨狸离犁鹂璃黎牢老姥涝乐勒雷垒泪类励例隶栗粒俩连帘怜莲礼李里理力历厉立丽利梁粮粱两亮谅辆量辽疗联廉镰脸" +
            "民敏名明鸣命摸模膜暮拿哪内那纳乃奶耐男谋某母亩木目牧墓幕慕尼泥你逆年念娘酿鸟尿南难挠恼脑闹呢嫩能奴努怒女暖挪欧偶辟趴捏您宁凝牛扭纽农浓弄乓旁胖抛炮袍跑泡陪培" +
            "爬怕拍牌派攀盘判叛盼碰批披劈皮疲脾匹僻片赔佩配喷盆朋棚蓬膨捧乒平评凭苹瓶萍坡泼婆偏篇骗漂飘票撇拼贫品谱七妻戚期欺漆齐其奇迫破魄剖仆扑铺葡朴普汽砌器恰洽千迁牵铅谦骑" +
            "棋旗乞企岂启起气弃腔强墙抢悄敲乔侨桥签前钱钳潜浅欠歉枪勤青轻倾清蜻情晴顷瞧巧切茄且窃亲侵芹琴屈趋渠取去趣圈全权泉请庆穷丘秋求球区曲驱群然燃染让饶扰绕拳犬" +
            "劝券缺却雀确鹊裙日绒荣容熔融柔揉肉如惹热人仁忍刃认任扔仍乳辱入软锐瑞润若弱撒洒塞赛三伞散桑嗓丧扫删衫闪陕扇善伤商裳晌嫂色森杀沙纱傻筛晒山哨舌蛇舍设社射涉摄申赏上尚捎梢" +
            "烧稍勺少绍慎升生声牲胜绳省圣盛伸身深神沈审婶肾甚渗石时识实拾蚀食史使始剩尸失师诗施狮湿十什视试饰室是柿适逝释誓驶士氏世市示式事侍势书叔殊梳疏舒输蔬熟暑收手守首寿受兽售" +
            "穴学雪血寻巡旬询循岩沿炎研盐颜掩眼演崖哑雅亚咽烟淹延严言扬羊阳杨洋仰养氧痒样厌宴艳验焰雁燕央殃秧耀爷也冶野业叶页夜液妖腰邀窑谣摇遥咬药要乙已以蚁倚椅义亿忆艺一衣医" +
            "依仪宜姨移遗疑毅翼因阴姻音银引饮隐议亦异役译易疫益谊意影映硬佣拥庸永咏泳勇印应英樱鹰迎盈营蝇赢游友有又右幼诱于予余涌用优忧悠尤由犹邮油雨语玉育郁狱浴预域欲鱼娱渔愉愚榆与" +
            "宇屿羽圆援缘源远怨院愿约月御裕遇愈誉冤元员园原晕韵杂灾栽宰载再在咱钥悦阅跃越云匀允孕运皂造燥躁则择泽责贼怎暂赞脏葬遭糟早枣澡灶宅窄债寨沾粘斩展盏崭增赠渣扎轧闸眨炸榨摘" +
            "宙昼皱骤朱株置中忠终钟肿种众重州住助注驻柱祝著筑铸抓珠诸猪蛛竹烛逐主煮嘱追准捉桌浊啄着仔姿资爪专砖转赚庄装壮状撞走奏租足族阻组祖钻嘴滋子紫字自宗棕踪总纵最罪醉尊遵昨左作" +
            "坐座做";

    private static String[] words = {
            "abandon", "ability", "able", "about", "above", "absent", "absorb", "abstract", "absurd", "abuse",
            "access", "accident", "account", "accuse", "achieve", "acid", "acoustic", "acquire", "across", "act",
            "alien", "all", "alley", "allow", "almost", "alone", "alpha", "already", "also", "alter",
            "always", "amateur", "amazing", "among", "amount", "amused", "analyst", "anchor", "ancient", "anger",
            "action", "actor", "actress", "actual", "adapt", "add", "addict", "address", "adjust", "admit",
            "adult", "advance", "advice", "aerobic", "affair", "afford", "afraid", "again", "age", "agent",
            "agree", "ahead", "aim", "air", "airport", "aisle", "alarm", "album", "alcohol", "alert",
            "angle", "angry", "animal", "ankle", "announce", "annual", "another", "answer", "antenna", "antique",
            "anxiety", "any", "apart", "apology", "appear", "apple", "approve", "april", "arch", "arctic",
            "area", "arena", "argue", "arm", "armed", "armor", "army", "around", "arrange", "arrest",
            "arrive", "arrow", "art", "artefact", "artist", "artwork", "ask", "aspect", "assault", "asset",
            "assist", "assume", "asthma", "athlete", "atom", "attack", "attend", "attitude", "attract", "auction",
            "audit", "august", "aunt", "author", "auto", "autumn", "average", "avocado", "avoid", "awake",
            "aware", "away", "awesome", "awful", "awkward", "axis", "baby", "bachelor", "bacon", "badge",
            "bag", "balance", "balcony", "ball", "bamboo", "banana", "banner", "bar", "barely", "bargain",
            "barrel", "base", "basic", "basket", "battle", "beach", "bean", "beauty", "because", "become",
            "beef", "before", "begin", "behave", "behind", "believe", "below", "belt", "bench", "benefit",
            "best", "betray", "better", "between", "beyond", "bicycle", "bid", "bike", "bind", "biology",
            "bird", "birth", "bitter", "black", "blade", "blame", "blanket", "blast", "bleak", "bless",
            "blind", "blood", "blossom", "blouse", "blue", "blur", "blush", "board", "boat", "body",
            "boil", "bomb", "bone", "bonus", "book", "boost", "border", "boring", "borrow", "boss",
            "bottom", "bounce", "box", "boy", "bracket", "brain", "brand", "brass", "brave", "bread",
            "breeze", "brick", "bridge", "brief", "bright", "bring", "brisk", "broccoli", "broken", "bronze",
            "broom", "brother", "brown", "brush", "bubble", "buddy", "budget", "buffalo", "build", "bulb",
            "bulk", "bullet", "bundle", "bunker", "burden", "burger", "burst", "bus", "business", "busy",
            "butter", "buyer", "buzz", "cabbage", "cabin", "cable", "cactus", "cage", "cake", "call",
            "calm", "camera", "camp", "can", "canal", "cancel", "candy", "cannon", "canoe", "canvas",
            "canyon", "capable", "capital", "captain", "car", "carbon", "card", "cargo", "carpet", "carry",
            "cart", "case", "cash", "casino", "castle", "casual", "cat", "catalog", "catch", "category",
            "cattle", "caught", "cause", "caution", "cave", "ceiling", "celery", "cement", "census", "century",
            "cereal", "certain", "chair", "chalk", "champion", "change", "chaos", "chapter", "charge", "chase",
            "chat", "cheap", "check", "cheese", "chef", "cherry", "chest", "chicken", "chief", "child",
            "chimney", "choice", "choose", "chronic", "chuckle", "chunk", "churn", "cigar", "cinnamon", "circle",
            "citizen", "city", "civil", "claim", "clap", "clarify", "claw", "clay", "clean", "clerk",
            "clever", "click", "client", "cliff", "climb", "clinic", "clip", "clock", "clog", "close",
            "cloth", "cloud", "clown", "club", "clump", "cluster", "clutch", "coach", "coast", "coconut",
            "code", "coffee", "coil", "coin", "collect", "color", "column", "combine", "come", "comfort",
            "comic", "common", "company", "concert", "conduct", "confirm", "congress", "connect", "consider", "control",
            "convince", "cook", "cool", "copper", "copy", "coral", "core", "corn", "correct", "cost",
            "cotton", "couch", "country", "couple", "course", "cousin", "cover", "coyote", "crack", "cradle",
            "craft", "cram", "crane", "crash", "crater", "crawl", "crazy", "cream", "credit", "creek",
            "crew", "cricket", "crime", "crisp", "critic", "crop", "cross", "crouch", "crowd", "crucial",
            "cruel", "cruise", "crumble", "crunch", "crush", "cry", "crystal", "cube", "culture", "cup",
            "cupboard", "curious", "current", "curtain", "curve", "cushion", "custom", "cute", "cycle", "dad",
            "damage", "damp", "dance", "danger", "daring", "dash", "daughter", "dawn", "day", "deal",
            "debate", "debris", "decade", "december", "decide", "decline", "decorate", "decrease", "deer", "defense",
            "define", "defy", "degree", "delay", "deliver", "demand", "demise", "denial", "dentist", "deny",
            "depart", "depend", "deposit", "depth", "deputy", "derive", "describe", "desert", "design", "desk",
            "despair", "destroy", "detail", "detect", "develop", "device", "devote", "diagram", "dial", "diamond",
            "diary", "dice", "diesel", "diet", "differ", "digital", "dignity", "dilemma", "dinner", "dinosaur",
            "direct", "dirt", "disagree", "discover", "disease", "dish", "dismiss", "disorder", "display", "distance",
            "divert", "divide", "divorce", "dizzy", "doctor", "document", "dog", "doll", "dolphin", "domain",
            "donate", "donkey", "donor", "door", "dose", "double", "dove", "draft", "dragon", "drama",
            "drastic", "draw", "dream", "dress", "drift", "drill", "drink", "drip", "drive", "drop",
            "drum", "dry", "duck", "dumb", "dune", "during", "dust", "dutch", "duty", "dwarf",
            "dynamic", "eager", "eagle", "early", "earn", "earth", "easily", "east", "easy", "echo",
            "ecology", "economy", "edge", "edit", "educate", "effort", "egg", "eight", "either", "elbow",
            "elder", "electric", "elegant", "element", "elephant", "elevator", "elite", "else", "embark", "embody",
            "embrace", "emerge", "emotion", "employ", "empower", "empty", "enable", "enact", "end", "endless",
            "endorse", "enemy", "energy", "enforce", "engage", "engine", "enhance", "enjoy", "enlist", "enough",
            "enrich", "enroll", "ensure", "enter", "entire", "entry", "envelope", "episode", "equal", "equip",
            "era", "erase", "erode", "erosion", "error", "erupt", "escape", "essay", "essence", "estate",
            "eternal", "ethics", "evidence", "evil", "evoke", "evolve", "exact", "example", "exceed", "exchange",
            "excite", "exclude", "excuse", "execute", "exercise", "exhaust", "exhibit", "exile", "exist", "exit",
            "exotic", "expand", "expect", "expire", "explain", "expose", "express", "extend", "extra", "eye",
            "eyebrow", "fabric", "face", "faculty", "fade", "faint", "faith", "fall", "false", "fame",
            "family", "famous", "fan", "fancy", "fantasy", "farm", "fashion", "fat", "fatal", "father",
            "fatigue", "fault", "favorite", "feature", "february", "federal", "fee", "feed", "feel", "female",
            "fence", "festival", "fetch", "fever", "few", "fiber", "fiction", "field", "figure", "file",
            "film", "filter", "final", "find", "fine", "finger", "finish", "fire", "firm", "first",
            "fiscal", "fish", "fit", "fitness", "fix", "flag", "flame", "flash", "flat", "flavor",
            "flee", "flight", "flip", "float", "flock", "floor", "flower", "fluid", "flush", "fly",
            "foam", "focus", "fog", "foil", "fold", "follow", "food", "foot", "force", "forest",
            "gallery", "game", "gap", "garage", "garbage", "garden", "garlic", "garment", "gas", "gasp",
            "gate", "gather", "gauge", "gaze", "general", "genius", "genre", "gentle", "genuine", "gesture",
            "forget", "fork", "fortune", "forum", "forward", "fossil", "foster", "found", "fox", "fragile",
            "frame", "frequent", "fresh", "friend", "fringe", "frog", "front", "frost", "frown", "frozen",
            "fruit", "fuel", "fun", "funny", "furnace", "fury", "future", "gadget", "gain", "galaxy",
            "ghost", "giant", "gift", "giggle", "ginger", "giraffe", "girl", "give", "glad", "glance",
            "glare", "glass", "glide", "glimpse", "globe", "gloom", "glory", "glove", "glow", "glue",
            "goat", "goddess", "gold", "good", "goose", "gorilla", "gospel", "gossip", "govern", "gown",
            "grab", "grace", "grain", "grant", "grape", "grass", "gravity", "great", "green", "grid",
            "grief", "grit", "grocery", "group", "grow", "grunt", "guard", "guess", "guide", "guilt",
            "guitar", "gun", "gym", "habit", "hair", "half", "hammer", "hamster", "hand", "happy",
            "harbor", "hard", "harsh", "harvest", "hat", "have", "hawk", "hazard", "head", "health",
            "heart", "heavy", "hedgehog", "height", "hello", "helmet", "help", "hen", "hero", "hidden",
            "high", "hill", "hint", "hip", "hire", "history", "hobby", "hockey", "hold", "hole",
            "holiday", "hollow", "home", "honey", "hood", "hope", "horn", "horror", "horse", "hospital",
            "host", "hotel", "hour", "hover", "hub", "huge", "human", "humble", "humor", "hundred",
            "hungry", "hunt", "hurdle", "hurry", "hurt", "husband", "hybrid", "ice", "icon", "idea",
            "identify", "idle", "ignore", "ill", "illegal", "illness", "image", "imitate", "immense", "immune",
            "impact", "impose", "improve", "impulse", "inch", "include", "income", "increase", "index", "indicate",
            "indoor", "industry", "infant", "inflict", "inform", "inhale", "inherit", "initial", "inject", "injury",
            "inmate", "inner", "innocent", "input", "inquiry", "insane", "insect", "inside", "inspire", "install",
            "intact", "interest", "into", "invest", "invite", "involve", "iron", "island", "isolate", "issue",
            "item", "ivory", "jacket", "jaguar", "jar", "jazz", "jealous", "jeans", "jelly", "jewel",
            "job", "join", "joke", "journey", "joy", "judge", "juice", "jump", "jungle", "junior",
            "junk", "just", "kangaroo", "keen", "keep", "ketchup", "key", "kick", "kid", "kidney",
            "kind", "kingdom", "kiss", "kit", "kitchen", "kite", "kitten", "kiwi", "knee", "knife",
            "knock", "know", "lab", "label", "labor", "ladder", "lady", "lake", "lamp", "language",
            "laptop", "large", "later", "latin", "laugh", "laundry", "lava", "law", "lawn", "lawsuit",
            "layer", "lazy", "leader", "leaf", "learn", "leave", "lecture", "left", "leg", "legal",
            "legend", "leisure", "lemon", "lend", "length", "lens", "leopard", "lesson", "letter", "level",
            "liar", "liberty", "library", "license", "life", "lift", "light", "like", "limb", "limit",
            "link", "lion", "liquid", "list", "little", "live", "lizard", "load", "loan", "lobster",
            "local", "lock", "logic", "lonely", "long", "loop", "lottery", "loud", "lounge", "love",
            "loyal", "lucky", "luggage", "lumber", "lunar", "lunch", "luxury", "lyrics", "machine", "mad",
            "magic", "magnet", "maid", "mail", "main", "major", "make", "mammal", "man", "manage",
            "mandate", "mango", "mansion", "manual", "maple", "marble", "march", "margin", "marine", "market",
            "marriage", "mask", "mass", "master", "match", "material", "math", "matrix", "matter", "maximum",
            "maze", "meadow", "mean", "measure", "meat", "mechanic", "medal", "media", "melody", "melt",
            "member", "memory", "mention", "menu", "mercy", "merge", "merit", "merry", "mesh", "message",
            "metal", "method", "middle", "midnight", "milk", "million", "mimic", "mind", "minimum", "minor",
            "minute", "miracle", "mirror", "misery", "miss", "mistake", "mix", "mixed", "mixture", "mobile",
            "model", "modify", "mom", "moment", "monitor", "monkey", "monster", "month", "moon", "moral",
            "more", "morning", "mosquito", "mother", "motion", "motor", "mountain", "mouse", "move", "movie",
            "much", "muffin", "mule", "multiply", "muscle", "museum", "mushroom", "music", "must", "mutual",
            "myself", "mystery", "myth", "naive", "name", "napkin", "narrow", "nasty", "nation", "nature",
            "near", "neck", "need", "negative", "neglect", "neither", "nephew", "nerve", "nest", "net",
            "network", "neutral", "never", "news", "next", "nice", "night", "noble", "noise", "nominee",
            "noodle", "normal", "north", "nose", "notable", "note", "nothing", "notice", "novel", "now",
            "nuclear", "number", "nurse", "nut", "oak", "obey", "object", "oblige", "obscure", "observe",
            "obtain", "obvious", "occur", "ocean", "october", "odor", "off", "offer", "office", "often",
            "oil", "okay", "old", "olive", "olympic", "omit", "once", "one", "onion", "online",
            "only", "open", "opera", "opinion", "oppose", "option", "orange", "orbit", "orchard", "order",
            "ordinary", "organ", "orient", "original", "orphan", "ostrich", "other", "outdoor", "outer", "output",
            "outside", "oval", "oven", "over", "own", "owner", "oxygen", "oyster", "ozone", "pact",
            "paddle", "page", "pair", "palace", "palm", "panda", "panel", "panic", "panther", "paper",
            "parade", "parent", "park", "parrot", "party", "pass", "patch", "path", "patient", "patrol",
            "pattern", "pause", "pave", "payment", "peace", "peanut", "pear", "peasant", "pelican", "pen",
            "penalty", "pencil", "people", "pepper", "perfect", "permit", "person", "pet", "phone", "photo",
            "phrase", "physical", "piano", "picnic", "picture", "piece", "pig", "pigeon", "pill", "pilot",
            "pink", "pioneer", "pipe", "pistol", "pitch", "pizza", "place", "planet", "plastic", "plate",
            "play", "please", "pledge", "pluck", "plug", "plunge", "poem", "poet", "point", "polar",
            "pole", "police", "pond", "pony", "pool", "popular", "portion", "position", "possible", "post",
            "potato", "pottery", "poverty", "powder", "power", "practice", "praise", "predict", "prefer", "prepare",
            "present", "pretty", "prevent", "price", "pride", "primary", "print", "priority", "prison", "private",
            "prize", "problem", "process", "produce", "profit", "program", "project", "promote", "proof", "property",
            "prosper", "protect", "proud", "provide", "public", "pudding", "pull", "pulp", "pulse", "pumpkin",
            "punch", "pupil", "puppy", "purchase", "purity", "purpose", "purse", "push", "put", "puzzle",
            "pyramid", "quality", "quantum", "quarter", "question", "quick", "quit", "quiz", "quote", "rabbit",
            "raccoon", "race", "rack", "radar", "radio", "rail", "rain", "raise", "rally", "ramp",
            "ranch", "random", "range", "rapid", "rare", "rate", "rather", "raven", "raw", "razor",
            "ready", "real", "reason", "rebel", "rebuild", "recall", "receive", "recipe", "record", "recycle",
            "reduce", "reflect", "reform", "refuse", "region", "regret", "regular", "reject", "relax", "release",
            "relief", "rely", "remain", "remember", "remind", "remove", "render", "renew", "rent", "reopen",
            "repair", "repeat", "replace", "report", "require", "rescue", "resemble", "resist", "resource", "response",
            "result", "retire", "retreat", "return", "reunion", "reveal", "review", "reward", "rhythm", "rib",
            "ribbon", "rice", "rich", "ride", "ridge", "rifle", "right", "rigid", "ring", "riot",
            "satisfy", "satoshi", "sauce", "sausage", "save", "say", "scale", "scan", "scare", "scatter",
            "scene", "scheme", "school", "science", "scissors", "scorpion", "scout", "scrap", "screen", "script",
            "ripple", "risk", "ritual", "rival", "river", "road", "roast", "robot", "robust", "rocket",
            "romance", "roof", "rookie", "room", "rose", "rotate", "rough", "round", "route", "royal",
            "rubber", "rude", "rug", "rule", "run", "runway", "rural", "sad", "saddle", "sadness",
            "safe", "sail", "salad", "salmon", "salon", "salt", "salute", "same", "sample", "sand",
            "scrub", "sea", "search", "season", "seat", "second", "secret", "section", "security", "seed",
            "seek", "segment", "select", "sell", "seminar", "senior", "sense", "sentence", "series", "service",
            "session", "settle", "setup", "seven", "shadow", "shaft", "shallow", "share", "shed", "shell",
            "sheriff", "shield", "shift", "shine", "ship", "shiver", "shock", "shoe", "shoot", "shop",
            "short", "shoulder", "shove", "shrimp", "shrug", "shuffle", "shy", "sibling", "sick", "side",
            "siege", "sight", "sign", "silent", "silk", "silly", "silver", "similar", "simple", "since",
            "sing", "siren", "sister", "situate", "six", "size", "skate", "sketch", "ski", "skill",
            "skin", "skirt", "skull", "slab", "slam", "sleep", "slender", "slice", "slide", "slight",
            "slim", "slogan", "slot", "slow", "slush", "small", "smart", "smile", "smoke", "smooth",
            "snack", "snake", "snap", "sniff", "snow", "soap", "soccer", "social", "sock", "soda",
            "soft", "solar", "soldier", "solid", "solution", "solve", "someone", "song", "soon", "sorry",
            "sort", "soul", "sound", "soup", "source", "south", "space", "spare", "spatial", "spawn",
            "speak", "special", "speed", "spell", "spend", "sphere", "spice", "spider", "spike", "spin",
            "spirit", "split", "spoil", "sponsor", "spoon", "sport", "spot", "spray", "spread", "spring",
            "spy", "square", "squeeze", "squirrel", "stable", "stadium", "staff", "stage", "stairs", "stamp",
            "stand", "start", "state", "stay", "steak", "steel", "stem", "step", "stereo", "stick",
            "still", "sting", "stock", "stomach", "stone", "stool", "story", "stove", "strategy", "street",
            "strike", "strong", "struggle", "student", "stuff", "stumble", "style", "subject", "submit", "subway",
            "success", "such", "sudden", "suffer", "sugar", "suggest", "suit", "summer", "sun", "sunny",
            "sunset", "super", "supply", "supreme", "sure", "surface", "surge", "surprise", "surround", "survey",
            "suspect", "sustain", "swallow", "swamp", "swap", "swarm", "swear", "sweet", "swift", "swim",
            "swing", "switch", "sword", "symbol", "symptom", "syrup", "system", "table", "tackle", "tag",
            "time", "tiny", "tip", "tired", "tissue", "title", "toast", "tobacco", "today", "toddler",
            "toe", "together", "toilet", "token", "tomato", "tomorrow", "tone", "tongue", "tonight", "tool",
            "tooth", "top", "topic", "topple", "torch", "tornado", "tortoise", "toss", "total", "tourist",
            "tail", "talent", "talk", "tank", "tape", "target", "task", "taste", "tattoo", "taxi",
            "teach", "team", "tell", "ten", "tenant", "tennis", "tent", "term", "test", "text",
            "thank", "that", "theme", "then", "theory", "there", "they", "thing", "this", "thought",
            "three", "thrive", "throw", "thumb", "thunder", "ticket", "tide", "tiger", "tilt", "timber",
            "toward", "tower", "town", "toy", "track", "trade", "traffic", "tragic", "train", "transfer",
            "trap", "trash", "travel", "tray", "treat", "tree", "trend", "trial", "tribe", "trick",
            "trigger", "trim", "trip", "trophy", "trouble", "truck", "true", "truly", "trumpet", "trust",
            "truth", "try", "tube", "tuition", "tumble", "tuna", "tunnel", "turkey", "turn", "turtle",
            "twelve", "twenty", "twice", "twin", "twist", "two", "type", "typical", "ugly", "umbrella",
            "unable", "unaware", "uncle", "uncover", "under", "undo", "unfair", "unfold", "unhappy", "uniform",
            "unique", "unit", "universe", "unknown", "unlock", "until", "unusual", "unveil", "update", "upgrade",
            "uphold", "upon", "upper", "upset", "urban", "urge", "usage", "use", "used", "useful",
            "useless", "usual", "utility", "vacant", "vacuum", "vague", "valid", "valley", "valve", "van",
            "vanish", "vapor", "various", "vast", "vault", "vehicle", "velvet", "vendor", "venture", "venue",
            "verb", "verify", "version", "very", "vessel", "veteran", "viable", "vibrant", "vicious", "victory",
            "vital", "vivid", "vocal", "voice", "void", "volcano", "volume", "vote", "voyage", "wage",
            "video", "view", "village", "vintage", "violin", "virtual", "virus", "visa", "visit", "visual",
            "wagon", "wait", "walk", "wall", "walnut", "want", "warfare", "warm", "warrior", "wash",
            "wasp", "waste", "water", "wave", "way", "wealth", "weapon", "wear", "weasel", "weather",
            "web", "wedding", "weekend", "weird", "welcome", "west", "wet", "whale", "what", "wheat",
            "wheel", "when", "where", "whip", "whisper", "wide", "width", "wife", "wild", "will",
            "win", "window", "wine", "wing", "wink", "winner", "winter", "wire", "wisdom", "wise",
            "wish", "witness", "wolf", "woman", "wonder", "wood", "wool", "word", "work", "world",
            "worry", "worth", "wrap", "wreck", "wrestle", "wrist", "write", "wrong", "yard", "year",
            "yellow", "you", "young", "youth", "zebra", "zero", "zone", "zoo"};

}
