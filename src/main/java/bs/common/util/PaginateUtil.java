package bs.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;

public class PaginateUtil implements Serializable {
	private static final long serialVersionUID = 5164672175011211656L;

	private PaginateUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static final int DEFAULT_PAGE = 0;
	public static final int DEFAULT_PAGE_SIZE = 10;

	public static int page(Optional<Integer> page) {
		return page.orElse(DEFAULT_PAGE);
	}

	public static int pageSize(Optional<Integer> pageSize) {
		return pageSize.orElse(DEFAULT_PAGE_SIZE);
	}
	
	public static int defaultPageSize(Optional<Integer> defaultPageSize) {
		return defaultPageSize.orElse(DEFAULT_PAGE_SIZE);
	}
	
	public static int pageSize(Optional<Integer> defaultPageSize, Optional<Integer> pageSize) {
		return defaultPageSize.orElse(pageSize.orElse(DEFAULT_PAGE_SIZE));
	}

	public static <T> List<T> paginate(final List<T> list, final int page, final int pageSize) {
		if (list != null && !list.isEmpty()) {
			val offset = page * pageSize;
			if (offset >= list.size()) {
				return new ArrayList<>();
			}

			return list.stream().skip(offset).limit(pageSize).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

}