<%-- 
    Document   : search
    Created on : Jun 5, 2023, 5:11:22 PM
    Author     : tan18
--%>

<%@page import="registration.RegistrationDTO"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search</title>
        <link rel="stylesheet" href="css/search.css">
    </head>
    <body>
        <!--Login thành công khi cookie tồn tại-->
        <div class="container">
            <div class="search-part">
                <div class="search-part-label">
                    <div class="nav">
                        <div class="welcome">
                            Welcome "${sessionScope.USER.fullName}"
                        </div>
                        <jsp:include page="logout.jsp"/>
                    </div>
                </div>

                <div class="search-part-label">
                    <div class="search-part-content">
                        <form action="DispatchServlet">
                            <div class="form">
                                <div class="search-nav-input">
                                    <input type="text" name="searchName" value="${param.searchName}" placeholder="Search a name">
                                </div>
                                <div class="search-nav-btn">
                                    <input type="submit" name="btnAction" value="Search">
                                </div>
                            </div>
                        </form>
                        <div class="search-part-result">
                            <div class="result-div no-hover">
                                <span class="label no">No.</span>
                                <span class="label username">Username</span>
                                <span class="label password">Password</span>
                                <span class="label fullname">Fullname</span>
                                <span class="label role">Role</span>
                                <span class="label delete">Delete</span>
                                <span class="label update">Update</span>
                            </div>
                            <c:set var="searchValue" value="${param.searchName}"/>
                            <c:if test="${not empty searchValue}">
                                <c:set var="result" value="${requestScope.SEARCH_RESULT}"/>
                                <c:if test="${not empty result}">
                                    <c:forEach items="${result}" var="dto" varStatus="counter">
                                        <form action="DispatchServlet">
                                            <div class="result-div">
                                                <span class="label no">
                                                    ${counter.count}
                                                </span>
                                                <span class="label username">
                                                    ${dto.username}
                                                    <input type="hidden" name="txtUsername" value="${dto.username}" />
                                                </span>
                                                <span class="label password">
                                                    ${dto.password}
                                                    <input type="hidden" name="txtPassword" value="${dto.password}" />
                                                </span>
                                                <span class="label fullname">
                                                    ${dto.fullName}
                                                </span>
                                                <span class="label role">
                                                    <input type="checkbox" name="checkAd" value="ON"
                                                           <c:if test="${dto.role}">
                                                               checked = "checked"
                                                           </c:if>
                                                           />
                                                </span>
                                                <span class="label delete">
                                                    <c:url var="deleteLink" value="DispatchServlet">
                                                        <c:param name="btnAction" value="delete"/>
                                                        <c:param name="pk" value="${dto.username}"/>
                                                        <c:param name="lastSearchValue" value="${searchValue}"/>
                                                    </c:url>
                                                    <a href="${deleteLink}">Delete</a>
                                                </span>
                                                <span class="label update btn">
                                                    <input type="hidden" name="txtSearchValue" value="${searchValue}" />
                                                    <input type="submit" name="btnAction" value="Update" />
                                                </span>
                                            </div>
                                        </form>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${empty result}">
                                    <div class="message-popup" id="popup">
                                        Can not find !
                                    </div>
                                </c:if>
                            </c:if>
                            <c:if test="${empty searchValue}">
                                <div class="message-popup" id="popup">
                                    ${requestScope.ERROR}
                                </div>
                            </c:if>
                        </div>
                    </div>
                </div>
                <%--<%
                    Cookie[] cookies = request.getCookies();
                    if (cookies != null) {
                        Cookie newestCookie = cookies[cookies.length - 1];
                        String username = newestCookie.getName();
                %>
                <font color="red">Welcome <%= username %></font>
                <%
                    }
                %>
                <form action="DispatchServlet">
                    Search<input type="text" name="searchName" value="<%= request.getParameter("searchName")%>">
                    <input type="submit" name="btnAction" value="Search">
                </form>
                <br/>
                <%
                    String searchValue = request.getParameter("searchName");
                    if (searchValue != null) {
                        List<RegistrationDTO> result = (List<RegistrationDTO>) request.getAttribute("SEARCH_RESULT");
                        if (result != null) { //search found at least
                %>
                <table border="1">
                    <thead>
                        <tr>
                            <th>NO.</th>
                            <th>USERNAME</th>
                            <th>PASSWORD</th>
                            <th>FULLNAME</th>
                            <th>ROLE</th>
                            <th>DELETE</th>
                            <th>UPDATE</th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            int count = 0;
                            for (RegistrationDTO dto : result) {
                                String urlRewriting = "DispatchServlet"
                                        + "?btnAction=delete"
                                        + "&pk=" + dto.getUsername()
                                        + "&lastSearchValue=" + searchValue;
                        %>
                    <form action="DispatchServlet" method="POST">
                        <tr>
                            <td>
                                <%= ++count%>
                            </td>
                            <td>
                                <%= dto.getUsername()%>
                                <input type="hidden" name="txtUsername" value="<%= dto.getUsername()%>"/>
                            </td>
                            <td>
                                <input type="text" name="txtPassword" value="<%= dto.getPassword()%>"/>
                            </td>
                            <td>
                                <%= dto.getFullName()%>
                            </td>
                            <td>
                                <input type="checkbox" name="chkAdmin" value="ON"
                                       <%
                                           if (dto.isRole()) {
                                       %>
                                       checked ="checked"
                                       <%
                                           }//end user is Admin
                                       %>
                                       />
                            </td>
                            <td>
                                <a href="<%= urlRewriting%>">Delete</a>
                            </td>
                            <td>
                                <input type="hidden" name="txtSearchValue"
                                       value="<%= searchValue%>"/>
                                <input type="submit" value="update" name="btnAction"/>
                            </td>
                        </tr>
                    </form>
                    <%
                        }
                    %>
                </tbody>
            </table>

    <%
    } else { //search did not found
    %>
    <h2>No record is matched!!</h2>
    <%
            }
        }//end prevent access directly resource or first access
    %>--%>
                <c:set var="numPage" value="${requestScope.TOTAL_PAGE}"/>
                <c:if test="${not empty result}">
                    <div class="page-result">
                        <c:forEach var="counter" begin="1" end="${numPage}" step="1">
                            <c:url var="pageNumLink" value="DispatchServlet">
                                <c:param name="btnAction" value="Search"/>
                                <c:param name="searchName" value="${searchValue}"/>
                                <c:param name="pageNum" value="${counter}"/>
                            </c:url>
                            <div class="numBtn">
                                <a class="btnA" href="${pageNumLink}">${counter}</a>
                            </div>
                        </c:forEach>
                    </div>
                </c:if>
            </div>
        </div>
        <script>
            const messagePopup = document.getElementById('popup');
            messagePopup.classList.add('show');
            setTimeout(() => {
                messagePopup.classList.remove('show');
            }, 2000);
        </script>            
    </body>
</html>
