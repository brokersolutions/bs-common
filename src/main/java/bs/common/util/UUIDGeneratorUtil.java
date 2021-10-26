package bs.common.util;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import lombok.val;

public class UUIDGeneratorUtil implements Serializable {
	private static final long serialVersionUID = 3711380077855390041L;

	private UUIDGeneratorUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	private static final Random random = new SecureRandom();
	
	/**
	 * Type 1 UUID Generation
	 */
	public static UUID generateType1UUID() {

		long most64SigBits = get64MostSignificantBitsForVersion1();
		long least64SigBits = get64LeastSignificantBitsForVersion1();

		return new UUID(most64SigBits, least64SigBits);
	}

	private static long get64LeastSignificantBitsForVersion1() {
		val random63BitLong = random.nextLong() & 0x3FFFFFFFFFFFFFFFL;
		val variant3BitFlag = 0x8000000000000000L;
		return random63BitLong + variant3BitFlag;
	}

	private static long get64MostSignificantBitsForVersion1() {
		val start = LocalDateTime.of(1582, 10, 15, 0, 0, 0);
		val duration = Duration.between(start, LocalDateTime.now());
		val seconds = duration.getSeconds();
		val nanos = duration.getNano();
		val timeForUuidIn100Nanos = seconds * 10000000 + nanos * 100;
		val least12SignificantBitOfTime = (timeForUuidIn100Nanos & 0x000000000000FFFFL) >> 4;
		val version = 1 << 12;

		return (timeForUuidIn100Nanos & 0xFFFFFFFFFFFF0000L) + version + least12SignificantBitOfTime;
	}

	/**
	 * Type 3 UUID Generation
	 *
	 * @throws UnsupportedEncodingException
	 */
	public static UUID generateType3UUID(String namespace, String name) throws UnsupportedEncodingException {

		byte[] nameSpaceBytes = ValueUtil.bytesFromUUID(namespace);
		byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
		byte[] result = ValueUtil.joinBytes(nameSpaceBytes, nameBytes);

		return UUID.nameUUIDFromBytes(result);
	}

	/**
	 * Type 4 UUID Generation
	 */
	public static UUID generateType4UUID() {
		return UUID.randomUUID();
	}

	/**
	 * Unique Keys Generation Using Message Digest and Type 4 UUID
	 *
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String generateUniqueKeysWithUUIDAndMessageDigest()
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		val salt = MessageDigest.getInstance("SHA-256");
		salt.update(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));

		return ValueUtil.bytesToHex(salt.digest());
	}

	/**
	 * Type 5 UUID Generation
	 *
	 * @throws UnsupportedEncodingException
	 */
	public static UUID generateType5UUID(String namespace, String name) throws UnsupportedEncodingException {

		byte[] nameSpaceBytes = ValueUtil.bytesFromUUID(namespace);
		byte[] nameBytes = name.getBytes(StandardCharsets.UTF_8);
		byte[] result = ValueUtil.joinBytes(nameSpaceBytes, nameBytes);

		return type5UUIDFromBytes(result);
	}

	public static UUID type5UUIDFromBytes(byte[] name) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException nsae) {
			throw new InternalError("SHA-1 not supported", nsae);
		}
		byte[] bytes = Arrays.copyOfRange(md.digest(name), 0, 16);
		bytes[6] &= 0x0f; /* clear version */
		bytes[6] |= 0x50; /* set to version 5 */
		bytes[8] &= 0x3f; /* clear variant */
		bytes[8] |= 0x80; /* set to IETF variant */
		return constructType5UUID(bytes);
	}

	private static UUID constructType5UUID(byte[] data) {
		long msb = 0;
		long lsb = 0;
		assert data.length == 16 : "data must be 16 bytes in length";

		for (int i = 0; i < 8; i++)
			msb = (msb << 8) | (data[i] & 0xff);

		for (int i = 8; i < 16; i++)
			lsb = (lsb << 8) | (data[i] & 0xff);
		return new UUID(msb, lsb);
	}
}
