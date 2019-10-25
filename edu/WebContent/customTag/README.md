# 커스텀 태그

>	## 커스텀 태그 란?
>
>	* 사용자가 작성한 태그이다.
>
>	* JSP에서 사용할 로직들을 커스텀 태그로 작성하여, 로직과 뷰를 분리할 수 있다. (가독성 향상 - JSP의 단점 보완)
>
>	* Back End / Front End 의 작업간 구분이 명확해 진다.
>
>	* 작성하는 태그 헨들러 클래스가 상속받는 클래스에 따라 ``JSP 버전``이 정해진다.
>
>		* ``JSP 1.2``버전 태그 : ``TagSupport`` 클래스 상속
>
>		* ``JSP 2.1``버전 태그 : ``SimpletagSupport`` 클래스 상속

---

>	## JSP 1.2버전 커스텀 태그 작성
>
>	1. 동작 Class파일 작성 : 커스텀 태그의 실제 동작을 Class파일로 작성 (각 단계별 메소드 재정의)
>
>		```java
>			public class MyTagHandler extends TagSupport {
>				// 시작태그를 만나면 호출되는 메소드
>				@Override
>				public int doStartTag() throws JspException {
>					// 처리 내용
>					return super.doStartTag(); // 기본값으로 흐름 설정
>				}
>
>				// 태그몸체의 처리가 완료되면 호출되는 메소드
>				@Override
>				public int doAfterBody() throws JspException {
>					// 처리내용
>					return super.doAfterBody(); // 기본값으로 흐름 설정
>				}
>
>				// 끝태그를 만나면 호출되는 메소드
>				@Override
>				public int doEndTag() throws JspException {
>					// 처리내용
>					return super.doEndTag(); // 기본값으로 흐름 설정
>				}
>			}
>		```
>
>	1. ``.tld``파일작성 : 작성한 Class파일을 태그에 설정
>
>	1. (선택사항) 태그의 유효성을 검사하려면 TEI 클래스를 작성하고 ``.tld``에 설정한다.
>
>	1. ``web.xml``설정 : ``.tld``파일(커스텀 태그 라이브러리)를 JSP에서 import할 수 있도록 설정
>
>	1. 사용할 JSP파일에서 ``<%@ taglib prefix="호출용 태그명 정의" uri="web.xml에 설정한 uri값" %>`` 설정

---

>	## 커스텀 태그의 동작 순서
>
>	1. JSP페이지에서 해당 태그를 만나면 ``doStartTag()``메소드가 호출된다.
>
>	1. ``doStartTag()``메소드가 종료되고, ``doAfterBody()``메소드가 호출된다.
>
>	1. ``doAfterBody()``메소드가 종료되고, ``doEndTag()``메소드가 호출된다.
>
>	* 위의 순서는 각 메소드의 반환값을 ``super.자신의 메소드명()``으로 설정된 기본 동작순서이다.
>
>	* 동작순서는 반환값에 따라 변경할 수 있다.
>
>	* 동작순서를 정하는 반환값 - **실행제어 값**
>
>	>	* ``EVAL_BODY_INCLUDE``
>	>
>	>		* 현재 메소드가 종료된 후, 태그의 몸체를 처리한다.
>	>
>	>	* ``SKIP_BODY``
>	>
>	>		* 현재 메소드가 종료된 후, 태그의 몸체에 대한 작업을 수행하지 않는다.
>	>
>	>	* EVAL_BODY_AGAIN
>	>
>	>		* 현재 메소드가 종료된 후, 태그의 몸체를 다시 처리한다.
>	>
>	>	* EVAL_PAGE
>	>
>	>		* 현재 메소드가 종료된 후, 태그를 호출한 JSP페이지를 계속 실행한다.
>	>
>	>	* SKIP_PAGE
>	>
>	>		* 현재 메소드가 종료된 후, 태그를 호출한 JSP페이지를 실행하지 않고 종료한다. (JSP페이지에서 이 태그 이후의 작업은 수행되지 않음) 	
>	>
>	>		```java
>	>			// 각 단계별 메소드의 반환값에 사용한다. (예 : ``doStartTag()``)
>	>			@Override
>	>			public int doStartTag() throws JspException {
>	>				// 처리내용
>	>				return TagSupport.EVAL_BODY_INCLUDE;
>	>			}
>	>		```

---

