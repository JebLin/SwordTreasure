package indi.sword.util.guava.collection;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import java.util.Set;

/**
 * @Description
 * @Author jeb_lin
 * @Date Created in 11:05 AM 11/07/2018
 * @MODIFIED BY
 */
/*
    Table它有两个支持所有类型的键：”行”和”列”。
 */
public class TableDemo {
    public static void main(String[] args) {
        //记录学生在某门课上的成绩
        Table<String,String,Integer> table = HashBasedTable.create();
        table.put("jack","java",100);
        table.put("jack","c",90);
        table.put("mike","java",93);
        table.put("mike","c",100);

        /*
                java    C
          jack  100     90
          mick  93      100
         */
        System.out.println(table);
        System.out.println("---------");

        Set<Table.Cell<String,String,Integer>> cells = table.cellSet();
        for (Table.Cell<String, String, Integer> cell
                : cells){
            System.out.println(cell.getRowKey() + " " + cell.getColumnKey() + " " + cell.getValue());
        }
        System.out.println("---------");

        System.out.println(table.row("jack"));
        System.out.println(table.rowKeySet());
        System.out.println(table.columnKeySet());
        System.out.println(table.values());

     }
}
