package bs.common.bean;

import org.springframework.data.domain.Page;

import lombok.Getter;
import lombok.val;
import bs.common.util.IntegerUtil;
import bs.common.util.MessageUtil;
import bs.common.util.PaginatorUtil;
import bs.common.util.ValidatorUtil;
import bs.common.util.ValueUtil;

@Getter
public class PaginatorBean<T> {
	private static final String FIRST_TITLE = MessageUtil.getMessage("page.text.begin");
	private static final String PREVIOUS_TITLE = MessageUtil.getMessage("page.text.previous");
	private static final String NEXT_TITLE = MessageUtil.getMessage("page.text.next");
	private static final String LAST_TITLE = MessageUtil.getMessage("page.text.end");
	
	private String firstTitle;
	private String firstRequest;
	private boolean isDisableFirst = false;
    
    private String previouslyTitle;
    private String previouslyRequest;
    private boolean isDisablePreviously = false;
    
    private String nextTitle;
    private String nextRequest;
    private boolean isDisableNext = false;
    
    private String lastTitle;
    private String lastRequest;
    private boolean isDisableLast = false;
    
    private Page<T> page;
    private String pageRecords;
    private String pageInfo;
    
	public PaginatorBean(final Page<T> page, final SearchBean searchBean) {
    	val pageNumber = searchBean.getPage();
    	
    	if(page.isFirst()) {
    		this.firstTitle = "";
    		this.isDisableFirst = true;
    		
    		this.previouslyTitle = "";
    		this.isDisablePreviously = true;
    	} else {
    		this.firstRequest = getRequest(searchBean, "0");
    		this.firstTitle = FIRST_TITLE;
    		
    		val previouslyPage = pageNumber - 1;
    		this.previouslyRequest = getRequest(searchBean, ValueUtil.str(previouslyPage));
    		this.previouslyTitle = PREVIOUS_TITLE;
    	}
    	
    	if(page.isLast()) {
    		this.lastTitle = "";
    		this.isDisableLast = true;
    		
    		this.nextTitle = "";
    		this.isDisableNext = true;
    	} else {
    		val lastPage = page.getTotalPages() - 1;
    		this.lastRequest = getRequest(searchBean, ValueUtil.str(lastPage));
    		this.lastTitle = LAST_TITLE;
    		
    		val nextPage = pageNumber + 1;
    		this.nextRequest = getRequest(searchBean, ValueUtil.str(nextPage));
    		this.nextTitle = NEXT_TITLE;
    	}
    	
    	this.pageRecords = PaginatorUtil.pageRecords(page);
    	this.pageInfo = PaginatorUtil.pageInfo(page);
    	this.page = page;
    }
	
	private String getRequest(final SearchBean searchBean, final String page) {
		var request = searchBean.getRequest();
    	request = request.concat("?page=").concat(page);
    	request = request.concat("&size=").concat(IntegerUtil.value(searchBean.getPageSize()));
    	
    	var params = "";
    	if(searchBean.getParams() != null && !searchBean.getParams().isEmpty()) {
    		params = searchBean.getParams().entrySet().stream()
													  .filter(item -> item.getKey() != null && item.getValue() != null)
													  .map(item -> item.getKey() + "=" + item.getValue())
													  .reduce((x, y) -> String.join("&", x, y))
													  .orElse("");
    	}
    	
    	if(ValidatorUtil.isNotEmpty(params)) {
    		request = request.concat("&").concat(params);
    	}
    	return request;
	}
	
}