>	## 커스텀 태그의 속성
>
>	* 태그 헨들러 클래스에 ``속성``, ``getter메소드``, ``setter메소드``를 작성한다.
>
>	* ``.tld``파일에 ``<attribute>``태그로 속성을 정의한다. (태그 헨들러 클래스의 속성명과 동일해야 한다)
>
>	* 태그를 호출하면 ``doStartTag()``메소드가 실행되기 전에, 태그 헨들러 클래스의 ``setter``메소드가 호출되어 속성이 바인딩된다.
>
>	* ``.tld``파일에 설정할 ``<attribute>``태그의 하위 태그
>
>		* ``<name>`` : 속성명 설정
>
>		* ``<required>`` : 필수 여부 설정 (true / false)
>
>		* ``<rtexprvalue>`` : 속성의 동적처리 여부 설정 (true : JSP태그 나 EL표현식 사용가능 / false)

---

>	## 커스텀 태그 몸체
>
>	* ``<태그>몸체</태그>``의 몸체에 대한 설정이다
>
>	* ``.tld``파일에서 ``<taglib>``태그의 하위에 ``<body-content>``태그로 설정한다.
>
>		* ``<body-content>JSP</body-content>`` : 태그몸체를 JSP코드로 처리한다.
>
>		* ``<body-content>tagdependent</body-content>`` : 몸체의 내용을 자신의 기능으로 처리한다.
>
>		* ``<body-content>empty</body-content>`` : 몸체를 가질 수 없게 된다. (JSP에서 ``<태그/>``의 형식으로 사용하게 된다)

---

>	## TEI - Tag Extra Information
>
>	* ``TagExtraInfo``클래스를 상속받은 클래스 이다.
>
>		```java
>			public class TagTEI extends TagExtraInfo {
>				@Override
>				public boolean isValid(TagData data) {
>					String value = data.getAttributeString("속성명");
>					if(value != null) {
>						return true;
>
>					} else {
>						return false;
>					}
>				}
>			}
>		```
>
>	* ``isValid()``메소드를 재정의 하여, 태그 속성값의 유효성을 검사한다.
>
>	* TEI는 ``.tld``파일의 대상 태그에 설정한다.
>
>		```xml
>			<tag>
>				// ...
>				<tei-class>com.edu.customTag.TagTEI</tei-class>
>				// ...
>			</tag>
>		```

---

>	## 커스텀 태그 작성 예
>
>	* 태그 헨들러 클래스 작성 - **MyTagHandler.java**
>
>		```java
>			public class MyTagHandler extends TagSupport {
>				@Override
>				public int doStartTag() throws JspException {
>					System.out.println("시작 태그가 호출되었습니다");
>					return TagSupport.EVAL_BODY_INCLUDE;
>				}
>
>				@Override
>				public int doAfterBody() throws JspException {
>					System.out.println("몸체 처리가 끝났습니다");
>					return TagSupport.SKIP_BODY;
>				}
>
>				@Override
>				public int doEndTag() throws JspException {
>					System.out.println("끝 태그가 호출되었습니다");
>					return TagSupport.EVAL_PAGE;
>				}
>			}
>		```
>
>	* TEI 클래스 작성 - **TagTEI.java**
>
>		```java
>			public class TagTEI extends TagExtraInfo {
>				@Override
>				public boolean isValid(TagData data) {
>					String value = data.getAttributeString("value");
>					if(value != null) {
>						return true;
>					
>					} else {
>						return false;
>					}
>				}
>			}
>		```
>
>	* 커스텀 태그 라이브러리 작성 - **myTags.tld**
>
>		```xml
>			<taglib>
>				<taglib-version>1.0</taglib-version>
>				<jsp-version>1.2</jsp-version>
>				<short-name>태그 라이브러리명</short-name>
>
>				<tag>
>					<name>태그명</name>
>					<tag-class>com.edu.customTag.MyTagHandler</tag-class>
>					<tei-class>com.edu.customTag.TagTEI</tei-class>
>					<body-content>JSP</body-content>
>
>					<attribute>
>						<name>속성명</name>
>						<required>true</required>
>						<rtexprvalue>true</rtexprvalue>
>					</attribute>
>				</tag>
>			</taglib>
>		```
>
>	* ``web.xml`` 설정하기
>
>		```xml
>			<jsp-config>
>				<taglib>
>					<taglib-uri>이 태그를 사용할 임의의 이름</taglib-uri>
>					<taglib-location>/WEB-INF/myTag.tld</taglib-location>
>				</taglib>
>			</jsp-config>
>		```
>
>	* JSP 페이지에서 해당 태그 사용하기
>
>		```jsp
>			<%@ taglib prefix="이 JSP페이지에서 사용할 태그명" uri="web.xml에서 설정한 uri값 또는 절대경로" %>
>			<%@ page ... %>
>
>			<prefix에 설정한 이름:.tld의 <tag>에 설정한 <name>값>태그몸체</...>
>		```