# Truncate integration test tables

Truncate tables before every integration test with Spring Boot and Kotlin, also being PostgresQL compatible.

This setup should be copy-paste ready and is inspired by: 

- https://dsebastien.medium.com/cleaning-up-database-tables-after-each-integration-test-method-with-spring-boot-2-and-kotlin-7279abcdd5cc
- https://stackoverflow.com/a/60010299

However, there are some differences with the listed article:

- no `REFERENTIAL_INTEGRITY` disabling is needed, as all tables are truncated at once with cascading. This makes the setup also Postgres compatible. (In case you do need to disable foreign key checks in Postgres for migrations: https://stackoverflow.com/a/49584660) 
- the code in the article only looks at a user defined name in the @Table annotation. The version in this repository:
  - looks at both the @Table annotation (which is an optional annotation) and @Entity annotation (which is always set on an entity)
  - favours a user defined name, but if not set will build a name based on the default naming strategy (snake_case class name)
- joins all table names together and executes one query in which they are all truncated together.

## Bonus: Hibernate Envers

This code can also clean Hibernate Envers if you provide the entity:
```kotlin
...

import org.hibernate.envers.RevisionEntity
import org.hibernate.envers.RevisionNumber
import org.hibernate.envers.RevisionTimestamp
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "revinfo")
@RevisionEntity
data class RevisionInfo(
    @Id
    @GeneratedValue
    @RevisionNumber
    val rev: Long,

    @RevisionTimestamp
    var revtstmp: Long,
)
```
