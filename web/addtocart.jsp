<%-- 
    Document   : addtocart
    Created on : Jun 23, 2023, 12:51:21 PM
    Author     : tan18
--%>

<%@page import="java.util.List"%>
<%@page import="product.ProductDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/addtocart.css">
    </head>
    <body>

        <jsp:include page="nav.jsp"/>
        <form action="DispatchServlet">
            <div class="addtocart">
                <c:set var="sizeOfCate" value="${requestScope.SIZE}"/>
                <c:if test="${not empty sizeOfCate}">
                    <div class="items-result">
                        Kết quả tìm kiếm cho từ khóa "<span>${param.searchProductValue}</span>" ( ${sizeOfCate} kết quả )
                    </div>
                </c:if>
                <c:if test="${empty sizeOfCate}">
                    <div class="items-result">
                        Kết quả tìm kiếm cho từ khóa "<span>${param.searchProductValue}</span>" ( 0 kết quả )
                    </div>
                </c:if>
                <div class="addtocart-content">
                    <div class="suggest-today-items">
                        <c:set var="searchValue" value="${param.searchProductValue}"/>
                        <c:if test="${not empty searchValue}">
                            <c:set var="proList" value="${requestScope.SEARCH_RESULT}"/>
                            <c:if test="${not empty proList}">
                                <c:forEach items="${proList}" var="pro">
                                    <div class="today-item">
                                        <img src="images/sale-item.jpg"/>
                                        <div class="today-item-des">
                                            <div class="today-item-des-name">
                                                ${pro.name}
                                            </div>
                                            <div class="today-item-des-num">
                                                <div class="num-price">
                                                    ${pro.price}
                                                </div>
                                                <div class="num-quan">
                                                    Còn: ${pro.quantity}
                                                </div>
                                            </div>
                                            <c:url var="addItem" value="DispatchServlet">
                                                <c:param name="btnAction" value="Add Product to Your Cart"/>
                                                <c:param name="chkPro" value="${pro.sku}"/>
                                                <c:param name="seachPro" value="${searchValue}"/>
                                            </c:url>
                                            <div class="add">
                                                <a href="${addItem}">
                                                    Add to cart
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </div>
        </form>
        <c:set var="error_page" value="${requestScope.ERROR_ADDTOCARD}"/>
        <c:if test="${not empty error_page}">
            <div class="message-popup" id="popup">
                ${loginFirst}
            </div>
        </c:if>                
        <div class="block"></div>
        <jsp:include page="footer.jsp"/>
        <script>
            const messagePopup = document.getElementById('popup');
            messagePopup.classList.add('show');
            setTimeout(() => {
                messagePopup.classList.remove('show');
            }, 2000);
        </script>  
    </body>
</html>
