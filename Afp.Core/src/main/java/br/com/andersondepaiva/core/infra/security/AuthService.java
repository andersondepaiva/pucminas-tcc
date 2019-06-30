package br.com.andersondepaiva.core.infra.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Formatter;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.base.Strings;

@Service
public class AuthService {

	private Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2a?\\$\\d\\d\\$[./0-9A-Za-z]{53}");
	private final Log logger = LogFactory.getLog(getClass());

	private final int strength;

	private final SecureRandom random;

	public AuthService() {
		this(-1);
	}

	/**
	 * @param strength the log rounds to use, between 4 and 31
	 */
	public AuthService(int strength) {
		this(strength, null);
	}

	/**
	 * @param strength the log rounds to use, between 4 and 31
	 * @param random   the secure random instance to use
	 *
	 */
	public AuthService(int strength, SecureRandom random) {
		if (strength != -1 && (strength < BCrypt.MIN_LOG_ROUNDS || strength > BCrypt.MAX_LOG_ROUNDS)) {
			throw new IllegalArgumentException("Bad strength");
		}
		this.strength = strength;
		this.random = random;
	}

	public String encode(CharSequence rawPassword) {
		String salt;
		if (strength > 0) {
			if (random != null) {
				salt = BCrypt.gensalt(strength, random);
			} else {
				salt = BCrypt.gensalt(strength);
			}
		} else {
			salt = BCrypt.gensalt();
		}
		return BCrypt.hashpw(rawPassword.toString(), salt);
	}

	public DecodedJWT decode(String token) {
		try {
			DecodedJWT jwt = JWT.decode(token);
			return jwt;
		} catch (JWTDecodeException exception) {
			logger.error(exception);
			return null;
		}
	}

	public String getUserIdByToken(String token) {
		if (Strings.isNullOrEmpty(token)) {
			return null;
		}

		DecodedJWT jwt = this.decode(token.replace("Bearer ", ""));
		if (jwt == null) {
			return null;
		}

		Claim claim = jwt.getClaim("userId");

		if (claim == null) {
			return null;
		}

		return claim.asString();
	}

	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (encodedPassword == null || encodedPassword.length() == 0) {
			logger.warn("Empty encoded password");
			return false;
		}

		if (!BCRYPT_PATTERN.matcher(encodedPassword).matches()) {
			logger.warn("Encoded password does not look like BCrypt");
			return false;
		}

		return BCrypt.checkpw(rawPassword.toString(), encodedPassword);
	}

	public String encryptPassword(String password) {
		String sha1 = "";
		try {
			MessageDigest crypt = MessageDigest.getInstance("SHA-1");
			crypt.reset();
			crypt.update(password.getBytes("UTF-8"));
			sha1 = byteToHex(crypt.digest());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return sha1;
	}

	private String byteToHex(final byte[] hash) {
		Formatter formatter = new Formatter();
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		String result = formatter.toString();
		formatter.close();
		return result;
	}
}
