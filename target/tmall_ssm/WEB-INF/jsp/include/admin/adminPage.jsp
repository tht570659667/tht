

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" isELIgnored="false"%>


 
<script>
$(function(){
    $("ul.pagination li.disabled a").click(function(){
        return false;
    });
});
 
</script>
 
<nav>
  <ul class="pagination">
    <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
      <a  href="?start=0${page.param}" aria-label="Previous" >
        <span aria-hidden="true">«</span>
      </a>
    </li>
 
    <li <c:if test="${!page.hasPreviouse}">class="disabled"</c:if>>
      <a  href="?start=${page.start-page.count}${page.param}" aria-label="Previous" >
        <span aria-hidden="true">‹</span>
      </a>
    </li>   

      <!--从1到页数开始循环，这里循环的是数字-->
    <c:forEach begin="0" end="${page.totalPage-1}" varStatus="status">
                <c:if test="${status.count*page.count-page.start<=20 && status.count*page.count-page.start>=-10}">
                    <li <c:if test="${status.index*page.count==page.start}">class="disabled"</c:if>>
	                <a href="?start=${status.index*page.count}${page.param}"
	                <c:if test="${status.index*page.count==page.start}">class="current"</c:if>
	                >${status.count}</a>
	            </li>
                </c:if>
    </c:forEach>
 
    <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
      <a href="?start=${page.start+page.count}${page.param}" aria-label="Next">
        <span aria-hidden="true">›</span>
      </a>
    </li>
    <li <c:if test="${!page.hasNext}">class="disabled"</c:if>>
      <a href="?start=${page.last}${page.param}" aria-label="Next">
        <span aria-hidden="true">»</span>
      </a>
    </li>
  </ul>
</nav>