package bs.common.json;

import java.util.Optional;

import lombok.Setter;
import lombok.val;
import bs.common.util.PaginateUtil;
import bs.common.util.ValueUtil;

@Setter
public abstract class JSearch {
	protected Optional<String> search;
	protected Optional<String> sortBy;
	protected Optional<String> sortDirection;
	protected Optional<Integer> page;
	protected Optional<Integer> pageSize;
	protected Optional<Integer> defaultPageSize;
	
	/* Getters */
	public String getSearch() {
		return this.search.orElse(ValueUtil.EMPTY);
	}
	
	public String getSortBy() {
		return this.sortBy.orElse(ValueUtil.EMPTY);
	}
	
	public String getSortDirection() {
		return this.sortDirection.orElse(ValueUtil.EMPTY);
	}
	
	public int getPage() {
		return PaginateUtil.page(this.page);
	}
	
	public int getPageSize() {
		val defPagSize = PaginateUtil.defaultPageSize(this.defaultPageSize);
		val pagSize = PaginateUtil.pageSize(this.pageSize);
		
		if(pagSize > defPagSize) {
			return pagSize;
		}
		return defPagSize;
	}
	
}