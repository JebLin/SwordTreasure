package indi.sword.util.chaos;

/**
 * @author jeb_lin
 * 9:39 PM 2019/10/10
 */
public class Test03 {
    public static void main(String[] args) {
//        print(38571, 200001, 60000);
        print(50000, 50000, 60000);
//        print(82857, 200001, 60000);

    }

    public static void print(double cost, double property, double quota) {
        System.out.println("消费金额 -> " + cost + " ,资产 -> " + property + " ,额度 -> " + quota);

        double limit = getLimit(quota, property);
        System.out.println("消费上限 -> " + limit);

        double rate = getRate(property);
        System.out.println("资质 -> " + rate + "A");

        double totalPoint = getRealTotalPoint(cost, property, quota);
        System.out.println("入账总分 -> " + totalPoint);

        System.out.println("这个月到账 -> " + cost);

        double totalRewardPoint = getTotalRewardPoint(cost, property, quota);
        System.out.println("下个月到账：总奖励积分 = 入账总分 - 基础积分（立刻到账）-> " + totalRewardPoint);

        double totalBaseRewardPoint = getTotalBaseRewardPoint(cost);
        System.out.println("总基础分奖励 -> " + totalBaseRewardPoint);

        double totalBasePoint = getTotalBasePoint(cost);
        System.out.println("总基础分（总基础分奖励+本身基础积分（立刻到账）） -> " + totalBasePoint);


        double pointRate = getPointRate(cost, property, quota);
        System.out.println("积分倍数 -> " + pointRate);

        double maxPointRate = getMaxPointRate(property, quota);
        System.out.println("该资产 + 额度下最大积分倍数 -> " + maxPointRate);

        double limitMaxTotalPoint = getLimitMaxTotalPoint(property,quota);
        System.out.println("该资产 + 额度下最大最优的入账总积分 -> " + limitMaxTotalPoint);
    }

    public static double getRate(double property) {
        if (property > 200000) {
            return 2;
        } else if (property > 50000) {
            return 1;
        } else {
            return 0;
        }
    }

    // 1. 总基础分奖励
    public static double getTotalBaseRewardPoint(double cost) {
        return 3 * cost - 15000;

    }

    // 2. 总基础分（总基础分奖励+本身基础积分（立刻到账））
    public static double getTotalBasePoint(double cost) {
        return cost + getTotalBaseRewardPoint(cost);
    }

    // 3.(假的) 入账总分
    public static double getTotalPoint(double cost, double property) {
        double rate = getRate(property);
        return getTotalBasePoint(cost) * rate;
    }

    // 4. 总奖励积分 = 入账总分 - 基础积分（立刻到账）
    public static double getTotalRewardPoint(double cost, double property, double quota) {
        double totalRewardPoint = getTotalPoint(cost, property) - cost;
        if (totalRewardPoint > quota * 8) {
            return quota * 8;
        } else {
            return totalRewardPoint;
        }
    }

    // 5.(真的) 入账总分
    public static double getRealTotalPoint(double cost, double property, double quota) {
        return getTotalRewardPoint(cost, property, quota) + cost;
    }

    // 消费上限
    public static double getLimit(double quota, double property) {
        double rate = getRate(property);
        return (quota * 8 + 15000 * rate) / (4 * rate - 1);
    }

    // 该额度+资产下最大最优的入账总积分
    public static double getLimitMaxTotalPoint( double property, double quota){
        double limit = getLimit(quota,property);
        return getRealTotalPoint(limit, property, quota);
    }

    // 积分倍数
    public static double getPointRate(double cost, double property, double quota) {
        return  getRealTotalPoint(cost, property, quota) / cost;
    }

    // 该额度下最大积分倍数
    public static double getMaxPointRate(double property, double quota) {
        double limit = getLimit(quota,property);
        return getPointRate(limit, property, quota);
    }
}
