<?php include 'views/inc/head.php';?>
    <div class="container">

      <div class="main">
        <h1>List of Orders</h1>
        <?php if (!empty($orders)): ?>
          <div class="table-responsive">
            <table class="table table-hover text-left">
              <thead class="text-center">
                <tr>
                  <th>ID</th>
                  <th>Price</th>
                  <th>City start</th>
                  <th>City end</th>
                  <th>Clinet</th>
                  <th>Comapny</th>
                </tr>
              </thead>
              <tbody>
              <?php foreach ($orders as $order): ?>
                <tr>
                  <td><?=$order->_id?></td>
                  <td><?=$order->price?></td>
                  <td><?=$order->city_start?></td>
                  <td><?=$order->city_end?></td>
                  <td><?=$order->name?></td>
                  <td><?=$order->company_name?></td>
                </tr>
              <?php endforeach;?>
              </tbody>
            </table>
          </div>
        <?php endif;?>
      </div>

    </div><!-- /.container -->
<?php include 'views/inc/footer.php';?>