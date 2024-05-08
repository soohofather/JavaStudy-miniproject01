# 프로젝트 목표

#### 1. **TMDB API - Movie List (Popular)에서 100 페이지까지 영화 정보 DB에 저장하기**

- 영화는 id, genre_id, original_title, title, release_date, poster_path, overview, title 까지 저장하기



#### 2. **TMDB API - Movie List (Now Playing)에서 100 페이지까지 영화 정보 DB에 저장하기**

- 1번과 동일하나 중복된 영화가 없어야 함.



#### 3. **영화 검색하기 (영화 리스트)**

- 검색어가 batman일 경우 아래와 같이 검색되어야한다.

``` sql
{
  [ 
    {
“영화ID” : “12312”,
 	“영화 제목" : “<영화 제목 1>”,
	“개봉 날짜" : “<개봉 날짜>”,
   }, 

{
“영화ID” : “12313”,
 	“영화 제목" : “<영화 제목 2>”,
	“개봉 날짜" : “<개봉 날짜>”,
   }, 

{
“영화ID” : “12314”,
 	“영화 제목" : “<영화 제목 3>”,
	“개봉 날짜" : “<개봉 날짜>”,
   }
	]
}

```



#### 4. **영화 상세 (영화 하나만 return)**

```sql
{
	“영화ID” : “12312”,
 	“영화 제목" : “<영화 제목>”,
  “장르" : [“스릴러", “액션"],
	“개봉 날짜" : “<개봉 날짜>”,
	“영화 포스터" : “포스터 이미지",
	“줄거리" : “영화 줄거리"
}

```



#### 5. **영화 리뷰 (작성, 삭제, 수정, 조회)**

- 리뷰에는 영화 제목 (유저 입력), 내용, 작성한 날짜/시간, 수정한 날짜/시간



#### 6. 회원가입, 로그인

- 유저 이름, 비밀번호, 권한 (외에 추가해도 됨)
- 영화 리뷰 작성, 삭제, 수정에 로그인한 유저에게만 권한을 준다.

---



# 기획

1. **TMDB API - Movie List (Popular)에서 100 페이지까지 영화 정보 DB에 저장하기**
   - 가져오는 DB를 저장하기 위해서 Database와 연동이 필요함
     - 아직 정확한 개념을 이해한건 아니지만, 기존 강의에서 사용했던 방식인 Docker로 MySQL과 연동 및 GUI형식인 MySQL 워크벤치를 이용하는 방법을 사용할 예정 
     - 그리고 데이터 크롤링해온다.
     - 가져온 데이터를 ID, 제목, 날짜가 보이게 리스트업 한다.
     - 가져온 리스트를 페이지네이션 한다.
2. **TMDB API - Movie List (Now Playing)에서 100 페이지까지 영화 정보 DB에 저장하기**
   - 데이터를 크롤링해온다.
   - 크롤링 해올때 기존 DB에 존재하는 영화인지를 확인하는 로직이 있어야 한다

3. **영화 검색하기 (영화 리스트)**
   - JPA Repository 기능 중 findBy(컬럼 이름)Containing 을 사용

4. **영화 상세 (영화 하나만 return)**
   - 영화 ID를 조회하면 해당 영화 ID값의 상세 정보를 볼 수 있도록 해야함.

5. **영화 리뷰 (작성, 삭제, 수정, 조회)**
   - 위 4번까지 완성 후 작성예정
6. **회원가입, 로그인**
   - 위 5번까지 완성 후 작성예정



### Genre 테이블

- 컬럼
  - id
    - PK
    - 자동생성 아님
    - Not Null
  - name
    - Not Null
- /genre
