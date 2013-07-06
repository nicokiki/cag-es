<!--[if IE]><!DOCTYPE html><![endif]-->
<html>
	<head>
	<%--		<canonical:show />--%>
		<g:layoutTitle />
		<r:layoutResources />
	</head>
	
	<body class="bodyPanel">
		<div id="spinner" class="spinner" style="display: none;">
			<img src="${resource(dir:'images',file:'spinner.gif')}"
				alt="Spinner" />
		</div>
	
		<section id="wrapPanel">
			<section class="wrap_blockPanel">
	
				<g:layoutBody />
	
			</section>
			<!-- End section .wrap_blockPanel -->
		</section>
		<!-- End section #wrapPanel -->
	
		<r:layoutResources />
	</body>
</html>