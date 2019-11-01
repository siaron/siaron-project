package com.siraon;

import javax.xml.ws.Holder;
import java.util.function.Function;

/**
 * @author xielongwang
 * @create 2019-05-1823:51
 * @email xielong.wang@nvr-china.com
 * @description
 */
public class Java8ClosePackage {

    public static void main(String[] args) {
        Function<Integer, Integer> aa = new Java8ClosePackage().adder();

        aa.andThen(integer -> {
            System.out.println("vvv" + integer);
            return integer;
        });

        aa.compose(o -> {
            System.out.println("compose" + 0);
            return null;
        });
        for (int i = 0; i < 10; i++) {
            System.out.println(aa.apply(i));

        }
    }

    public Function<Integer, Integer> adder() {
        final Holder<Integer> sum = new Holder<>(0);
        return (Integer value) -> {
            sum.value += value;
            return sum.value;
        };
    }

}