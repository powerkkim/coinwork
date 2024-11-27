package com.coinwork.base.acommon.util;


import com.coinwork.base.acommon.constants.Const;
import com.coinwork.base.acommon.security.vo.CustomUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Date;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.security.core.authority.AuthorityUtils.createAuthorityList;

@Slf4j
@Component
public class JwtTokenUtil implements Serializable {

    static final String CLAIM_KEY_USERNAME = "sub";
    static final String CLAIM_KEY_CREATED = "iat";
    private static final long serialVersionUID = -3301605591108950415L;

    private Clock clock = DefaultClock.INSTANCE;

    @Value("${api-server.jwt.secret:'12345'}")
    private String secret;

    @Value("${api-server.jwt.expiration:100}")
    private Long expiration;

    @Value("${api-server.jwt.refxpiration:1000}")
    private Long ref_expiration;

    @Value("${api-key:''}")
    public String API_KEY;


    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }


    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getIssuedAtDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);

        log.debug("******************* EXPIRATION : " + expiration);
        log.debug("******************* CLOCK : " + clock.now());
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    private Boolean ignoreTokenExpiration(String token) {
        // here you specify tokens, for that the expiration is ignored
        return false;
    }

//    public String generateToken(UserDetails userDetails) {
//        Map<String, Object> claims = new HashMap<>();
//        return doGenerateToken(claims, userDetails.getUsername());
//    }
//
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//        final Date createdDate = clock.now();
//        final Date expirationDate = calculateExpirationDate(createdDate);
//
//        log.info("********** Fecha  :" + new Date());
//        log.info("********** createDate clock now :" + createdDate);
//        log.info("********** EXPIRATIONDATE :" + expirationDate);
//
////        claims.put("name", "김병현");
//        return Jwts.builder()
//                .claim("role", authorities.stream().findFirst().get().toString())
//                .setSubject(subject)
//                .setIssuedAt(createdDate)
//                .setExpiration(expirationDate)
//                .signWith(SignatureAlgorithm.HS512, secret)
//                .compact();
//    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date created = getIssuedAtDateFromToken(token);
        return !isCreatedBeforeLastPasswordReset(created, lastPasswordReset)
                && (!isTokenExpired(token) || ignoreTokenExpiration(token));
    }

    // USER Modify
//    public String refreshToken(String token) {
//        final Date createdDate = clock.now();
//        final Date expirationDate = calculateExpirationDate(createdDate);
//
//        final Claims claims = getAllClaimsFromToken(token);
//        claims.setIssuedAt(createdDate);
//        claims.setExpiration(expirationDate);
//        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
//    }

    public String resolveToken(HttpServletRequest request) {
        String apiKey = request.getHeader("X-API-KEY");
        String headerValue = request.getHeader( Const.T_AUTHORIZATION );

        if( !API_KEY.equals( apiKey ) ) {
            log.error("NOT_FOUND API KEY:{}, Bearer Token:{}, requestPath:{}" , apiKey , headerValue, request.getRequestURL());
            return "";
        }

        if (  !StringUtils.hasLength(headerValue) || headerValue.startsWith( Const.T_BEARER ) == false ) {
            log.error("NOT_FOUND AUTHORIZATION KEY, Bearer Token:{}, requestPath:{}" , headerValue, request.getRequestURL());
            return "";
        }

        String authToken = headerValue.substring( Const.T_BEARER.length() );
        return authToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        CustomUserDetails user = (CustomUserDetails) userDetails;
        final String username = getUsernameFromToken(token);
        final Date created = getIssuedAtDateFromToken(token);
        // final Date expiration = getExpirationDateFromToken(token);

        return (username.equals(user.getUsername()) && !isTokenExpired(token)
                && !isCreatedBeforeLastPasswordReset(created, DateUtil.localDateTimeToDate( user.getLastPasswordResetDate() ) ));
    }

    private Date calculateExpirationDate(Date createdDate, Long expiration) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }

    public String getUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String genToken(UserDetails userDetails) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate, expiration);

        return Jwts.builder()
                .claim("role", userDetails.getAuthorities().stream().map(m-> m.getAuthority() ).collect(Collectors.joining(",") ) )
                .claim("userNo", ((CustomUserDetails)userDetails).getUser().getUserNo() )
                .setSubject(userDetails.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    // USER Modify
    public String genRefreshToken(UserDetails userDetails) {
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate, ref_expiration);

        return Jwts.builder()
                .claim("role", userDetails.getAuthorities().stream().map(m-> m.getAuthority() ).collect(Collectors.joining(",") ) )
                .claim("userNo", ((CustomUserDetails)userDetails).getUser().getUserNo() )
                .setSubject(userDetails.getUsername())
                .setIssuedAt(createdDate)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String[] getRoleFromToken(String token) {
        String roles = getClaimFromToken(token, (c->(String)c.get("role") ) );
        return roles.split(",");
    }

    public String getUserNoFromToken(String token) {
        String roles = getClaimFromToken(token, (c->(String)c.get("userNo") ) );
        return roles;
    }

    public boolean validateToken(String accessToken) {
        if ( !StringUtils.hasLength(accessToken ) ) {
            return false;
        }

        try {
            return getExpirationDateFromToken(accessToken).after(clock.now());
        }
        catch (Exception e) {
            return false;
        }
    }

    public Authentication getAuthentication(String accessToken) {
        return new UsernamePasswordAuthenticationToken(getUserInfo(accessToken), "", createAuthorityList(getRoleFromToken(accessToken)));
    }

    public String getUserInfo(String accessToken) {
        String userInfo = getUsername(accessToken)+"|"+getUserNoFromToken(accessToken);
        return userInfo;
    }

    public Optional<String> getAuthnticationUserEmail() {
        String userInfo = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userEmail = userInfo.split("\\|")[0];
        return Optional.ofNullable(userEmail);
    }

    public Optional<String> getAuthnticationUserNo() {
        String userInfo = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userNo = userInfo.split("\\|")[1];
        return Optional.ofNullable(userNo);
    }

}
