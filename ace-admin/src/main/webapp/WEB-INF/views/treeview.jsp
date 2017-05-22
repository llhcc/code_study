<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<div class="main-content">
	<div class="breadcrumbs" id="breadcrumbs">
		<script type="text/javascript">
			try {
				ace.settings.check('breadcrumbs', 'fixed')
			} catch (e) {
			}
		</script>

		<ul class="breadcrumb">
			<li><i class="icon-home home-icon"></i> <a href="#">Home</a></li>

			<li><a href="#">UI Elements</a></li>
			<li class="active">Treeview</li>
		</ul>
		<!-- .breadcrumb -->

		<div class="nav-search" id="nav-search">
			<form class="form-search">
				<span class="input-icon"> <input type="text"
					placeholder="Search ..." class="nav-search-input"
					id="nav-search-input" autocomplete="off" /> <i
					class="icon-search nav-search-icon"></i>
				</span>
			</form>
		</div>
		<!-- #nav-search -->
	</div>

	<div class="page-content">
		<div class="page-header">
			<h1>
				Treeview <small> <i class="icon-double-angle-right"></i>
					with selectable items(single &amp; multiple) and custom icons
				</small>
			</h1>
		</div>
		<!-- /.page-header -->

		<div class="row">
			<div class="col-xs-12">
				<!-- PAGE CONTENT BEGINS -->

				<div class="row">
					<div class="col-sm-6">
						<div class="widget-box">
							<div class="widget-header header-color-blue2">
								<h4 class="lighter smaller">Choose Categories</h4>
							</div>

							<div class="widget-body">
								<div class="widget-main padding-8">
									<div id="tree1" class="tree"></div>
								</div>
							</div>
						</div>
					</div>

					<div class="col-sm-6">
						<div class="widget-box">
							<div class="widget-header header-color-green2">
								<h4 class="lighter smaller">Browse Files</h4>
							</div>

							<div class="widget-body">
								<div class="widget-main padding-8">
									<div id="tree2" class="tree"></div>
								</div>
							</div>
						</div>
					</div>
				</div>

				<script type="text/javascript">
					var $assets = "assets";//this will be used in fuelux.tree-sampledata.js
				</script>

				<!-- PAGE CONTENT ENDS -->
			</div>
			<!-- /.col -->
		</div>
		<!-- /.row -->
	</div>
	<!-- /.page-content -->
</div>
<!-- /.main-content -->




<!-- basic scripts -->

<!--[if !IE]> -->

<script type="text/javascript">
	window.jQuery
			|| document.write("<script src='assets/js/jquery-2.0.3.min.js'>"
					+ "<"+"/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='assets/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
</script>
<![endif]-->

<script type="text/javascript">
	if ("ontouchend" in document)
		document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"
				+ "<"+"/script>");
</script>
<script src="assets/js/bootstrap.min.js"></script>
<script src="assets/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->

<script src="assets/js/fuelux/data/fuelux.tree-sampledata.js"></script>
<script src="assets/js/fuelux/fuelux.tree.min.js"></script>

<!-- ace scripts -->

<script src="assets/js/ace-elements.min.js"></script>
<script src="assets/js/ace.min.js"></script>

<!-- inline scripts related to this page -->

<script type="text/javascript">
	jQuery(function($) {

		$('#tree1')
				.ace_tree(
						{
							dataSource : treeDataSource,
							multiSelect : true,
							loadingHTML : '<div class="tree-loading"><i class="icon-refresh icon-spin blue"></i></div>',
							'open-icon' : 'icon-minus',
							'close-icon' : 'icon-plus',
							'selectable' : true,
							'selected-icon' : 'icon-ok',
							'unselected-icon' : 'icon-remove'
						});

		$('#tree2')
				.ace_tree(
						{
							dataSource : treeDataSource2,
							loadingHTML : '<div class="tree-loading"><i class="icon-refresh icon-spin blue"></i></div>',
							'open-icon' : 'icon-folder-open',
							'close-icon' : 'icon-folder-close',
							'selectable' : false,
							'selected-icon' : null,
							'unselected-icon' : null
						});

		/**
		$('#tree1').on('loaded', function (evt, data) {
		});

		$('#tree1').on('opened', function (evt, data) {
		});

		$('#tree1').on('closed', function (evt, data) {
		});

		$('#tree1').on('selected', function (evt, data) {
		});
		 */
	});
</script>
