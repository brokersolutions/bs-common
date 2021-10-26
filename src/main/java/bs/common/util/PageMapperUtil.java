package bs.common.util;

import java.io.Serializable;

import org.springframework.data.domain.Page;

import bs.common.json.JPagination.JPage;

public class PageMapperUtil<T, S> implements Serializable {
	private static final long serialVersionUID = 5098853446657898069L;

	public JPage<S> page(Page<T> page) {
		return PaginatorUtil.pageMapper(page);
	}
	
}