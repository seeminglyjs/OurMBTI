package com.our.ourmbti.service.board.face;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface BoardService {

	public HashMap<String, Object> getPaging(HttpServletRequest request);

	public List<HashMap<String, Object>> getList(HashMap<String, Object> map);

}
