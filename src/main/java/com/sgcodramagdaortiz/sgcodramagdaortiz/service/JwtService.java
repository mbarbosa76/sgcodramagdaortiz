package com.sgcodramagdaortiz.sgcodramagdaortiz.service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.sgcodramagdaortiz.sgcodramagdaortiz.model.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * ============================================================
 * JWTSERVICE
 * SISTEMA DE GESTIÓN DE CITAS ODONTOLÓGICAS
 * ============================================================
 *
 * Servicio encargado de:
 *
 * - Generar tokens JWT.
 * - Obtener el username almacenado en un token.
 * - Validar tokens JWT.
 * - Comprobar la fecha de expiración.
 */
@Service
public class JwtService {

    /**
     * Clave secreta utilizada para firmar los tokens JWT.
     *
     * El valor se obtiene desde application.properties.
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * Tiempo de duración del token en milisegundos.
     */
    @Value("${jwt.expiration}")
    private long expiration;


    /*
     * ============================================================
     * OBTENER CLAVE CRIPTOGRÁFICA
     * ============================================================
     */

    /**
     * Convierte la clave secreta en una clave criptográfica.
     *
     * @return clave utilizada para firmar y validar JWT.
     */
    private Key obtenerClave() {

        return Keys.hmacShaKeyFor(
            secret.getBytes(StandardCharsets.UTF_8)
        );

    }


    /*
     * ============================================================
     * GENERAR TOKEN
     * ============================================================
     */

    /**
     * Genera un token JWT utilizando directamente
     * el objeto Usuario.
     *
     * Este método mantiene compatibilidad con AuthService,
     * que actualmente utiliza:
     *
     * jwtService.generarToken(usuario)
     *
     * @param usuario usuario autenticado.
     * @return token JWT.
     */
    public String generarToken(Usuario usuario) {

        Date fechaActual = new Date();

        Date fechaExpiracion =
            new Date(
                fechaActual.getTime() + expiration
            );

        return Jwts.builder()

            /*
             * Username del usuario.
             */
            .setSubject(
                usuario.getUsername()
            )

            /*
             * Rol del usuario.
             */
            .claim(
                "rol",
                usuario.getRol()
            )

            /*
             * Fecha de creación.
             */
            .setIssuedAt(
                fechaActual
            )

            /*
             * Fecha de expiración.
             */
            .setExpiration(
                fechaExpiracion
            )

            /*
             * Firma del token.
             */
            .signWith(
                obtenerClave(),
                SignatureAlgorithm.HS256
            )

            .compact();

    }


    /*
     * ============================================================
     * OBTENER USERNAME
     * ============================================================
     */

    /**
     * Obtiene el username almacenado dentro del JWT.
     *
     * @param token token JWT.
     * @return username almacenado en el token.
     */
    public String obtenerUsername(String token) {

        Claims claims =
            obtenerClaims(token);

        return claims.getSubject();

    }


    /*
     * ============================================================
     * OBTENER INFORMACIÓN DEL TOKEN
     * ============================================================
     */

    /**
     * Lee y verifica los datos contenidos
     * dentro del token JWT.
     *
     * @param token token JWT.
     * @return información del token.
     */
    private Claims obtenerClaims(String token) {

        return Jwts.parser()

            /*
             * Utiliza la misma clave utilizada
             * para generar el token.
             */
            .setSigningKey(
                obtenerClave()
            )

            .build()

            .parseClaimsJws(token)

            .getPayload();

    }


    /*
     * ============================================================
     * VALIDAR TOKEN
     * ============================================================
     */

    /**
     * Comprueba que el token:
     *
     * - Sea válido.
     * - Tenga una firma correcta.
     * - Corresponda al usuario.
     * - No esté expirado.
     *
     * @param token token JWT.
     * @param username username esperado.
     * @return true si el token es válido.
     */
    public boolean validarToken(
            String token,
            String username) {

        try {

            String usernameToken =
                obtenerUsername(token);

            return usernameToken.equals(username)
                && !estaExpirado(token);

        } catch (Exception exception) {

            /*
             * Si el token tiene una firma incorrecta,
             * está alterado o presenta algún problema,
             * se considera inválido.
             */
            return false;

        }

    }


    /*
     * ============================================================
     * COMPROBAR EXPIRACIÓN
     * ============================================================
     */

    /**
     * Comprueba si el token ya expiró.
     *
     * @param token token JWT.
     * @return true si el token está expirado.
     */
    private boolean estaExpirado(String token) {

        Date fechaExpiracion =
            obtenerClaims(token)
                .getExpiration();

        return fechaExpiracion.before(
            new Date()
        );

    }

}