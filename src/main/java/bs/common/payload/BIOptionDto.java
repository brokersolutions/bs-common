package bs.common.payload;

import java.math.BigInteger;

public interface BIOptionDto {
	BigInteger getId();
	String getCode();
	String getValue();
	String getName();
}