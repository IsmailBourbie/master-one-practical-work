<?php include 'views/inc/head.php';?>
<div class="container">

<?php if (isset($_SESSION['msg'])): ?>
    <?=$_SESSION['msg']?>
    <?php unset($_SESSION['msg']);?>
<?php endif;?>

    <div class="main">
        <h1>Add new client</h1>
        <form action="client?page=new" method="post">
            <div class="input-group input-group-lg">
                <span class="input-group-addon" id="sizing-addon1">@</span>
                <input type="text" name="name" class="form-control" placeholder="Name" aria-describedby="sizing-addon1">
            </div>
        </form>
    </div>
</div><!-- /.container -->
<?php include 'views/inc/footer.php';?>