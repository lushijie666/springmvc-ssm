<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp"%>
<body>
<div class="layui-layout layui-layout-admin">
    <div class="layui-header header">

        <div class="layui-logo" style="width: 270px;">
            <a class="logo" href="javascript:location.reload();">
                <img src="${ctx}/images/logo-top.png" alt="Lz_CMS">
            </a>
            <span style="padding-left: 90px;">后台管理系统</span>
        </div>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <ul class="layui-nav layui-nav-tree">
                <li class="layui-nav-item layui-nav-itemed">
                    <a class="" href="javascript:;">系统管理</a>
                    <dl class="layui-nav-child">
                        <dd>
                            <a href="${ctx}/user2.jsp" target="main">
                                <i class="layui-icon">&#xe658;</i>
                                <cite>用户管理</cite>
                            </a>
                        </dd>
                        <dd>
                            <a href="${ctx}/course2.jsp" target="main">
                                <i class="layui-icon">&#xe60f;</i>
                                <cite>课程管理</cite>
                            </a>
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="layui-body iframe-container">
        <div class="iframe-mask" id="iframe-mask"></div>
        <iframe class="admin-iframe" id="admin-iframe" name="main" src="${ctx}/user2.jsp"></iframe>
    </div>

</div>
</body>
</html>