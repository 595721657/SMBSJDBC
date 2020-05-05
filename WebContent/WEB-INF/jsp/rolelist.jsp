<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@include file="/WEB-INF/jsp/common/head.jsp"%>
        <div class="right">
            <div class="location">
                <strong>你现在所在的位置是:</strong>
                <span>角色管理页面</span>
            </div>
            <div class="search">
           		<form method="get" action="${ctx }/role/rolelist.html">
					<input name="method" value="query" class="input-text" type="hidden">
					 <span>角色编码：</span>
					 <input name="queryname" class="input-text"	type="text" value="${queryUserName }">					 
					 <span>用户角色：</span>
					 <select name="queryUserRole">
						<c:if test="${roleList != null }">
						   <option value="0">--请选择--</option>
						   <c:forEach var="role" items="${roleList}">
						   		<option <c:if test="${role.id == queryUserRole }">selected="selected"</c:if>
						   		value="${role.id}">${role.roleName}</option>
						   </c:forEach>
						</c:if>
	        		</select>
					 
					 <input type="hidden" name="pageIndex" value="1"/>
					 <input	value="查 询" type="submit" id="searchbutton">
					 <a href="${ctx}/user/adduser.html" >添加用户</a>
				</form>
            </div>
            <!--用户-->
            <table class="providerTable" cellpadding="0" cellspacing="0">
                <tr class="firstTr">
                    <th width="10%">角色id</th>
                    <th width="10%">角色编码</th>
                    <th width="20%">角色名称</th>
                    <th width="10%">创建id</th>
                    <th width="10%">创建时间</th>
                    <th width="30%">操作</th>
                </tr>
                   <c:forEach var="role" items="${roleList }" varStatus="status">
					<tr>
					    <td>${role.id }</td>
					    <td>${role.roleCode }</td>
					    <td>${role.roleName }</td>
					    <td>${role.createdBy }</td>
					    <td>${role.creationDate }</td>
						<td>
						<span><a class="viewRole" href="${ctx }/user/view.html?id=${role.id }"><img src="${pageContext.request.contextPath }/static/images/read.png" alt="查看" title="查看"/></a></span>
						<span><a class="modifyRole" href="${ctx }/user/updateuser.html?id=${role.id }"><img src="${pageContext.request.contextPath }/static/images/xiugai.png" alt="修改" title="修改"/></a></span>
						<span><a class="deleteRole" href="${ctx }/user/deluser.html?id=${role.id }"><img src="${pageContext.request.contextPath }/static/images/schu.png" alt="删除" title="删除"/></a></span>
						</td>
					</tr>
				</c:forEach>
			</table>
			<input type="hidden" id="totalPageCount" value="${totalPageCount}"/>
		  	<c:import url="rollpage.jsp">
	          	<c:param name="totalCount" value="${totalCount}"/>
	          	<c:param name="currentPageNo" value="${currentPageNo}"/>
	          	<c:param name="totalPageCount" value="${totalPageCount}"/>
          	</c:import>
        </div>
    </section>

<!--点击删除按钮后弹出的页面-->
<div class="zhezhao"></div>
<div class="remove" id="removeUse">
    <div class="removerChid">
        <h2>提示</h2>
        <div class="removeMain">
            <p>你确定要删除该用户吗？</p>
            <a href="#" id="yes">确定</a>
            <a href="#" id="no">取消</a>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsp/common/foot.jsp" %>
<script type="text/javascript" src="${ctx }/static/js/userlist.js"></script>
