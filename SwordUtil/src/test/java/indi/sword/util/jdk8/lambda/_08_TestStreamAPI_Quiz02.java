package indi.sword.util.jdk8.lambda;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description StreamApi的测试题
 * @Author:rd_jianbin_lin
 * @Date: 19:15 2017/9/18
 */
public class _08_TestStreamAPI_Quiz02 {

    List<Transaction> transactions = null;

    @Before
    public void before(){
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );
    }

    //1. 找出2011年发生的所有交易， 并按交易额排序（从高到低）
    @Test
    public void test01(){
        transactions.stream().filter(e -> e.getYear() == 2011)
                .sorted((e1,e2) -> -Integer.compare(e1.getValue(),e2.getValue()))
                .forEach(System.out::println);
    }

    //2. 交易员都在哪些不同的城市工作过？
    @Test
    public void test02(){
        transactions.stream().map(e -> e.getTrader().getCity()).distinct().forEach(System.out::println);
    }

    //3. 查找所有来自剑桥的交易员，并按姓名排序
    @Test
    public void test03(){

        transactions.stream().filter(e -> e.getTrader().getCity().equals("Cambridge"))
                .map(Transaction::getTrader).sorted((t1,t2) -> t1.getName().compareTo(t2.getName()))
                .distinct().forEach(System.out::print);
        System.out.println();
        System.out.println("---------------------");

        String str =
                transactions.stream().filter(e -> e.getTrader().getCity().equals("Cambridge"))
                        .map(Transaction::getTrader)
                        .sorted((t1,t2) -> t1.getName().compareTo(t2.getName())).distinct()
                        .map(Trader::toString)
                        .collect(Collectors.joining(",","---","---"));
        System.out.println(str);
    }

    //4. 返回所有交易员的姓名字符串，按字母顺序排序
    @Test
    public void test04(){
        transactions.stream().map(e -> e.getTrader().getName())
                .distinct().sorted().forEach(System.out::print);
        System.out.println();
        System.out.println("------------------------------");

        transactions.stream().map(e -> e.getTrader().getName())
                .flatMap(_05_TestStreamAPI_CenterOp::filterCharacter)
                .sorted().forEach(System.out::print);
        System.out.println();
        System.out.println("------------------------------");

        transactions.stream().map(e -> e.getTrader().getName())
                .flatMap(_05_TestStreamAPI_CenterOp::filterCharacter)
                .sorted((ch1,ch2) -> {
                    return String.valueOf(ch1).compareToIgnoreCase(String.valueOf(ch2));
                }).forEach(System.out::print);
    }

    //5. 有没有交易员是在米兰工作的？
    @Test
    public void test05(){
        // 使用 findAny
        Optional<Transaction> op1 = transactions.stream().filter(e -> e.getTrader().getCity().equals("Milan"))
                .findAny();
        try {
            op1.get();
            System.out.println(true);
        } catch (Exception e) {
            System.out.println(false); //没有就抛异常
        }

        // 使用 anyMatch
        Boolean flag = transactions.stream().anyMatch(e -> e.getTrader().getCity().equals("Milan"));
        System.out.println(flag);
    }

    //6. 打印生活在剑桥的交易员的所有交易额
    @Test
    public void test06(){
        Optional<Integer> op1 =
        transactions.stream().filter(t -> t.getTrader().getCity().equals("Cambridge"))
                .map(t-> t.getValue()).reduce(Integer::sum);
        System.out.println(op1.get());

    }

    //7. 所有交易中，最高的交易额是多少
    @Test
    public void test07(){
        Optional<Integer> op1 =
        transactions.stream().map(t -> t.getValue()).max(Integer::compareTo);

        System.out.println(op1.get());
    }

    //8. 找到交易额最小的交易
    @Test
    public void test08(){

        Optional<Transaction> op1 = transactions.stream().min((t1,t2) -> {
            return Integer.compare(t1.getValue(),t2.getValue());
        });
        System.out.println(op1.get());


    }
}


//交易员类
class Trader {

    private String name; // 姓名
    private String city; // 城市

    public Trader() {
    }

    public Trader(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "Trader [name=" + name + ", city=" + city + "]";
    }

}

// 交易类
class Transaction {

    private Trader trader; // 交易员
    private int year; // 交易日期（交易年份）
    private int value; // 交易值

    public Transaction() {
    }

    public Transaction(Trader trader, int year, int value) {
        this.trader = trader;
        this.year = year;
        this.value = value;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Transaction [trader=" + trader + ", year=" + year + ", value="
                + value + "]";
    }

}