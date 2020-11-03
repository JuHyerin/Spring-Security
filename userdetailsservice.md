---
description: interface
---

# UserDetailsService

DB에서 유저 정보를 가져오는 역할을 한다.

UserDetailsService 인터페이스에서 DB에서 유저정보를 불러오는 중요한 메소드는 loadUserByUsername\(\) 메소드이다.

loadUserByUsername\(\)메소드에서 CustomUserDetails형으로 사용자의 정보를 받아오면 된다.

