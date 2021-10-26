package bs.common.enums;

/**
 * <p>This Enum contains different actions that can be used to control or restrict the users's access level</p>
 * The defined actions are: <br>
 * <b><i>VIEW</i></b> This action is for view only. Actions such as register, edit and delete are blocked<br>
 * <b><i>CONFIG</i></b> This action is for configuration (view, register and edit) only. Delete action are blocked<br>
 * <b><i>FULL</i></b> This action is for full (view, register, dit and delete) access
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
public enum EAction {
	/**
	 * This action is for view only. Actions such as register, edit and delete are blocked
	 */
	VIEW,
	
	/**
	 * This action is for configuration (view, register and edit) only. Delete action are blocked
	 */
	CONFIG,
	
	/**
	 * This action is for full (view, register, edit and delete) access
	 */
	FULL;
}