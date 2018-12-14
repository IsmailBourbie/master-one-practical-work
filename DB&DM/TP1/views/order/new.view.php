<?php include 'views/inc/head.php';?>
<div class="container">
<?php if (isset($_SESSION['msg'])): ?>
    <?=$_SESSION['msg']?>
    <?php unset($_SESSION['msg']);?>
<?php endif;?>

    <div class="main">
        <h1>Add new order</h1>
        <form action="order?page=new" method="post">
            <div class="input-group input-group-lg">
                <span class="input-group-addon" id="sizing-addon1">@</span>
                <input type="text" name="price" class="form-control" placeholder="Price" aria-describedby="sizing-addon1">
            </div>
            <div class="input-group input-group-lg">
                <span class="input-group-addon" id="sizing-addon1">@</span>
                <input type="text" name="city_start" class="form-control" placeholder="City start" aria-describedby="sizing-addon1">
            </div>
            <div class="input-group input-group-lg">
                <span class="input-group-addon" id="sizing-addon1">@</span>
                <input type="text" name="city_end" class="form-control" placeholder="City end" aria-describedby="sizing-addon1">
            </div>
            <div class="input-group input-group-lg">
                <span class="input-group-addon" id="sizing-addon1">@</span>
                <select name="societe" class="select_company">
                    <option value="">choose a company</option>
                    <?php if (!empty($companies)): ?>
                    <?php foreach ($companies as $company): ?>
                    <option value="<?=$company->_id?>"><?=$company->company_name?></option>
                    <?php endforeach;?>
                    <?php endif;?>
                </select>
            </div>
            <div class="input-group input-group-lg">
                <span class="input-group-addon" id="sizing-addon1">@</span>
                <select name="client" class="select_company">
                    <option value="">choose a client</option>
                    <?php if (!empty($clients)): ?>
                    <?php foreach ($clients as $client): ?>
                    <option value="<?=$client->_id?>"><?=$client->name?></option>
                    <?php endforeach;?>
                    <?php endif;?>
                </select>
            </div>
            <div class="input-group input-group-lg text-center btn_container">
                <input type="submit" name="submit" class="btn btn-success" value="Save" aria-describedby="sizing-addon1">
                <input type="reset" class="btn btn-danger" value="Annuler" aria-describedby="sizing-addon1">
            </div>
        </form>
    </div>
</div><!-- /.container -->
<?php include 'views/inc/footer.php';?>