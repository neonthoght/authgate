package my.steam.authgate.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;
import org.springframework.session.web.http.CookieSerializer;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.MapSessionRepository;
import org.springframework.context.annotation.Bean;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

//@EnableJdbcHttpSession (tableName="auth.session") раскомментировать если работаю на линуксе, там есть эта таблица
@EnableJdbcHttpSession
@EnableSpringHttpSession
@Configuration
public class SessionConfig {

	/* 
	@Bean
	public MapSessionRepository sessionRepository() {
		return new MapSessionRepository(new ConcurrentHashMap<>());
	}
	*/


	@Bean
	public CookieSerializer cookieSerializer() {
		DefaultCookieSerializer serializer = new DefaultCookieSerializer();
		serializer.setCookieName("JSESSIONID"); 
		serializer.setCookiePath("/auth"); 
		serializer.setDomainNamePattern("^.+?\\.(\\w+\\.[a-z]+)$"); 
		return serializer;
	}
}

