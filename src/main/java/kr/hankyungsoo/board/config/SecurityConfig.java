package kr.hankyungsoo.board.config;

import kr.hankyungsoo.board.dto.UserAccountDto;
import kr.hankyungsoo.board.dto.security.BoardPrincipal;
import kr.hankyungsoo.board.repository.UserAccountRepository;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.GET;

@Configuration
public class SecurityConfig  {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                        .mvcMatchers("/api/**").permitAll()
                        .mvcMatchers(GET,
                                "/",
                                "/articles",
                                "/articles/search-hashtag"
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin().and()
                .logout()
                    .logoutSuccessUrl("/")
                    .and()
                .csrf(csrf -> csrf.ignoringAntMatchers("/api/**"))
                .build();
    }

/*
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web
                        .ignoring()
                        .requestMatchers(PathRequest.toStaticResources().atCommonLocations());  //정적 리소스에 대한 인증 제외
    }
*/

    @Bean
    public UserDetailsService userDetailsService(UserAccountRepository userAccountRepository){
        return username -> userAccountRepository
                .findById(username)
                .map(UserAccountDto::from)
                .map(BoardPrincipal::from)
                .orElseThrow(()->new UsernameNotFoundException("유저를 찾을 수 없습니다 - username: "+ username));

    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
