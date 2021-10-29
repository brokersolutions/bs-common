package bs.common.custom;

import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.val;
import bs.common.json.JSelect;
import bs.common.payload.OptionDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseAddress implements Serializable {
	private static final long serialVersionUID = -1629443805533001409L;
	
	private JSelect stateSelect;
	private List<OptionDto> states;
	
	private JSelect townSelect;
	private List<OptionDto> towns;
	
	private JSelect colonySelect;
	private List<OptionDto> colonies;
	
	private JSelect select;
	private List<OptionDto> options;
	
	private String state;
	private String town;
	private String colony;
	private String zipCode;
	
	public static BaseAddress populate(final JSelect select, final List<OptionDto> options) {
		val wa = new BaseAddress();
		wa.setSelect(select);
		wa.setOptions(options);
		return wa;
	}
	
	public static BaseAddress stateSelect(final JSelect stateSelect) {
		val wa = new BaseAddress();
		wa.setStateSelect(stateSelect);
		return wa;
	}
	
	public static BaseAddress stateSelect(final JSelect stateSelect, final List<OptionDto> states) {
		val wa = new BaseAddress();
		wa.setStateSelect(stateSelect);
		wa.setStates(states);
		return wa;
	}
	
	public static BaseAddress states(final List<OptionDto> states) {
		val wa = new BaseAddress();
		wa.setStates(states);
		return wa;
	}
	
	public static BaseAddress townSelect(final JSelect townSelect) {
		val wa = new BaseAddress();
		wa.setTownSelect(townSelect);
		return wa;
	}
	
	public static BaseAddress townSelect(final JSelect townSelect, final List<OptionDto> towns) {
		val wa = new BaseAddress();
		wa.setTownSelect(townSelect);
		wa.setTowns(towns);
		return wa;
	}
	
	public static BaseAddress towns(final List<OptionDto> towns) {
		val wa = new BaseAddress();
		wa.setTowns(towns);
		return wa;
	}
	
	public static BaseAddress colonySelect(final JSelect colonySelect) {
		val wa = new BaseAddress();
		wa.setColonySelect(colonySelect);
		return wa;
	}
	
	public static BaseAddress colonySelect(final JSelect colonySelect, final List<OptionDto> colonies) {
		val wa = new BaseAddress();
		wa.setColonySelect(colonySelect);
		wa.setColonies(colonies);
		return wa;
	}
	
	public static BaseAddress colonies(final List<OptionDto> colonies) {
		val wa = new BaseAddress();
		wa.setColonies(colonies);
		return wa;
	}
}