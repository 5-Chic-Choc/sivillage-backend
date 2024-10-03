# 🍪 칙칙한 초코칩 팀(Chic-Choc)

🙋‍♀️ 신세계 I&C 와 부산광역시가 주관하는 스파로스 아카데미 5기, 1차 리빌딩 프로젝트 입니다. [스파로스 아카데미(Spharos Academy)](https://swedu.spharosacademy.com/spharos_total.html)

저희 팀의 주제는 대용량 데이터를 고려한 설계를 바탕으로 SI-VILLAGE를 리빌딩하기였습니다.

⏰활동기간(Period) 2024/08/06 ~ 2024/10/01

## 팀원 소개

### [최번영](https://github.com/GwendolyNM)

- 팀장 (Team Leader)
- FrontEnd
  - 장바구니 페이지
  - 카테고리 페이지
  - 브랜드 페이지

### [백승준](https://github.com/flskhhdf)

- BackEnd
  - 장바구니(회원/비회원)
  - 리뷰, 주문, 배송지
- DevOps
  - AWS EC2
  - Nginx(DoH, Reverse proxy)
  - CI/CD (Back)
    - Github Actions
    - Docker / Docker Compose
      - Prometheus, Grapana, Jmeter / mysql, redis 설정
    - 부하테스트 진행

### [이재훈](https://github.com/JaeHunLee-git)

- BackEnd
  - 상품 필터링 및 Pagenation 구현(Query DSL)
  - Best 100 상품(Spring Scheduler)
  - 최근 본 상품, 최근 검색어(Redis)
  - 프로모션(필터링), 브랜드, 카테고리, 프로모션, 검색(제품/해시태그/브랜드)
  - 성능 최적화 및 테스트

### [주성광](https://github.com/SeongGwangJu)

- BackEnd
  - Auth(Spring Security, JWT, SMTP, oAuth2)
  - ID / PW찾기, 약관, 찜하기
  - 스크래핑 데이터 가공/매핑/저장
  - 프로모션 / 카테고리 데이터 스크래핑[(Repo)](https://github.com/5-Chic-Choc/sivillage-scraping)
  - 프로젝트 및 협업도구 설정, 일정 및 문서 관리

### [홍정현](https://github.com/oror-sine)

- FrontEnd
  - 메인페이지, 제품, 프로모션,
  - Auth(NextAuth, oAuth2(카카오/네이버))
  - 공통 레이아웃 및 form 컴포넌트
  - 상품 / 브랜드 / 카테고리 데이터 스크래핑[(Repo)](https://github.com/5-Chic-Choc/sivillage-scraping)
  - 배포(Vercel), middleware 설정, 프로젝트 및 협업도구 설정

## 프로젝트 산출물

### 이벤트 스토밍

<img width="1506" alt="image" src="https://github.com/user-attachments/assets/96d4e967-0528-44ee-a57d-4a13ec45c24d">

### 아키텍처

![image](https://github.com/user-attachments/assets/11060886-20f2-44a8-9a56-404a48f5d06e)

### ERD

[ERD cloud](https://www.erdcloud.com/d/4JTP48haeZthXnHgu)

### 기술스택

![image](./source/image/기술스택.PNG)

### 페이지

<table>
<tr>
<td><br><img src="./source/pagegif/main.gif" alt="main" width="300"/></td>
<td><img src="./source/pagegif/event.gif" alt="event" width="300"/></td>
<td><img src="./source/pagegif/best.gif" alt="best" width="300"/></td>
</tr>
<tr>
<td><img src="./source/pagegif/category.gif" alt="main" width="300"/></td>
<td><img src="./source/pagegif/brand.gif" alt="event" width="300"/></td>
<td><img src="./source/pagegif/signin_signout.gif" alt="best" width="300"/></td>
</tr>
<tr>
<td><img src="./source/pagegif/filter.gif" alt="main" width="300"/></td>
<td><img src="./source/pagegif/hash.gif" alt="event" width="300"/></td>
<td><img src="./source/pagegif/cart.gif" alt="best" width="300"/></td>
</tr>
</table>
