package ar.modularsoft.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ar.modularsoft.config.AppProperties;
import ar.modularsoft.model.Role;
import ar.modularsoft.dto.LocalUser;
import com.auth0.jwt.JWT;
import ar.modularsoft.model.User;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

import com.auth0.jwt.algorithms.Algorithm;

@Service
public class TokenProvider {

	private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);
	private static final String BEARER = "Bearer ";
	private static final String USER_CLAIM = "user";
	private static final String NAME_CLAIM = "name";
	private static final String ROLE_CLAIM = "role";


	private AppProperties appProperties;

	public TokenProvider(AppProperties appProperties) {
		this.appProperties = appProperties;
	}


	public String createToken(Authentication authentication) {
		LocalUser userPrincipal = (LocalUser) authentication.getPrincipal();
		User user = userPrincipal.getUser();
		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());
/*
return Jwts.builder().setSubject(Long.toString(userPrincipal.getUser().getId())).setIssuedAt(new Date()).setExpiration(expiryDate)
			.signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret()).compact();
 */
	/*
		for (ar.modularsoft.data.model.Role roleClaim:user.getRoles()
			 ) {
			if (!authorizedRoles(roleClaim).contains(user.getRole())) {
				throw new ForbiddenException("Insufficient role to create this user: " + user);
			}
		}
*/

		return JWT.create()
				.withIssuer(appProperties.getAuth().getTokenIssuer())
				.withIssuedAt(new Date())
				.withNotBefore(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + appProperties.getAuth().getTokenExpirationMsec() * 1000))
				.withClaim(USER_CLAIM, user.getEmail())
				.withClaim(NAME_CLAIM, user.getUsername())
				.withClaim(ROLE_CLAIM, "USER")
				.sign(Algorithm.HMAC256(appProperties.getAuth().getTokenSecret()));
		}

	public String createToken(User user) {

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getTokenExpirationMsec());

		return JWT.create()
				.withIssuer(appProperties.getAuth().getTokenIssuer())
				.withIssuedAt(new Date())
				.withNotBefore(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + appProperties.getAuth().getTokenExpirationMsec() * 1000))
				.withClaim(USER_CLAIM, user.getEmail())
				.withClaim(NAME_CLAIM, user.getUsername())
				.withClaim(ROLE_CLAIM, "USER")
				.sign(Algorithm.HMAC256(appProperties.getAuth().getTokenSecret()));
	}

	/*
    {
      "nbf": 1654045820,
      "role": "OPERATOR",
      "iss": "ar-modularsoft-ranti",
      "name": "Mono ATEEE",
      "exp": 1654049420,
      "iat": 1654045820,
      "user": "test"
    }
     */

	public String createRefreshToken(User user) {
		// LocalUser userPrincipal = (LocalUser) authentication.getPrincipal();
		// User user = userPrincipal.getUser();

		Date now = new Date();
		Date expiryDate = new Date(now.getTime() + appProperties.getAuth().getRefreshTokenExpirationMs());

		return JWT.create()
				.withIssuer(appProperties.getAuth().getTokenIssuer())
				.withIssuedAt(new Date())
				.withNotBefore(new Date())
				.withExpiresAt(new Date(System.currentTimeMillis() + appProperties.getAuth().getRefreshTokenExpirationMs() * 1000))
				.withClaim(USER_CLAIM, user.getEmail())
				.withClaim(NAME_CLAIM, user.getUsername())
				.withClaim(ROLE_CLAIM, "USER")
				.sign(Algorithm.HMAC256(appProperties.getAuth().getTokenSecret()));
	}


	public Long getUserIdFromToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token).getBody();

		return Long.parseLong(claims.getSubject());
	}

	public String getUserEmailFromToken(String token) {
		// Claims claims = Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(token).getBody();
//		System.err.println("TOKEN "+token);
//		DecodedJWT claimss = JWT.decode(token);
//		System.err.println(claimss.getPayload());
// return claimss.getPayload();
		return Optional.of(JWT.require(Algorithm.HMAC256(appProperties.getAuth().getTokenSecret()))
				.withIssuer(appProperties.getAuth().getTokenIssuer()).build()
				.verify(token)).map(jwt -> jwt.getClaim(USER_CLAIM).asString())
				.orElse("");


	}

	public boolean validateToken(String authToken) {
		try {
			// Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
			JWT.require(Algorithm.HMAC256(appProperties.getAuth().getTokenSecret()))
					.withIssuer(appProperties.getAuth().getTokenIssuer())
					.build()
					.verify(authToken);
			return true;
		} catch (SignatureException ex) {
			logger.error("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			logger.error("Invalid JWT token");
		} catch (ExpiredJwtException ex) {
			logger.error("Expired JWT token");
		} catch (TokenExpiredException ex) {
			logger.error("Expired JWT token");
		} catch (UnsupportedJwtException ex) {
			logger.error("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			logger.error("JWT claims string is empty.");
		}
		return false;
	}

	/*
	private List< Role > authorizedRoles(Role roleClaim) {
		if (Role.ROLE_ADMIN.equals(roleClaim)) {
			return List.of(Role.ROLE_ADMIN,  Role.ROLE_USER, Role.ROLE_MODERATOR);
		} else if (Role.USER.equals(roleClaim)) {
			return List.of(Role.ROLE_USER);
//		} else if (Role.OPERATOR.equals(roleClaim)) {
//			return List.of(Role.CUSTOMER);
		} else {
			return List.of();
		}
	}
	*/

/*
	private Optional<DecodedJWT> verify(String token) {
        try {
            return Optional.of(JWT.require(Algorithm.HMAC256(appProperties.getAuth().getTokenSecret()))
                    .withIssuer(appProperties.getAuth().getTokenIssuer()).build()
                    .verify(token));
        } catch (Exception exception) {
            return Optional.empty();
        }
    }

 */

}

