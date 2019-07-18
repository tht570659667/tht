
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>编辑用户</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_news_list">新闻管理</a></li>
        <li class="active">新闻管理</li>
    </ol>

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">新闻编辑</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_news_update">
                <table class="editTable">
                    <tr>
                        <td>新的标题</td>
                        <td><input id="title" name="title" value="${n.title}"
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr>
                        <td>新的内容</td>
                        <td><input id="content" name="content" value="${n.content}"
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" value="${n.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>