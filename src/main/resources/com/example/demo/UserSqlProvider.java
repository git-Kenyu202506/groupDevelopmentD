package com.example.demo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.jdbc.SQL;

public class UserSqlProvider { //動的にmapperを作成（controllerから受け取ったconditionによってmapperの命令文を変える）
	public String selectUserresult(SelectCondition condition) {
		return new SQL() {{ //SQL命令文を作成
			SELECT("*"); //命令文は１文ずつ作成。検索条件が空の場合全件検索となる。
			FROM("user");
			if (condition.getId() != null) { //condition(検索条件)のidが空出なければ命令文に「WHERE id = #{id}」を追加
				WHERE("id = #{id}");
			}
			if (condition.getName() != null && !condition.getName().isEmpty()) { //conditionのnameが空でなければ追加
                WHERE("name LIKE CONCAT('%', #{name}, '%')");
            }
            if (condition.getAge_min() != null) {
                WHERE("age >= #{age_min}");
            }
            if (condition.getAge_max() != null) {
                WHERE("age <= #{age_max}");
            }
            if (condition.getDay_start_min() != null) {
                WHERE("day_start >= #{day_start_min}");
            }
            if (condition.getDay_start_max() != null) {
                WHERE("day_start <= #{day_start_max}");
            }
            if (condition.getDay_end_min() != null) {
                WHERE("day_end >= #{day_end_min}");
            }
            if (condition.getDay_end_max() != null) {
                WHERE("day_end <= #{day_end_max}");
            }
		}}
		.toString(); //作成した命令文を文字列に変換
	}
	
	public String buildSelectByIds(Map<String, Object> params) { //複数のid検索
	    List<Integer> ids = (List<Integer>) params.get("ids");
	    StringBuilder sql = new StringBuilder();
	    sql.append("SELECT * FROM user WHERE id IN (");
	    for (int i = 0; i < ids.size(); i++) {
	        sql.append("#{ids[").append(i).append("]}");
	        if (i < ids.size() - 1) sql.append(",");
	    }
	    sql.append(")");
	    return sql.toString();
	}

}
