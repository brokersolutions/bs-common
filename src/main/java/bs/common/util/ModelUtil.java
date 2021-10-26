package bs.common.util;

import java.io.Serializable;
import java.util.Map;

import org.springframework.ui.Model;

import lombok.val;

public class ModelUtil implements Serializable {
	private static final long serialVersionUID = 7718332970135407755L;

	private ModelUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	public static Model populateModel(Model model, Map<String, Object> modelMap) {
		if(modelMap != null && !modelMap.isEmpty()) {
			for (val entry : modelMap.entrySet()) {
				val key = entry.getKey();
				val value = entry.getValue();
				model.addAttribute(key, value);
			}
		}
		return model;
	}
	
}