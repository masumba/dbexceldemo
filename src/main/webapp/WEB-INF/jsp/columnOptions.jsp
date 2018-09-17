<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>


<div class="form-group">
<c:forEach var="list" items="${tblClmnLists}">

<c:set var = "salary" scope = "session" value = "${list.Key}"/>
<c:if test = "${salary != 'PRI'}">

    <div class="row">

        <div class="col-lg-5 col-md-5 col-sm-5">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon"><span>Excel</span></div>
                    <input class="form-control" name="ExcelValue" type="text">
                </div>
            </div>
        </div>
        <div class="col-lg-2 col-md-2 col-sm-2">
            <input type="checkbox" name="my-checkbox" id="my-checkbox${list.Field}" checked class="switch"><span>Use Data From This Feild</span>
            <input type="hidden" name="entryState" value="off" id="entryState${list.Field}">
             <script type="text/javascript">

               $('#my-checkbox${list.Field}').change(function(){
                   if(this.checked)
                       $('#entryState${list.Field}').val('on');
                  else
                       $('#entryState${list.Field}').val('off');
               });
             </script>
        </div>
        <div class="col-lg-5 col-md-5 col-sm-5">
            <div class="form-group">
                <div class="input-group">
                    <div class="input-group-addon"><span>XML</span></div>
                    <input class="form-control" name="columnName" value="${list.Field}" readonly type="text">
                </div>
            </div>
        </div>
    </div>

</c:if>

 </c:forEach>
</div>
<h2 class="text-center">Data Upload</h2>
    <div class="form-group"><input type="file" class="form-control" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" required=""></div>


 <script type="text/javascript">

   $('#my-checkbox').change(function(){
       if(this.checked)
           $('#entryState').val('on');
      else
           $('#entryState').val('off');
   });
 </script>