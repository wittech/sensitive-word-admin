package com.github.houbb.sensitive.word.admin.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.houbb.menu.api.annotation.Menu;
import com.github.houbb.auto.log.annotation.AutoLog;
import com.github.houbb.heaven.util.io.FileUtil;
import com.github.houbb.iexcel.util.ExcelHelper;
import com.github.houbb.sensitive.word.admin.web.biz.Result;
import com.github.houbb.sensitive.word.admin.web.biz.WordBiz;
import com.github.houbb.web.common.dto.resp.BaseResp;
import com.github.houbb.sensitive.word.admin.dal.entity.BasePageInfo;
import com.github.houbb.web.common.util.RespUtil;
import com.github.houbb.privilege.api.annotation.PrivilegeAcquire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.github.houbb.sensitive.word.admin.service.service.WordService;
import com.github.houbb.sensitive.word.admin.dal.entity.Word;
import com.github.houbb.sensitive.word.admin.dal.entity.po.WordPagePo;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;



/**
 * <p>
 * 敏感词表 前端控制器
 * </p>
 *
 * @author dh
 * @since 2024-02-05
 */
@Controller
@RequestMapping("/api/v7/word")
@AutoLog
@Menu(id = "word", name = "敏感词表", orderNum = 0, type = "MENU", level = 1)
public class WordController {

    @Autowired
    private WordService wordService;

    @Autowired
    private WordBiz wordBiz;

    /**
    * 首页信息
    * @return 结果
    */
    @RequestMapping("/index")
    @PrivilegeAcquire({"admin", "word-index"})
    @Menu(id = "word-index", pid = "word", name = "敏感词表-首页", orderNum = 0, type = "INDEX", level = 2)
    public String index() {
        return "word/index";
    }

    /**
    * 添加元素
    * @param entity 实体 {word: "日本人", type: "DENY", status: "S", remark: ""}
    * @return 结果
    */
    @RequestMapping("/add")
    @ResponseBody
    @PrivilegeAcquire({"admin", "word-add"})
    @Menu(id = "word-add", pid = "word", name = "敏感词表-添加", orderNum = 1, type = "API", level = 2)
    public Result<String> add(@RequestBody final Word entity) {
        wordBiz.addTx(entity);

        return Result.success("成功");
    }

    /**
    * 编辑
    * @param entity 实体
    * @return 结果
    */
    @RequestMapping("/edit")
    @ResponseBody
    @PrivilegeAcquire({"admin", "word-edit"})
    @Menu(id = "word-edit", pid = "word", name = "敏感词表-编辑", orderNum = 2, type = "API", level = 2)
    public Result<String> edit(final Word entity) {
        wordBiz.editTx(entity);

        return Result.success("成功");
    }

    /**
    * 明细
    * @param id 主键
    * @return 结果
    */
    @RequestMapping("/detail/{id}")
    @ResponseBody
    @PrivilegeAcquire({"admin", "word-detail"})
    @Menu(id = "word-detail", pid = "word", name = "敏感词表-详情", orderNum = 3, type = "API", level = 2)
    public BaseResp detail(@PathVariable final Integer id) {
        Word entity = wordService.selectById(id);

        return RespUtil.of(entity);
    }

    /**
    * 删除
    * @param id 实体
    * @return 结果
    */
    @RequestMapping("/remove/{id}")
    @ResponseBody
    @PrivilegeAcquire({"admin", "word-remove"})
    @Menu(id = "word-remove", pid = "word", name = "敏感词表-删除", orderNum = 4, type = "API", level = 2)
    public Result<String> remove(@PathVariable final Integer id) {
        wordBiz.removeTx(id);

        return Result.success("成功");
    }

    /**
    * 列表
    * @return 结果
    */
    @RequestMapping("/list")
    @ResponseBody
    @PrivilegeAcquire({"admin", "word-list"})
    @Menu(id = "word-list", pid = "word", name = "敏感词表-列表", orderNum = 5, type = "API", level = 2)
    public Result<BasePageInfo<Word>> list(@RequestBody JSONObject jsonObject) {
        if(jsonObject == null){
            return Result.error("参数错误");
        }
        int limit = jsonObject.containsKey("limit") ? jsonObject.getInteger("limit") : 10;
        int page = jsonObject.containsKey("page") ? jsonObject.getInteger("page") : 1;
        WordPagePo pageReq = new WordPagePo();
        pageReq.setPageSize(limit);
        pageReq.setPageNum(page);
        BasePageInfo<Word> pageInfo = wordService.pageQueryList(pageReq);
        return Result.success(pageInfo);
    }

    /**
    * 导出
    * @param pageReq 入参
    * @param response 响应
    */
    @RequestMapping("/export")
    @ResponseBody
    @CrossOrigin
    @PrivilegeAcquire({"admin", "word-export"})
    @Menu(id = "word-export", pid = "word", name = "敏感词表-导出", orderNum = 6, type = "API", level = 2)
    public void export(@RequestBody WordPagePo pageReq, HttpServletResponse response) {
        final String fileName = "文件导出-敏感词表-" + System.currentTimeMillis() + ".xls";
        File file = new File(fileName);
        try {
            pageReq.setPageNum(1);
            pageReq.setPageSize(Integer.MAX_VALUE);

            BasePageInfo<Word> pageInfo = wordService.pageQueryList(pageReq);

            // 直接写入到文件
            ExcelHelper.write(file.getAbsolutePath(), pageInfo.getData());

            // 根据客户端，选择信息
            response.addHeader("content-Type", "application/octet-stream");
            response.addHeader("content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            try(InputStream in = new FileInputStream(file);
                ServletOutputStream out = response.getOutputStream();) {
                byte[] bs = new byte[1024];
                int len = -1;
                while ((len = in.read(bs)) != -1) {
                    out.write(bs, 0, len);
                }
                out.flush();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            FileUtil.deleteFile(file);
        }
    }

    /**
    * 批量删除
    *
    * @param ids 唯一主键
    * @return 结果
    */
    @RequestMapping("/deleteBatch")
    @ResponseBody
    @PrivilegeAcquire({"admin", "word-deleteBatch"})
    @Menu(id = "word-deleteBatch", pid = "word", name = "敏感词表-批量删除", orderNum = 7, type = "API", level = 2)
    public Result<String> deleteBatch(@RequestBody List<Integer> ids) {
        wordBiz.removeBatchTx(ids);
        return Result.success("成功");
    }
}
