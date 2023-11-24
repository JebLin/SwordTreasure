package indi.sword.util.chaos;


import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jeb_lin
 * 8:46 PM 2020/1/2
 */
public class TT {

    private static Map<Integer, WordNode> firstCharMap = new HashMap<>();

    @ToString
    @Data
    public static class WordNode {
        private int charValue; // 日 -> int值
        private Map<Integer, WordNode> childMap;// 本 -> WordNode
        private boolean isEnd;
        private String content; // 是有当isEnd=true的时候，这个才有意义，比如 日本军官

        public WordNode(int charValue) {
            this.charValue = charValue;
        }

    }


    public static void main(String[] args) {
        String word1 = "日本鬼子";
        String word2 = "日本军官";
        addSensitiveWord(word1);
        addSensitiveWord(word2);
        System.out.println(JSON.toJSONString(firstCharMap));

        String content = "你全家都是日本军官";
        System.out.println(validate(content));
        String content2 = "你全家都是日猫";
        System.out.println(validate(content2));

    }

    private static WordNode validate(String content){
        if(null == content){
            return null;
        }
        WordNode wordNode = null;
        content = content.trim();
        for (int i = 0; i < content.length(); i++) {
            int charVal = content.codePointAt(i);
            wordNode = firstCharMap.get(charVal);
            if(wordNode == null){
                continue;
            }
            if(wordNode.isEnd){
                return wordNode;
            }

            int index = i;
            while(index + 1 <= content.length() - 1){
                int nextCharVal = content.codePointAt(index + 1);
                wordNode = wordNode.childMap.get(nextCharVal);
                if(wordNode == null){
                    break;
                }
                if(wordNode.isEnd){
                    return wordNode;
                }
                index++;
            }
        }


        return null;
    }

    private static void addSensitiveWord(String wordContent) {
        if (wordContent.isEmpty()) {
            return;
        }
        wordContent = wordContent.trim(); // 大小写，全角半角，emoji 表情过滤
        int firstCharVal = wordContent.codePointAt(0);
        if (null == firstCharMap.get(firstCharVal)) {
            firstCharMap.put(firstCharVal, new WordNode(firstCharVal));
        }
        WordNode wordNode = firstCharMap.get(firstCharVal);
        if (wordContent.length() == 1) {
            wordNode.isEnd = true;
            wordNode.content = wordContent;
            return;
        }

        for (int i = 1; i < wordContent.length(); i++) {
            int charVal = wordContent.codePointAt(i);

            if (wordNode.childMap == null) {
                wordNode.childMap = new HashMap<>();
            }
            if(wordNode.childMap.get(charVal) == null){
                wordNode.childMap.put(charVal, new WordNode(charVal));
            }
            wordNode = wordNode.childMap.get(charVal);
        }

        wordNode.isEnd = true;
        wordNode.content = wordContent;
    }


}
