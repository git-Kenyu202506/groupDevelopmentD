package com.example.demo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ★ 空文字→null、フォーマット統一
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
        binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));

        // HTMLの<input type="date">形式に合わせる
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));

        binder.setDisallowedFields("class");
    }

    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    private String selectUserform(Model m_condition, Model m_result, Model m_count) {
        SelectCondition condition = new SelectCondition();
        List<User> user = service.selectUserresult(condition);
        m_condition.addAttribute("condition", condition);
        m_result.addAttribute("user", user);
        long count = user.size();
        m_count.addAttribute("count", count);
        return "selectForm";
    }

    @RequestMapping("/selectForm")
    public String selectForm(Model m_condition, Model m_result, Model m_count) {
        return selectUserform(m_condition, m_result, m_count);
    }

    @GetMapping("/clearCondition")
    public String clearCondition(Model m_condition, Model m_result, Model m_count) {
        return selectUserform(m_condition, m_result, m_count);
    }

    @PostMapping("/selectResult")
    public String selectUserresult(
            @ModelAttribute SelectCondition condition,
            BindingResult bindingResult,
            Model model) {

        // Spring自動変換エラーの確認
        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "入力形式が正しくありません。（社員IDや年齢は数値で入力）");
            model.addAttribute("condition", condition);
            model.addAttribute("user", null);
            model.addAttribute("count", 0);
            return "selectForm";
        }

        // 手動チェック
        String error = validateCondition(condition);
        if (error != null) {
            model.addAttribute("error", error);
            model.addAttribute("condition", condition);
            model.addAttribute("user", null);
            model.addAttribute("count", 0);
            return "selectForm";
        }

        // 条件検索
        List<User> user = service.selectUserresult(condition);
        model.addAttribute("condition", condition);
        model.addAttribute("user", user);
        model.addAttribute("count", user.size());
        return "selectForm";
    }

    @PostMapping("/selectUser")
    public String selectUser(@RequestParam(value = "selectedUser", required = false) List<String> selectedUsers, Model m) {
        m.addAttribute("selectedUser", selectedUsers);
        return "selectedUsers";
    }

    private String validateCondition(SelectCondition cond) {
        if (cond.getId() != null && cond.getId() < 0) return "社員IDは正の整数で入力してください。";
        if (cond.getAge_min() != null && cond.getAge_max() != null && cond.getAge_min() > cond.getAge_max())
            return "年齢範囲が不正です。左側が右側を上回っています。";
        if (cond.getDay_start_min() != null && cond.getDay_start_max() != null
                && cond.getDay_start_min().after(cond.getDay_start_max()))
            return "開始日範囲が不正です。左側が右側を上回っています。";
        if (cond.getDay_end_min() != null && cond.getDay_end_max() != null
                && cond.getDay_end_min().after(cond.getDay_end_max()))
            return "終了日範囲が不正です。左側が右側を上回っています。";
        return null;
    }
}