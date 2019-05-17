package com.siraon.support;

import lombok.*;
import lombok.experimental.Accessors;

/**
 * @author xielongwang
 * @create 2019-05-1417:48
 * @email xielong.wang@nvr-china.com
 * @description
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GlobalException extends RuntimeException {

    private long code;

    private String msg;
}