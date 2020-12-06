package com.ieseuropa.spring.service

import com.ieseuropa.spring.config.exception.BadRequestException
import com.ieseuropa.spring.config.exception.NotFoundException
import com.ieseuropa.spring.entity.Authority
import com.ieseuropa.spring.entity.User
import com.ieseuropa.spring.repository.UserRepository
import com.ieseuropa.spring.service.tool.Constants
import com.ieseuropa.spring.service.tool.EmailTool
import com.ieseuropa.spring.service.tool.ProfileTool
import com.ieseuropa.spring.service.tool.StringTool
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

            register("superadmin@ieseuropa.com", password, "Superadmin", false)
            register("admin@ieseuropa.com", password, "Administrador", false)

            authorityService.relateUser(Authority.Role.SUPER_ADMIN, 1)
            authorityService.relateUser(Authority.Role.ADMIN, 2)

            activate(1, false)
            activate(2, false)
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
                name = name,
                active = false
        )

        userRepository.save(user)

        if (sendEmail) {
            emailTool.send(email, "Solicitud de registro", EmailTool.Type.REGISTER, mapOf())
        }

        return user
    }

    fun update(id: Long, request: User): User {
        val user = findById(id)
        request.name?.let { user.name = it }
        request.active?.let { user.active = it }
        request.email?.let {
            oAuthService.logoutByUserId(id)
            user.email = it
        }

        return userRepository.save(user)
    }

    fun activate(id: Long, sendEmail: Boolean = true): User {
        val user = findById(id)
        if (sendEmail) {
            emailTool.send(user.email!!, "Usuario activado", EmailTool.Type.ACTIVATE, mapOf())
        }
        user.active = true
        return userRepository.save(user)
    }

    fun deactivate(id: Long): User {
        val user = findById(id)
        emailTool.send(user.email!!, "Usuario desactivado", EmailTool.Type.DEACTIVATE, mapOf())
        user.active = false
        return userRepository.save(user)
    }

    fun existsById(id: Long): Boolean {
        return userRepository.existsById(id)
    }

    fun existsByEmailAndActiveTrue(email: String): Boolean {
        return userRepository.existsByEmailAndActiveTrue(email)
    }

    fun findById(id: Long): User {
        if (!userRepository.existsById(id)) {
            throw NotFoundException()
        }
        return userRepository.getOne(id)
    }

    fun findAllById(ids: List<Long>): List<User> {
        if (userRepository.countByIdIn(ids) != ids.size) {
            throw NotFoundException()
        }
        return userRepository.findAllById(ids)
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

    fun generatePassword(id: Long) {
        val user = findById(id)
        val password = StringTool.generatePasswordString()
        user.password = passwordEncoder.encode(password)
        userRepository.save(user)

        emailTool.send(user.email!!, "Nueva contraseña", EmailTool.Type.PASSWORD, mapOf("password" to password))
    }

    fun changePassword(id: Long, password: String, newPassword: String) {
        val user = findById(id)
        if (!passwordEncoder.matches(password, user.password)) {
            throw BadRequestException()
        }
        if (newPassword.isBlank() || newPassword.length < Constants.PASSWORD_MIN_SIZE) {
            throw BadRequestException()
        }
        user.password = passwordEncoder.encode(newPassword)
        userRepository.save(user)
    }

}