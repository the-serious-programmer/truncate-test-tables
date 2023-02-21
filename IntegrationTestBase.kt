...

import org.apache.logging.log4j.LogManager
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

// You might not need all these annotations
@AutoConfigureMockMvc
@SpringBootTest(properties = ["spring.profiles.active=test"])
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class IntegrationTestBase {
    // Optional logging
    private val log = LogManager.getLogger()

    @Autowired
    private lateinit var databaseCleanupService: DatabaseCleanupService

    @BeforeEach
    fun cleanUp() {
        log.info("Cleaning up database before test.")
        databaseCleanupService.truncate()
        log.info("Completed database clean up.")
    }
}
