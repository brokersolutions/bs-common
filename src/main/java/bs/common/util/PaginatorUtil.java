package bs.common.util;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import bs.common.json.JPagination;
import bs.common.json.JPagination.JPage;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PaginatorUtil implements Serializable {
	private static final long serialVersionUID = 4774075635992142156L;

	private PaginatorUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static <T> JPage<T> populatePage(final Page<T> page) {
		return populatePage(page, page.getContent());
	}
	
	public static <T, S> JPage<S> pageMapper(final Page<T> page) {
		return populatePageMapper(page);
	}
	
	public static <T> JPage<T> populatePage(final List<T> content, final Pageable pageable) {
		val page = PageUtil.toPage(content, pageable);
		return populatePage(page, page.getContent());
	}
	
	private static <T> JPage<T> populatePage(final Page<T> page, final List<T> content) {
		try {
			
			if(ValidatorUtil.isNotEmpty(page) && !page.isEmpty()) {
				val pageJson = new JPage<T>();
				pageJson.setFirst(page.isFirst());
				pageJson.setHasContent(page.hasContent());
				pageJson.setHasNext(page.hasNext());
				pageJson.setHasPrevious(page.hasPrevious());
				pageJson.setLast(page.isLast());
				pageJson.setNumber(page.getNumber());
				pageJson.setNumberOfElements(page.getNumberOfElements());
				pageJson.setTotalElements(IntegerUtil.value(LongUtil.value(page.getTotalElements())));
				pageJson.setSize(page.getSize());
				pageJson.setTotalPages(page.getTotalPages());
				pageJson.setRecords(content);
				
				//##### Records
				val pageRecords = pageRecords(page);
				pageJson.setPageRecords(pageRecords);
				
				//##### Pages
				val pageInfo = pageInfo(page);
				pageJson.setPageInfo(pageInfo);
				
				return pageJson;
			}
			
		} catch (Exception e) {
			log.error("PaginatorUtil#populatePage error {}", e.getMessage());
		}		
		return JPagination.empty();
	}
	
	private static <T, S> JPage<S> populatePageMapper(final Page<T> page) {
		try {
			
			if(ValidatorUtil.isNotEmpty(page) && !page.isEmpty()) {
				val pageJson = new JPage<S>();
				pageJson.setFirst(page.isFirst());
				pageJson.setHasContent(page.hasContent());
				pageJson.setHasNext(page.hasNext());
				pageJson.setHasPrevious(page.hasPrevious());
				pageJson.setLast(page.isLast());
				pageJson.setNumber(page.getNumber());
				pageJson.setNumberOfElements(page.getNumberOfElements());
				pageJson.setTotalElements(IntegerUtil.value(LongUtil.value(page.getTotalElements())));
				pageJson.setSize(page.getSize());
				pageJson.setTotalPages(page.getTotalPages());
				
				//##### Records
				val pageRecords = pageRecords(page);
				pageJson.setPageRecords(pageRecords);
				
				//##### Pages
				val pageInfo = pageInfo(page);
				pageJson.setPageInfo(pageInfo);
				
				return pageJson;
			}
			
		} catch (Exception e) {
			log.error("PaginatorUtil#populatePageMapper error {}", e.getMessage());
		}		
		return JPagination.empty();
	}
	
	public static <T> String pageRecords(final Page<T> page) {
		if(page.getTotalElements() <= page.getSize()) {
			return pageResultInfo(page);
		}
		return pageSizeInfo(page);
	}
	
	public static <T> String pageRecords(final JPage<T> page) {
		if(page.getTotalElements() <= page.getSize()) {
			return pageResultInfo(page);
		}
		return pageSizeInfo(page);
	}
	
	public static <T> String pageInfo(final Page<T> page) {
		if(page.hasContent()) {
			val pageNumber = page.getNumber() + 1;
			val params = new Object[] {pageNumber, page.getTotalPages()};
			return MessageUtil.getMessage("paginate.text.info", params);
		}
		return ValueUtil.EMPTY;
	}
	
	public static <T> String pageInfo(final JPage<T> page) {
		if(page.hasContent()) {
			val pageNumber = page.getNumber() + 1;
			val params = new Object[] {pageNumber, page.getTotalPages()};
			return MessageUtil.getMessage("paginate.text.info", params);
		}
		return ValueUtil.EMPTY;
	}
	
	public static <T> String pageResultInfo(final Page<T> page) {
		var totalElements = page.getTotalElements();
		return pageResultInfo(totalElements);
	}
	
	public static <T> String pageResultInfo(final JPage<T> page) {
		var totalElements = LongUtil.value(page.getTotalElements());
		return pageResultInfo(totalElements);
	}
	
	private static String pageResultInfo(final Long totalElements) {
		val params = new Long[] {totalElements};
		val message = (totalElements == 0) ? "paginate.text.result.empty" : "paginate.text.result.info";
		return MessageUtil.getMessage(message, params);
	}
	
	public static <T> String pageSizeInfo(final T obj) {
		var init = 1;
		var number = 0;
		var numberOfElements = 0;
		var totalElements = 0;
		var pageSize = 0;
		var totalPages = 0;
		
		if(obj instanceof Page) {
			@SuppressWarnings("unchecked")
			val page = ((Page<T>) obj);
			
			number = page.getNumber();
			numberOfElements = page.getNumberOfElements();
			totalElements = IntegerUtil.value(page.getTotalElements());
			pageSize = page.getSize();
			totalPages = page.getTotalPages();
		} else if(obj instanceof JPage) {
			@SuppressWarnings("unchecked")
			val page = ((JPage<T>) obj);
			
			number = page.getNumber();
			numberOfElements = page.getNumberOfElements();
			totalElements = page.getTotalElements();
			pageSize = page.getSize();
			totalPages = page.getTotalPages();
		}
		
		if(totalElements > pageSize && number > 0) {
			init = pageSize * number + 1;
			if(number + 1 == totalPages) {
				numberOfElements = totalElements;
			} else {
				number ++;
				numberOfElements *= number;
			}
		}
		
		val params = new Object[] {init, numberOfElements, totalElements};
		return MessageUtil.getMessage("paginate.text.result.info.size", params);
	}
	
}