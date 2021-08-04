package com.danli.common.lang.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 传给前端的PV和UV的视图对象
 *
 * @author fanfanli
 * @date  2021/4/8
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@AllArgsConstructor
public class VisitorNum {
    int uv;
    int pv;
}