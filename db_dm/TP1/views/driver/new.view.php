<?php include 'views/inc/head.php';?>
<div class="container">

<?php if (isset($_SESSION['msg'])): ?>
    <?=$_SESSION['msg']?>
    <?php unset($_SESSION['msg']);?>
<?php endif;?>

    <div class="main">
        <h1>Add new Driver</h1>
        <form action="driver?page=new" method="post">
            <div class="input-group input-group-lg">
                <span class="input-group-addon" id="sizing-addon1">@</span>
                <input type="text" name="name" class="form-control" placeholder="Name" aria-describedby="sizing-addon1">
            </div>
            <div class="input-group input-group-lg">
                <span class="input-group-addon" id="sizing-addon1">@</span>
                <select name="societe" class="select_company">
                    <option value="">choose a company</option>
                    <?php foreach($companies as $company) : ?>
                    <option value="<?=$company->_id?>"><?=$company->company_name?></option>
                    <?php endforeach;?>
                </select>
            </div>
            <div class="input-group input-group-lg text-center">
                <input type="submit" name="submit" class="btn btn-success" value="Save" aria-describedby="sizing-addon1">
                <input type="reset" class="btn btn-danger" value="Annuler" aria-describedby="sizing-addon1">
            </div>
        </form>
    </div>
</div><!-- /.container -->
<?php include 'views/inc/footer.php';?>