package com.siraon.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author xielongwang
 * @create 2019-05-1313:14
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class Person {

    private BaseInfo base;

    private OtherInfo other;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Accessors(chain = true)
    private static class BaseInfo {

        private String name;

        private Long age;

    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Accessors(chain = true)
    private static class OtherInfo {

        private String address;

    }
}