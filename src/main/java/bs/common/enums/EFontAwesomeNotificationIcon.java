package bs.common.enums;

/**
 * IziToastFontAwesomeIcon. FontAwesome icons for IziToast notifications. <br><br>
 * 
 * <b>SUCCESS</b> -> fas fa-check-circle <br>
 * <b>ERROR</b> -> fas fa-exclamation-circle <br>
 * <b>INFO</b> -> fas fa-info-circle <br>
 * <b>WARNING</b> -> fas fa-exclamation-triangle <br>
 * <b>DARK</b> -> fas fa-check <br>
 * 
 */
public enum EFontAwesomeNotificationIcon {
    SUCCESS("fas fa-check-circle"),
    ERROR("fas fa-exclamation-circle"),
    INFO("fas fa-info-circle"),
    WARNING("fas fa-exclamation-triangle"),
    DARK("fas fa-check");

    private final String value;

    EFontAwesomeNotificationIcon(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public EFontAwesomeNotificationIcon fromValue(String v) {
        return valueOf(v);
    }
}