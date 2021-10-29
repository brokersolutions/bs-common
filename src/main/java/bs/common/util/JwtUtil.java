package bs.common.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.val;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * JWT generation Utility class.
 * </p>
 * <p>
 * It uses {@link SignatureAlgorithm.HS256} by default and if not specified,
 * defines 3600000 milliseconds for JWT validity. Be sure to provide a
 * large-strong secret key to avoid errors in JWT generation
 * </p>
 * 
 * @author Maikel Guerra Ferrer - mguerraferrer@gmail.com
 *
 */
@Slf4j
public class JwtUtil {
	private JwtUtil() {
		throw new IllegalStateException("Utility class");
	}
	
	/**
	 * Default JWT validity time
	 */
	public static final long JWT_TOKEN_VALIDITY = 3600000L;
	
	
	/**
	 * Default JWT signature algorithm
	 */
	public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;
	
	
	/**
	 * Internal use of the provided secret key
	 */
	private static String secret;
	
	public static Claims jwtClaims(String id, String issuer, String subject, String secretKey) {
		return jwtClaims(id, issuer, subject, secretKey, JWT_TOKEN_VALIDITY);
	}
	
	public static Claims jwtClaims(String id, String issuer, String subject, String secretKey, long ttlMillis) {
		val jwt = generateToken(id, issuer, subject, secretKey, ttlMillis);
		return decodeJWT(jwt);
	}
	
	public static String jwtClaims(UserDetails userDetails, String secretKey) {
		return jwtClaims(userDetails, secretKey, JWT_TOKEN_VALIDITY);
	}
	
	public static String jwtClaims(UserDetails userDetails, String secretKey, long ttlMillis) {
		return generateToken(userDetails, secretKey, ttlMillis);
	}
	
	public static String generateToken(String id, String issuer, String subject, String secretKey) {
		return generateToken(id, issuer, subject, secretKey, JWT_TOKEN_VALIDITY);
	}
	
	public static String generateToken(String id, String issuer, String subject, String secretKey, long ttlMillis) {
		try {
			
			if (secret == null) {
	        	secret = secretKey;
	        }
			
	        // We will sign our JWT with our ApiKey secret
	        byte[] apiKeySecretBytes = Base64Util.stringToByte(secret);
	        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName());

	        // Let's set the JWT Claims
	        val jwtBuilder = Jwts.builder()
	        					 .setId(id)
	        					 .setIssuedAt(issueAt())
	        					 .setSubject(subject)
	        					 .setIssuer(issuer)
	        					 .signWith(SIGNATURE_ALGORITHM, signingKey);

	        // If it has been specified, let's add the expiration
	        if (ttlMillis >= 0) {
	            jwtBuilder.setExpiration(expirationAt(ttlMillis));
	        }
	        
	        // Builds the JWT and serializes it to a compact, URL-safe string
	        return jwtBuilder.compact();
			
		} catch (Exception e) {
			log.error("JwtUtil#generateToken error {}", e.getMessage());
			secret = null;
		}
        return null;
    }
	
	public static String generateToken(UserDetails userDetails, String secretKey) {
		return generateToken(userDetails, secretKey, JWT_TOKEN_VALIDITY, new HashMap<String, Object>());
	}
	
	public static String generateToken(UserDetails userDetails, String secretKey, long ttlMillis) {
		return generateToken(userDetails, secretKey, ttlMillis, new HashMap<String, Object>());
	}
	
	public static String generateToken(UserDetails userDetails, String secretKey, Map<String, Object> claims) {
		return generateToken(userDetails, secretKey, JWT_TOKEN_VALIDITY, claims);
	}
	
	public static String generateToken(UserDetails userDetails, String secretKey, long ttlMillis, Map<String, Object> claims) {
		try {
			
			if (secret == null) {
				secret = secretKey;
			}
			
			// We will sign our JWT with our ApiKey secret
	        byte[] apiKeySecretBytes = Base64Util.stringToByte(secret);
	        Key signingKey = new SecretKeySpec(apiKeySecretBytes, SIGNATURE_ALGORITHM.getJcaName());
			
			return Jwts.builder()
					   		.setClaims(claims)
					   		.setSubject(userDetails.getUsername())
					   		.setIssuedAt(issueAt())
					   		.setExpiration(expirationAt(ttlMillis))
					   		.signWith(SIGNATURE_ALGORITHM, signingKey)
					   		.compact();
			
		} catch (Exception e) {
			log.error("JwtUtil#generateToken error {}", e.getMessage());
			secret = null;
		}
		return null;
	}
	
	public static String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	public static Date getIssuedAtDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getIssuedAt);
	}

	public static Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
    public static <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		val claims = decodeJWT(token);
		return claimsResolver.apply(claims);
	}
    
	public static Boolean canTokenBeRefreshed(String token) {
		return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	}

	public static Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
    
    public static Claims decodeJWT(String jwt) {
        // This line will throw an exception if it is not a signed JWS (as expected)
        return Jwts.parser()
                   .setSigningKey(Base64Util.stringToByte(secret))
                   .parseClaimsJws(jwt).getBody();
    }
    
    private static Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private static Boolean ignoreTokenExpiration(String token) {
		// here you specify tokens, for that the expiration is ignored
		return false;
	}
    
    private static Date issueAt() {
		return new Date(System.currentTimeMillis());
	}
	
	private static Date expirationAt(long ttlMillis) {
		long nowMillis = System.currentTimeMillis();
		long expMillis = nowMillis + ttlMillis;
		return new Date(expMillis);
	}
    
}