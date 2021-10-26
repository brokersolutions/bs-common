package bs.common.json;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.val;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JPagination<T> {

	private JPage<T> page;
	private JResponse response;

	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class JPage<T> {
		private int size;
		private int totalPages;
		private boolean first;
		private boolean last;
		@Getter(AccessLevel.PUBLIC)
		private boolean hasPrevious;
		@Getter(AccessLevel.PUBLIC)
		private boolean hasNext;
		private int number;
		private int numberOfElements;
		private int totalElements;
		@Getter(AccessLevel.PUBLIC)
		private Boolean hasContent;
		@Singular("content")
		private List<T> content;		
		private List<?> records;
		private String pageRecords;
		private String pageInfo;
		
		public boolean hasContent() {
			return this.hasContent;
		}
		
		public boolean hasPrevious() {
			return this.hasPrevious;
		}
		
		public boolean hasNext() {
			return this.hasNext;
		}
		
		public List<?> getRecords() {
			if(records == null) {
				records = new ArrayList<>();
			}
			return this.records;
		}
		
		public void updateContent(List<?> records) {
			this.content = null;
			this.records = records;
		}
	}
	
	public static <T> JPagination<T> paginationJson(final JPage<T> pageJson, final JResponse response) {
		val paginationJson = new JPagination<T>();
		paginationJson.setPage(pageJson);
		paginationJson.setResponse(response);
		return paginationJson;
	}
	
	public static <T> JPage<T> empty() {
		return new JPage<>();
	}
	
}