# TrendinGifs (Wacha's Android 개발자 과제)

## 과제 설명
GIPHY API를 이용해서 GIPHY Application의 Trending GIFs 화면과 Favorites 화면
의 일부 기능셋을 구현하시면 됩니다.

* [GIPHY API Reference](https://developers.giphy.com/docs/api/)

## 필수 구현
1. Trending GIFs 목록 화면
    1. 목록 화면에서 각 아이템에 Favorite 버튼이 노출됨.
    2. Favorite 버튼은 해당 아이템의 Favorite 상태를 나타내며, 누르면 Toggle 됨.
2. Favorites 화면
    1. Favorite 된 아이템들의 목록
    2. Favorites된 목록은 앱을 다시 시작해도 유지되도록 구현

## 제약 사항
1. Target API 29 / Min API 19
2. 공개된 샘플 소스의 Copy code는 평가 대상에서 제외

** 오픈 소스 사용에 대한 제약은 없지만 사용하게 되면 사용한 이유를 작성해 주세요.

## 오픈 소스 사용 목록
1. Retrofit2
    - 네트워크 통신을 하기 위해 사용
    - 다른 네트워크 라이브러리를 사용하지 않은 이유: 일정 상 (1주일) 작업 효율이 높은 (사용해본) 라이브러리를 선택
2. RxJava3
    - 네트워크 통신시 비동기 처리를 해야하기 때문에 사용
    - Coroutine Flow 사용하지 않은 이유 : 아직 공부 중이라서 적용하기에는 시간이 좀 걸릴 거라고 판단함
    - AsynTask 를 사용하지 않은 이유 : 곧 Deprecated 되기 때문에
3. RxAndroid
    - 안드로이드 메인 스케줄러 (`AndroidScheduler.mainThread()`)를 사용하기 위해 
4. RxJavaRetrofitAdapter
    - Retrofit service 리턴 타입을 RxJava로 만들기 위해 
5. Gson Converter
    - api response 값을 parsing 하는 작업을 하는 시간을 줄이기 위해
6. Glide
    - Image Loading 을 위해 사용
7. Koin
    - Dependency Injection을 위임하기 위해 사용하기 위해 사용
