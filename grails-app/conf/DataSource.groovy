dataSource {
	pooled = true
	driverClassName = "com.mysql.jdbc.Driver"
	url = "jdbc:mysql://localhost:3306/clickandgolf"
	dialect = org.hibernate.dialect.MySQL5InnoDBDialect
}
hibernate {
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.provider_class='org.hibernate.cache.EhCacheProvider'
//	cache.region.factory_class = 'net.sf.ehcache.hibernate.EhCacheRegionFactory'
}
// environment specific settings
environments {
	development {
		dataSource {
			pooled = true
			
//			dbCreate = "create-drop" // one of 'create', 'create-drop', 'update', 'validate', ''
			dbCreate = "update"
			
			username = "clickngolf_owner"
			password = "clickngolf_owner_pwd"

			logSql = true
		}
	}
	test {
		dataSource {
			// Usamos otra BD para probar todo!
			url = "jdbc:mysql://localhost:3306/clickandgolf-test"
			
			dbCreate = "update"
			username = "clickntest_user"
			password = "clickntest_user_pwd"

//			dbCreate = "create"
//			username = "clickntest_owner"
//			password = "clickntest_owner_pwd"

			properties {
				maxActive = 30
				maxIdle = 25
				minIdle = 5
				initialSize = 5
				minEvictableIdleTimeMillis = 60000
				timeBetweenEvictionRunsMillis = 60000
				maxWait = 10000
			}
		}
	}
	production {
		dataSource {
//			dbCreate = "create"
//			username = "clickngolf_owner"
//			password = "clickngolf_owner_pwd"
			
			// El create va unicamente la primera vez. Luego va el update SIEMPRE
			
			dbCreate = "update" // one of 'create', 'create-drop', 'update', 'validate', ''
			username = "clickngolf_user"
			password = "clickngolf_user_pwd"
//			username = "clickngolf_owner"
//			password = "clickngolf_owner_pwd"
			
			properties {
				maxActive = 30
				maxIdle = 25
				minIdle = 5
				initialSize = 5
				minEvictableIdleTimeMillis = 60000
				timeBetweenEvictionRunsMillis = 60000
				maxWait = 10000
			}
		}
	}
}
