

<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
function showProductsAsideCategorys(cid){
	$("div.eachCategory[cid="+cid+"]").css("background-color","white");
	$("div.eachCategory[cid="+cid+"] a").css("color","#87CEFA");
	$("div.productsAsideCategorys[cid="+cid+"]").show();
}


function hideProductsAsideCategorys(cid){
	$("div.eachCategory[cid="+cid+"]").css("background-color","#e2e2e3");
	$("div.eachCategory[cid="+cid+"] a").css("color","#000");
	$("div.productsAsideCategorys[cid="+cid+"]").hide();
}
$(function(){
    $("div.eachCategory").mouseenter(function(){
        var cid = $(this).attr("cid");
        showProductsAsideCategorys(cid);
    });
    $("div.eachCategory").mouseleave(function(){
        var cid = $(this).attr("cid");
        hideProductsAsideCategorys(cid);
    });
    $("div.productsAsideCategorys").mouseenter(function(){
    	var cid = $(this).attr("cid");
    	showProductsAsideCategorys(cid);
    });
    $("div.productsAsideCategorys").mouseleave(function(){
    	var cid = $(this).attr("cid");
    	hideProductsAsideCategorys(cid);
    });
	
	$("div.rightMenu span").mouseenter(function(){
		var left = $(this).position().left;
		var top = $(this).position().top;
		var width = $(this).css("width");
		var destLeft = parseInt(left) + parseInt(width)/2;
		$("img#catear").css("left",destLeft);
		$("img#catear").css("top",top-20);
		$("img#catear").fadeIn(500);
				
	});
	$("div.rightMenu span").mouseleave(function(){
		$("img#catear").hide();
	});
	
	var left = $("div#carousel-of-product").offset().left;
	$("div.categoryMenu").css("left",left-20);
	$("div.categoryWithCarousel div.head").css("margin-left",left);
	$("div.productsAsideCategorys").css("left",left-20);
	
	
});
</script>


<img src="img/site/catear.png" id="catear" class="catear"/>
	
<div class="categoryWithCarousel">

	<style type="text/css">
		.hua_list {
			width: 235px;
			height: 275px;
			border: 1px solid #e6dede;
			float: left;
			margin-left: 0px;
			overflow: hidden;
			background: #fff6f6;
			margin-top: 36px;
		}
		.t1 {
			height: 22px;
			color: #999;
			border: 1px solid #333;
			position: relative;
			font-weight: normal;
			width:100%;
		}
		.t1 span{
			font-size: 14px;
			color: #999;
			font-weight: normal;
			padding: 2px;
			margin-left: 100px;
		}
		.text_list {
			width: 235px;
			height: 253px;
			overflow: hidden;
		}
		.text_list  ul li {
			list-style: none;
			line-height: 27px;
			height: 27px;
			font-size: 12px;
			margin-left: -15px;
			overflow: hidden;
		}
	</style>
	<div class="hua_list" style="position: relative;left: 0;top: 0;">
		<h6 class="t1"><span>公告</span></h6>
		<div class="text_list" id="div_bleach_animation">
			<ul id="ul_bleach_animation" >
				<c:forEach items="${ns}" var="n">
				<li><a href="forenews?id=${n.id}">${n.title}</a></li>
				</c:forEach>
			</ul>
		</div>
	</div>
<div class="headbar show1">
	<div class="head ">
	
		<span style="margin-left:10px" class="glyphicon glyphicon-th-list"></span>
		<span style="margin-left:10px" >商品分类</span>
		
	</div>
	
	<div class="rightMenu">
		<span><a href=""><img src="img/site/chaoshi.png"/></a></span>
		<span><a href=""><img src="img/site/guoji.png"/></a></span>

		<c:forEach items="${cs}" var="c" varStatus="st">
			<c:if test="${st.count<=4}">
				<span>
				<a href="forecategory?cid=${c.id}">
					${c.name}
				</a></span>			
			</c:if>
		</c:forEach>
	</div>
	
</div>

	
<div style="position: relative">
	<%@include file="categoryMenu.jsp" %>
</div>

<div style="position: relative;left: 0;top: 0;">
	<%@include file="productsAsideCategorys.jsp" %>
</div>

<%@include file="carousel.jsp" %>

<div class="carouselBackgroundDiv">
</div>

</div>





