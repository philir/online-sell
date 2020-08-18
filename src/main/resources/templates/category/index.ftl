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
                        <h3><strong>类目新增</strong></h3>
                    </div>
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <div class="form-group">
                            <label >名字</label>
                            <input name="categoryName" value="${(productCategory.getCategoryName())!""}" type="text" class="form-control" />
                        </div>
                        <div class="form-group">
                            <label >type</label>
                            <input name="categoryType" value="${(productCategory.getCategoryType())!""}" type="number" class="form-control" />
                        </div>
                </div>
                <input hidden type="number" name="categoryId" value="${(productCategory.getCategoryId())!""}">
                <button type="submit" class="btn btn-default">提交</button>
                </form>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>