<?php include 'views/inc/head.php';?>
<div class="container">

  <div class="main">
    <h1>List of Companies</h1>
    <?php if(! empty($companies)):?>
    <div class="table-responsive">
      <table class="table table-hover text-left">
        <thead class="text-center">
          <tr>
            <th>ID</th>
            <th>Name</th>
          </tr>
        </thead>
        <tbody>
        <?php foreach ($companies as $company): ?>
          <tr>
            <td><?=$company->_id?></td>
            <td><?=$company->company_name?></td>
          </tr>
        <?php endforeach;?>
        </tbody>
      </table>
    </div>
    <?php endif;?>
  </div>

</div><!-- /.container -->
<?php include 'views/inc/footer.php';?>