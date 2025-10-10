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
import org.springframework.web.bind.annotation.PathVariable;
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

	@InitBinder //フォームのデータをコントローラーのメソッド引数や Form クラスにバインド（自動変換）する前 に呼び出される
	public void initBinder(WebDataBinder binder) { //引数の WebDataBinder binder は、フォーム入力やリクエストパラメータをオブジェクトに変換する際の設定を管理するクラス
		//このメソッド内でカスタムエディタ（データ変換ルール）を登録することでフォーム入力の型変換や空文字処理などを統一
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));//フォームから送られたString型の前後の空白を削除および空文字をnullに変換
		binder.registerCustomEditor(Integer.class, new CustomNumberEditor(Integer.class, true));//フォームから送られたInteger型の空文字をnullに変換
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//日付のフォーマットを指定
		dateFormat.setLenient(false);//不正な日付は例外
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));//空文字の場合nullに変換
		binder.setDisallowedFields("class");//セキュリティ対策。フォーム入力の中でclassフィールドを送信されても無視。
	}

	@RequestMapping("/index") //index(メニュー画面)へのリクエストを受け取ったら
	public String index() {
		return "index"; //index画面(メニュー画面)へ遷移
	}

	@RequestMapping("/insert") //insert(登録画面仮値)へのリクエストを受け取ったら 
	public String insert() {
		return "insert"; //登録画面へ遷移(仮値)
	}

	@RequestMapping("/selectForm") //selectForm(検索画面)へのリクエストを受け取ったら
	public String selectForm(Model model) {
		return selectUserform(model); //社員検索を実行
	}

	@GetMapping("/clearCondition") ///clearCondition(検索条件のクリア)を受け取ったら
	public String clearCondition(Model model) {
		return selectUserform(model);//社員検索を実行(検索画面へ戻る)
	}

	@PostMapping("/selectResult") //selectResult(検索)を受け取ったら
	public String selectUserresult(
			@ModelAttribute SelectCondition condition, //パラメーターをそれぞれ検索条件のクラスオブジェクトに格納
			BindingResult bindingResult, //Formの入力チェック結果
			Model model) {

		// 入力チェック時の自動変換エラーの確認
		if (bindingResult.hasErrors()) { //入力チェック結果にエラーが含まれていたらエラー文、入力済みの検索条件、検索結果のuserはnull、検索結果件数は0にして検索画面に戻る
			model.addAttribute("error", "入力形式が正しくありません。社員IDおよび年齢は数値を入力してください。日付が正しいかご確認ください。");
			model.addAttribute("condition", condition);
			model.addAttribute("user", null);
			model.addAttribute("count", 0);
			return "selectForm";
		}

		// 入力された数値の範囲に問題がないか確認
		String error = validateCondition(condition); //後述のvalidateCondition(condition)メソッドで入力値チェック。結果をerrorとする。
		if (error != null) { //エラーがあればエラー文、入力済みの検索条件、検索結果のuserはnull、検索結果件数は0にして検索画面に戻る
			model.addAttribute("error", error);
			model.addAttribute("condition", condition);
			model.addAttribute("user", null);
			model.addAttribute("count", 0);
			return "selectForm";
		}

		// 条件検索
		List<User> user = service.selectUserresult(condition);//serviceクラスに検索条件を渡し返ってきた結果をuserとして検索画面に渡して表示
		model.addAttribute("condition", condition);
		model.addAttribute("user", user);
		model.addAttribute("count", user.size());
		return "selectForm";
	}

	@PostMapping("/delete") //delete(削除画面仮値)を受け取ったら
	public String showDeleteForm(@RequestParam(value = "selectedUser", required = false) List<Integer> selectedIds
			, Model model) { //selectedUser(html上でユーザーに紐付けているチェックボックスに命名、値は選択したユーザーのid)を選択の有無に関係なく受け取る(選択されていない場合パラメーターはnullとなる)。

		if (selectedIds == null || selectedIds.isEmpty()) { //ユーザーが選択されていなければエラー文等をわたした状態で検索画面を再表示
			model.addAttribute("error_select", "ユーザーが選択されていません");
			SelectCondition condition = new SelectCondition(); 
			model.addAttribute("condition", condition);
			List<User> user = service.selectUserresult(condition);
			model.addAttribute("user", user);
			model.addAttribute("count", user.size());
			return "selectForm";
		}

		List<User> selectedUsers = service.selectByIds(selectedIds); //選択されたユーザーの情報をid検索。
		model.addAttribute("selectedUsers", selectedUsers);//検索結果を削除画面に渡す。渡す値がidのみでいいならここまでの２行は不要。
		model.addAttribute("selectedIds", selectedIds);//選択したユーザーidを削除画面に渡す
		return "delete";//delete(削除画面仮値)に遷移
	}

	@GetMapping("/update/{id}")//update(更新画面仮値)を受け取ったら
	public String showUpdateForm(@PathVariable("id") int id, Model model) { //URL内のidを引数として受け取る
		User user = service.selectById(id); // IDで1件取得
		if (user == null) {
			model.addAttribute("error", "指定されたユーザーが存在しません。");
			SelectCondition condition = new SelectCondition(); 
			model.addAttribute("condition", condition);
			return "selectForm"; // 元の一覧に戻る
		}
		model.addAttribute("user", user); //id以外の情報を更新画面に渡す必要がなければ「IDで1件取得」〜この行は不要
		model.addAttribute("id", id);
		return "update"; // update(更新画面仮値)に遷移
	}

	private String selectUserform(Model model) { //社員検索メソッド
		SelectCondition condition = new SelectCondition(); //検索条件のクラスオブジェクトを作成
		List<User> user = service.selectUserresult(condition); //検索結果をuserとする
		model.addAttribute("condition", condition);
		model.addAttribute("user", user);
		long count = user.size();
		model.addAttribute("count", count);
		return "selectForm";//検索条件、結果、検索結果の件数をmodelに入れて検索画面に渡して表示
	}

	private String validateCondition(SelectCondition condition) { //入力チェック(数値の範囲)メソッド
		if (condition.getId() != null && condition.getId() < 0) return "社員IDは正の整数で入力してください。";
		if (condition.getAge_min() != null && condition.getAge_max() != null && condition.getAge_min() > condition.getAge_max())
			return "年齢範囲が不正です。";
		if (condition.getDay_start_min() != null && condition.getDay_start_max() != null
				&& condition.getDay_start_min().after(condition.getDay_start_max()))
			return "開始日範囲が不正です。";
		if (condition.getDay_end_min() != null && condition.getDay_end_max() != null
				&& condition.getDay_end_min().after(condition.getDay_end_max()))
			return "終了日範囲が不正です。";
		return null;
	}
}