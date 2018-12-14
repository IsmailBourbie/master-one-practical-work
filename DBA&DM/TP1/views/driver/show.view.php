<?php include 'views/inc/head.php';?>
<div class="container">

  <div class="main">
    <h1>List of drivers</h1>
    <?php if(! empty($drivers)):?>
    <div class="table-responsive">
      <table class="table table-hover text-left">
        <thead class="text-center">
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Comapny</th>
          </tr>
        </thead>
        <tbody>
        <?php foreach ($drivers as $driver): ?>
          <tr>
            <td><?=$driver->_id?></td>
            <td><?=$driver->driver_name?></td>
            <td><?=$driver->company_name?></td>
          </tr>
        <?php endforeach;?>
        </tbody>
      </table>
    </div>
    <?php endif;?>
  </div>

</div><!-- /.container -->
<?php include 'views/inc/footer.php';?>