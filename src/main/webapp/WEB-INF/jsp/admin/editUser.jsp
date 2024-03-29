
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@include file="../include/admin/adminHeader.jsp"%>
<%@include file="../include/admin/adminNavigator.jsp"%>

<title>编辑用户</title>

<div class="workingArea">
    <ol class="breadcrumb">
        <li><a href="admin_user_list">用户管理</a></li>
        <li class="active">编辑用户</li>
    </ol>

    <div class="panel panel-warning editDiv">
        <div class="panel-heading">编辑用户名</div>
        <div class="panel-body">
            <form method="post" id="editForm" action="admin_user_update">
                <table class="editTable">
                    <tr>
                        <td>新的用户名</td>
                        <td><input id="name" name="name" value="${u.name}"
                                   type="text" class="form-control"></td>
                    </tr>
                    <tr class="submitTR">
                        <td colspan="2" align="center">
                            <input type="hidden" name="id" value="${u.id}">
                            <button type="submit" class="btn btn-success">提 交</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>