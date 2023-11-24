package indi.sword.util.Exception;

/**
 * @author linjianbin02
 * 5:03 PM 2020/5/25
 */
public class OK {
    private static String excludeListStr = "obj:{objectId}:ot:{objectType}:out:{outSideId}:d:{dimension}";

    private static String formatExcludeListStr(Long objectId, Long outSideId){
        return excludeListStr.replace("{objectId}",String.valueOf(objectId))
                .replace("{objectType}",String.valueOf(1))
                .replace("{outSideId}",String.valueOf(outSideId))
                .replace("{dimension}",String.valueOf(2));
    }

    public static void main(String[] args) {
        System.out.println(formatExcludeListStr(123L,555L));
    }
}
