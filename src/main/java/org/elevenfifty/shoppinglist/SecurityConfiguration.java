package org.elevenfifty.shoppinglist;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.encoding.PlaintextPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity//
				.authorizeRequests().antMatchers("/h2-console/**").permitAll()//
				.and()//
				.authorizeRequests().antMatchers("/console/**").permitAll()//
				.and()//
				.authorizeRequests().antMatchers("/", "/login", "/signup", "/error").permitAll()//
				.and()//
				.authorizeRequests().antMatchers("/lists/**", "/user/**").authenticated().anyRequest().permitAll()//
				.and()//
				.formLogin().loginPage("/login").usernameParameter("username").defaultSuccessUrl("/")
				.passwordParameter("password").permitAll()//
				.and()//
				.logout().logoutSuccessUrl("/login?logout").permitAll()//
				.and()//
				.csrf().disable().headers().frameOptions().disable();
	}

	@Autowired
	private DataSource dataSource;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource).rolePrefix("").passwordEncoder(new PlaintextPasswordEncoder())
				.usersByUsernameQuery(
						"select email as username, password, active as enabled from heroku_5e006be6efd68c2.users where email = ?")
				.authoritiesByUsernameQuery(
						"select email as username, role as authority from heroku_5e006be6efd68c2.users where email = ?");
	}

}
