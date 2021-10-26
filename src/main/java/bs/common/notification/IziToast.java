package bs.common.notification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import bs.common.bean.IconBean;
import bs.common.bean.IconBean.CustomIcon;
import bs.common.enums.EFontAwesomeNotificationIcon;
import bs.common.enums.EIziToastPosition;
import bs.common.enums.ENotificationType;


/**
 * This class provides IziToast notifications
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 * @see IziToastCssClass
 * @see EIziToastPosition
 * @see ENotificationType
 * @see EFontAwesomeNotificationIcon
 * @see IconBean
 *
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IziToast {
	private String type;
	private String title;
	private String message;
	private String cssClass;
	private String position;
	private String icon;
	
	/*
	 * IziToast default position 
	 */
	public static final EIziToastPosition DEFAULT_POSITION = EIziToastPosition.TOP_RIGHT;
	
	public IziToast(final String type, final String title, final String message, final String cssClass, final String position) {
		this.type = type;
		this.title = title;
		this.message = message;
		this.cssClass = cssClass;
		this.position = position;
	}
	
	//############################
	//##### STATIC INSTANCES #####
	//############################
	
	/**
	 * IziToast static instance (without) title and FontAwesome icon
	 * 
	 * @param iziToastType ENotificationType
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * 
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast instance(final ENotificationType iziToastType, final String message, final EIziToastPosition iziToastPosition) {
		return switch (iziToastType) {
			case SUCCESS -> success(message, iziToastPosition);
			case WARNING -> warning(message, iziToastPosition);
			case INFO -> info(message, iziToastPosition);
			case ERROR -> error(message, iziToastPosition);
			case DARK -> dark(message, iziToastPosition);
			default -> null;
		};
	}
	
	/**
	 * IziToast static instance (without) title and FontAwesome icon
	 * 
	 * @param iziToastType ENotificationType
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast instance(final ENotificationType iziToastType, final String message) {
		return instance(iziToastType, message, DEFAULT_POSITION);
	}

	/**
	 * IziToast static instance with title and FontAwesome icon
	 * 
	 * @param iziToastType ENotificationType
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * 
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast instance(final ENotificationType iziToastType, final String title, final String message, final EIziToastPosition iziToastPosition) {
		return switch (iziToastType) {
			case SUCCESS -> success(title, message, iziToastPosition);
			case WARNING -> warning(title, message, iziToastPosition);
			case INFO -> info(title, message, iziToastPosition);
			case ERROR -> error(title, message, iziToastPosition);
			case DARK -> dark(title, message, iziToastPosition);
			default -> null;
		};
	}
	
	/**
	 * IziToast static instance with title and FontAwesome icon
	 * 
	 * @param iziToastType ENotificationType
	 * @param title String
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast instance(final ENotificationType iziToastType, final String title, final String message) {
		return instance(iziToastType, title, message, DEFAULT_POSITION);
	}
	
	//###################
	//##### SUCCESS #####
	//###################
	
	/**
	 * IziToast success message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast successSimple(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.SUCCESS.value(), null, message, IziToastCssClass.SUCCESS.value, iziToastPosition.value());
	}
	
	/**
	 * IziToast success message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast successSimple(final String message) {
		return new IziToast(ENotificationType.SUCCESS.value(), null, message, IziToastCssClass.SUCCESS.value, DEFAULT_POSITION.value());
	}
	
	/**
	 * IziToast success message (without title) using FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast success(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.SUCCESS.value(), null, message, IziToastCssClass.SUCCESS.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.SUCCESS.value());
	}
	
	/**
	 * IziToast success message (without title) using FontAwesome icon
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast success(final String message) {
		return new IziToast(ENotificationType.SUCCESS.value(), null, message, IziToastCssClass.SUCCESS.value, DEFAULT_POSITION.value(), EFontAwesomeNotificationIcon.SUCCESS.value());
	}

	/**
	 * IziToast success message with title using FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast success(final String title, final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.SUCCESS.value(), title, message, IziToastCssClass.SUCCESS.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.SUCCESS.value());
	}
	
	/**
	 * IziToast success message with title using FontAwesome icon
	 * 
	 * @param title String
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast success(final String title, final String message) {
		return new IziToast(ENotificationType.SUCCESS.value(), title, message, IziToastCssClass.SUCCESS.value, DEFAULT_POSITION.value(), EFontAwesomeNotificationIcon.SUCCESS.value());
	}

	/**
	 * IziToast success message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast successCustom(final String message, final EIziToastPosition iziToastPosition, final String successIcon) {
		IconBean.successInstance(successIcon);
		return new IziToast(ENotificationType.SUCCESS.value(), null, message, IziToastCssClass.SUCCESS.value, iziToastPosition.value(), CustomIcon.SUCCESS.value());
	}
	
	/**
	 * IziToast success message (without title) using custom icon
	 *
	 * @param message String
	 *
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast successCustom(final String message, final String successIcon) {
		IconBean.successInstance(successIcon);
		return new IziToast(ENotificationType.SUCCESS.value(), null, message, IziToastCssClass.SUCCESS.value, DEFAULT_POSITION.value(), CustomIcon.SUCCESS.value());
	}

	/**
	 * IziToast success message with title using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast successCustom(final String title, final String message, final EIziToastPosition iziToastPosition, final String successIcon) {
		IconBean.successInstance(successIcon);
		return new IziToast(ENotificationType.SUCCESS.value(), title, message, IziToastCssClass.SUCCESS.value, iziToastPosition.value(), CustomIcon.SUCCESS.value());
	}
	
	/**
	 * IziToast success message with title using custom icon
	 * 
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast successCustom(final String title, final String message, final String successIcon) {
		IconBean.successInstance(successIcon);
		return new IziToast(ENotificationType.SUCCESS.value(), title, message, IziToastCssClass.SUCCESS.value, DEFAULT_POSITION.value(), CustomIcon.SUCCESS.value());
	}
	
	//################
	//##### INFO #####
	//################
	
	/**
	 * IziToast info message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast infoSimple(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.INFO.value(), null, message, IziToastCssClass.INFO.value, iziToastPosition.value());
	}
	
	/**
	 * IziToast info message (without title and without icon)
	 * 
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast infoSimple(final String message) {
		return new IziToast(ENotificationType.INFO.value(), null, message, IziToastCssClass.INFO.value, DEFAULT_POSITION.value());
	}
	
	/**
	 * IziToast info message (without title) using FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast info(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.INFO.value(), null, message, IziToastCssClass.INFO.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.INFO.value());
	}
	
	/**
	 * IziToast info message (without title) using FontAwesome icon
	 * 
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast info(final String message) {
		return new IziToast(ENotificationType.INFO.value(), null, message, IziToastCssClass.INFO.value, DEFAULT_POSITION.value(), EFontAwesomeNotificationIcon.INFO.value());
	}

	/**
	 * IziToast info message with title using FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast info(final String title, final String message, EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.INFO.value(), title, message, IziToastCssClass.INFO.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.INFO.value());
	}
	
	/**
	 * IziToast info message with title using FontAwesome icon
	 * 
	 * @param title String
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast info(final String title, final String message) {
		return new IziToast(ENotificationType.INFO.value(), title, message, IziToastCssClass.INFO.value, DEFAULT_POSITION.value(), EFontAwesomeNotificationIcon.INFO.value());
	}
	
	/**
	 * IziToast info message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast infoCustom(final String message, final EIziToastPosition iziToastPosition, final String infoIcon) {
		IconBean.infoInstance(infoIcon);
		return new IziToast(ENotificationType.INFO.value(), null, message, IziToastCssClass.INFO.value, iziToastPosition.value(), CustomIcon.INFO.value());
	}
	
	/**
	 * IziToast info message (without title) using custom icon
	 * 
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast infoCustom(final String message, final String infoIcon) {
		IconBean.infoInstance(infoIcon);
		return new IziToast(ENotificationType.INFO.value(), null, message, IziToastCssClass.INFO.value, DEFAULT_POSITION.value(), CustomIcon.INFO.value());
	}

	/**
	 * IziToast info message with title using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * 
	 * @see EIziToastPosition
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast infoCustom(final String title, final String message, EIziToastPosition iziToastPosition, final String infoIcon) {
		IconBean.infoInstance(infoIcon);
		return new IziToast(ENotificationType.INFO.value(), title, message, IziToastCssClass.INFO.value, iziToastPosition.value(), CustomIcon.INFO.value());
	}
	
	/**
	 * IziToast info message with title using custom icon
	 * 
	 * @param message String
	 * 
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast infoCustom(final String title, final String message, final String infoIcon) {
		IconBean.infoInstance(infoIcon);
		return new IziToast(ENotificationType.INFO.value(), title, message, IziToastCssClass.INFO.value, DEFAULT_POSITION.value(), CustomIcon.INFO.value());
	}

	//###################
	//##### WARNING #####
	//###################
	
	/**
	 * IziToast warning message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast warningSimple(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.WARNING.value(), null, message, IziToastCssClass.WARNING.value, iziToastPosition.value());
	}
	
	/**
	 * IziToast warning message (without title and without icon)
	 * 
	 * @param message String
	 * 
	 * @return IziToast
	 */
	public static IziToast warningSimple(final String message) {
		return new IziToast(ENotificationType.WARNING.value(), null, message, IziToastCssClass.WARNING.value, DEFAULT_POSITION.value());
	}
	
	/**
	 * IziToast warning message (without title) using FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast warning(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.WARNING.value(), null, message, IziToastCssClass.WARNING.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.WARNING.value());
	}
	
	/**
	 * IziToast warning message (without title) using FontAwesome icon
	 * 
	 * @param message String
	 * 
	 * @return IziToast
	 */
	public static IziToast warning(final String message) {
		return new IziToast(ENotificationType.WARNING.value(), null, message, IziToastCssClass.WARNING.value, DEFAULT_POSITION.value(), EFontAwesomeNotificationIcon.WARNING.value());
	}

	/**
	 * IziToast warning message with title using FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast warning(final String title, final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.WARNING.value(), title, message, IziToastCssClass.WARNING.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.WARNING.value());
	}
	
	/**
	 * IziToast warning message with title using FontAwesome icon
	 * 
	 * @param title String
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast warning(final String title, final String message) {
		return new IziToast(ENotificationType.WARNING.value(), title, message, IziToastCssClass.WARNING.value, DEFAULT_POSITION.value(), EFontAwesomeNotificationIcon.WARNING.value());
	}
	
	/**
	 * IziToast warning message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast warningCustom(final String message, final EIziToastPosition iziToastPosition, final String warningIcon) {
		IconBean.warningInstance(warningIcon);
		return new IziToast(ENotificationType.WARNING.value(), null, message, IziToastCssClass.WARNING.value, iziToastPosition.value(), CustomIcon.WARNING.value());
	}
	
	/**
	 * IziToast warning message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast warningCustom(final String message, final String warningIcon) {
		IconBean.warningInstance(warningIcon);
		return new IziToast(ENotificationType.WARNING.value(), null, message, IziToastCssClass.WARNING.value, DEFAULT_POSITION.value(), CustomIcon.WARNING.value());
	}

	/**
	 * IziToast warning message with title using custom icon
	 * 
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast warningCustom(final String title, final String message, final EIziToastPosition iziToastPosition, final String warningIcon) {
		IconBean.warningInstance(warningIcon);
		return new IziToast(ENotificationType.WARNING.value(), title, message, IziToastCssClass.WARNING.value, iziToastPosition.value(), CustomIcon.WARNING.value());
	}
	
	/**
	 * IziToast warning message with title using custom icon
	 * 
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast warningCustom(final String title, final String message, final String warningIcon) {
		IconBean.warningInstance(warningIcon);
		return new IziToast(ENotificationType.WARNING.value(), title, message, IziToastCssClass.WARNING.value, DEFAULT_POSITION.value(), CustomIcon.WARNING.value());
	}
	
	//#################
	//##### ERROR #####
	//#################
	
	/**
	 * IziToast error message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast errorSimple(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.ERROR.value(), null, message, IziToastCssClass.ERROR.value, iziToastPosition.value());
	}
	
	/**
	 * IziToast error message (without title and without icon)
	 * 
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @return IziToast
	 */
	public static IziToast errorSimple(final String message) {
		return new IziToast(ENotificationType.ERROR.value(), null, message, IziToastCssClass.ERROR.value, DEFAULT_POSITION.value());
	}
	
	/**
	 * IziToast error message (without title) using FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast error(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.ERROR.value(), null, message, IziToastCssClass.ERROR.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.ERROR.value());
	}
	
	/**
	 * IziToast error message (without title) using FontAwesome icon
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast error(final String message) {
		return new IziToast(ENotificationType.ERROR.value(), null, message, IziToastCssClass.ERROR.value, DEFAULT_POSITION.value(), EFontAwesomeNotificationIcon.ERROR.value());
	}

	/**
	 * IziToast error message with title using FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast error(final String title, final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.ERROR.value(), title, message, IziToastCssClass.ERROR.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.ERROR.value());
	}
	
	/**
	 * IziToast error message with title using FontAwesome icon
	 * 
	 * @param title String
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast error(final String title, final String message) {
		return new IziToast(ENotificationType.ERROR.value(), title, message, IziToastCssClass.ERROR.value, DEFAULT_POSITION.value(), EFontAwesomeNotificationIcon.ERROR.value());
	}
	
	/**
	 * IziToast error message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast errorCustom(final String message, final EIziToastPosition iziToastPosition, final String errorIcon) {
		IconBean.errorInstance(errorIcon);
		return new IziToast(ENotificationType.ERROR.value(), null, message, IziToastCssClass.ERROR.value, iziToastPosition.value(), CustomIcon.ERROR.value());
	}
	
	/**
	 * IziToast error message (without title) using custom icon
	 * 
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast errorCustom(final String message, final String errorIcon) {
		IconBean.errorInstance(errorIcon);
		return new IziToast(ENotificationType.ERROR.value(), null, message, IziToastCssClass.ERROR.value, DEFAULT_POSITION.value(), CustomIcon.ERROR.value());
	}

	/**
	 * IziToast error message with title using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast errorCustom(final String title, final String message, final EIziToastPosition iziToastPosition, final String errorIcon) {
		IconBean.errorInstance(errorIcon);
		return new IziToast(ENotificationType.ERROR.value(), title, message, IziToastCssClass.ERROR.value, iziToastPosition.value(), CustomIcon.ERROR.value());
	}
	
	/**
	 * IziToast error message with title using custom icon
	 * 
	 * @param message String
	 *
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast errorCustom(final String title, final String message, final String errorIcon) {
		IconBean.errorInstance(errorIcon);
		return new IziToast(ENotificationType.ERROR.value(), title, message, IziToastCssClass.ERROR.value, DEFAULT_POSITION.value(), CustomIcon.ERROR.value());
	}
	
	//################
	//##### DARK #####
	//################
	
	/**
	 * IziToast dark message (without title and without icon)
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast darkSimple(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.DARK.value(), null, message, IziToastCssClass.DARK.value, iziToastPosition.value());
	}
	
	/**
	 * IziToast dark message (without title and without icon)
	 * 
	 * @param message String
	 *
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast darkSimple(final String message) {
		return new IziToast(ENotificationType.DARK.value(), null, message, IziToastCssClass.DARK.value, DEFAULT_POSITION.value());
	}
	
	/**
	 * IziToast dark message (without title) using FontAwesome icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast dark(final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.DARK.value(), null, message, IziToastCssClass.DARK.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.DARK.value());
	}
	
	/**
	 * IziToast dark message (without title) using FontAwesome icon
	 * 
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast dark(final String message) {
		return new IziToast(ENotificationType.DARK.value(), null, message, IziToastCssClass.DARK.value, DEFAULT_POSITION.value(), EFontAwesomeNotificationIcon.DARK.value());
	}

	/**
	 * IziToast error message with title using FontAwesome icon
	 * @param title String
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast dark(final String title, final String message, final EIziToastPosition iziToastPosition) {
		return new IziToast(ENotificationType.DARK.value(), title, message, IziToastCssClass.DARK.value, iziToastPosition.value(), EFontAwesomeNotificationIcon.DARK.value());
	}
	
	/**
	 * IziToast error message with title using FontAwesome icon
	 * 
	 * @param title String
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see EFontAwesomeNotificationIcon
	 * @return IziToast
	 */
	public static IziToast dark(final String title, final String message) {
		return new IziToast(ENotificationType.DARK.value(), title, message, IziToastCssClass.DARK.value, DEFAULT_POSITION.value(), EFontAwesomeNotificationIcon.DARK.value());
	}
	
	/**
	 * IziToast dark message (without title) using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast darkCustom(final String message, final EIziToastPosition iziToastPosition, final String darkIcon) {
		IconBean.darkInstance(darkIcon);
		return new IziToast(ENotificationType.DARK.value(), null, message, IziToastCssClass.DARK.value, iziToastPosition.value(), CustomIcon.DARK.value());
	}
	
	/**
	 * IziToast dark message (without title) using custom icon
	 * 
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast darkCustom(final String message, final String darkIcon) {
		IconBean.darkInstance(darkIcon);
		return new IziToast(ENotificationType.DARK.value(), null, message, IziToastCssClass.DARK.value, DEFAULT_POSITION.value(), CustomIcon.DARK.value());
	}

	/**
	 * IziToast dark message with title using custom icon
	 * @param message String
	 * @param iziToastPosition EIziToastPosition
	 * @see EIziToastPosition
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast darkCustom(final String title, final String message, final EIziToastPosition iziToastPosition, final String darkIcon) {
		IconBean.darkInstance(darkIcon);
		return new IziToast(ENotificationType.DARK.value(), title, message, IziToastCssClass.DARK.value, iziToastPosition.value(), CustomIcon.DARK.value());
	}
	
	/**
	 * IziToast dark message with title using custom icon
	 * 
	 * @param message String
	 * 
	 * @see ENotificationType
	 * @see CustomIcon
	 * @return IziToast
	 */
	public static IziToast darkCustom(final String title, final String message, final String darkIcon) {
		IconBean.darkInstance(darkIcon);
		return new IziToast(ENotificationType.DARK.value(), title, message, IziToastCssClass.DARK.value, DEFAULT_POSITION.value(), CustomIcon.DARK.value());
	}
	
	/**
	 * Default CSS classes for IziToast. Has to be either SUCCESS, ERROR, INFO, WARNING or DARK <br><br>
	 * <b>SUCCESS</b> -> iziToast iziToast-success <br>
	 * <b>ERROR</b> -> iziToast iziToast-danger <br>
	 * <b>INFO</b> -> iziToast iziToast-info <br>
	 * <b>WARNING</b> -> iziToast iziToast-warning <br>
	 * <b>DARK</b> -> iziToast iziToast-dark <br>
	 */
	public enum IziToastCssClass {
		SUCCESS("iziToast iziToast-success"),
		ERROR("iziToast iziToast-danger"),
		INFO("iziToast iziToast-info"),
		WARNING("iziToast iziToast-warning"),
		DARK("iziToast iziToast-dark");

		private final String value;

		IziToastCssClass(String v) {
	        value = v;
	    }

	    public String value() {
	        return value;
	    }

	    public IziToastCssClass fromValue(String v) {
	        return valueOf(v);
	    }
	}
}