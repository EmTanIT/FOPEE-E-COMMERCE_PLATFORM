<%-- 
    Document   : viewcart
    Created on : Jun 19, 2023, 1:37:54 PM
    Author     : tan18
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.Map"%>
<%@page import="cart.CartObj"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="css/viewcart.css">
    </head>
    <body>
        <div class="header-cart">
            <div class="content-main-top">
                <div class="content-top">
                    <div class="top-left">
                        <div class="top-item">
                            <a href="">Kênh Người Bán</a>
                        </div>
                        <div class="top-item">
                            <a href="">Trở thành Người bán</a>
                        </div>
                        <div class="top-item">
                            <a href="">Tải ứng dụng</a>
                        </div>
                        <div class="top-item">
                            <a href="https://www.facebook.com/tomtran2913/">Kết nối</a>
                        </div>
                    </div>
                    <c:if test="${not empty sessionScope}">
                        <c:set var="user" value="${sessionScope.USER}"/>
                        <c:if test="${not empty user}">
                            <div class="top-right">
                                <div class="top-item">
                                    <a href="">${user.username}</a>
                                </div>
                                <div class="top-item">
                                    <c:url var="logOut" value="DispatchServlet">
                                        <c:param name="btnAction" value="Logout Account"/>
                                    </c:url>
                                    <a href="${logOut}">Thoát !</a>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${empty user}">
                            <div class="top-right">
                                <div class="top-item">
                                    <a href="createAccount.jsp">Đăng Ký</a>
                                </div>
                                <div class="top-item">
                                    <a href="login.jsp">Đăng Nhập</a>
                                </div>
                            </div>
                        </c:if>
                    </c:if>
                    <c:if test="${empty sessionScope}">
                        <div class="top-right">
                            <div class="top-item">
                                <a href="createAccount.jsp">Đăng Ký</a>
                            </div>
                            <div class="top-item">
                                <a href="login.jsp">Đăng Nhập</a>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
            <div class="content-main-bottom">
                <div class="content-bottom">
                    <div class="bottom-tiltle">
                        <a href="home.jsp">KhanhKT</a>

                    </div>
                    <div class="search-bar">
                        <form action="DispatchServlet">
                            <input class="input" type="text" name="searchProductValue" value="${param.searchProductValue}" placeholder="Em đã dành 1 tuần nghỉ hè để tu luyện đó thầy"/>
                            <input class="submit" type="submit" name="btnAction" value="S" />
                        </form>
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${not empty sessionScope}">
            <c:set var="cart" value="${sessionScope.CART}"/>
            <c:if test="${not empty cart}">
                <c:set var="items" value="${cart.items}"/>
                <c:if test="${not empty items}">
                    <%--<%
                        //1. Cust gose his/her cart place
                        //Mac dinh session la getSession false
                        if (session != null) {
                            //2. Cust takes his/her cart
                            CartObj cart = (CartObj) session.getAttribute("CART");
                            if (cart != null) {
                                //3. Cust gets items
                                Map<String, Integer> items = cart.getItems();
                                if (items != null) {
                                    //4. Cust picks all items up
                    %>--%>
                    <form action="DispatchServlet">
                        <div class="body-cart">
                            <div class="body-cart-content">
                                <div class="msg">
                                    <i class="fa-solid fa-cart-shopping fa-lg"></i>
                                    Kiểm tra lại danh sách sản phẩm trước khi đặt hàng bạn nhé !
                                </div>
                                <div class="cart-title">
                                    <div class="item-check cart-ele">
                                        <input type="checkbox" id="checkAll" onchange="checkAllitems()" />
                                    </div>
                                    <div class="item-name cart-ele">
                                        Sản Phẩm
                                    </div>
                                    <div class="item-price cart-ele grey-ele">
                                        Đơn Giá
                                    </div>
                                    <div class="item-quan cart-ele grey-ele">
                                        Số Lượng
                                    </div>
                                    <div class="item-price-minus cart-ele grey-ele">
                                        Số Tiền
                                    </div>
                                    <div class="item-remove cart-ele grey-ele">
                                        Thao Tác
                                    </div>
                                </div>
                                <c:forEach items="${items.keySet()}" var="pro">
                                    <div class="cart-items">
                                        <%--<%
                                            int count = 0;
                                            for (String key : items.keySet()) {
                                        }
                                        %>--%>
                                        <div class="seller">
                                            <div class="item-check cart-ele">
                                                <input type="checkbox" name="" value="" />
                                            </div>
                                            <div class="seller-name">
                                                <a href="">
                                                    <div class="love">Yêu Thích</div><span style="color: black">Tên Người Bán</span>
                                                </a>
                                            </div>
                                        </div>
                                        <div class="product">
                                            <div class="product-content">
                                                <div class="product-content-title">

                                                </div>
                                                <div class="product-content-ins">
                                                    <div class="item-ins">
                                                        <div class="item-ins-check">
                                                            <input class="checkItem" type="checkbox" name="chkOut" value="${pro.sku}" onchange="updateCount()"/>
                                                        </div>
                                                        <div class="item-ins-img">
                                                            <img src="images/sale-item.jpg"/>
                                                        </div>
                                                        <div class="item-ins-name">
                                                            ${pro.name}
                                                        </div>
                                                        <div class="item-ins-price">
                                                            <span class="in-discount">${pro.price-pro.price*0.25}</span><span>${pro.price}</span>
                                                        </div>
                                                        <div class="item-ins-quan">
                                                            <div class="quan-cart">                                                               
                                                                <div class="quan-action">
                                                                    <!--sua lai-->
                                                                    <c:url var="changeQuan" value="DispatchServlet">
                                                                        <c:param name="btnAction" value="Action with Quan"/>
                                                                        <c:param name="chkPro" value="${pro.sku}"/>
                                                                        <c:param name="action" value="-"/>
                                                                    </c:url>
                                                                    <a href="${changeQuan}" >-</a>
                                                                </div>
                                                                <input readonly="" id="currentQuan" type="number" name="chkQuan" value="${items.get(pro)}" onchange="activated()"/>
                                                                <c:url var="updateQuan" value="DispatchServlet">
                                                                    <c:param name="btnAction" value=""/>
                                                                    <c:param name="newQuan" value=""/>
                                                                </c:url>
                                                                <a href="${updateQuan}" id="hiddenLink" style="visibility: hidden"></a>
                                                                <div class="quan-action">
                                                                    <!--sua lai-->
                                                                    <c:url var="changeQuan" value="DispatchServlet">
                                                                        <c:param name="btnAction" value="Action with Quan"/>
                                                                        <c:param name="chkPro" value="${pro.sku}"/>
                                                                        <c:param name="action" value="+"/>
                                                                    </c:url>
                                                                    <a href="${changeQuan}">+</a>
                                                                </div>
                                                            </div>
                                                            <div class="quan-db">
                                                                Còn ${pro.quantity} sản phẩm
                                                            </div>
                                                        </div>
                                                        <div class="item-ins-price-real">
                                                            ${pro.price}
                                                        </div>
                                                        <div class="item-ins-remove">
                                                            <c:url var="delPro" value="DispatchServlet">
                                                                <c:param name="btnAction" value="Remove Selected Items"/>
                                                                <c:param name="chkItem" value="${pro.sku}"/>
                                                            </c:url>
                                                            <a href="${delPro}">
                                                                Xóa
                                                            </a>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="checkout">
                            <div class="checkout-content checkout-item">
                                <div id="numItem" class="checkout-tilte">

                                </div>
                                <div id="totalMoney" class="checkout-tilte">

                                </div>
                            </div>
                            <div class="checkout-btn checkout-item">
                                <input type="submit" name="btnAction" value="Check Out" />
                            </div>
                        </div>
                    </form>
                </c:if>
            </c:if>
            <c:if test="${empty cart}">
                <div class="body-cart-empty">
                    <div class="empty-content">
                        <div class="icon">
                            <i class="fa-regular fa-face-frown fa-2xl"></i>
                        </div>
                        <div class="empty-title">
                            Giỏ hàng của bạn còn trống
                        </div>
                        <div class="buyNow">
                            <a href="home.jsp">MUA NGAY</a>
                        </div>
                    </div>

                </div>
                <div class="checkout">
                    <div class="checkout-content checkout-item">
                        <div id="numItem" class="checkout-tilte">

                        </div>
                        <div id="totalMoney" class="checkout-tilte">

                        </div>
                    </div>
                    <div class="empty-checkout-btn">
                        <c:url var="checkOut" value="DispatchServlet">
                            <c:param name="btnAction" value="Check Out"/>
                        </c:url>
                        <a href="${checkOut}">Check Out</a>
                    </div>
                </div>
            </c:if>
        </c:if>
        <%--<%
                        return;
                    }
                }
            }
        %>--%>
        <script src="https://kit.fontawesome.com/cd2a035284.js" crossorigin="anonymous"></script>
        <script>
                                                                        function updateCount() {
                                                                            var checkItems = document.querySelectorAll(".checkItem");
                                                                            var count = 0;

                                                                            var checkboxes = document.getElementsByName("chkOut");
                                                                            var totalMoney = 0;

                                                                            for (var i = 0; i < checkItems.length; i++) {
                                                                                if (checkItems[i].checked) {
                                                                                    count++;
                                                                                }
                                                                            }

                                                                            for (var i = 0; i < checkboxes.length; i++) {
                                                                                if (checkboxes[i].checked) {
                                                                                    var quantity = parseInt(checkboxes[i].parentNode.parentNode.querySelector(".item-ins-quan input[name='chkQuan']").value);
                                                                                    var price = parseFloat(checkboxes[i].parentNode.parentNode.querySelector(".item-ins-price span:last-child").textContent);
                                                                                    var itemTotal = quantity * price;
                                                                                    totalMoney += itemTotal;
                                                                                }
                                                                            }

                                                                            var resultDiv = document.getElementById('numItem');
                                                                            resultDiv.textContent = "Số sản phẩm: " + count;

                                                                            var resultMoney = document.getElementById('totalMoney');
                                                                            resultMoney.textContent = "Số tiền phải trả: " + totalMoney + "đ";
                                                                        }

                                                                        function checkAllitems() {
                                                                            var isChecked = document.getElementById("checkAll").checked;

                                                                            var checkItems = document.querySelectorAll(".checkItem");
                                                                            for (var i = 0; i < checkItems.length; i++) {
                                                                                checkItems[i].checked = isChecked;
                                                                            }

                                                                            updateCount();
                                                                        }
                                                                        updateCount();

                                                                        function activated() {
                                                                            const input = document.getElementById('currentQuan');
                                                                            const hiddenLink = document.getElementById('hiddenLink');
                                                                        }
        </script>
    </body>
</html>
