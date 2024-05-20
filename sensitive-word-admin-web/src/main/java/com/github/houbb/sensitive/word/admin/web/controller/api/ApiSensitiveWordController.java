package com.github.houbb.sensitive.word.admin.web.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.sensitive.word.admin.web.biz.Result;
import com.github.houbb.sensitive.word.bs.SensitiveWordBs;
import com.github.houbb.web.common.dto.resp.BaseResp;
import com.github.houbb.web.common.util.RespUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * api 服务
 * @author binbin.hou
 * @since 1.1.0
 */
@RestController
@RequestMapping("/api/v7/sensitiveWord/")
@AutoLog
public class ApiSensitiveWordController {

    @Autowired
    private SensitiveWordBs sensitiveWordBs;

    /**
     * 是否包含敏感词
     */
    @RequestMapping("/contains")
    public Result<Boolean> contains(@RequestBody JSONObject data) {
        boolean contains = sensitiveWordBs.contains(data.getString("text"));
        return Result.success(contains);
    }

    /**
     * 获取所有的敏感词
     */
    @RequestMapping("/findAll")
    public Result<List<String>> findAll(@RequestBody JSONObject data) {
        List<String> results = sensitiveWordBs.findAll(data.getString("text"));
        return Result.success(results);
    }

    /**
     * 获取第一个的敏感词
     */
    @RequestMapping("/findFist")
    public Result<String> findFist(@RequestBody JSONObject data) {
        String results = sensitiveWordBs.findFirst(data.getString("text"));
        return Result.success(results);
    }

    /**
     * 获取敏感词的标签列表
     */
    @RequestMapping("/tags")
    public Result<Set<String>> tags(@RequestBody JSONObject data) {
        Set<String> tags = sensitiveWordBs.tags(data.getString("text"));
        return Result.success(tags);
    }

    /**
     * 获取替换后的结果
     */
    @RequestMapping("/replace")
    public BaseResp replace(@RequestBody JSONObject data) {
        String results = sensitiveWordBs.replace(data.getString("text"));
        return RespUtil.of(results);
    }

}
