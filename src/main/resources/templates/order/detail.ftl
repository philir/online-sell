<html>
<#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">
<#--边栏sidebar-->
<#include "../common/nav.ftl">

<div id="page-content-wrapper">
<div class="container-fluid">
    <div class="row clearfix">
        <div class="col-md-4 column">
            <div>
                <h4><strong>订单人：${orderDTO.getBuyerName()}</strong></h4>
            </div>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>订单Id</th>
                    <th>订单总额</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${orderDTO.getOrderId()}</td>
                    <td>${orderDTO.getOrderAmount()}</td>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 column">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>商品Id</th>
                    <th>商品名称</th>
                    <th>价格</th>
                    <th>数量</th>
                    <th>总额</th>
                </tr>
                </thead>
                <tbody>
                <#--订单详情-->
                <#list orderDTO.getOrderDetailList() as orderDetail>
                <tr>
                    <td>${orderDetail.productId}</td>
                    <td>${orderDetail.getProductName()}</td>
                    <td>${orderDetail.getProductPrice()}</td>
                    <td>${orderDetail.getProductQuantity()}</td>
                    <td>${orderDetail.getProductQuantity()*orderDetail.getProductPrice()}</td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <#--操作-->
        <div class="col-md-12 column">
            <#if orderDTO.getOrderStatusEnum().getMessage() == "新订单">
                <a href="/sell/seller/order/accept?orderId=${orderDTO.getOrderId()}&number=${number}" type="button" class="btn btn-default btn-success">接收订单</a>
            </#if>
            <#if orderDTO.getOrderStatusEnum().getMessage() == "新订单" || orderDTO.getOrderStatusEnum().message == "已接单">
            <a href="/sell/seller/order/finish?orderId=${orderDTO.getOrderId()}&number=${number}" type="button" class="btn btn-default btn-primary">完结订单</a>
            <a href="/sell/seller/order/cancel?orderId=${orderDTO.getOrderId()}&number=${number}" type="button" class="btn btn-default btn-danger">取消订单</a>
            </#if>
        </div>

    </div>
</div>
</div>
</div>
</body>
</html>