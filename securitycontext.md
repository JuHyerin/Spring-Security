---
description: 'SecurityContext 객체의 생성, 저장, 조회'
---

# SecurityContext



* 익명사용자
  * 새로운 SecurityContext객체를 생성하여 SecurityContextHolder에 저장
  * AnonymousAuthenticationFilter에서 AnonymousAuthenticationToken객체를 SecurityContext에 저장
* 인증시
  * 새로운 SecurityContext객체를 생성하여 SecurityContextHolder에 저장
  * **UsernamePasswordAuthenticationFilter**에서 인증 성공 후 SecurityContext에 **UsernamePasswordAuthentication**객체를 SecurityContext에 저장
  * 인증이 완료되면 Session에 SecurityContext를 저장
* 인증후
  * Session에서 SecurityContext를 꺼내 SecurityContextHolder에 저장
  * SecurityContext내 **Authentication**객체가 있으면 인증유지
* 최종응답
  * SecurityContextHolder.clearContext\(\) 수행됨

request

#### -&gt; **SecurityContextPersistenceFilter**

**--&gt; HttpSecurityContextRepository**

---&gt; **인증전인가?**

----&gt; **Yes**

------&gt; SecurityContextHolder\( SecurityContext, 인증객체 없음\)

               -&gt; 인증필터

                  -&gt; 인증후 인증객체 저장SecurityContetHolder\(SecurityContext, **Authentication**\)  
                   -&gt; chain.doFilter -&gt; **Session에 SecurityContext저장** -&gt; Response

----&gt; **No**

--------&gt; Session에서 SeurityContext 불러옴 -&gt; SecurityContextHolder\( SecurityContext \( Authentication\) -&gt; chain.doFilter  
  
출처: [https://fenderist.tistory.com/348](https://fenderist.tistory.com/348) \[Devman\]

