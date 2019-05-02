package com.siaron.drools.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author xielongwang
 * @create 2019-04-3009:04
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class Car {
    private int discount = 100;
    private Person person;
}