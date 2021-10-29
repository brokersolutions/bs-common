package bs.common.custom;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class CustomAddress implements Serializable {
	private static final long serialVersionUID = 2387737426230481721L;
	
	private String street;
	private String outsideNumber;
	private String insideNumber;
	private String colonyName;
	private String zipCode;
	private String town;
	private String state;
	private String reference;
	private String googleMapSearchUrl;
	private String address;
}