<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>MPS</title>
    <link rel="stylesheet" href="assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="assets/css/jquery.bootgrid.css">
    <link rel="stylesheet" href="assets/css/styles.css">
</head>

<body>
    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header"><a class="navbar-brand" href="#">Brand</a><button class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navcol-1"><span class="sr-only">Toggle navigation</span><span class="icon-bar"></span><span class="icon-bar"></span><span class="icon-bar"></span></button></div>
            <div
                class="collapse navbar-collapse" id="navcol-1">
                <ul class="nav navbar-nav"></ul>
        </div>
        </div>
    </nav>
    <div>
        <div class="container">
            <h3 class="text-center">${message}</h3>
            <div class="row">
                <div class="col-md-8">
                    <h2 class="text-center">Data Output</h2>
                    <div class="table-responsive">
                        <table class="table" id="target_data_output">
                            <thead>
                                <tr>
                                    <th data-column-id="id" data-type="numeric">ID</th>
                                    <th data-column-id="first_name">First Name</th>
                                    <th data-column-id="last_name">Last Name</th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="list" items="${lists}">
                                <tr>
                                    <td>${list.id}</td>
                                    <td>${list.name}</td>
                                    <td>${list.address}</td>
                                </tr>
                             </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="col-md-4">
                    <form method="post"  action="" enctype="multipart/form-data">
                        <h2 class="text-center">Data Upload</h2>
                        <div class="form-group"><input type="file" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet" name="file" required=""></div>
                        <div class="form-group"><button class="btn btn-default btn-block" type="submit">Submit</button></div>
                        <div class="form-group"><button class="btn btn-default btn-block" type="reset">Reset</button></div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/js/bootstrap.min.js"></script>
    <script src="assets/js/jquery.bootgrid.js"></script>
    <script src="assets/js/Bootgridlauncher.js"></script>
</body>

</html>