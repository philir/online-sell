<html>
     <#include "../common/header.ftl">
<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <div>
                        <h3><strong>商品修改</strong></h3>
                    </div>
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <div class="form-group">
                            <label >名称</label>
                            <input name="productName" value="${(productInfo.getProductName())!""}" type="text" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >价格</label>
                            <input name="productPrice" value="${(productInfo.getProductPrice())!""}" type="text" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >库存</label>
                            <input name="productStock" value="${(productInfo.getProductStock())!""}" type="number" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >描述</label>
                            <input name="productDescription" value="${(productInfo.getProductDescription())!""}" type="text" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >图片</label>
                            <img height="100" width="100" src="/sell/${(productInfo.getProductIcon())!""}" alt="">
                            <input name="productIcon" value="${(productInfo.getProductIcon())!""}" type="text" class="form-control" />
                            <#--<div class="file-loading">
                                <input id="input-id" type="file">
                                <p class="help-block">支持jpg、jpeg、png、gif格式，大小不超过1M</p>
                            </div>-->
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list productCategoryList as category>
                                    <option value="${category.getCategoryType()}"
                                             <#if (productInfo.getCategoryType())?? && productInfo.getCategoryType() == category.getCategoryType()>
                                                   selected
                                             </#if>
                                    >
                                        ${category.getCategoryName()}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        </div>
                        <input hidden type="text" name="productId" value="${(productInfo.getProductId())!""}">
                        <button type="submit" class="btn btn-default">提交</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>