package com.ecommercedemo.userservice.controller

import com.ecommercedemo.security.JwtUtil
import com.ecommercedemo.userservice.dto.auth.AuthRequest
import com.ecommercedemo.userservice.dto.auth.AuthResponse
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController {

    @PostMapping("/login")
    fun login(
        @RequestBody authRequest: AuthRequest,
        response: HttpServletResponse
    ): AuthResponse {
        val username = authRequest.username
        val accessToken = JwtUtil.generateToken(username, 15 * 60 * 1000) // 15 minutes
        val refreshToken = JwtUtil.generateToken(username, 7 * 24 * 60 * 60 * 1000) // 7 days

        // Set refresh token as HTTP-only cookie
        response.addCookie(Cookie("refreshToken", refreshToken).apply {
            isHttpOnly = true
            maxAge = 7 * 24 * 60 * 60 // 7 days
        })

        return AuthResponse(accessToken)
    }

    @PostMapping("/refresh")
    fun refresh(
        @CookieValue("refreshToken") refreshToken: String,
        response: HttpServletResponse
    ): AuthResponse {
        if (JwtUtil.validateToken(refreshToken)) {
            val username = JwtUtil.getUsernameFromToken(refreshToken)
            val newAccessToken = JwtUtil.generateToken(username, 15 * 60 * 1000) // 15 minutes
            return AuthResponse(newAccessToken)
        } else {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            return AuthResponse("")
        }
    }
}