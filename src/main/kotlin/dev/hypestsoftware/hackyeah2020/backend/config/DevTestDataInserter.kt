package dev.hypestsoftware.hackyeah2020.backend.config

import dev.hypestsoftware.hackyeah2020.backend.model.Role
import dev.hypestsoftware.hackyeah2020.backend.model.RoleName
import dev.hypestsoftware.hackyeah2020.backend.model.User
import dev.hypestsoftware.hackyeah2020.backend.repository.RoleRepository
import dev.hypestsoftware.hackyeah2020.backend.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.sql.DataSource
import javax.transaction.Transactional

@Profile("development")
@Component
class DevTestDataInserter : ApplicationRunner {

    @Value("\${app.dev.database.insert-test-data}")
    var insertTestData: Boolean = true

    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var roleRepository: RoleRepository

    private val logger = LoggerFactory.getLogger(DevTestDataInserter::class.java)

    @Transactional
    override fun run(args: ApplicationArguments?) {
        if (insertTestData && !userRepository.findAll().any()) {
            logger.info("Inserting development test user data")
            // The encrypted password is `pass`
            val user = userRepository.save(
                User(
                    username = "user",
                    password = "{bcrypt}\$2a\$10\$cyf5NfobcruKQ8XGjUJkEegr9ZWFqaea6vjpXWEaSqTa2xL9wjgQC",
                    enabled = true
                )
            )

            val role = Role(name = RoleName.ROLE_ADMIN, user = user)
            roleRepository.save(role)
        } else {
            logger.info("Skipped inserting development test data because data already exists")
        }
    }
}
