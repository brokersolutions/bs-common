package bs.common.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import bs.common.enums.ENotificationType;
import bs.common.util.MessageUtil;
import bs.common.util.PropertiesUtil;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Swal {

	private String title;
	private String text;
	private String type;
	
	@Default
	private boolean closeOnConfirm = true;
	@Default
	private boolean closeOnCancel = true;
	@Default
	private boolean showCancelBtn = false;
	
	private String confirmTitle;
	private String confirmText;
	private String confirmType;
	private String confirmBtnText;
	private String confirmBtnColor;
	
	private String cancelTitle;
	private String cancelText;
	private String cancelType;
	private String cancelBtnText;
	private String cancelBtnColor;
	
	public static Swal swal(final String text, ENotificationType swalType) {
		return new Swal(null, text, MessageUtil.getMessage(PropertiesUtil.ACCEPT), swalType);
	}
	
	public static Swal success(final String text) {
		return success(null, text, MessageUtil.getMessage(PropertiesUtil.ACCEPT));
	}
	
	public static Swal success(final String title, final String text, final String confirmBtnText) {
		return new Swal(title, text, confirmBtnText, ENotificationType.SUCCESS);
	}
	
	public static Swal success(final String title, final String text) {
		return new Swal(title, text, MessageUtil.getMessage(PropertiesUtil.ACCEPT), ENotificationType.SUCCESS);
	}
	
	public static Swal info(final String text) {
		return info(null, text, MessageUtil.getMessage(PropertiesUtil.ACCEPT));
	}
	
	public static Swal info(final String title, final String text, final String confirmBtnText) {
		return new Swal(title, text, confirmBtnText, ENotificationType.INFO);
	}
	
	public static Swal info(final String title, final String text) {
		return new Swal(title, text, MessageUtil.getMessage(PropertiesUtil.ACCEPT), ENotificationType.INFO);
	}
	
	public static Swal warning(final String text) {
		return warning(null, text, MessageUtil.getMessage(PropertiesUtil.ACCEPT));
	}
	
	public static Swal warning(final String title, final String text, final String confirmBtnText) {
		return new Swal(title, text, confirmBtnText, ENotificationType.WARNING);
	}
	
	public static Swal warning(final String title, final String text) {
		return new Swal(title, text, MessageUtil.getMessage(PropertiesUtil.ACCEPT), ENotificationType.WARNING);
	}
	
	public static Swal error(final String text) {
		return error(null, text, MessageUtil.getMessage(PropertiesUtil.ACCEPT));
	}
	
	public static Swal error(final String title, final String text, final String confirmBtnText) {
		return new Swal(title, text, confirmBtnText, ENotificationType.ERROR);
	}
	
	public static Swal error(final String title, final String text) {
		return new Swal(title, text, MessageUtil.getMessage(PropertiesUtil.ACCEPT), ENotificationType.ERROR);
	}
	
	public Swal(final String text, final String confirmBtnText) {
		super();
		
		this.text = text;
		this.type = ENotificationType.SUCCESS.value();
		this.confirmBtnText = confirmBtnText;
		this.confirmBtnColor = SwalBtnColor.SUCCESS.value();
	}
	
	public Swal(final String title, final String text, final String confirmBtnText) {
		super();
		
		this.title = title;
		this.text = text;
		this.type = ENotificationType.SUCCESS.value();
		this.confirmBtnText = confirmBtnText;
		this.confirmBtnColor = SwalBtnColor.SUCCESS.value();
	}
	
	public Swal(final String title, final String text, final String confirmBtnText, final ENotificationType swalType) {
		super();
		
		this.title = title;
		this.text = text;
		this.type = swalType.value();
		this.confirmBtnText = confirmBtnText;
		this.confirmBtnColor = switch (this.type) {
			case "success" -> SwalBtnColor.SUCCESS.value();
			case "info" -> SwalBtnColor.INFO.value();
			case "warning" -> SwalBtnColor.WARNING.value();
			case "error" -> SwalBtnColor.ERROR.value();
			default-> null; 
		};
	}
	
	public enum SwalBtnColor {
		SUCCESS("#3c763d"),
		ERROR("#a94442"),
		INFO("#31708f"),
		WARNING("#8a6d3b");
		
		private final String value;

		SwalBtnColor(String color) {
	        value = color;
	    }

	    public String value() {
	        return value;
	    }

	    public SwalBtnColor fromValue(String value) {
	        return valueOf(value);
	    }
	}
}