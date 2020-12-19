package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.BadRequestException
import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.Authority
import com.ieseuropa.spring.entity.User
import com.ieseuropa.spring.repository.UserRepository
import com.ieseuropa.spring.service.tool.Constants
import com.ieseuropa.spring.service.tool.EmailTool
import com.ieseuropa.spring.service.tool.ProfileTool
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
@Transactional
class UserService {

    @Autowired lateinit var userRepository: UserRepository
    @Autowired lateinit var authorityService: AuthorityService
    @Autowired lateinit var passwordEncoder: PasswordEncoder
    @Autowired lateinit var emailTool: EmailTool
    @Autowired lateinit var profileTool: ProfileTool
    @Autowired lateinit var oAuthService: OAuthService
    @Value("\${custom.username}") lateinit var username: String
    @Value("\${custom.password}") lateinit var password: String


    fun init() {
        if (userRepository.count() <= 0) {
            println("UserService init()")

            register(username, password, "Administrador", false)
            authorityService.relateUser(Authority.Role.ADMIN, 1)
        }
    }

    fun register(email: String, password: String, name: String, sendEmail: Boolean = true): User {
        if (!EmailTool.validate(email)) {
            throw BadRequestException("Email is not valid")
        }
        if (userRepository.existsByEmail(email)) {
            throw BadRequestException("Email already exists")
        }
        if (password.isBlank() || password.length < Constants.PASSWORD_MIN_SIZE) {
            throw BadRequestException("Password must be greater than 4 characters")
        }

        val user = User(
                email = email,
                password = passwordEncoder.encode(password),
                name = name
        )

        userRepository.save(user)

        return user
    }

    fun update(id: Long, request: User): User {
        val user = findById(id)
        request.name?.let { user.name = it }
        request.email?.let {
            oAuthService.logoutByUserId(id)
            user.email = it
        }

        return userRepository.save(user)
    }

    fun findById(id: Long): User {
        if (!userRepository.existsById(id)) {
            throw NotFoundException()
        }
        return userRepository.getOne(id)
    }

    fun findByEmail(email: String): User {
        if (!userRepository.existsByEmail(email)) {
            throw NotFoundException()
        }
        return userRepository.findByEmail(email)
    }

    fun findAll(): List<User> {
        return userRepository.findAll()
    }

    fun delete(id: Long) {
        if (!userRepository.existsById(id)) {
            throw NotFoundException()
        }
        userRepository.deleteById(id)
    }

}