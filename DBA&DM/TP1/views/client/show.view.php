<?php include 'views/inc/head.php';?>
<div class="container">

  <div class="main">
    <h1>List of clients</h1>
    <?php if(! empty($clients)):?>
    <div class="table-responsive">
      <table class="table table-hover text-left">
        <thead class="text-center">
          <tr>
            <th>ID</th>
            <th>Name</th>
          </tr>
        </thead>
        <tbody>
        <?php foreach ($clients as $client): ?>
          <tr>
            <td><?=$client->_id?></td>
            <td><?=$client->name?></td>
          </tr>
        <?php endforeach;?>
        </tbody>
      </table>
    </div>
    <?php endif;?>
  </div>

</div><!-- /.container -->
<?php include 'views/inc/footer.php';?>