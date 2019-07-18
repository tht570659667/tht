
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<script>
</script>

<title>新闻管理</title>


<div class="workingArea">
    <h1 class="label label-info" >新闻管理</h1>

    <br>
    <br>


    <div class="listDataTableDiv">
        <table class="table table-striped table-bordered table-hover  table-condensed">
            <thead>
            <tr class="success">
                <th>ID</th>
                <th>新闻标题</th>
                <th>新闻内容</th>
                <th>创建时间</th>
                <th>编辑</th>
                <th>删除</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${ns}" var="n">
                <tr>
                    <td>${n.id}</td>
                    <td>${n.title}</td>
                    <td>${n.content}</td>
                    <td><fmt:formatDate value="${n.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                    <td><a href="admin_news_edit?id=${n.id}"><span class="glyphicon glyphicon-edit"></span></a></td>
                    <td><a deleteLink="true" href="/admin_news_delete?id=${u.id}"><span
                            class="glyphicon glyphicon-trash"></span></a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <div class="pageDiv">
        <%@include file="../include/admin/adminPage.jsp" %>
    </div>
    <div class="panel panel-warning addDiv">
        <div class="panel-heading">新增新闻</div>
        <div class="panel-body">
            <form method="post" id="addForm" action="admin_news_add" enctype="multipart/form-data">
                <table class="addTable">
                    <tr>
                        <td>新闻标题</td>
                        <td><input  id="title" name="title" type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>标题内容</td>
                        <td><input  id="content" name="content" type="text" class="form-control"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>

</div>

<%@include file="../include/admin/adminFooter.jsp"%>

